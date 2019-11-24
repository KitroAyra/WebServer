import java.io.*;

public class Response {
    //定义换行和输出流
    final static private String CRLF = "\r\n";
    private OutputStream outputStream = null;
    private FileInputStream fis = null;
    private String fileName = null;


    public Response(OutputStream outputStream,FileInputStream fis){
        this.outputStream = outputStream;
        this.fis = fis;
    }

    /**
     * @description: 发送请求的文件
     * @param fis：获取的请求文件的文件读入流
     */
    public void send(FileInputStream fis){
        byte [] bytes =  new byte[1024];

        //定义要用到的返回信息
        String statusLine = null;
        String contentTypeLine = null;
        String entitybody = null;
        Boolean exitflag = false;
        //正确读取的头信息
        if(fis != null){
            statusLine = "HTTP/1.1 200 OK" + CRLF;
            contentTypeLine = "Content-Type:" + contentType(fileName) + CRLF;
            exitflag = true;
        }
        //错误读取的头信息
        else{
            statusLine = "HTTP/1.1 404" + CRLF;
            contentTypeLine = "Content-type:" + contentType(fileName) + CRLF;
            entitybody = "<!DOCTYPE html><HTML>" + "<head><title>NOT FOUND</title></head>" +"</HTML>";
        }

        try {
            outputStream.write(statusLine.getBytes());
            outputStream.write(contentTypeLine.getBytes());
            outputStream.write(CRLF.getBytes());
            if(exitflag){
                SendBytes(fis,outputStream);
                fis.close();
            }
            else{
               outputStream.write(entitybody.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description sendBytes：发送字节
     * @param fis:文件指针
     * @param os：输出流
     */
    private static void SendBytes(FileInputStream fis, OutputStream os){
        byte[] bytesBuffer = new byte[1024];
        try {
            int bytes = fis.read(bytesBuffer);
            while(bytes!= -1){
                os.write(bytesBuffer,0,bytes);
                bytes = fis.read(bytesBuffer,0,1024);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取文件流出错");
        }

    }
    public void getFileName(String fn){
        this.fileName = fn;
    }


    /**
     * @description: 获取要发送的文件内容类型
     * @param fileName：要发送的文件的名字
     * @return:文件名
     */
    private static String contentType(String fileName){
        //根据文件名返回相应的contentType
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".png")) {
            return "image/png";
        }
        if (fileName.endsWith(".css")) {
            return "text/css";
        }
        if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";

    }
}
