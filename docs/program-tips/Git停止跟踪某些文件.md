# 让git停止跟踪某些文件

小王新建了一个工程
```text
- src
- resources
```
随后小王编译了一下，随即目录变成了
```text
- src
- resources
- dist
```
其中`dist`是编译产物，但是由于小王的疏忽大意
```shell
git commit -a .
```
他把所有的文件都提交了，包括不想要的`dist`文件

小李发现了这个问题，他在互联网上搜到了
[这篇回答](https://stackoverflow.com/questions/1274057/how-do-i-make-git-forget-about-a-file-that-was-tracked-but-is-now-in-gitignore)

为了剔除掉`dist`目录，他用了下边这个命令
```shell
git rm -r --cached dist
```
成功的将dist目录去掉了