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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.BaseResponse;
import com.jsksy.app.bean.offer.OfferDoc;
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
import java.util.List;
import java.util.Map;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class OfferFailActivity extends BaseActivity implements OnClickListener
{
    private NetLoadingDailog dailog;
    
    private String sNum, sTicket;
    
    private OfferResponse offerResponse;
    private LinearLayout doc_content;

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
        title_name.setText("录取结果");
        app_title_back.setOnClickListener(this);
        
        LinearLayout title_call_layout = (LinearLayout)findViewById(R.id.title_call_layout);
        TextView title_btn_call = (TextView)findViewById(R.id.title_btn_call);
        title_btn_call.setText("取消预约");
        title_call_layout.setVisibility(View.VISIBLE);
        title_call_layout.setOnClickListener(this);
        
        TextView sNum_txt = (TextView)findViewById(R.id.sNum_txt);
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        sNum_txt.setText(sNum);
        name_txt.setText(offerResponse.getsName());

        doc_content = (LinearLayout) findViewById(R.id.doc_content);
        showDoc(offerResponse.getDoc());
    }

    private void showDoc(List<OfferDoc> doc){
        doc_content.removeAllViews();
        for (OfferDoc bean: doc){
            View view = LayoutInflater.from(this).inflate(R.layout.component_offer_list_item,null,false);
            TextView time_txt = (TextView) view.findViewById(R.id.time_txt);
            TextView school_txt = (TextView) view.findViewById(R.id.school_txt);
            TextView batch_txt = (TextView) view.findViewById(R.id.batch_txt);
            TextView clazz_txt = (TextView) view.findViewById(R.id.clazz_txt);
            TextView reason_txt = (TextView) view.findViewById(R.id.reason_txt);
            TextView ps_txt = (TextView) view.findViewById(R.id.ps_txt);
            if (GeneralUtils.isNotNull(bean)){
                time_txt.setText(bean.getTime());
                school_txt.setText(bean.getSchoolName());
                batch_txt.setText(bean.getBatch());
                clazz_txt.setText(bean.getClazz());
                reason_txt.setText(bean.getReason());
                ps_txt.setText("备注:"+bean.getPs());
                doc_content.addView(view);
            }
        }
    }
    
    private void reqBus400201()
    {
        dailog.loading();
        Map<String, String> param = new HashMap<String, String>();
        try
        {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sTicket", SecurityUtils.encode2Str("")); //兼容Bus400102接口,没有sTicket了
            param.put("alias", SecurityUtils.encode2Str(SharePref.getString(SharePref.STORAGE_ALIAS, null)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            this,
            BaseResponse.class,
            URLUtil.Bus400201,
            Constants.ENCRYPT_SIMPLE);
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
                    SharePref.saveString(SharePref.STORAGE_SCHECKA, null);
                    SharePref.saveString(SharePref.STORAGE_SCHECKB, null);
                    
                    //JPUSH ��ӱ���
                    Global.setAliasApp(this, "");
                    
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
