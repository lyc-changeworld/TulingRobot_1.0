package com.example.achuan.tulingrobot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.achuan.tulingrobot.bean.ChatMessage;
import com.example.achuan.tulingrobot.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;//消息显示列表的适配
    private List<ChatMessage>mDatas;//将消息存放在数据源中
    private EditText mInputMsg;//消息发送框
    private Button mSendMsg;//发送消息按钮

    //刷新数据集
   private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            //等待接收，子线程完成数据的返回
            ChatMessage fromMessage=(ChatMessage)msg.obj;
            mDatas.add(fromMessage);//添加收到的消息到数据集中
            mAdapter.notifyDataSetChanged();//控制更新
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化布局
        initDatas();//初始化数据
        initListener();//初始化事件
    }
    /*初始化事件*/
    private void initListener()
    {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg=mInputMsg.getText().toString();
                /*进行空输入判断，添加相关提示语*/
                if(TextUtils.isEmpty(toMsg))
                {
                    Toast.makeText(MainActivity.this,"发送消息不能为空...",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                /*封装客户输入的信息*/
                ChatMessage toMessage=new ChatMessage();
                toMessage.setMsg(toMsg);//将发送的消息传给对象
                toMessage.setDate(new Date());//设置发送的时间
                toMessage.setType(ChatMessage.Type.OUTCOMING);//设置类型为发送
                mDatas.add(toMessage);//添加到数据集中
                mAdapter.notifyDataSetChanged();//控制更新
                mInputMsg.setText("");//输入完后将文本清空
                /*********添加网络子线程(异步任务)*******/
                new Thread(){
                    @Override
                    public void run() {
                        ChatMessage fromMessage=null;
                        try
                        {
                            fromMessage=HttpUtils
                                    .sendMessage(toMsg);//客户发送数据后获得返回的数据
                        }catch (Exception e)
                        {
                            fromMessage= new ChatMessage("亲，网络好像出问题了...",
                                    ChatMessage.Type.INCOMING,new Date() );
                            /*Toast.makeText(MainActivity.this,"网络出了点问题...",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                        Message m=Message.obtain();//消息容器
                        m.obj=fromMessage;//转换
                        mHandler.sendMessage(m);//传输
                    }
                }.start();
            }
        });
    }
    /*初始化数据*/
    private void initDatas()
    {
      mDatas=new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好，小貅貅为您服务",
                ChatMessage.Type.INCOMING,new Date()));
        //mDatas.add(new ChatMessage("你好", ChatMessage.Type.OUTCOMING,new Date()));
        mAdapter=new ChatMessageAdapter(this,mDatas); //适配器获得数据集
        mMsgs.setAdapter(mAdapter);//为ListView添加适配器
    }
    /*初始化布局*/
    private void initView()
    {
        mMsgs= (ListView)findViewById(R.id.id_listview_msg);//列表加载
        mInputMsg= (EditText)findViewById(R.id.id_input_msg);//消息输入框加载
        mSendMsg= (Button)findViewById(R.id.id_send_msg_bt);//发送按钮加载
    }
}
