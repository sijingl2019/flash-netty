package bio.kingo;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8000);
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            int len;
                            byte []data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (Exception e) {

                        }
                    }).start();
                } catch (Exception e) {

                }
            }
        }).start();

    }
}
