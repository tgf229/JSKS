/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
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
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
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
        title_name.setText("志愿参考");
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
                    ToastUtil.makeText(this, "请阅读并勾选志愿辅助服务购买规则与条款");
                }
                break;
            default:
                break;
        }
    }
}
