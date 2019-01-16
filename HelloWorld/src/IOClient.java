import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author jimmy
 * @create 2019-01-09 16:10
 * @desc 客户端
 **/
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ":hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
