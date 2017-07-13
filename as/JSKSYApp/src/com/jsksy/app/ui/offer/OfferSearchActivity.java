/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.offer;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class OfferSearchActivity extends BaseActivity implements OnClickListener
{
    /**
     * ��������ȴ���
     */
    private NetLoadingDailog dailog = new NetLoadingDailog(this);
    
    private EditText num, ticket;
    
    private String sNum, sTicket;
    private String sCheckKeyA,sCheckKeyB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.offer_search);

        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取结果");
        app_title_back.setOnClickListener(this);

        Random random = new Random();
        String checkA_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkA_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        String checkB_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkB_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        sCheckKeyA = checkA_Num + checkA_Point;
        sCheckKeyB = checkB_Num + checkB_Point;

        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        TextView check_txt = (TextView)findViewById(R.id.check_txt);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);

        sNum = SharePref.getString(SharePref.STORAGE_SNUM, null);
        sTicket = SharePref.getString(SharePref.STORAGE_STICKET, null);
        if (GeneralUtils.isNotNullOrZeroLenght(sNum) && GeneralUtils.isNotNullOrZeroLenght(sTicket))
        {
            sCheckKeyA = SharePref.getString(SharePref.STORAGE_SCHECKA, null);
            sCheckKeyB = SharePref.getString(SharePref.STORAGE_SCHECKB, null);
            reqOffer();
        }
        check_txt.setText(sCheckKeyA + " : " + sCheckKeyB);
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
            ToastUtil.makeText(this, "请输入你的动态口令");
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
            param.put("sCheck", SecurityUtils.encode2Str(sTicket));
            param.put("sCheckKeyA", SecurityUtils.encode2Str(sCheckKeyA));
            param.put("sCheckKeyB", SecurityUtils.encode2Str(sCheckKeyB));
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
            URLUtil.Bus400102,
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
                    Intent intent = new Intent(this, OfferResultActivity.class);
                    intent.putExtra("sNum", sNum);
                    intent.putExtra("offerResponse", resp);
                    startActivity(intent);
                    finish();
                }
                else if ("000002".equals(resp.getRetcode()))
                {
                    SharePref.saveString(SharePref.STORAGE_SNUM, sNum);
                    SharePref.saveString(SharePref.STORAGE_STICKET, sTicket);
                    SharePref.saveString(SharePref.STORAGE_SCHECKA, sCheckKeyA);
                    SharePref.saveString(SharePref.STORAGE_SCHECKB, sCheckKeyB);
                    
                    String alias = SharePref.getString(SharePref.STORAGE_ALIAS, null);
                    if (GeneralUtils.isNotNullOrZeroLenght(alias))
                    {
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
