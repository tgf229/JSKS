/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.wish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.view.SectionListAdapter;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WishConditionActivity extends BaseActivity implements OnClickListener
{
    private boolean isEYY = false; //是否211
    
    private boolean isJBW = false; //是否985
    
    private String batchId;
    private String provId;
    private String schoolId;
    private String majorId;
    private ImageView eyy_img, jbw_img;
    private TextView batch_val,prov_val,school_val,major_val;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_condition);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("筛选");
        app_title_back.setOnClickListener(this);
        
        LinearLayout eyy_layout = (LinearLayout)findViewById(R.id.eyy_layout);
        LinearLayout jbw_layout = (LinearLayout)findViewById(R.id.jbw_layout);
        LinearLayout batch_layout = (LinearLayout)findViewById(R.id.batch_layout);
        batch_val = (TextView)findViewById(R.id.batch_val);
        LinearLayout prov_layout = (LinearLayout)findViewById(R.id.prov_layout);
        prov_val = (TextView)findViewById(R.id.prov_val);
        LinearLayout school_layout = (LinearLayout)findViewById(R.id.school_layout);
        school_val = (TextView)findViewById(R.id.school_val);
        LinearLayout major_layout = (LinearLayout)findViewById(R.id.major_layout);
        major_val = (TextView)findViewById(R.id.major_val);
        
        eyy_img = (ImageView)findViewById(R.id.eyy_img);
        jbw_img = (ImageView)findViewById(R.id.jbw_img);
        
        eyy_layout.setOnClickListener(this);
        jbw_layout.setOnClickListener(this);
        batch_layout.setOnClickListener(this);
        prov_layout.setOnClickListener(this);
        school_layout.setOnClickListener(this);
        major_layout.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.eyy_layout:
                if (isEYY)
                {
                    isEYY = false;
                    eyy_img.setImageResource(R.drawable.switch_off);
                }
                else
                {
                    isEYY = true;
                    eyy_img.setImageResource(R.drawable.switch_on);
                }
                break;
            case R.id.jbw_layout:
                if (isJBW)
                {
                    isJBW = false;
                    jbw_img.setImageResource(R.drawable.switch_off);
                }
                else
                {
                    isJBW = true;
                    jbw_img.setImageResource(R.drawable.switch_on);
                }
                break;
            case R.id.batch_layout:
                Intent intentBatch = new Intent(this, WishConditionListActivity.class);
                intentBatch.putExtra("type", "1");
                intentBatch.putExtra("choise", batchId);
                intentBatch.putExtra("title", "录取批次");
                startActivityForResult(intentBatch, 1001);
                break;
            case R.id.prov_layout:
                Intent intentProv = new Intent(this, WishConditionListActivity.class);
                intentProv.putExtra("type", "2");
                intentProv.putExtra("choise", provId);
                intentProv.putExtra("title", "院校省份");
                startActivityForResult(intentProv, 1002);
                break;
            case R.id.school_layout:
                Intent intentSchool = new Intent(this, WishConditionListActivity.class);
                intentSchool.putExtra("type", "3");
                intentSchool.putExtra("choise", schoolId);
                intentSchool.putExtra("title", "院校类型");
                startActivityForResult(intentSchool, 1003);
                break;
            case R.id.major_layout:
                Intent intentMajor = new Intent(this, WishConditionListActivity.class);
                intentMajor.putExtra("type", "4");
                intentMajor.putExtra("choise", majorId);
                intentMajor.putExtra("title", "开设专业");
                startActivityForResult(intentMajor, 1004);
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
                batchId = data.getStringExtra("id");
                batch_val.setText(data.getStringExtra("val"));
                break;
            case 1002:
                provId = data.getStringExtra("id");
                prov_val.setText(data.getStringExtra("val"));
                break;
            case 1003:
                schoolId = data.getStringExtra("id");
                school_val.setText(data.getStringExtra("val"));
                break;
            case 1004:
                majorId = data.getStringExtra("id");
                major_val.setText(data.getStringExtra("val"));
                break;
            default:
                break;
        }
    }
}
