/*
 * �� �� ��:  SetActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-5-4
 
 */
package com.jsksy.app.ui.set;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.jsksy.app.R;
import com.jsksy.app.constant.Global;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-5-4]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
        title_name.setText("����");
        app_title_back.setOnClickListener(this);
        
        TextView version_txt = (TextView)findViewById(R.id.version_txt);
        version_txt.setText("��ǰ�汾V"+GeneralUtils.getVersionName(this));
        
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
            case R.id.switch_layout://��Ϣ����
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
            default:
                break;
        }
        
    }
}
