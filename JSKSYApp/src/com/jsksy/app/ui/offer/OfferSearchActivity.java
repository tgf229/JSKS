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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.offer.OfferResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.Global;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.SecurityUtils;
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
public class OfferSearchActivity extends BaseActivity implements OnClickListener
{
    /**
     * 网络请求等待框
     */
    private NetLoadingDailog dailog = new NetLoadingDailog(this);
    
    private EditText num, ticket;
    
    private String sNum, sTicket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.offer_search);
        
        sNum = SharePref.getString(SharePref.STORAGE_SNUM, null);
        sTicket = SharePref.getString(SharePref.STORAGE_STICKET, null);
        if (GeneralUtils.isNotNullOrZeroLenght(sNum) && GeneralUtils.isNotNullOrZeroLenght(sTicket))
        {
            reqOffer();
        }
        
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取结果");
        app_title_back.setOnClickListener(this);
        
        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    
    private void onSubmit()
    {
        sNum = num.getText().toString().trim();
        sTicket = ticket.getText().toString().trim();
        
        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "请输入你的考生号");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket))
        {
            ToastUtil.makeText(this, "请输入你的准考证号");
            return;
        }
        
        reqOffer();
    }
    
    private void reqOffer()
    {
        dailog.loading();
        Map<String, String> param = new HashMap<String, String>();
        try
        {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sTicket", SecurityUtils.encode2Str(sTicket));
            param.put("type", SecurityUtils.encode2Str("1"));
            param.put("alias", SecurityUtils.encode2Str(SharePref.getString(SharePref.STORAGE_ALIAS, null)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            this,
            OfferResponse.class,
            URLUtil.Bus400101,
            Constants.ENCRYPT_SIMPLE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        dailog.dismissDialog();
        if (ob instanceof OfferResponse)
        {
            OfferResponse resp = (OfferResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    Intent intent = new Intent(this, OfferSuccessActivity.class);
                    intent.putExtra("sNum", sNum);
                    intent.putExtra("offerResponse", resp);
                    startActivity(intent);
                    finish();
                }
                else if ("000002".equals(resp.getRetcode()))
                {
                    SharePref.saveString(SharePref.STORAGE_SNUM, sNum);
                    SharePref.saveString(SharePref.STORAGE_STICKET, sTicket);
                    
                    String alias = SharePref.getString(SharePref.STORAGE_ALIAS, null);
                    if (GeneralUtils.isNotNullOrZeroLenght(alias))
                    {
                        //JPUSH 添加别名
                        Global.setAliasApp(this, alias);
                    }
                    
                    Intent intent = new Intent(this, OfferFailActivity.class);
                    intent.putExtra("sNum", sNum);
                    intent.putExtra("sTicket", sTicket);
                    intent.putExtra("offerResponse", resp);
                    startActivity(intent);
                    finish();
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
            case R.id.btn:
                onSubmit();
                break;
            default:
                break;
        }
    }
}
