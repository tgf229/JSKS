/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.offer;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.offer.OfferResponse;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OfferSuccessActivity extends BaseActivity implements OnClickListener
{
    private String sNum;
    private OfferResponse offerResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_success);
        sNum = getIntent().getStringExtra("sNum");
        offerResponse = (OfferResponse)getIntent().getSerializableExtra("offerResponse");
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取查询");
        app_title_back.setOnClickListener(this);
        
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        TextView offer_content = (TextView)findViewById(R.id.offer_content);
        
        name_txt.setText("姓名："+offerResponse.getsName()+"    考生号："+sNum);
        if (GeneralUtils.isNullOrZeroLenght(offerResponse.getMajor()))
        {
            offer_content.setText("已被"+offerResponse.getSchool()+"预录取");
        }
        else
        {
            offer_content.setText("已被"+offerResponse.getSchool()+"-"+offerResponse.getMajor()+"录取");
        }
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
