/*
 * �� �� ��:  HomeActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-8
 
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
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-8]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WishListActivity extends BaseActivity implements OnHeaderRefreshListener, OnClickListener
{
    private PullToRefreshView mPullToRefreshView;
    
    private ArrayList<WishDoc> wishList;
    
    private WishListAdapter wishListAdapter;
    
    private View headView;
    
    private String batchId = "1";
    private String batchVal =  "����һ��";
    private String provId = "";
    private String provVal = "ȫ��";
    private String schoolId = "";
    private String schoolVal = "";
    private String majorId = "";
    private String majorVal = "";
    private boolean isEYY = false; //�Ƿ�211
    private boolean isJBW = false; //�Ƿ�985
    
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
        title_name.setText("¼ȡ����");
        app_title_back.setOnClickListener(this);
        
        //head��Ⱦ
        headView =
            ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wish_list_head, null);
        LinearLayout condition_layout = (LinearLayout)headView.findViewById(R.id.condition_layout);
        condition_layout.setOnClickListener(this);
        
        //List��Ⱦ
        ListView wishListView = (ListView)findViewById(R.id.fresh_news_listview);
        wishListView.addHeaderView(headView);
        wishList = new ArrayList<WishDoc>();
        wishListAdapter = new WishListAdapter(this, wishList, this);
        wishListView.setAdapter(wishListAdapter);
    }
    
    private void reqList()
    {
        //����
        WishDoc doc = new WishDoc();
        doc.setCode("1101");
        doc.setName("�Ͼ���ѧ");
        doc.setBatch("����һ��");
        doc.setNum("159");
        
        WishDoc doc1 = new WishDoc();
        doc1.setCode("1102");
        doc1.setName("�Ͼ����պ����ѧ");
        doc1.setBatch("����һ��");
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
