/*
 * ?? ?? ??:  HomeActivity.java
 * ??    ?:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ??    ??:  <????>
 * ??    ???? <?汾??> 
 * ?? ?? ??:  tgf
 * ???????:  2016-4-8
 
 */
package com.jsksy.app.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.bean.gk.NewsResponse;
import com.jsksy.app.bean.home.BannerDoc;
import com.jsksy.app.bean.home.BannerResponse;
import com.jsksy.app.bean.home.InitResponse;
import com.jsksy.app.bean.home.UpdateVersionResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.callback.DialogCallBack;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.gk.GKHomeActivity;
import com.jsksy.app.ui.gk.adapter.GKNewsAdapter;
import com.jsksy.app.ui.home.adapter.BannerAdapter;
import com.jsksy.app.ui.point.PointSearchActivity;
import com.jsksy.app.ui.point.PointWaitActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.ui.set.SetActivity;
import com.jsksy.app.ui.wish.WishAgreementActivity;
import com.jsksy.app.ui.zz.ZZPointSearchActivity;
import com.jsksy.app.util.DialogUtil;
import com.jsksy.app.util.DownApkUtil;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.ToastUtil;
import com.jsksy.app.view.MyImageView;
import com.jsksy.app.view.PullToRefreshView;
import com.jsksy.app.view.PullToRefreshView.OnHeaderRefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <??仰???????>
 * <???????????>
 *
 * @author tgf
 * @version [?汾??, 2016-4-8]
 * @see  [?????/????]
 * @since [???/???汾]
 */
public class HomeActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener {
    private PullToRefreshView mPullToRefreshView;

    private ViewPager banner_Pager;

    private MyImageView default_img;

    private View headView;

    private ArrayList<com.jsksy.app.bean.gk.NewsDoc> freshNewsList;

    private ArrayList<BannerDoc> bannerList;

    private GKNewsAdapter freshNewsAdapter;

    private BannerAdapter bannerAdapter;

    private CirclePageIndicator banner_indicator;

    private boolean isRefreshing = false;

    private NetLoadingDailog dailog;

    /**
     * ???????????
     */
    private long downTime;

    private String waitType = "1";

    /**
     * ????apk?????
     */
    private DownApkUtil downApkUtil;

    /**
     * ????汾??
     */
    private String versionName;

    /**
     * handle?????涨????,????apk???apk
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                int postion = banner_Pager.getCurrentItem() + 1;
                if (null != bannerList && bannerList.size() > 0)
                    banner_Pager.setCurrentItem(postion % bannerList.size(), true);
                handler.sendEmptyMessageDelayed(0, Constants.SKIP_TIME);
            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case Constants.DOWNLOAD:
                    // ?????????λ??  
                    downApkUtil.updateProgress();
                    break;
                case Constants.DOWNLOAD_FINISH:
                    // ??????  
                    downApkUtil.installApk();
                    break;
                case Constants.NO_SD:
                    ToastUtil.makeText(HomeActivity.this, "?????SD??");
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        reqBanner();
        reqList();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
//        }else{
        reqInit();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//        }else{
            reqUpdateVersion();
//        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (event.getDownTime() - downTime <= 2000)
            {
                JSKSYApplication.jsksyApplication.onTerminate();
            }
            else
            {
                Toast.makeText(this, getString(R.string.home_back), Toast.LENGTH_SHORT).show();
                downTime = event.getDownTime();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private void init()
    {
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        
        //title???
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        app_title_back.setVisibility(View.GONE);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText(getString(R.string.app_name));
        
        //title?????????
        LinearLayout title_call_layout = (LinearLayout)findViewById(R.id.title_call_layout);
        TextView title_btn_call = (TextView)findViewById(R.id.title_btn_call);
        title_btn_call.setBackgroundResource(R.drawable.setting);
        title_call_layout.setVisibility(View.VISIBLE);
        title_call_layout.setOnClickListener(this);
        
        //head???
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_list_head, null);
        banner_Pager = (ViewPager)headView.findViewById(R.id.circlepager);
        banner_Pager.setVisibility(View.VISIBLE);
        default_img = (MyImageView)headView.findViewById(R.id.default_banner);
        default_img.setVisibility(View.VISIBLE);
        bannerList = new ArrayList<BannerDoc>();
        bannerAdapter = new BannerAdapter(this, bannerList,"2");
        banner_Pager.setAdapter(bannerAdapter);
        banner_indicator = (CirclePageIndicator)headView.findViewById(R.id.circleindicator);
        banner_indicator.setViewPager(banner_Pager);
        handler.sendEmptyMessageDelayed(0, Constants.SKIP_TIME);
        
        ImageView gk_img = (ImageView)headView.findViewById(R.id.gk_img);
        ImageView zz_img = (ImageView)headView.findViewById(R.id.zz_img);
        ImageView school_img = (ImageView) headView.findViewById(R.id.school_img);
        gk_img.setOnClickListener(this);
        zz_img.setOnClickListener(this);
        school_img.setOnClickListener(this);
        
        //        //footer???
        //        loadingFooterView =
        //            ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading, null);
        //        endTips = (RelativeLayout)loadingFooterView.findViewById(R.id.end_tips);
        //        loadingMore = (LinearLayout)loadingFooterView.findViewById(R.id.loading_more);
        //        loadingMore.setVisibility(View.GONE);
        
        //List???
        ListView freshNewsListView = (ListView)findViewById(R.id.fresh_news_listview);
        freshNewsListView.addHeaderView(headView);
        //        freshNewsListView.addFooterView(loadingFooterView);
        freshNewsList = new ArrayList<com.jsksy.app.bean.gk.NewsDoc>();
        freshNewsAdapter = new GKNewsAdapter(this, freshNewsList, this,"3");
        freshNewsListView.setAdapter(freshNewsAdapter);
        //        freshNewsListView.setOnScrollListener(new OnScrollListener()
        //        {
        //            @Override
        //            public void onScrollStateChanged(AbsListView view, int scrollState)
        //            {
        //                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && anyMore && !isRefreshing
        //                    && view.getLastVisiblePosition() == view.getCount() - 1)
        //                {
        //                    loadingMore.setVisibility(View.VISIBLE);
        //                    isRefreshing = true;
        //                    page++;
        //                    reqList();
        //                }
        //            }
        //            
        //            @Override
        //            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        //            {
        //                
        //            }
        //        });
    }
    
    /**
     * 
     * <?????>
     * <???????????>
     * @see [????#????????#???]
     */
    private void reqInit()
    {
            Map<String, String> param = new HashMap<String, String>();
            param.put("version", GeneralUtils.getVersionName(this));
            param.put("type", Constants.clientType);
            param.put("model", android.os.Build.MODEL);
//        try {
            param.put("imei", GeneralUtils.getDeviceId(this));
//        }catch (Exception e){
//            param.put("imei","0");
//        }
            ConnectService.instance().connectServiceReturnResponse(this,
                    param,
                    HomeActivity.this,
                    InitResponse.class,
                    URLUtil.Bus100101,
                    Constants.ENCRYPT_NONE);
    }
    
    /**
     * 
     * <???????????б?>
     * <???????????>
     * @see [????#????????#???]
     */
    private void reqBanner()
    {
        Map<String, String> param = new HashMap<String, String>();
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            HomeActivity.this,
            BannerResponse.class,
            URLUtil.Bus100201,
            Constants.ENCRYPT_NONE);
    }
    
    private void reqList()
    {
        Map<String, String> param = new HashMap<String, String>();
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            HomeActivity.this,
            NewsResponse.class,
            URLUtil.Bus100501,
            Constants.ENCRYPT_NONE);
    }
    
    /**
     * <???????У????>
     * <???????????>
     * @see [????#????????#???]
     */
    private void reqPointTime(String type)
    {
        waitType = type;
        Map<String, String> param = new HashMap<String, String>();
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            HomeActivity.this,
            PointTimeResponse.class,
            URLUtil.Bus200101,
            Constants.ENCRYPT_NONE);
    }
    
    /**
     * 
     * <???汾????>
     * <???????????>
     * @see [????#????????#???]
     */
    private void reqUpdateVersion()
    {

        versionName = GeneralUtils.getVersionName(this);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("version", "" + versionName);
        param.put("type", "" + Constants.clientType);
//        try {
//            param.put("imei", GeneralUtils.getDeviceId(this));
//        }catch (Exception e){
            param.put("imei","0");
//        }
            ConnectService.instance().connectServiceReturnResponse(this,
                    param,
                    HomeActivity.this,
                    UpdateVersionResponse.class,
                    URLUtil.Bus500101,
                    Constants.ENCRYPT_NONE);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        mPullToRefreshView.onHeaderRefreshComplete();
        if (ob instanceof NewsResponse)
        {
            isRefreshing = false;
            //            loadingMore.setVisibility(View.GONE);
            
            NewsResponse resp = (NewsResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    freshNewsList.clear();
                    freshNewsList.addAll(resp.getDoc());
                    freshNewsAdapter.notifyDataSetChanged();
                }
                else
                {
                }
            }
            else
            {
            }
        }
        else if (ob instanceof BannerResponse)
        {
            BannerResponse resp = (BannerResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    ArrayList<BannerDoc> tempPlans = resp.getDoc();
                    if (null != tempPlans && tempPlans.size() > 0)
                    {
                        bannerList.clear();
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = width * 7 / 15 + 60;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                        banner_Pager.setLayoutParams(params);
                        
                        bannerList.addAll(tempPlans);
                        banner_indicator.setVisibility(View.VISIBLE);
                        banner_Pager.setVisibility(View.VISIBLE);
                        default_img.setVisibility(View.GONE);
                        
                        bannerAdapter.notifyDataSetChanged();
                        banner_Pager.setCurrentItem(0);
                        banner_indicator.notifyDataSetChanged();
                    }
                    else
                    {
                        banner_indicator.setVisibility(View.GONE);
                        banner_Pager.setVisibility(View.GONE);
                        default_img.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    banner_indicator.setVisibility(View.GONE);
                    banner_Pager.setVisibility(View.GONE);
                    default_img.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                banner_indicator.setVisibility(View.GONE);
                banner_Pager.setVisibility(View.GONE);
                default_img.setVisibility(View.VISIBLE);
                bannerList.clear();
                banner_indicator.notifyDataSetChanged();
            }
        }
        else if (ob instanceof InitResponse)
        {
            InitResponse initresp = (InitResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(initresp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(initresp.getRetcode()))
                {
                    String flag = initresp.getFlag();
                    
                    if (flag != null && flag.equals(Constants.EXIT_APP))
                    {
                        JSKSYApplication.jsksyApplication.onTerminate();
                        return;
                    }
                    if (GeneralUtils.isNotNull(initresp.getConf())){
                        String gkAdSchool = initresp.getConf().getGkAdSchool();
                        if (GeneralUtils.isNotNullOrZeroLenght(gkAdSchool)){
                            SharePref.saveString(SharePref.STORAGE_CONF_GKADSCHOOL,gkAdSchool);
                        }
                    }
                }
            }
        }
        else if (ob instanceof PointTimeResponse)
        {
            dailog.dismissDialog();
            PointTimeResponse ptrest = (PointTimeResponse)ob;
            
            if (GeneralUtils.isNotNullOrZeroLenght(ptrest.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(ptrest.getRetcode()))
                {
                    if ("1".equals(waitType))
                    {
                        if (Double.parseDouble(ptrest.getExTime()) > Double.parseDouble(ptrest.getCuTime()))
                        {
                            Intent intentPoint = new Intent(this, PointWaitActivity.class);
                            intentPoint.putExtra("cuTime", ptrest.getCuTime());
                            intentPoint.putExtra("exTime", ptrest.getExTime());
                            intentPoint.putExtra("wsTime", ptrest.getWsTime());
                            intentPoint.putExtra("waitType", waitType);
                            startActivity(intentPoint);
                        }
                        else
                        {
                            Intent intentPoint = new Intent(this, PointSearchActivity.class);
                            startActivity(intentPoint);
                        }
                    }
                    else if ("2".equals(waitType))
                    {
                        if (Double.parseDouble(ptrest.getWsTime()) > Double.parseDouble(ptrest.getCuTime()))
                        {
                            Intent intentPoint = new Intent(this, PointWaitActivity.class);
                            intentPoint.putExtra("cuTime", ptrest.getCuTime());
                            intentPoint.putExtra("exTime", ptrest.getExTime());
                            intentPoint.putExtra("wsTime", ptrest.getWsTime());
                            intentPoint.putExtra("waitType", waitType);
                            startActivity(intentPoint);
                        }
                        else
                        {
                            Intent intentPoint = new Intent(this, WishAgreementActivity.class);
                            startActivity(intentPoint);
                        }
                    }
                }
                else
                {
                    ToastUtil.makeText(this, ptrest.getRetinfo());
                    //                    if ("2".equals(waitType))
                    //                    {
                    //                        Intent intentPoint = new Intent(this, WishSearchActivity.class);
                    //                        startActivity(intentPoint);
                    //                    }
                    //                    else
                    //                    {
                    //                        Intent intentPoint = new Intent(this, PointSearchActivity.class);
                    //                        startActivity(intentPoint);
                    //                    }
                }
            }
            else
            {
                ToastUtil.makeText(this, "????????????????????????");
                //                if ("2".equals(waitType))
                //                {
                //                    Intent intentPoint = new Intent(this, WishSearchActivity.class);
                //                    startActivity(intentPoint);
                //                }
                //                else
                //                {
                //                    Intent intentPoint = new Intent(this, PointSearchActivity.class);
                //                    startActivity(intentPoint);
                //                }
            }
        }
        else if (ob instanceof UpdateVersionResponse)
        {
            final UpdateVersionResponse response = (UpdateVersionResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(response.getRetcode()))
            {
                if (response.getRetcode().equals(Constants.SUCESS_CODE))
                {
                    downApkUtil = new DownApkUtil(this, handler, response.getUrlAddress());
                    
                    String[] contentString = response.getContent().split(";");
                    String cancel = getResources().getString(R.string.set_update_cancel);
                    
                    if ("1".equals(response.getIsUpdate()))
                    {
                        cancel = getResources().getString(R.string.set_update_quit);
                    }
                    DialogUtil.showUpdateDialog(this,
                        getResources().getString(R.string.updateVersionTitel),
                        contentString,
                        getResources().getString(R.string.updateVersion),
                        cancel,
                        response.getIsUpdate(),
                        new DialogCallBack()
                        {
                            
                            @Override
                            public void dialogBack()
                            {
                                downApkUtil.downApk(response.getIsUpdate());
                            }
                        });
                }
                else
                {
                    //                    ToastUtil.makeText(getActivity(), response.getRetinfo());
                }
            }
            else
            {
                //                ToastUtil.showError(getActivity());
            }
        }
    }
    
    @Override
    public void onHeaderRefresh(PullToRefreshView view)
    {
        freshNewsList.clear();
        reqBanner();
        reqList();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.gk_img:
                Intent intentGK = new Intent(this, GKHomeActivity.class);
                startActivity(intentGK);
                break;
            case R.id.zz_img:
                Intent intentZZ = new Intent(this, ZZPointSearchActivity.class);
                startActivity(intentZZ);
                break;
            case R.id.title_call_layout:
                Intent intentSet = new Intent(this, SetActivity.class);
                startActivity(intentSet);
                break;
            case R.id.school_img:
                startActivity(new Intent(this, SchoolListActivity.class));
                break;
            default:
                break;
        }
    }

//    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    reqUpdateVersion();
                }else{
                }
                break;
        }
    }
}