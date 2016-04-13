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

import com.jsksy.app.R;
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
public class WelcomeActivity extends BaseActivity
{
    private Handler handler = new Handler()
    {
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
    }
    
    private void init()
    {
        handler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
