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

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WishPayActivity extends BaseActivity implements OnClickListener
{
    private int payType = 1;
    
    private ImageView pay_zhifubao_image, pay_weixin_image, pay_yinlian_image, pay_apple_image;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_pay);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("购买");
        app_title_back.setOnClickListener(this);
        
        LinearLayout pay_zhifubao_layout = (LinearLayout)findViewById(R.id.pay_zhifubao_layout);
        LinearLayout pay_weixin_layout = (LinearLayout)findViewById(R.id.pay_weixin_layout);
        LinearLayout pay_yinlian_layout = (LinearLayout)findViewById(R.id.pay_yinlian_layout);
        LinearLayout pay_apple_layout = (LinearLayout)findViewById(R.id.pay_apple_layout);
        pay_zhifubao_layout.setOnClickListener(this);
        pay_weixin_layout.setOnClickListener(this);
        pay_yinlian_layout.setOnClickListener(this);
        pay_apple_layout.setOnClickListener(this);
        
        pay_zhifubao_image = (ImageView)findViewById(R.id.pay_zhifubao_image);
        pay_weixin_image = (ImageView)findViewById(R.id.pay_weixin_image);
        pay_yinlian_image = (ImageView)findViewById(R.id.pay_yinlian_image);
        pay_apple_image = (ImageView)findViewById(R.id.pay_apple_image);
        
        Button btn = (Button)findViewById(R.id.btn);
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
            case R.id.pay_zhifubao_layout:
                clearChoosenImage();
                payType = 1;
                pay_zhifubao_image.setImageResource(R.drawable.wish_check_circle_press);
                break;
            case R.id.pay_weixin_layout:
                clearChoosenImage();
                payType = 2;
                pay_weixin_image.setImageResource(R.drawable.wish_check_circle_press);
                break;
            case R.id.pay_yinlian_layout:
                clearChoosenImage();
                payType = 3;
                pay_yinlian_image.setImageResource(R.drawable.wish_check_circle_press);
                break;
            case R.id.pay_apple_layout:
                clearChoosenImage();
                payType = 4;
                pay_apple_image.setImageResource(R.drawable.wish_check_circle_press);
                break;
            case R.id.btn:
                Intent intent = new Intent(this, WishPaySuccessActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    
    private void clearChoosenImage()
    {
        pay_zhifubao_image.setImageResource(R.drawable.wish_check_circle);
        pay_weixin_image.setImageResource(R.drawable.wish_check_circle);
        pay_yinlian_image.setImageResource(R.drawable.wish_check_circle);
        pay_apple_image.setImageResource(R.drawable.wish_check_circle);
    }
}
