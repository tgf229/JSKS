/*
 * 文 件 名:  ZZPointResultActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-12-1
 
 */
package com.jsksy.app.ui.zz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.zz.PointDoc;
import com.jsksy.app.bean.zz.PointResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.zz.adapter.ZZPointResultAdapter;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.SecurityUtils;
import com.jsksy.app.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-12-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ZZPointResultActivity extends BaseActivity implements OnClickListener
{
    private LinearLayout load_layout, content_layout;
    
    private ProgressBar load_progress;
    
    private TextView load_txt,name_txt,num_txt,card_txt;
    
    private String sNum, sTicket;
    
    private List<PointDoc> mList;
    
    private ZZPointResultAdapter listAdapter;
    
    private MyGridView point_grid;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zz_point_result);
        sNum = getIntent().getStringExtra("sNum");
        sTicket = getIntent().getStringExtra("sTicket");
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("中职学考成绩查询");
        app_title_back.setOnClickListener(this);
        
        load_layout = (LinearLayout)findViewById(R.id.load_layout);
        load_progress = (ProgressBar)findViewById(R.id.load_progress);
        load_txt = (TextView)findViewById(R.id.load_txt);
        
        name_txt = (TextView)findViewById(R.id.name_txt);
        num_txt = (TextView)findViewById(R.id.num_txt);
        card_txt = (TextView)findViewById(R.id.card_txt);
        
        content_layout = (LinearLayout)findViewById(R.id.content_layout);
        point_grid = (MyGridView)findViewById(R.id.point_grid);
        mList = new ArrayList<PointDoc>();
        listAdapter = new ZZPointResultAdapter(this, mList, this);
        point_grid.setAdapter(listAdapter);
        
        reqPoint();
    }
    
    private void reqPoint()
    {
        Map<String, String> param = new HashMap<String, String>();
        try
        {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sTicket", SecurityUtils.encode2Str(sTicket));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            ZZPointResultActivity.this,
            PointResponse.class,
            URLUtil.Bus600101,
            Constants.ENCRYPT_SIMPLE);
    }
    
    @Override
    public void netBack(Object ob)
    {
        super.netBack(ob);
        if (ob instanceof PointResponse)
        {
            PointResponse resp = (PointResponse)ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode()))
            {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode()))
                {
                    content_layout.setVisibility(View.VISIBLE);
                    load_layout.setVisibility(View.GONE);
                    showPoint(resp);
                }
                else
                {
                    content_layout.setVisibility(View.GONE);
                    load_layout.setVisibility(View.VISIBLE);
                    load_progress.setVisibility(View.GONE);
                    load_txt.setText(resp.getRetinfo());
                }
            }
            else
            {
                content_layout.setVisibility(View.GONE);
                load_layout.setVisibility(View.VISIBLE);
                load_progress.setVisibility(View.GONE);
                load_txt.setText(Constants.ERROR_MESSAGE);
            }
        }
    }
    
    private void showPoint(PointResponse obj)
    {
        name_txt.setText(obj.getsName());
        num_txt.setText(obj.getsNum());
        card_txt.setText(obj.getsIdCard());
        mList.clear();
        for (PointDoc doc : obj.getDoc())
        {
            mList.add(doc);
        }
        listAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            
            default:
                break;
        }
    }
}
