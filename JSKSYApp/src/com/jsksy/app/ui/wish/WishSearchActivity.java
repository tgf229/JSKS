/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.wish;

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
import com.jsksy.app.bean.wish.WishResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.Global;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.offer.OfferFailActivity;
import com.jsksy.app.ui.offer.OfferSuccessActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.SecurityUtils;
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
public class WishSearchActivity extends BaseActivity implements OnClickListener
{
    /**
     * ��������ȴ���
     */
    private NetLoadingDailog dailog = new NetLoadingDailog(this);
    private EditText num, ticket;
    private String sNum, sTicket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.wish_search);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("¼ȡ����");
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
        
        sNum = "15320102000029";
        sTicket="1501020002322";
        
        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "��������Ŀ�����");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket))
        {
            ToastUtil.makeText(this, "���������׼��֤��");
            return;
        }
        
        reqWish();
    }
    private void reqWish()
    {
        dailog.loading();
        Map<String, String> param = new HashMap<String, String>();
        try
        {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sTicket", SecurityUtils.encode2Str(sTicket));
            param.put("isJbw", SecurityUtils.encode2Str("0"));
            param.put("isEyy", SecurityUtils.encode2Str("0"));
            param.put("batch", SecurityUtils.encode2Str("1"));
//            param.put("province", SecurityUtils.encode2Str(province));
//            param.put("type", SecurityUtils.encode2Str(type));
//            param.put("major", SecurityUtils.encode2Str(major));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            this,
            WishResponse.class,
            URLUtil.Bus300101,
            Constants.ENCRYPT_SIMPLE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        dailog.dismissDialog();
        if (ob instanceof WishResponse)
        {
            WishResponse resp = (WishResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
//                    Intent intent = new Intent(this, OfferSuccessActivity.class);
//                    intent.putExtra("sNum", sNum);
//                    intent.putExtra("offerResponse", resp);
//                    startActivity(intent);
//                    finish();
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
//                onSubmit();
                ToastUtil.makeText(this, "��δ���Ŵ˹��ܣ������ĵȴ������App�汾");
                break;
            default:
                break;
        }
    }
}
