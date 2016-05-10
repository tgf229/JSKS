/*
 * �� �� ��:  WelcomeActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-8
 
 */
package com.jsksy.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mobstat.StatService;
import com.jsksy.app.R;
import com.jsksy.app.constant.Global;
import com.jsksy.app.ui.home.HomeActivity;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-8]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WelcomeActivity extends BaseActivity implements OnClickListener
{
    private Handler handler = new Handler();
    Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        //�����ѿ���APP
        Global.setAppOpen(true);
        init();
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        JPushInterface.onPause(this);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        JPushInterface.onResume(this);
    }
    
    private void init()
    {
        LinearLayout skip_layout = (LinearLayout)findViewById(R.id.skip_layout);
        skip_layout.setOnClickListener(this);
        
        handler.postDelayed(runnable, 3000);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.skip_layout:
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            
            default:
                break;
        }
        
    }
}
