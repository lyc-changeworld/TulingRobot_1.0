package com.example.achuan.tulingrobot.utils;
import com.example.achuan.tulingrobot.bean.ChatMessage;
import com.example.achuan.tulingrobot.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
/**
 * Created by achuan on 16-2-1.
 * 功能：实现消息的发送与服务器返回的数据接收（工具类）
 */
public class HttpUtils {
    //先添加图灵机器人的URL链接和我注册后获得的API_KEY
    private  static  final String URL="http://www.tuling123.com/openapi/api";
    private  static  final String API_KEY="e06c551250cccc6a3050c29a3c9eacbf";
    /**
     * 发送一个消息，得到返回的消息
     **/
    public static ChatMessage sendMessage(String msg)
    {
        ChatMessage chatMessage=new ChatMessage();//创建一个对话对象
        String jsonRes=doGet(msg);//得到服务器返回的字符串
        Gson gson=new Gson();//创建一个转换对象
        Result result=null;
        try{/*将字符串转换为对象*/
            result=gson.fromJson(jsonRes, Result.class);//将字符串转换为result对象
            chatMessage.setMsg(result.getText());//将对象里的字符串提取出来显示
        }catch (JsonSyntaxException e)
        {
            chatMessage.setMsg("服务器繁忙，请稍候再试！");//异常时警告异常发生
        }
        chatMessage.setDate(new Date());//设置日期时间
        chatMessage.setType(ChatMessage.Type.INCOMING);//设置对话属性为接收
        return chatMessage;
    }
    //对用户传入的消息进行处理后，获得服务器返回的结果
    public static String doGet(String msg)//msg为用户输入的数据
    {
        String result="";//保存服务器返回的消息
        String url=setParams(msg);//为URL设置参数
        InputStream inputStream=null;
        ByteArrayOutputStream baos=null;
        try {
            java.net.URL urlNet=new URL(url);/*设置数据发送请求*/
            HttpURLConnection conn= (HttpURLConnection) urlNet
                    .openConnection();//打开网络连接
            conn.setReadTimeout(5 * 1000);//设置读取的时间间隔
            conn.setConnectTimeout(5 * 1000);//设置连接的时间间隔

            inputStream=conn.getInputStream();//拿到服务器返回的数据流（类型为流）
            int len=-1;//-1代表结尾标志
            byte[] buf=new byte[128];//设置缓冲区为128字节
            baos=new ByteArrayOutputStream();//将流读后写入
            while ((len=inputStream.read(buf))!=-1)//如果流没读完就一直写
            {
                baos.write(buf,0,len);//将buf中的流写入baos内
            }
            baos.flush();//清除缓冲区
            result =new String(baos.toByteArray());//获得最终的String结果
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {//最后释放内存
            if(baos!=null)
            {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    /****************为URL设置参数,具体方法参考使用文档*************/
    private static String setParams(String msg) {
        String url= null;
        try {
            url = URL+"?key="+API_KEY+"&info="//拼接得到一个完整的Url
                    + URLEncoder.encode(msg, "UTF-8");//将用户传入的数据设置为UTF-8编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
