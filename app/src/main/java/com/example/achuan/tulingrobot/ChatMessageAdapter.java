package com.example.achuan.tulingrobot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.achuan.tulingrobot.bean.ChatMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by achuan on 16-2-2.
 * 功能：添加对话框适配器，用来处理不同的消息对应的不同处理
 */
public class ChatMessageAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;//用来设置Item的布局
    private List<ChatMessage>mDatas;
    public ChatMessageAdapter(Context context,List<ChatMessage> mDatas)
    {
       mInflater=LayoutInflater.from(context);
        this.mDatas=mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size();//返回消息的个数
    }
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return 2;//有两种布局，设置为2
    }
    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage=mDatas.get(position);
        if(chatMessage.getType()== ChatMessage.Type.INCOMING)
        {
            return 0;//接收消息为返回0
        }
        return 1;//发送消息为返回1
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage=mDatas.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null)//如果当前布局为空
        {
            viewHolder = new ViewHolder();//创建一个
            /*通过ItemType设置相关的布局*/
            if(getItemViewType(position)==0)//类型为0设置接收布局
            {
                convertView=mInflater.inflate(R.layout.item_from_msg,parent,false);
                viewHolder.mDate= (TextView) convertView.findViewById(R.id.id_from_msg_date);
                viewHolder.mMsg= (TextView) convertView.findViewById(R.id.id_from_msg_info);
            }
            else
            {
                convertView=mInflater.inflate(R.layout.item_to_msg,null);
                viewHolder.mDate= (TextView) convertView.findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg= (TextView) convertView.findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);//将viewHolder的信息存储起来
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //设置数据
        DateFormat df=new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");//设置时间显示的格式
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));//将时间转换为字符形式
        viewHolder.mMsg.setText(chatMessage.getMsg());

        return convertView;
    }
    public final class ViewHolder
    {
      public TextView mDate;//声明时间
      public TextView mMsg;//声明消息
    }
}
