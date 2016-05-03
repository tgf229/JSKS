/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
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
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
        title_name.setText("¼ȡ��ѯ");
        app_title_back.setOnClickListener(this);
        
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        TextView offer_content = (TextView)findViewById(R.id.offer_content);
        
        name_txt.setText("������"+offerResponse.getsName()+"    �����ţ�"+sNum);
        if (GeneralUtils.isNullOrZeroLenght(offerResponse.getMajor()))
        {
            offer_content.setText("�ѱ�"+offerResponse.getSchool()+"Ԥ¼ȡ");
        }
        else
        {
            offer_content.setText("�ѱ�"+offerResponse.getSchool()+"-"+offerResponse.getMajor()+"¼ȡ");
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
