package com.test.nio;

import java.util.Scanner;

import com.test.nio.client.Client;
import com.test.nio.server.Server;
/**
 * DK 1.4中的java.nio.*包中引入新的Java I/O库，其目的是提高速度。实际上，“旧”的I/O包已经使用NIO重新实现过，即使我们不显式的使用NIO编程，也能从中受益。速度的提高在文件I/O和网络I/O中都可能会发生，但本文只讨论后者。
 *   2.1、简介
 *   NIO我们一般认为是New I/O（也是官方的叫法），因为它是相对于老的I/O类库新增的（其实在JDK 1.4中就已经被引入了，但这个名词还会继续用很久，即使它们在现在看来已经是“旧”的了，所以也提示我们在命名时，需要好好考虑），做了很大的改变。但民间跟多人称之为Non-block I/O，即非阻塞I/O，因为这样叫，更能体现它的特点。而下文中的NIO，不是指整个新的I/O库，而是非阻塞I/O。
 *   NIO提供了与传统BIO模型中的Socket和ServerSocket相对应的SocketChannel和ServerSocketChannel两种不同的套接字通道实现。
 *   新增的着两种通道都支持阻塞和非阻塞两种模式。
 *   阻塞模式使用就像传统中的支持一样，比较简单，但是性能和可靠性都不好；非阻塞模式正好与之相反。
 *   对于低负载、低并发的应用程序，可以使用同步阻塞I/O来提升开发速率和更好的维护性；对于高负载、高并发的（网络）应用，应使用NIO的非阻塞模式来开发。
 *    下面会先对基础知识进行介绍。
 *    2.2、缓冲区 Buffer
 *    Buffer是一个对象，包含一些要写入或者读出的数据。
 *    在NIO库中，所有数据都是用缓冲区处理的。在读取数据时，它是直接读到缓冲区中的；在写入数据时，也是写入到缓冲区中。任何时候访问NIO中的数据，都是通过缓冲区进行操作。
 *    缓冲区实际上是一个数组，并提供了对数据结构化访问以及维护读写位置等信息。
 *    具体的缓存区有这些：ByteBuffe、CharBuffer、 ShortBuffer、IntBuffer、LongBuffer、FloatBuffer、DoubleBuffer。他们实现了相同的接口：Buffer。
 *    2.3、通道 Channel
 *    我们对数据的读取和写入要通过Channel，它就像水管一样，是一个通道。通道不同于流的地方就是通道是双向的，可以用于读、写和同时读写操作。
 *    底层的操作系统的通道一般都是全双工的，所以全双工的Channel比流能更好的映射底层操作系统的API。
 *    Channel主要分两大类：
 *  ●     SelectableChannel：用户网络读写
 *  ●     FileChannel：用于文件操作
 *   后面代码会涉及的ServerSocketChannel和SocketChannel都是SelectableChannel的子类。
 *    2.4、多路复用器 Selector
 *    Selector是Java  NIO 编程的基础。
 *    Selector提供选择已经就绪的任务的能力：Selector会不断轮询注册在其上的Channel，如果某个Channel上面发生读或者写事件，这个Channel就处于就绪状态，会被Selector轮询出来，然后通过SelectionKey可以获取就绪Channel的集合，进行后续的I/O操作。
 *    一个Selector可以同时轮询多个Channel，因为JDK使用了epoll()代替传统的select实现，所以没有最大连接句柄1024/2048的限制。所以，只需要一个线程负责Selector的轮询，就可以接入成千上万的客户端。
 *    2.5、NIO服务端
 *    代码比传统的Socket编程看起来要复杂不少。
 *    直接贴代码吧，以注释的形式给出代码说明。
 */
public class TestApp {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 运行服务器
		Server.start();
		// 避免客户端先于服务器启动前执行代码
		Thread.sleep(100);
		// 运行客户端
		Client.start();
		//通过命令行发送信息
		//while (Client.sendMsg(new Scanner(System.in).nextLine()))
		//	;
		
		Thread.sleep(200);
		
		//也可以这样发送信息
		Client.sendMsg("234*43");
		
		
	}
}
