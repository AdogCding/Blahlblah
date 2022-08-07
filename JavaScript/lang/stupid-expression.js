
/**
 * 展开表达式
 * 项目中遇到的一个小问题，例如有判断条件[1],[2],[3]分别对应了比较复杂的逻辑表达式
 * 例如[1]可能对应的是case_a && (case_b || case_c), [2]对应的是caseb || (case_c && case_a)
 * 要求能够将例如 ([1]/[2])&[3] 展开为 (...case_x)的形式
 */
function tail_fun(n, t, limit) {
    if (t > limit) {
        return t
    }
    return tail_fun(n, t + n)
}
'use strict'
tail_fun(1, 1, 1000000)