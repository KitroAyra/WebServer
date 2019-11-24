import java.io.*;
import java.net.Socket;

public class WebThread {
    private Socket socket;
    private static String CLOSE = "/SHUTDOWN";

    /**
     * 构造函数
     * @param socket：socket对应线程的socket
     */
    public WebThread(Socket socket){
        this.socket = socket;
    }

    /**
     * 线程主体
     */
    public void run(){
        //定义本次线程的输入和输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //接受客户端请求
            Request request = new Request(inputStream);
            //解析请求
            FileInputStream fileInputStream = request.parse();
            //处理响应请求
            Response response = new Response(outputStream,fileInputStream);
            response.getFileName(request.fileName);
            response.send(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭连接中……");
                try {
                    if(inputStream != null){
                    inputStream.close();
                    }
                    if(outputStream != null){
                        outputStream.close();
                    }
                    if(socket != null){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("连接已关闭");
                }
        }


    }
}
