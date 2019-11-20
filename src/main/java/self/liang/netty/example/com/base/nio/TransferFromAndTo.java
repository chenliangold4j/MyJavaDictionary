package self.liang.netty.example.com.base.nio;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 在Java NIO中，如果两个通道中有一个是FileChannel，那你可以直接将数据从一个channel（译者注：channel中文常译作通道）传输到另外一个channel。
 */
public class TransferFromAndTo {

    public static void main(String[] args) throws IOException {
            example1();
    }

    public static void example1() throws IOException {
        /*
        FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中

        transferFrom方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
        此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
        * */
        RandomAccessFile fromFile = new RandomAccessFile("D:/test.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("D:/toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel,position, count);
    }

    public static void example2() throws  Exception{
        //transferTo()方法将数据从FileChannel传输到其他的channel中。下面是一个简单的例子：
//        上面所说的关于SocketChannel的问题在transferTo()方法中同样存在。SocketChannel会一直传输数据直到目标buffer被填满。
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
    }

}
