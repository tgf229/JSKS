package com.jsksy.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;

import com.jsksy.app.constant.Constants;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.WelcomeActivity;
import com.jsksy.app.ui.offer.OfferSearchActivity;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JRReceiver extends BroadcastReceiver
{
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        
        SharedPreferences pref = context.getSharedPreferences(Constants.JSKSY_INFO, Context.MODE_PRIVATE);
        
        boolean openApp = pref.getBoolean(SharePref.APP_OPEN, false);
        
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            if (openApp)
            {
                //打开自定义的Activity
                Intent i = new Intent(context, OfferSearchActivity.class);
                i.putExtras(bundle);
//                i.putExtra("from_jpush_noti", true);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
            else
            {
                Intent i = new Intent(context, WelcomeActivity.class);
                i.putExtras(bundle);
//                i.putExtra("from_jpush", true);
//                i.putExtra("from_jpush_noti", true);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
            
        }
    }
}
