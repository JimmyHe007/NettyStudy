import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jimmy
 * @create 2019-01-09 16:10
 * @desc 服务端
 **/
public class IOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);

        new Thread(() -> {
            while(true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() ->{
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while(true) {
                                int len;
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {

                        }
                    }).start();
                } catch (IOException e) {

                }
            }
        }).start();
    }
}