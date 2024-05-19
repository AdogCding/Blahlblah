# PostgreSQL使用CTE如何防止死循环

小李在平时的工作中用的数据库是Oracle，但是今天他打算用Postgres写写自己的项目。
现在他遇到一个问题，当他想要用with-clause在这样一个类似链表的表格时

|my_id | my_next_id |
|------|------------|
| 1 | 2 |
| 2 | 3 |
| 3 | 4 |

查询语句一般写成这样

```sql
with recursive traverse_linked_list(id, next_id, cnt) as (
    select my_id, my_next_id, 1 from linked_list where my_id = '1'
                                     union all
                                     select b.my_id, b.my_next_id, a.cnt + 1 from traverse_linked_list a, linked_list b where b.my_id = a.next_id
)
```

当加入了一个循环引用后

```sql
insert into linked_list (my_id, my_next_id) select '4', '1';
```

|my_id | my_next_id |
|-|-|
| 1 | 2 |
| 2 | 3 |
| 3 | 4 |
| 4 | 1 |

当小李再执行上面的SQL查询的时候，Postgres会查询出来成千上万条数据。
不过Oracle则会报错，他会防止我们犯错

# Cycle Detection

小李该怎么做呢？Postgres官方给出了[方法](https://www.postgresql.org/docs/current/queries-with.html)
我们写成这样

```sql
with recursive traverse_linked_list(id, next_id, cnt) as (
    select my_id, my_next_id, 1 from linked_list where my_id = '1'
                                     union all
                                     select b.my_id, b.my_next_id, a.cnt + 1
                                     from traverse_linked_list a, linked_list b
                                     where b.my_id = a.next_id
) cycle id set is_cycle using path
select id, cnt from traverse_linked_list limit 1000;
```

加上`limit`语句是为了更保险，小李不想半夜接到运维的电话。
其实上面的写法是Postgres帮我们简化了下面的写法

```sql
with recursive traverse_linked_list(id, next_id, cnt, is_cycle, path) as (
    select my_id, my_next_id, 1, false, array[my_id] from linked_list where my_id = '1'
                                     union all
                                     select b.my_id, b.my_next_id, a.cnt + 1, my_id=any(path),
                                            a.path || b.my_id
                                     from traverse_linked_list a, linked_list b
                                     where b.my_id = a.next_id
                                     and not is_cycle
)
select id, cnt from traverse_linked_list limit 1000;
```

生产无小事，不熟悉的东西不要乱用。
