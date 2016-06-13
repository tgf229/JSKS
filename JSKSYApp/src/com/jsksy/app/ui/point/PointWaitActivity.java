/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.wish.WishAgreementActivity;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class PointWaitActivity extends BaseActivity implements OnClickListener
{
    private TextView dayTxt, hourTxt, minuteTxt, secondTxt;
    
    private Date cuDate;
    
    private Date exDate;
    
    private String waitType;
    
    private Handler handler = new Handler();
    
    Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cuDate);
            calendar.add(Calendar.SECOND, 1);
            cuDate = calendar.getTime();
            
            if (cuDate.after(exDate))
            {
                if ("2".equals(waitType))
                {
                    Intent intent = new Intent(PointWaitActivity.this, WishAgreementActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(PointWaitActivity.this, PointSearchActivity.class);
                    startActivity(intent);
                }
                finish();
                return;
            }
            
            long ms = exDate.getTime() - cuDate.getTime();
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
    };
    
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_wait);
        String cuStr = getIntent().getStringExtra("cuTime");
        String exStr = getIntent().getStringExtra("exTime");
        waitType = getIntent().getStringExtra("waitType");
        
        //TODO ���� Ҫɾ��
//        cuStr = "20160426161201";
//        exStr = "20160426161203";
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        try
        {
            cuDate = sdf.parse(cuStr);
            exDate = sdf.parse(exStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        if ("2".equals(waitType))
        {
            title_name.setText("¼ȡ����");
            name_txt.setText("����¼ȡ���Ϸ��񿪷Ż���");
        }else
        {
            title_name.setText("�߿����");
            name_txt.setText("���뽭��ʡ�߿��ɼ���������");
        }
        
        app_title_back.setOnClickListener(this);
        
        dayTxt = (TextView)findViewById(R.id.day);
        hourTxt = (TextView)findViewById(R.id.hour);
        minuteTxt = (TextView)findViewById(R.id.minute);
        secondTxt = (TextView)findViewById(R.id.second);
        
        handler.postDelayed(runnable, 1000);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    
    private String checkTime(int str)
    {
        String strC = String.valueOf(str);
        if (str < 10)
        {
            strC = "0" + str;
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
