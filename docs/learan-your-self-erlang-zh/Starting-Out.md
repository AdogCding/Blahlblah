# Starting Out

## The Shell

在Erlang的世界里，你可以在你的终端(`emulator`)里尝试任何事情。
你可以把你的写的脚本文件编译部署后，依然能够在继续编辑的你的文件。
在控制台输入`$ erl`，你应该可以在你的Linux终端看到类似的内容

```Erlang
Erlang R13B01 (erts-5.7.2) [source] [smp:2:2] [rq:2] [async-threads:0] [hipe] [kernel-poll:false]
 
Eshell V5.7.2  (abort with ^G)
```

恭喜你，Erlang Shell在你的电脑上成功运行了。
对于Windows的用户，你可以运行`erl.exe`，但是更推荐你使用`werl.exe`，
这是一个Windows独占的Erlang Shell，有自己的滚动条，同时支持命令行编辑(例如复制-粘贴，使用cmd.exe进行这些操作会非常痛苦)

## `Shell`的常用命令
Erlang shell的内置编辑模块是Emacs的一个子集(Emacs是一款从70年代开始就非常流行文本编辑器)
这一部分还是让读者自己去看吧。
比较重要的有以下几个
- `help().`查看帮助
- `q().`退出shell
- `^G` 调出user swithc