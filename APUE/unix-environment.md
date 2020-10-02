大概是APUE的前两章内容
1. 第一章
对UNIX操作系统有一个系统的认识，了解操作系统包括哪些模块。
2. 第二章
前半部分主要讲解了UNIX的相关标准:
    1. ISO C
    这是一个面向C语言的标准，规定了C的标准库
    2. POSIX.1(Portable Operating System Interface)
    这是面向操作系统的标准，规定了一个符合标准的操作系统需要提供的API
    3. SUS(Single Unix Specification)
    是POSIX的一个超集，除了基本的POSIX标准的接口外，还要有XSI(X/Open System Interface)的必需实现，包括：
        1. 文件同步
        2. 线程栈地址和长度属性
        3. 线程共享同步
        4. _XOPEN_UNIX符号常量
    除此之外还有若干可选接口。
对于这些标准的实现，书中主要列出了以下几类:
    1. SVR4
    2. 4.4BSD
    3. FreeBSD
    4. Mac OS X
显然有标准就会有限制，比如一个路径的最大长度，可以打开文件的个数...，这些限制可以被分为编译时限制和运行时限制。
在具体的在POSIX.1标准中，这些标准可以分成以下几类:
    1. 数值限制
    2. 最大/小值
    3. 运行时可以增加的值
    4. 运行时不变的值
    5. 路径名可变值
这些值有些可以在头文件中找到定义，有些则要用System Call在运行时得知。
POSIX提供了两种得到限制的函数,没有路径依赖的sysconf和要求路径的fpathconf,pathconf。
另外，当编写要求可移植的应用程序时，要考虑到XSI提供的选项组。因为应用程序依赖的选项可能并没有在相应的操作系统上实现。
一般使用sysconf调用时使用"_SC_"开头的常量，而fpathconf和pathconf使用的时候用“_PC_”开头的常量。
关于基本的数据类型，比如"size_t"这种，书中说在多个文件中被多次定义(typedef)，但是我在macOS的环境下查看后发现，这些头文件都会引用
<sys/_types/_size_t.h>，这个文件的内容中关于size_t的定义如下
<script src="https://gist.github.com/AdogCding/7aa977f1cdf90bd8ecf323f03e9e3fba.js"></script>

3. 第三章
这部分讲了IO相关的知识，主要是无缓冲的IO(unbufferd IO)，同标准IO不同的是，在这章中的IO操作都是系统调用，每次read(..) 或者 write()都会有一次IO操作。
这部分内容涉及到了关于UNIX对文件系统的抽象:vnode->file->fd。
vnode是操作系统对inode的抽象，是对保存在硬盘中的文件的抽象，操作系统维护着一个vnode table，在操作系统运行的时候从硬盘中读取。对于打开的文件，内核维护一个file table，file table中的每个entry都代表一个打开了的文件。这个table独立于进程，取决于内核，这个file包括两方面的内容：
    1. 元数据，包括文件的打开描述符(file discription)，文件的偏移量(related to lseek)。
    2. vnode的指针
fd(file discriptor)是一个对file table中file的指针，每个进程都有自己的fd table，它指向一个file table中的entry。
UNIX提供了两种方法来打开一个文件:open/openat。
openat可以使用一个fd来修饰一个相对路径。**注意**:在使用O_CREAT这个flag创建文件的时候需要提供mode，也就是权限。
那么多次打开一个相同的文件会是什么结果呢？
一般来说，会在file table中创建两个不同的entry，不同的entry指向一个相同的vnode，也就是相同的文件(物理)。
使用lseek函数可以看到这一点，lseek是改变文件偏移量的函数，在unix中，文件的偏移量是保存在file table中的。也就是说，其实lseek并没有涉及到io操作，不过会陷入内核。
<script src="https://gist.github.com/AdogCding/dc48c6604173a8604880207a4e7d4a8d.js"></script>
这个代码的输出结果是"hello, world"，正巧将连个字符串拼接了起来，足以说明问题。

因为文件是IO操作，所以在多进程(线程)时，容易出现race condition。为了解决这问题，unix提供了一些原子操作，比如pread，pwrite和O_APPEND。**注意**:pread是lseek+read，pwrite是lseek+write，但是实际并没有改变文件的偏移。
一般来说，当使用write的时候，返回时数据并没有直接写到磁盘上，而是有一个内核维护的块缓冲区，当对一个块操作完成时，才会flush到磁盘上。因此，像数据库读写这样对同步很敏感的操作，需要函数每次写入都是真正物理写到磁盘上。unix提供了fsync和fdsync来保证这一点。这个函数两个不同的地方在于，fdsync没有要求改变文件的属性而只是保证数据同步写入，fsync则包括来数据的写入和属性的更新。同时，open函数也用O_SYNC选项支持了同步写，书中提到，在linux系统中O_SYNC是不支持的，而我在MAC上尝试的时候，由于现在的磁盘都是固体，读写非常快，我也没法很好的用数据说明，不过确实时间上有一些差异，与书中吻合。

已知两次打开同一个文件得到的是指向不同文件描述符的fd，所以要想复制一个fd的功能，那么要用dup和dup2。dup2特殊的地方在于，新fd的FD_CLOEXEC会被清除，也就是在执行了EXEC操作后新的fd会一直打开。其实dup2是close和fcntl复合后的原子操作。fcntl是针对fd的操作，功能强大，基本的应用有五种：
1. 复制一个已有的描述符
2. 获取/设置file description
3. 获取/设置是否“执行关闭”(close execute)
4. 获取/设置锁
5. 获取/设置异步IO

另外，还有ioctl的函数作用于终端设备。
另外有些习题：
<script src="https://gist.github.com/AdogCding/6849c36f740f8856f53466d913ecb33f.js"></script>

