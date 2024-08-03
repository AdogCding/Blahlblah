# MacOS环境使用du命令查询硬盘占用
> 20240804

前段时间买了一台Mac Studio，在闲鱼上入手的，花了8000多，美版。

M1 MAX的芯片，32GB的内存，512GB的硬盘容量。

Apple的产品实在是太贵了，我的台式机Wintel的两条1TB的SSD也才一千来块。

偏偏现在的软件开发毫无节制的使用我的存储资源，`Java`和`Node`的依赖不知道占用了多少空间

`du`是`disk usage`的缩写，可以比较具体的展示硬盘的使用情况

在`~`下执行
`du -gh -d 1 -I Library -I .Trash`

`-h`表示可读性高的输出格式

`-d`表示递归查询的深度是1

`-I`表示排除某些明细

```
4.4M	./.hex
5.9M	./.config
256K	./Music
 24K	./.clojure
308K	./.templateengine
113M	./.local
7.9G	./Pictures
252M	./Workspace
201M	./.nvm
871M	./.nuget
220M	./.asdf
1.6G	./.opam
  0B	./Desktop
1.0G	./.sdkman
 16M	./.lein
  0B	./Public
 16K	./.ssh
212K	./Movies
612M	./.dotnet
9.3G	./Applications
8.0K	./.gradle
 87M	./.npm
 16K	./Documents
268K	./.gitlibs
936M	./.vscode
130M	./.m2
4.0K	./.aspnet
 13M	./.oh-my-zsh
8.0K	./Downloads
  0B	./.cache
 88K	./.zsh_sessions
 23G	.
```

手机上的图片会同步到本地，吃掉了我8G的存储

没想道`Ocaml`的依赖会占用我这么大空间