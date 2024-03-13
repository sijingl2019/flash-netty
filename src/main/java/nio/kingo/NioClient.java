package nio.kingo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioClient {
    private static ByteBuffer buffer = ByteBuffer.allocate(4096);
    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            //设置为非阻塞模式
            socketChannel.configureBlocking(false);
            //打开选择器
            Selector selector = Selector.open();
            //注册连接服务端socket动作
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8000));

            while (true) {
                if (selector.select(1) > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isConnectable()) {
                            System.out.println("client connect");
                            SocketChannel client = (SocketChannel)key.channel();
                            if (client.isConnected()) {
                                buffer.clear();
                                buffer.put((new Date() + ": hello world").getBytes());
                                buffer.flip();
                                client.write(buffer);
                            }
                            client.register(selector, SelectionKey.OP_READ);
                        }
                        keyIterator.remove();
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
