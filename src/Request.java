import java.io.*;
import java.util.StringTokenizer;

public class Request {

    private InputStream inputStream = null;
    public String fileName = null;
    private String uri;

    public Request(InputStream inputStream){
        this.inputStream = inputStream;
    }

    //解析客户请求
    public FileInputStream parse(){
        boolean fileExists = true;
        //先从socket中读入数据
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String requestLine = br.readLine();
            System.out.println(requestLine);
            String headerLine = null;
            while((headerLine = br.readLine()).length()!=0){
                System.out.println(headerLine);
            }

            //从行中提取文件名
            StringTokenizer tokens = new StringTokenizer(requestLine);
            tokens.nextToken();
            fileName = tokens.nextToken();
            fileName = "." + fileName;

            File file = new File(fileName);
            FileInputStream fis = null;

            //判断文件是否存在
            fis = new FileInputStream(file);
            if(fis == null){
                System.out.println("fileinputsteam是空");
            }
            System.out.println("以下是fis的内容：");
            System.out.println(fis);
            return fis;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("read inputStream error!");
            return null;
        }
    }
}
