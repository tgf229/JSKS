package com.jsksy.app.ui.gk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsksy.app.R;
import com.jsksy.app.bean.gk.NewsDoc;
import com.jsksy.app.bean.gk.NewsResponse;
import com.jsksy.app.bean.home.BannerDoc;
import com.jsksy.app.bean.home.BannerResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.gk.adapter.GKNewsAdapter;
import com.jsksy.app.ui.home.adapter.BannerAdapter;
import com.jsksy.app.ui.offer.OfferSearchActivity;
import com.jsksy.app.ui.point.PointSearchActivity;
import com.jsksy.app.ui.point.PointWaitActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.ui.wish.WishAgreementActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.ToastUtil;
import com.jsksy.app.view.MyImageView;
import com.jsksy.app.view.MyImageView2;
import com.jsksy.app.view.PullToRefreshView;
import com.jsksy.app.view.PullToRefreshView.OnHeaderRefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class GKHomeActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    private ViewPager banner_Pager;
    private MyImageView default_img;
    private ArrayList<BannerDoc> bannerList;
    private BannerAdapter bannerAdapter;
    private CirclePageIndicator banner_indicator;

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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gk_home);
        init();
        reqBanner();
        reqList();
    }

    private void init()
    {
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);

        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("高考频道");

        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.gk_home_list_head, null);
        banner_Pager = (ViewPager)headView.findViewById(R.id.circlepager);
        banner_Pager.setVisibility(View.VISIBLE);
        default_img = (MyImageView)headView.findViewById(R.id.default_banner);
        default_img.setVisibility(View.VISIBLE);
        bannerList = new ArrayList<BannerDoc>();
        bannerAdapter = new BannerAdapter(this, bannerList,"4");
        banner_Pager.setAdapter(bannerAdapter);
        banner_indicator = (CirclePageIndicator)headView.findViewById(R.id.circleindicator);
        banner_indicator.setViewPager(banner_Pager);
        handler.sendEmptyMessageDelayed(0, Constants.SKIP_TIME);

        LinearLayout point_layout = (LinearLayout)headView.findViewById(R.id.point_layout);
        LinearLayout wish_layout = (LinearLayout)headView.findViewById(R.id.wish_layout);
        LinearLayout offer_layout = (LinearLayout)headView.findViewById(R.id.offer_layout);
        LinearLayout school_layout = (LinearLayout)headView.findViewById(R.id.school_layout);
        RelativeLayout school_img = (RelativeLayout) headView.findViewById(R.id.school_img);
        MyImageView2 school_img_view = (MyImageView2) headView.findViewById(R.id.school_img_view);
        point_layout.setOnClickListener(this);
        wish_layout.setOnClickListener(this);
        offer_layout.setOnClickListener(this);
        school_layout.setOnClickListener(this);
        school_img.setOnClickListener(this);
        Glide.with(this).load("http://sdata.jseea.cn:8081/imgs/edu/university/yxfc.png").into(school_img_view);

        String isConditionShow = SharePref.getString(SharePref.STORAGE_CONF_GKADSCHOOL,"");
        //若初始化接口配置项为1 则推荐院校列表展示
        if ("1".equals(isConditionShow)){
            school_img.setVisibility(View.VISIBLE);
        }else{
            school_img.setVisibility(View.GONE);
        }

//        loadingFooterView =
//            ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading, null);
//        endTips = (RelativeLayout)loadingFooterView.findViewById(R.id.end_tips);
//        loadingMore = (LinearLayout)loadingFooterView.findViewById(R.id.loading_more);
//        loadingMore.setVisibility(View.GONE);

        ListView freshNewsListView = (ListView)findViewById(R.id.fresh_news_listview);
        freshNewsListView.addHeaderView(headView);
//        freshNewsListView.addFooterView(loadingFooterView);
        freshNewsList = new ArrayList<NewsDoc>();
        freshNewsAdapter = new GKNewsAdapter(this, freshNewsList, this,"5");
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

    private void reqBanner()
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type","1");
        ConnectService.instance().connectServiceReturnResponse(this,
                param,
                GKHomeActivity.this,
                BannerResponse.class,
                URLUtil.Bus100201,
                Constants.ENCRYPT_NONE);
    }

    private void reqList()
    {
        Map<String, String> param = new HashMap<String, String>();
//        param.put("page", String.valueOf(page));
//        param.put("num", String.valueOf(pageNum));
        param.put("type","1");
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            GKHomeActivity.this,
            NewsResponse.class,
            URLUtil.Bus100501,
            Constants.ENCRYPT_NONE);
    }

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
//            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
//            {
//                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
//                {
//                    if (page == 1)
//                    {
//                        freshNewsList.clear();
//                    }
//                    if (resp.getDoc().size() < pageNum)
//                    {
//                        anyMore = false;
//                        endTips.setVisibility(View.VISIBLE);
//                    }
//                    freshNewsList.addAll(resp.getDoc());
//                    freshNewsAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    anyMore = false;
//                }
//            }
//            else
//            {
//                anyMore = false;
//            }
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
        else if (ob instanceof PointTimeResponse)
        {
            dailog.dismissDialog();
            PointTimeResponse ptrest = (PointTimeResponse)ob;

            //TODO ?????? ?????
//                        ptrest.setCuTime("20160616000000");
//                        ptrest.setExTime("20160615000003");
//                        ptrest.setWsTime("20160615000005");

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
                ToastUtil.makeText(this, "网络异常,请检查网络");
            }
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view)
    {
//        page = 1;
        freshNewsList.clear();
//        endTips.setVisibility(View.GONE);
//        anyMore = true;
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
            case R.id.app_title_back:
                finish();
                break;
            case R.id.school_img:
                Intent intentSchool = new Intent(this,SchoolListActivity.class);
                intentSchool.putExtra("isAdSchoolChannle",true); //是推荐学校列表
                startActivity(intentSchool);
                break;
            case R.id.school_layout:
                startActivity(new Intent(this,SchoolListActivity.class));
                break;
            default:
                break;
        }
    }
}
