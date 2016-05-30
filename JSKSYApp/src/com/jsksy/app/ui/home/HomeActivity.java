/*
 * 文 件 名:  HomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
 */
package com.jsksy.app.ui.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.bean.home.BannerDoc;
import com.jsksy.app.bean.home.BannerResponse;
import com.jsksy.app.bean.home.InitResponse;
import com.jsksy.app.bean.home.NewsDoc;
import com.jsksy.app.bean.home.NewsResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.home.adapter.BannerAdapter;
import com.jsksy.app.ui.home.adapter.FreshNewsAdapter;
import com.jsksy.app.ui.offer.OfferSearchActivity;
import com.jsksy.app.ui.point.PointSearchActivity;
import com.jsksy.app.ui.point.PointWaitActivity;
import com.jsksy.app.ui.set.SetActivity;
import com.jsksy.app.ui.wish.WishSearchActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.view.MyImageView;
import com.jsksy.app.view.PullToRefreshView;
import com.jsksy.app.view.PullToRefreshView.OnHeaderRefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HomeActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    
    private ViewPager banner_Pager;
    
    private MyImageView default_img;
    
    private View headView, loadingFooterView;
    
    private ArrayList<NewsDoc> freshNewsList;
    
    private ArrayList<BannerDoc> bannerList;
    
    private FreshNewsAdapter freshNewsAdapter;
    
    private BannerAdapter bannerAdapter;
    
    private CirclePageIndicator banner_indicator;
    
    private RelativeLayout endTips;
    
    private LinearLayout loadingMore;
    
    private boolean anyMore = true;
    
    private boolean isRefreshing = false;
    
    private int page = 1;
    
    private int pageNum = 10;
    
    private NetLoadingDailog dailog;
    
    /**
     * 跳转时间
     */
    private final int SKIP_TIME = 5 * 1000;
    
    /**
     * 上次退出的时间
     */
    private long downTime;
    
    private String waitType = "1";
    
    /**
     * handle接受广告定时跳转,下载apk后安装apk
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                int postion = banner_Pager.getCurrentItem() + 1;
                if (null != bannerList && bannerList.size() > 0)
                    banner_Pager.setCurrentItem(postion % bannerList.size(), true);
                handler.sendEmptyMessageDelayed(0, SKIP_TIME);
            }
        }
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        reqBanner();
        reqList();
        reqInit();
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
        
        //title渲染
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        app_title_back.setVisibility(View.GONE);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText(getString(R.string.app_name));
        
        //head渲染
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_list_head, null);
        banner_Pager = (ViewPager)headView.findViewById(R.id.circlepager);
        banner_Pager.setVisibility(View.VISIBLE);
        default_img = (MyImageView)headView.findViewById(R.id.default_banner);
        default_img.setVisibility(View.VISIBLE);
        bannerList = new ArrayList<BannerDoc>();
        bannerAdapter = new BannerAdapter(this, bannerList);
        banner_Pager.setAdapter(bannerAdapter);
        banner_indicator = (CirclePageIndicator)headView.findViewById(R.id.circleindicator);
        banner_indicator.setViewPager(banner_Pager);
        handler.sendEmptyMessageDelayed(0, SKIP_TIME);
        
        LinearLayout point_layout = (LinearLayout)headView.findViewById(R.id.point_layout);
        LinearLayout wish_layout = (LinearLayout)headView.findViewById(R.id.wish_layout);
        LinearLayout offer_layout = (LinearLayout)headView.findViewById(R.id.offer_layout);
        LinearLayout set_layout = (LinearLayout)headView.findViewById(R.id.set_layout);
        point_layout.setOnClickListener(this);
        wish_layout.setOnClickListener(this);
        offer_layout.setOnClickListener(this);
        set_layout.setOnClickListener(this);
        
        //footer渲染
        loadingFooterView =
            ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading, null);
        endTips = (RelativeLayout)loadingFooterView.findViewById(R.id.end_tips);
        loadingMore = (LinearLayout)loadingFooterView.findViewById(R.id.loading_more);
        loadingMore.setVisibility(View.GONE);
        
        //List渲染
        ListView freshNewsListView = (ListView)findViewById(R.id.fresh_news_listview);
        freshNewsListView.addHeaderView(headView);
        freshNewsListView.addFooterView(loadingFooterView);
        freshNewsList = new ArrayList<NewsDoc>();
        freshNewsAdapter = new FreshNewsAdapter(this, freshNewsList, this);
        freshNewsListView.setAdapter(freshNewsAdapter);
        freshNewsListView.setOnScrollListener(new OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && anyMore && !isRefreshing
                    && view.getLastVisiblePosition() == view.getCount() - 1)
                {
                    loadingMore.setVisibility(View.VISIBLE);
                    isRefreshing = true;
                    page++;
                    reqList();
                }
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                
            }
        });
    }
    
    /**
     * 
     * <初始化>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    private void reqInit()
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("version", GeneralUtils.getVersionName(this));
        param.put("type", Constants.clientType);
        param.put("model", android.os.Build.MODEL);
        param.put("imei", GeneralUtils.getDeviceId(this));
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            HomeActivity.this,
            InitResponse.class,
            URLUtil.Bus100101,
            Constants.ENCRYPT_NONE);
    }
    
    /**
     * 
     * <请求轮播通告列表>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
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
        param.put("page", String.valueOf(page));
        param.put("num", String.valueOf(pageNum));
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            HomeActivity.this,
            NewsResponse.class,
            URLUtil.Bus100301,
            Constants.ENCRYPT_NONE);
    }
    
    /**
     * <访问时间校准接口>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
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
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        mPullToRefreshView.onHeaderRefreshComplete();
        if (ob instanceof NewsResponse)
        {
            isRefreshing = false;
            loadingMore.setVisibility(View.GONE);
            
            NewsResponse resp = (NewsResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    if (page == 1)
                    {
                        freshNewsList.clear();
                    }
                    if (resp.getDoc().size() < pageNum)
                    {
                        anyMore = false;
                        endTips.setVisibility(View.VISIBLE);
                    }
                    freshNewsList.addAll(resp.getDoc());
                    freshNewsAdapter.notifyDataSetChanged();
                }
                else
                {
                    anyMore = false;
                }
            }
            else
            {
                anyMore = false;
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
                // 请求失败显示默认图片
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
                    if (Double.parseDouble(ptrest.getExTime()) > Double.parseDouble(ptrest.getCuTime()))
                    {
                        Intent intentPoint = new Intent(this, PointWaitActivity.class);
                        intentPoint.putExtra("cuTime", ptrest.getCuTime());
                        intentPoint.putExtra("exTime", ptrest.getExTime());
                        intentPoint.putExtra("waitType", waitType);
                        startActivity(intentPoint);
                    }
                }
                else
                {
                    if ("2".equals(waitType))
                    {
                        Intent intentPoint = new Intent(this, WishSearchActivity.class);
                        startActivity(intentPoint);
                    }
                    else
                    {
                        Intent intentPoint = new Intent(this, PointSearchActivity.class);
                        startActivity(intentPoint);
                    }
                }
            }
            else
            {
                if ("2".equals(waitType))
                {
                    Intent intentPoint = new Intent(this, WishSearchActivity.class);
                    startActivity(intentPoint);
                }
                else
                {
                    Intent intentPoint = new Intent(this, PointSearchActivity.class);
                    startActivity(intentPoint);
                }
            }
        }
    }
    
    @Override
    public void onHeaderRefresh(PullToRefreshView view)
    {
        page = 1;
        freshNewsList.clear();
        endTips.setVisibility(View.GONE);
        anyMore = true;
        reqBanner();
        reqList();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.point_layout:
                dailog = new NetLoadingDailog(this);
                dailog.loading();
                reqPointTime("1");
                break;
            case R.id.offer_layout:
                Intent intentOffer = new Intent(this, OfferSearchActivity.class);
                startActivity(intentOffer);
                break;
            case R.id.wish_layout:
                dailog = new NetLoadingDailog(this);
                dailog.loading();
                reqPointTime("2");
                break;
            case R.id.set_layout:
                Intent intentSet = new Intent(this, SetActivity.class);
                startActivity(intentSet);
                break;
            default:
                break;
        }
    }
}
