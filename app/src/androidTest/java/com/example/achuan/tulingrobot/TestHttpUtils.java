package com.example.achuan.tulingrobot;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.achuan.tulingrobot.utils.HttpUtils;

/**
 * Created by achuan on 16-2-1.
 * 功能：对工具类进行测试（测试类）
 */
public class TestHttpUtils extends AndroidTestCase
{
    public void testSendInfo()
    {
        String res=HttpUtils.doGet("给我讲个笑话");
        Log.e("TAG",res);
        res=HttpUtils.doGet("阿川是谁呀");
        Log.e("TAG",res);
        res=HttpUtils.doGet("你好帅");
        Log.e("TAG",res);
    }

}
