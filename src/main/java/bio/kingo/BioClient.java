package bio.kingo;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class BioClient {
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while(true) {
                    try {
                        socket.getOutputStream().write((new Date() + ":hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }
        }).start();

    }
}
