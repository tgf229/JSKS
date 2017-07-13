/*
 * �� �� ��:  WelcomeActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-8
 
 */
package com.jsksy.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.bean.home.AdResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.Global;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.home.HomeActivity;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.util.GeneralUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-8]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WelcomeActivity extends BaseActivity implements OnClickListener
{
    private static final String TAG = "WelcomeActivity";
    private String add_guide = Constants.GUIDE_VERSION_CODE + "";
    
    private GuidePagerAdapter circleImagePagerAdapter;
    
    private ArrayList<String> images;
    
    private ViewPager banner_Pager;
    
    private CirclePageIndicator banner_indicator;
    
    private ImageView welcome_ad;
    private TextView skip_txt;
    
    /**
     * ����ʱ
     */
    private MyTime myTime;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        //�����ѿ���APP
        Global.setAppOpen(true);
        if (add_guide.equals(Global.getUserGuide()))
        {
            init();
        }
        else
        {
            appGuide();
            Global.saveUserGuide(add_guide);
        }
    }
    
    private void appGuide()
    {
        images = new ArrayList<String>();
        images.add("guide_one");
        images.add("guide_two");
        images.add("guide_three");
        images.add("guide_four");
        images.add("guide_five");
        images.add("guide_six");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.circlepager_rl);
        LinearLayout welcome_layout = (LinearLayout)findViewById(R.id.welcome_layout);
        welcome_layout.setVisibility(View.GONE);
        rl.setVisibility(View.VISIBLE);
        banner_Pager = (ViewPager)findViewById(R.id.circlepager);
        circleImagePagerAdapter = new GuidePagerAdapter(this, images, WelcomeActivity.this);
        banner_Pager.setAdapter(circleImagePagerAdapter);
        //ʵ����CirclePageIndicator ��������ViewPager����
        banner_indicator = (CirclePageIndicator)findViewById(R.id.circleindicator);
        banner_indicator.setViewPager(banner_Pager);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        JPushInterface.onPause(this);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        JPushInterface.onResume(this);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (GeneralUtils.isNotNull(myTime))
        {
            myTime.cancel();
        }
    }
    
    private void init()
    {
        LinearLayout welcome_layout = (LinearLayout)findViewById(R.id.welcome_layout);
        welcome_layout.setVisibility(View.VISIBLE);
        
        welcome_ad = (ImageView)findViewById(R.id.welcome_ad);
        
        LinearLayout skip_layout = (LinearLayout)findViewById(R.id.skip_layout);
        skip_layout.setOnClickListener(this);
        skip_txt = (TextView)findViewById(R.id.skip_txt);
        
        
        reqAD();
        
        myTime = new MyTime(5000, 1000);
        myTime.start();
    }
    
    private void reqAD()
    {
        Map<String, String> param = new HashMap<String, String>();
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            WelcomeActivity.this,
            AdResponse.class,
            URLUtil.Bus100401,
            Constants.ENCRYPT_NONE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        if (ob instanceof AdResponse)
        {
            final AdResponse resp = (AdResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    ImageLoader.getInstance().displayImage(resp.getImageUrl(),
                        welcome_ad,
                        JSKSYApplication.setAllDisplayImageOptions(this, "loading_content", "loading_content", "loading_content"));
                    if (GeneralUtils.isNotNullOrZeroLenght(resp.getaUrl()))
                    {
                        welcome_ad.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View arg0)
                            {
                                String url = resp.getaUrl();
                                urlSchemaJump(url);
                                OkHttpUtil.reqADLog(WelcomeActivity.this,resp.getaId(),"1");
                            }
                        });
                    }
                }
            }
        }
    }

    private void urlSchemaJump(String url){
        switch (GeneralUtils.urlSchemaForWhat(url)){
            case 1:
                String urlSchemaDetail = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaDetail != null){
                    Intent intent = new Intent(WelcomeActivity.this, SchoolDetailActivity.class);
                    intent.putExtra("uCode", urlSchemaDetail);
                    intent.putExtra("back_to_home", "1");
                    startActivity(intent);
                    finish();
                }
                break;
            case 2:
                String urlSchemaList = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaList != null){
                    Intent intent = new Intent(WelcomeActivity.this, SchoolListActivity.class);
                    intent.putExtra("searchKey", urlSchemaList);
                    intent.putExtra("back_to_home", "1");
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                Intent skipIntent = new Intent(WelcomeActivity.this, WebviewActivity.class);
                skipIntent.putExtra("wev_view_url",url);
                skipIntent.putExtra("back_to_home", "1");
                startActivity(skipIntent);
                finish();
                break;
        }
    }


    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.skip_layout:
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        
    }
    
    //����ʱ
    private class MyTime extends CountDownTimer
    {
        
        public MyTime(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }
        
        @Override
        public void onFinish()
        {
            skip_txt.setText("跳过");
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        
        @Override
        public void onTick(long millisUntilFinished)
        {
            skip_txt.setText("跳过" + millisUntilFinished / 1000);
        }
        
    }
}
