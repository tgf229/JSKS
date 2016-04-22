/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
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
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class WishConditionActivity extends BaseActivity implements OnClickListener
{
    private boolean isEYY = false; //�Ƿ�211
    
    private boolean isJBW = false; //�Ƿ�985
    
    private String batchId;
    private String provId;
    private String schoolId;
    private String majorId;
    
    private String batchVal;
    private String provVal;
    private String schoolVal;
    private String majorVal;
    
    private ImageView eyy_img, jbw_img;
    private TextView batch_val,prov_val,school_val,major_val;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_condition);
        batchId = getIntent().getStringExtra("batchId");
        provId = getIntent().getStringExtra("provId");
        schoolId = getIntent().getStringExtra("schoolId");
        majorId = getIntent().getStringExtra("majorId");
        isEYY = getIntent().getBooleanExtra("isEYY", false);
        isJBW = getIntent().getBooleanExtra("isJBW", false);
        batchVal = getIntent().getStringExtra("batchVal");
        provVal = getIntent().getStringExtra("provVal");
        schoolVal = getIntent().getStringExtra("schoolVal");
        majorVal = getIntent().getStringExtra("majorVal");
        
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("ɸѡ");
        app_title_back.setOnClickListener(this);
        
        TextView submit_btn = (TextView)findViewById(R.id.submit_btn);
        
        
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
        
        batch_val.setText(batchVal);
        prov_val.setText(provVal);
        school_val.setText(schoolVal);
        major_val.setText(majorVal);
        
        eyy_img = (ImageView)findViewById(R.id.eyy_img);
        jbw_img = (ImageView)findViewById(R.id.jbw_img);
        
        if (isEYY)
        {
            eyy_img.setImageResource(R.drawable.switch_on);
        }
        else
        {
            eyy_img.setImageResource(R.drawable.switch_off);
        }
        if (isJBW)
        {
            jbw_img.setImageResource(R.drawable.switch_on);
        }
        else
        {
            jbw_img.setImageResource(R.drawable.switch_off);
        }
        
        
        eyy_layout.setOnClickListener(this);
        jbw_layout.setOnClickListener(this);
        batch_layout.setOnClickListener(this);
        prov_layout.setOnClickListener(this);
        school_layout.setOnClickListener(this);
        major_layout.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
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
                intentBatch.putExtra("title", "¼ȡ����");
                startActivityForResult(intentBatch, 1001);
                break;
            case R.id.prov_layout:
                Intent intentProv = new Intent(this, WishConditionListActivity.class);
                intentProv.putExtra("type", "2");
                intentProv.putExtra("choise", provId);
                intentProv.putExtra("title", "ԺУʡ��");
                startActivityForResult(intentProv, 1002);
                break;
            case R.id.school_layout:
                Intent intentSchool = new Intent(this, WishConditionListActivity.class);
                intentSchool.putExtra("type", "3");
                intentSchool.putExtra("choise", schoolId);
                intentSchool.putExtra("title", "ԺУ����");
                startActivityForResult(intentSchool, 1003);
                break;
            case R.id.major_layout:
                Intent intentMajor = new Intent(this, WishConditionListActivity.class);
                intentMajor.putExtra("type", "4");
                intentMajor.putExtra("choise", majorId);
                intentMajor.putExtra("title", "����רҵ");
                startActivityForResult(intentMajor, 1004);
                break;
            case R.id.submit_btn:
                Intent intent = new Intent(this, WishListActivity.class);
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
                setResult(1001, intent);
                finish();
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
                batchVal = data.getStringExtra("val");
                break;
            case 1002:
                provId = data.getStringExtra("id");
                prov_val.setText(data.getStringExtra("val"));
                provVal = data.getStringExtra("val");
                break;
            case 1003:
                schoolId = data.getStringExtra("id");
                school_val.setText(data.getStringExtra("val"));
                schoolVal = data.getStringExtra("val");
                break;
            case 1004:
                majorId = data.getStringExtra("id");
                major_val.setText(data.getStringExtra("val"));
                majorVal = data.getStringExtra("val");
                break;
            default:
                break;
        }
    }
}
