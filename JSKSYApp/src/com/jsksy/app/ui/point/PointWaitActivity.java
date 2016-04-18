/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.ui.BaseActivity;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PointWaitActivity extends BaseActivity implements OnClickListener
{
    private TextView dayTxt, hourTxt, minuteTxt, secondTxt;
    
    String d1 = "20160601000000";
    
    Date dd1;
    
    private Handler handler = new Handler()
    {
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_wait);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        try
        {
            dd1 = sdf.parse(d1);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("高考查分");
        app_title_back.setOnClickListener(this);
        
        dayTxt = (TextView)findViewById(R.id.day);
        hourTxt = (TextView)findViewById(R.id.hour);
        minuteTxt = (TextView)findViewById(R.id.minute);
        secondTxt = (TextView)findViewById(R.id.second);
        
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                long ms = dd1.getTime() - new Date().getTime();
                int day = (int)(ms / 1000 / 60 / 60 / 24);
                int hour = (int)(ms / 1000 / 60 / 60 % 24);
                int minute = (int)(ms / 1000 / 60 % 60);
                int second = (int)(ms / 1000 % 60);
                dayTxt.setText(checkTime(day));
                hourTxt.setText(checkTime(hour));
                minuteTxt.setText(checkTime(minute));
                secondTxt.setText(checkTime(second));
                handler.postDelayed(this, 1000);
            }
        }, 1000);
        
        //测试
        handler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                Intent intent = new Intent(PointWaitActivity.this,PointSearchActivity.class);
                startActivity(intent);
            }
        }, 5000);
        
    }
    
    private String checkTime(int str)
    {
        String strC = String.valueOf(str);
        if(str < 10)
        {
            strC = "0"+str;
        }
        return strC;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
