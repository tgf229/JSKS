/*
 * 文 件 名:  HomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
 */
package com.jsksy.app.ui.gk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.gk.NewsDoc;
import com.jsksy.app.bean.gk.NewsResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.gk.adapter.GKNewsAdapter;
import com.jsksy.app.ui.home.adapter.FreshNewsAdapter;
import com.jsksy.app.ui.offer.OfferSearchActivity;
import com.jsksy.app.ui.point.PointSearchActivity;
import com.jsksy.app.ui.point.PointWaitActivity;
import com.jsksy.app.ui.wish.WishAgreementActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.ToastUtil;
import com.jsksy.app.view.PullToRefreshView;
import com.jsksy.app.view.PullToRefreshView.OnHeaderRefreshListener;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GKHomeActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    
    private View headView, loadingFooterView;
    
    private ArrayList<NewsDoc> freshNewsList;
    
    private GKNewsAdapter freshNewsAdapter;
    
    private RelativeLayout endTips;
    
    private LinearLayout loadingMore;
    
    private boolean anyMore = true;
    
    private boolean isRefreshing = false;
    
    private int page = 1;
    
    private int pageNum = 10;
    
    private NetLoadingDailog dailog;
    
    private String waitType = "1";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gk_home);
        init();
        reqList();
    }
    
    private void init()
    {
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        
        //title渲染
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("高考频道");
        
        //head渲染
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.gk_home_list_head, null);
        
        LinearLayout point_layout = (LinearLayout)headView.findViewById(R.id.point_layout);
        LinearLayout wish_layout = (LinearLayout)headView.findViewById(R.id.wish_layout);
        LinearLayout offer_layout = (LinearLayout)headView.findViewById(R.id.offer_layout);
        point_layout.setOnClickListener(this);
        wish_layout.setOnClickListener(this);
        offer_layout.setOnClickListener(this);
        
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
        freshNewsAdapter = new GKNewsAdapter(this, freshNewsList, this);
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
    
    private void reqList()
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("page", String.valueOf(page));
        param.put("num", String.valueOf(pageNum));
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            GKHomeActivity.this,
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
            GKHomeActivity.this,
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
                    else if("2".equals(waitType))
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
                }
            }
            else
            {
                ToastUtil.makeText(this, "网络异常，请检查您的网络设置");
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
            case R.id.app_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
