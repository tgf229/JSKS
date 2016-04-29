/*
 * 文 件 名:  PointSearchActivity.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.point.PointResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PointResultActivity extends BaseActivity implements OnClickListener
{
    private LinearLayout load_layout, content_layout, KM_layout, rank_layout, 
            single_layout, double_layout,double_saadd_layout;
    
    private ProgressBar load_progress;
    
    private TextView load_txt, sName, totalTitle, totalPoint, totalName, chineseTitle, chinesePoint, mathTitle,
        mathPoint, englishPoint, KM4_level, KM4_name, KM5_level, KM5_name, poAdd, addTitle, addPoint, SAPoint,
        DOUBLETitle, DOUBLEPoint,saAdd;
    
    private ImageView KM4_img, KM5_img, addPic;
    private String sNum,sTicket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_result);
        sNum = getIntent().getStringExtra("sNum");
        sTicket = getIntent().getStringExtra("sTicket");
        init();
        reqPoint();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("高考查分");
        app_title_back.setOnClickListener(this);
        
        load_layout = (LinearLayout)findViewById(R.id.load_layout);
        load_progress = (ProgressBar)findViewById(R.id.load_progress);
        load_txt = (TextView)findViewById(R.id.load_txt);
        
        content_layout = (LinearLayout)findViewById(R.id.content_layout);
        single_layout = (LinearLayout)findViewById(R.id.single_layout);
        double_layout = (LinearLayout)findViewById(R.id.double_layout);
        sName = (TextView)findViewById(R.id.sName);
        totalTitle = (TextView)findViewById(R.id.totalTitle);
        totalPoint = (TextView)findViewById(R.id.totalPoint);
        totalName = (TextView)findViewById(R.id.totalName);
        DOUBLETitle = (TextView)findViewById(R.id.DOUBLETitle);
        DOUBLEPoint = (TextView)findViewById(R.id.DOUBLEPoint);
        SAPoint = (TextView)findViewById(R.id.SAPoint);
        rank_layout = (LinearLayout)findViewById(R.id.rank_layout);
        
        chineseTitle = (TextView)findViewById(R.id.chineseTitle);
        chinesePoint = (TextView)findViewById(R.id.chinesePoint);
        mathTitle = (TextView)findViewById(R.id.mathTitle);
        mathPoint = (TextView)findViewById(R.id.mathPoint);
        englishPoint = (TextView)findViewById(R.id.englishPoint);
        
        KM4_img = (ImageView)findViewById(R.id.KM4_img);
        KM5_img = (ImageView)findViewById(R.id.KM5_img);
        KM4_level = (TextView)findViewById(R.id.KM4_level);
        KM4_name = (TextView)findViewById(R.id.KM4_name);
        KM5_level = (TextView)findViewById(R.id.KM5_level);
        KM5_name = (TextView)findViewById(R.id.KM5_name);
        KM_layout = (LinearLayout)findViewById(R.id.KM_layout);
        
        poAdd = (TextView)findViewById(R.id.poAdd);
        addTitle = (TextView)findViewById(R.id.addTitle);
        addPoint = (TextView)findViewById(R.id.addPoint);
        addPic = (ImageView)findViewById(R.id.addPic);
        
        double_saadd_layout = (LinearLayout)findViewById(R.id.double_saadd_layout);
        saAdd = (TextView)findViewById(R.id.saAdd);
    }
    
    private void reqPoint()
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sNum", sNum);
        param.put("sTicket", sTicket);
        ConnectService.instance().connectServiceReturnResponse(this,
            param,
            PointResultActivity.this,
            PointResponse.class,
            URLUtil.Bus200201,
            Constants.ENCRYPT_NONE);
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
        sName.setText("姓名：" + obj.getsName() + "    考生号："+sNum);
        totalName.setText(obj.getTotalName() + obj.getTotalLevel() + "名");
        chinesePoint.setText(obj.getChinese() + "分");
        mathPoint.setText(obj.getMath() + "分");
        englishPoint.setText(obj.getEnglish() + "分");
        
        //KM4 KM5图片过滤
        filterImg(obj);
        KM4_name.setText(obj.getKM4Name());
        KM4_level.setText(obj.getKM4Level());
        KM5_name.setText(obj.getKM5Name());
        KM5_level.setText(obj.getKM5Level());
        
        poAdd.setText(obj.getPoAdd() + "分");
        addTitle.setText("文理类奖励分");
        addPoint.setText(obj.getCmAdd() + "分");
        
        if (GeneralUtils.isNullOrZeroLenght(obj.getTotalName()))
        {
            rank_layout.setVisibility(View.GONE);
        }
        
        //文
        if ("1".equals(obj.getType()))
        {
            totalTitle.setText("文科类总分");
            totalPoint.setText(obj.getChTotal());
            chineseTitle.setText("语文+附加分");
            chinesePoint.setText(obj.getChinese() + "分+" + obj.getChAdd() + "分");
        }
        //理
        else if ("2".equals(obj.getType()))
        {
            totalTitle.setText("理科类总分");
            totalPoint.setText(obj.getMaTotal());
            mathTitle.setText("数学+附加分");
            mathPoint.setText(obj.getMath() + "分+" + obj.getMaAdd() + "分");
        }
        //艺术       体育
        else if ("3".equals(obj.getType()) || "4".equals(obj.getType()))
        {
            totalTitle.setText("体艺文化总分");
            totalPoint.setText(obj.getSaTotal());
            addTitle.setText("体艺类奖励分");
            addPoint.setText(obj.getSaAdd() + "分");
            addPic.setImageResource(R.drawable.point_icon_b_add);
            KM_layout.setVisibility(View.GONE);
        }
        //艺术兼文       体育兼文
        else if ("5".equals(obj.getType()) || "7".equals(obj.getType()))
        {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("文科类总分");
            DOUBLEPoint.setText(obj.getChTotal());
            SAPoint.setText(obj.getSaTotal());
            chineseTitle.setText("语文+附加分");
            chinesePoint.setText(obj.getChinese() + "分+" + obj.getChAdd() + "分");
            saAdd.setText(obj.getSaAdd());
        }
        //艺术兼理       体育兼理
        else if ("6".equals(obj.getType()) || "8".equals(obj.getType()))
        {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("理科类总分");
            DOUBLEPoint.setText(obj.getMaTotal());
            SAPoint.setText(obj.getSaTotal());
            mathTitle.setText("数学+附加分");
            mathPoint.setText(obj.getMath() + "分+" + obj.getMaAdd() + "分");
            saAdd.setText(obj.getSaAdd());
        }
    }
    
    private void filterImg(PointResponse obj)
    {
        if ("地理".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_dili);
        }
        else if ("化学".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_huaxue);
        }
        else if ("历史".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_lishi);
        }
        else if ("生物".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_shengwu);
        }
        else if ("物理".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_wuli);
        }
        else if ("政治".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_zhengzhi);
        }
        
        if ("地理".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_dili);
        }
        else if ("化学".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_huaxue);
        }
        else if ("历史".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_lishi);
        }
        else if ("生物".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_shengwu);
        }
        else if ("物理".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_wuli);
        }
        else if ("政治".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_zhengzhi);
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
