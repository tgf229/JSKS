/*
 * 文 件 名:  HomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
 */
package com.jsksy.app.ui.wish;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.home.NewsResponse;
import com.jsksy.app.bean.wish.WishDoc;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.wish.adapter.WishListAdapter;
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
public class WishListActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    
    private ArrayList<WishDoc> wishList;
    
    private WishListAdapter wishListAdapter;
    
    private View headView;
    
    private String batchId = "1";
    private String batchVal =  "本科一批";
    private String provId = "";
    private String provVal = "全国";
    private String schoolId = "";
    private String schoolVal = "";
    private String majorId = "";
    private String majorVal = "";
    private boolean isEYY = false; //是否211
    private boolean isJBW = false; //是否985
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_list);
        init();
        reqList();
    }
    
    private void init()
    {
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.home_main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取资料");
        app_title_back.setOnClickListener(this);
        
        //head渲染
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wish_list_head, null);
        LinearLayout condition_layout = (LinearLayout)headView.findViewById(R.id.condition_layout);
        condition_layout.setOnClickListener(this);
        
        //List渲染
        ListView wishListView = (ListView)findViewById(R.id.fresh_news_listview);
        wishListView.addHeaderView(headView);
        wishList = new ArrayList<WishDoc>();
        wishListAdapter = new WishListAdapter(this, wishList, this);
        wishListView.setAdapter(wishListAdapter);
    }
    
    private void reqList()
    {
        //测试
        WishDoc doc = new WishDoc();
        doc.setCode("1101");
        doc.setName("南京大学");
        doc.setBatch("本科一批");
        doc.setNum("159");
        
        WishDoc doc1 = new WishDoc();
        doc1.setCode("1102");
        doc1.setName("南京航空航天大学");
        doc1.setBatch("本科一批");
        doc1.setNum("168");
        wishList.add(doc);
        wishList.add(doc1);
        wishListAdapter.notifyDataSetChanged();
        //        Map<String, String> param = new HashMap<String, String>();
        //        param.put("cId", "1");
        //        param.put("uId", "1");
        //        param.put("page", "1");
        //        param.put("num", "10");
        //        ConnectService.instance().connectServiceReturnResponse(this,
        //            param,
        //            WishListActivity.this,
        //            NewsResponse.class,
        //            URLUtil.Bus302301,
        //            Constants.ENCRYPT_NONE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        mPullToRefreshView.onHeaderRefreshComplete();
        if (ob instanceof NewsResponse)
        {
            //            WishResponse resp = (WishResponse)ob;
            //            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            //            {
            //                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
            //                {
            //                    wishList.addAll(resp.getDoc());
            //                    wishListAdapter.notifyDataSetChanged();
            //                }
            //            }
        }
    }
    
    @Override
    public void onHeaderRefresh(PullToRefreshView view)
    {
        wishList.clear();
        reqList();
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
                break;
            default:
                break;
        }
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
                break;
            default:
                break;
        }
    }
}
