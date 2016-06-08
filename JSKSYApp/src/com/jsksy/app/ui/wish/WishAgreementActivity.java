/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.wish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.ToastUtil;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WishAgreementActivity extends BaseActivity implements OnClickListener
{
    private boolean isAgree = false;
    private ImageView agree_image;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_agreement);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("־Ը�ο�");
        app_title_back.setOnClickListener(this);
        
        LinearLayout agree_layout = (LinearLayout)findViewById(R.id.agree_layout);
        agree_image = (ImageView)findViewById(R.id.agree_image);
        Button btn = (Button)findViewById(R.id.btn);
        agree_layout.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.agree_layout:
                if (isAgree)
                {
                    agree_image.setImageResource(R.drawable.wish_checkbox_none);
                    isAgree = false;
                }
                else
                {
                    agree_image.setImageResource(R.drawable.wish_checkbox_press);
                    isAgree = true;
                }
              break;
            case R.id.btn:
                if (isAgree)
                {
                    Intent intent = new Intent(this, WishSearchActivity.class);
                    startActivity(intent);
                }
                else
                {
                    ToastUtil.makeText(this, "���Ķ�����ѡ־Ը�������������������");
                }
                break;
            default:
                break;
        }
    }
}
