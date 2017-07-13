/*
 * 文 件 名:  HomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
 */
package com.jsksy.app.ui.wish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.wish.WishDoc;
import com.jsksy.app.bean.wish.WishResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.wish.adapter.WishListAdapter;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.SecurityUtils;
import com.jsksy.app.view.PullToRefreshView;
import com.jsksy.app.view.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WishListActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    
    private ArrayList<WishDoc> wishList;
    
    private WishListAdapter wishListAdapter;
    
    private View headView;

    private LinearLayout load_layout,title_call_layout,school_layout,major_layout,eyy_layout,jbw_layout;
    
    private ProgressBar load_progress;
    
    private TextView load_txt,batch_val,prov_val,school_val,major_val;
    
    private com.jsksy.app.view.PullToRefreshView home_main_pull_refresh_view;
    
    private String batchId = "4";
    
    private String batchVal = "本科三批";
    
    private String provId = "";
    
    private String provVal = "全国";
    
    private String schoolId = "";
    
    private String schoolVal = "";
    
    private String majorId = "";
    
    private String majorVal = "";
    
    private boolean isEYY = false; //是否211
    
    private boolean isJBW = false; //是否985
    
    private String sNum, sTicket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_list);
        sNum = getIntent().getStringExtra("sNum");
        sTicket = getIntent().getStringExtra("sTicket");
        init();
        reqWish();
    }
    
    public String getBatchId()
    {
        return this.batchId;
    }
    
    private void init()
    {
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("志愿参考");
        app_title_back.setOnClickListener(this);
        
        title_call_layout = (LinearLayout)findViewById(R.id.title_call_layout);
        TextView title_btn_call = (TextView)findViewById(R.id.title_btn_call);
        title_btn_call.setText("筛选");
        title_call_layout.setVisibility(View.INVISIBLE);
        title_call_layout.setOnClickListener(this);
        
        load_layout = (LinearLayout)findViewById(R.id.load_layout);
        load_progress = (ProgressBar)findViewById(R.id.load_progress);
        load_txt = (TextView)findViewById(R.id.load_txt);
        
        home_main_pull_refresh_view = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        
        //head渲染
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wish_list_head, null);
        LinearLayout condition_layout = (LinearLayout)headView.findViewById(R.id.condition_layout);
        condition_layout.setOnClickListener(this);
        
        batch_val = (TextView)headView.findViewById(R.id.batch_val);
        prov_val = (TextView)headView.findViewById(R.id.prov_val);
        school_layout = (LinearLayout)headView.findViewById(R.id.school_layout);
        school_val = (TextView)headView.findViewById(R.id.school_val);
        major_layout = (LinearLayout)headView.findViewById(R.id.major_layout);
        major_val = (TextView)headView.findViewById(R.id.major_val);
        eyy_layout = (LinearLayout)headView.findViewById(R.id.eyy_layout);
        jbw_layout = (LinearLayout)headView.findViewById(R.id.jbw_layout);
        
        
        //List渲染
        ListView wishListView = (ListView)findViewById(R.id.fresh_news_listview);
        wishListView.addHeaderView(headView);
        wishList = new ArrayList<WishDoc>();
        wishListAdapter = new WishListAdapter(this, wishList, this,sNum,batchId);
        wishListView.setAdapter(wishListAdapter);
    }
    
    private void reqWish()
    {
        Map<String, String> param = new HashMap<String, String>();
        try
        {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sTicket", SecurityUtils.encode2Str(sTicket));
            param.put("isJbw", SecurityUtils.encode2Str(isJBW ? "1" : ""));
            param.put("isEyy", SecurityUtils.encode2Str(isEYY ? "1" : ""));
            param.put("batch", SecurityUtils.encode2Str(batchId));
            param.put("province", SecurityUtils.encode2Str(provId));
            param.put("type", SecurityUtils.encode2Str(schoolId));
            param.put("major", SecurityUtils.encode2Str(majorId));
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
    
    private void showList(ArrayList<WishDoc> docList)
    {
        for (WishDoc d : docList)
        {
            wishList.add(d);
        }
        wishListAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        mPullToRefreshView.onHeaderRefreshComplete();
        if (ob instanceof WishResponse)
        {
            WishResponse resp = (WishResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    title_call_layout.setVisibility(View.VISIBLE);
                    if (resp.getDoc().size()>0)
                    {
                        home_main_pull_refresh_view.setVisibility(View.VISIBLE);
                        load_layout.setVisibility(View.GONE);
                        showList(resp.getDoc());
                    }
                    else
                    {
                        home_main_pull_refresh_view.setVisibility(View.GONE);
                        load_layout.setVisibility(View.VISIBLE);
                        load_progress.setVisibility(View.GONE);
                        load_txt.setText("未查询到数据哦，点右上角筛选再试试吧");
                    }
                }
                else
                {
                    home_main_pull_refresh_view.setVisibility(View.GONE);
                    load_layout.setVisibility(View.VISIBLE);
                    load_progress.setVisibility(View.GONE);
                    load_txt.setText(resp.getRetinfo());
                    title_call_layout.setVisibility(View.INVISIBLE);
                }
            }
            else
            {
                home_main_pull_refresh_view.setVisibility(View.GONE);
                load_layout.setVisibility(View.VISIBLE);
                load_progress.setVisibility(View.GONE);
                load_txt.setText(Constants.ERROR_MESSAGE);
                title_call_layout.setVisibility(View.INVISIBLE);
            }
        }
    }
    
    @Override
    public void onHeaderRefresh(PullToRefreshView view)
    {
        wishList.clear();
        reqWish();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.condition_layout:
                conditionClick();
                break;
            case R.id.title_call_layout:
                conditionClick();
                break;
            default:
                break;
        }
    }
    
    private void conditionClick()
    {
        Intent intent = new Intent(this, WishConditionActivity.class);
        intent.putExtra("batchId", batchId);
        intent.putExtra("batchVal", batchVal);
        intent.putExtra("provId", provId);
        intent.putExtra("provVal", provVal);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("schoolVal", schoolVal);
        intent.putExtra("majorId", majorId);
        intent.putExtra("majorVal", majorVal);
        intent.putExtra("isEYY", isEYY);
        intent.putExtra("isJBW", isJBW);
        startActivityForResult(intent, 1001);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 1001:
                batchId = data.getStringExtra("batchId");
                batchVal = data.getStringExtra("batchVal");
                provId = data.getStringExtra("provId");
                provVal = data.getStringExtra("provVal");
                schoolId = data.getStringExtra("schoolId");
                schoolVal = data.getStringExtra("schoolVal");
                majorId = data.getStringExtra("majorId");
                majorVal = data.getStringExtra("majorVal");
                isEYY = data.getBooleanExtra("isEYY", false);
                isJBW = data.getBooleanExtra("isJBW", false);
                
                batch_val.setText(batchVal);
                prov_val.setText(provVal);
                if (GeneralUtils.isNullOrZeroLenght(schoolId))
                {
                    school_layout.setVisibility(View.GONE);
                }else
                {
                    school_layout.setVisibility(View.VISIBLE);
                    school_val.setText(schoolVal);
                }
                if (GeneralUtils.isNullOrZeroLenght(majorId))
                {
                    major_layout.setVisibility(View.GONE);
                }else
                {
                    major_layout.setVisibility(View.VISIBLE);
                    major_val.setText(majorVal);
                }
                if (isEYY)
                {
                    eyy_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    eyy_layout.setVisibility(View.GONE);
                }
                if (isJBW)
                {
                    jbw_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    jbw_layout.setVisibility(View.GONE);
                }
                
                
                wishList.clear();
                reqWish();
                break;
            default:
                break;
        }
    }
}
