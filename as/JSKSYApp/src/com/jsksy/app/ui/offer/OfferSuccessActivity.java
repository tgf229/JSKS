/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.offer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jsksy.app.R;
import com.jsksy.app.bean.gk.NewsDoc;
import com.jsksy.app.bean.gk.NewsResponse;
import com.jsksy.app.bean.offer.OfferResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.ShareSDKUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private LinearLayout point_ad_layout,point_ad_tips;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_success);
        sNum = getIntent().getStringExtra("sNum");
        offerResponse = (OfferResponse)getIntent().getSerializableExtra("offerResponse");
        init();
        reqAdList();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取结果");
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

        //分享
        LinearLayout share_wx = (LinearLayout) findViewById(R.id.share_wx);
        LinearLayout share_wx_timeline = (LinearLayout) findViewById(R.id.share_wx_timeline);
        LinearLayout share_qq = (LinearLayout) findViewById(R.id.share_qq);
        LinearLayout share_qq_timeline = (LinearLayout) findViewById(R.id.share_qq_timeline);
        share_wx.setOnClickListener(this);
        share_wx_timeline.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qq_timeline.setOnClickListener(this);

        point_ad_layout = (LinearLayout) findViewById(R.id.point_ad_layout);
        point_ad_tips = (LinearLayout) findViewById(R.id.point_ad_tips);
    }

    private void reqAdList(){
        Map<String, String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("type", "3");
        OkHttpUtil.sendRequestPost(URLUtil.Bus100501, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final NewsResponse newsResponse = JSON.parseObject(result,NewsResponse.class);
                if (Constants.SUCESS_CODE.equals(newsResponse.getRetcode())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showList(newsResponse);
                        }
                    });
                }
            }
        });
    }

    private void showList(final NewsResponse newsResponse){
        point_ad_layout.removeAllViews();
        if (newsResponse.getDoc().size() > 0){
            point_ad_tips.setVisibility(View.VISIBLE);
        }else{
            point_ad_tips.setVisibility(View.GONE);
        }
        for (final NewsDoc bean:newsResponse.getDoc()){
            View view = LayoutInflater.from(this).inflate(R.layout.gk_home_list_adv_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = bean.getaUrl();
                    urlSchemaJump(url);
                    OkHttpUtil.reqADLog(OfferSuccessActivity.this,bean.getaId(),"8");
                }
            });
            Glide.with(this).load(bean.getImageUrl()).into(img);
            point_ad_layout.addView(view);
        }
    }

    private void urlSchemaJump(String url){
        switch (GeneralUtils.urlSchemaForWhat(url)){
            case 1:
                String urlSchemaDetail = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaDetail != null){
                    Intent intent = new Intent(this, SchoolDetailActivity.class);
                    intent.putExtra("uCode", urlSchemaDetail);
                    startActivity(intent);
                }
                break;
            case 2:
                String urlSchemaList = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaList != null){
                    Intent intent = new Intent(this, SchoolListActivity.class);
                    intent.putExtra("searchKey", urlSchemaList);
                    this.startActivity(intent);
                }
                break;
            default:
                Intent skipIntent = new Intent(this, WebviewActivity.class);
                skipIntent.putExtra("wev_view_url",url);
                this.startActivity(skipIntent);
                break;
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
            case R.id.share_wx:
                String share_wx_url = ShareSDKUtil.structureUrl("2",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_wx_url)){
                    ShareSDKUtil.showSDKShare(OfferSuccessActivity.this, getString(R.string.share_offer_text), getString(R.string.share_offer_text),share_wx_url , Wechat.NAME);
                }
                break;
            case R.id.share_wx_timeline:
                String share_wx_timeline_url = ShareSDKUtil.structureUrl("2",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_wx_timeline_url)){
                    ShareSDKUtil.showSDKShare(OfferSuccessActivity.this, getString(R.string.share_offer_text), getString(R.string.share_offer_text),share_wx_timeline_url, WechatMoments.NAME);
                }
                break;
            case R.id.share_qq:
                String share_qq_url = ShareSDKUtil.structureUrl("2",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_qq_url)){
                    ShareSDKUtil.showSDKShare(OfferSuccessActivity.this, getString(R.string.share_offer_text), getString(R.string.share_offer_text),share_qq_url, QQ.NAME);
                }
                break;
            case R.id.share_qq_timeline:
                String share_qq_timeline_url = ShareSDKUtil.structureUrl("2",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_qq_timeline_url)){
                    ShareSDKUtil.showSDKShare(OfferSuccessActivity.this, getString(R.string.share_offer_text), getString(R.string.share_offer_text),share_qq_timeline_url, QZone.NAME);
                }
                break;
            default:
                break;
        }
    }
}
