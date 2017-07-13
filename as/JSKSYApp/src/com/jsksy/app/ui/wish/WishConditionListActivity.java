/*
 * 文 件 名:  HomeActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-8
 
 */
package com.jsksy.app.ui.wish;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.wish.adapter.WishConditionAdapter;
import com.jsksy.app.view.SectionListAdapter;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WishConditionListActivity extends BaseActivity implements OnClickListener
{
    private String[] cList;
    
    private String choise;
    
    private String type; //1-录取批次  2-院校省份  3-院校类型  4-开设专业
    
    private WishConditionAdapter cAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_condition_list);
        choise = getIntent().getStringExtra("choise");
        type = getIntent().getStringExtra("type");
        if ("1".equals(type))
        {
            cList = Constants.BATCH_DATA;
        }
        else if ("2".equals(type))
        {
            cList = Constants.PROV_DATA;
        }
        else if ("3".equals(type))
        {
            cList = Constants.SCHOOL_DATA;
        }
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText(getIntent().getStringExtra("title"));
        app_title_back.setOnClickListener(this);
        
        ListView cListView = (ListView)findViewById(R.id.fresh_news_listview);
        if ("4".equals(type))
        {
            SectionListAdapter adapter = new SectionListAdapter(this);
            adapter.addSection(Constants.MAJOR_DATA.DATA1.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA1.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA2.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA2.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA3.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA3.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA4.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA4.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA5.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA5.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA6.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA6.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA7.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA7.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA8.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA8.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA9.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA9.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA10.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA10.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA11.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA11.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA12.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA12.getInfo(), this, choise, type));
            adapter.addSection(Constants.MAJOR_DATA.DATA13.getCode(), new WishConditionAdapter(this, Constants.MAJOR_DATA.DATA13.getInfo(), this, choise, type));
            cListView.setAdapter(adapter);
        }
        else
        {
            //List渲染
            cAdapter = new WishConditionAdapter(this, cList, this, choise, type);
            cListView.setAdapter(cAdapter);
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
            default:
                break;
        }
    }
}
