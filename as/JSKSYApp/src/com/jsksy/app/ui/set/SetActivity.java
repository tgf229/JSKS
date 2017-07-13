/*
 * �� �� ��:  SetActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-5-4
 
 */
package com.jsksy.app.ui.set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.Global;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;

import cn.jpush.android.api.JPushInterface;

/**
 *
 */
public class SetActivity extends BaseActivity implements OnClickListener
{
    private ImageView switch_img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("设置");
        app_title_back.setOnClickListener(this);


        String alias = SharePref.getString(SharePref.STORAGE_ALIAS, "");

        TextView version_txt = (TextView)findViewById(R.id.version_txt);
        version_txt.setText("当前版本v"+GeneralUtils.getVersionName(this)+"-"+alias);

        LinearLayout share_layout = (LinearLayout) findViewById(R.id.share_layout);
        LinearLayout switch_layout = (LinearLayout)findViewById(R.id.switch_layout);
        switch_img = (ImageView)findViewById(R.id.switch_img);
        
        boolean op_noti = JPushInterface.isPushStopped(SetActivity.this.getApplicationContext());
        if (Global.getPush())
        {
            switch_img.setImageResource(R.drawable.switch_on);
            if (op_noti)
            {
                JPushInterface.resumePush(SetActivity.this.getApplicationContext());
            }
        }
        else
        {
            switch_img.setImageResource(R.drawable.switch_off);
            if (!op_noti)
            {
                JPushInterface.stopPush(SetActivity.this.getApplicationContext());
            }
        }
        share_layout.setOnClickListener(this);
        switch_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.switch_layout:
                if (Global.getPush())
                {
                    Global.savePush(false);
                    switch_img.setImageResource(R.drawable.switch_off);
                    JPushInterface.stopPush(SetActivity.this.getApplicationContext());
                }
                else
                {
                    Global.savePush(true);
                    switch_img.setImageResource(R.drawable.switch_on);
                    JPushInterface.resumePush(SetActivity.this.getApplicationContext());
                }
                break;
            case R.id.share_layout:
                startActivity(new Intent(this,SetQrcodeActivity.class));
                break;
            default:
                break;
        }
        
    }
}
