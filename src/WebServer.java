import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @description:WebServer的启动
 */
public class WebServer {
    //定义网页信息
    public static String WEBROOT = "/Users/guopeiqi/IdeaProjects/WebServer/";//默认目录
    public static String defaultPage = "index.html";//默认文件


    public static void main(String argv[]) throws Exception{

        /*
         * 让用户选择服务器的端口号
         * 启动服务器并等候用户的链接
         */
        System.out.println("请选择服务器端口号：");
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            //阻塞，等待用户连接
            Socket connectionSocket = serverSocket.accept();
            System.out.println("Connection has been build...");
            //启动线程
            WebThread thread = new WebThread(connectionSocket);
            thread.run();
        }
    }
}

