/*
 * 文 件 名:  WelcomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
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
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
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
        //设置已开启APP
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
