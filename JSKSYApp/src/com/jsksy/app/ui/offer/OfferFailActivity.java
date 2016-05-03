/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.offer;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.BaseResponse;
import com.jsksy.app.bean.offer.OfferResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
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
public class OfferFailActivity extends BaseActivity implements OnClickListener
{
    private NetLoadingDailog dailog;
    
    private String sNum, sTicket;
    
    private OfferResponse offerResponse;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_fail);
        sNum = getIntent().getStringExtra("sNum");
        sTicket = getIntent().getStringExtra("sTicket");
        offerResponse = (OfferResponse)getIntent().getSerializableExtra("offerResponse");
        init();
    }
    
    private void init()
    {
        dailog = new NetLoadingDailog(this);
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取查询");
        app_title_back.setOnClickListener(this);
        
        LinearLayout title_call_layout = (LinearLayout)findViewById(R.id.title_call_layout);
        TextView title_btn_call = (TextView)findViewById(R.id.title_btn_call);
        title_btn_call.setText("取消预约");
        title_call_layout.setOnClickListener(this);
        
        TextView sNum_txt = (TextView)findViewById(R.id.sNum_txt);
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        
        sNum_txt.setText(sNum);
        name_txt.setText(offerResponse.getsName());
    }
    
    private void reqBus400201()
    {
        dailog.loading();
        Map<String, String> param = new HashMap<String, String>();
        param.put("sNum", sNum);
        param.put("sTicket", sTicket);
        param.put("alias", SharePref.getString(SharePref.STORAGE_ALIAS, null));
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            this,
            BaseResponse.class,
            URLUtil.Bus400201,
            Constants.ENCRYPT_NONE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        dailog.dismissDialog();
        if (ob instanceof BaseResponse)
        {
            BaseResponse resp = (BaseResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    SharePref.saveString(SharePref.STORAGE_SNUM, null);
                    SharePref.saveString(SharePref.STORAGE_STICKET, null);
                    ToastUtil.makeText(this, "取消预约成功");
                }
                else
                {
                    ToastUtil.makeText(this, resp.getRetinfo());
                }
            }
            else
            {
                ToastUtil.showError(this);
            }
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
            case R.id.title_call_layout:
                reqBus400201();
                break;
            default:
                break;
        }
    }
}
