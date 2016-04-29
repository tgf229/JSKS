/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
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
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
        title_name.setText("�߿����");
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
        sName.setText("������" + obj.getsName() + "    �����ţ�"+sNum);
        totalName.setText(obj.getTotalName() + obj.getTotalLevel() + "��");
        chinesePoint.setText(obj.getChinese() + "��");
        mathPoint.setText(obj.getMath() + "��");
        englishPoint.setText(obj.getEnglish() + "��");
        
        //KM4 KM5ͼƬ����
        filterImg(obj);
        KM4_name.setText(obj.getKM4Name());
        KM4_level.setText(obj.getKM4Level());
        KM5_name.setText(obj.getKM5Name());
        KM5_level.setText(obj.getKM5Level());
        
        poAdd.setText(obj.getPoAdd() + "��");
        addTitle.setText("�����ཱ����");
        addPoint.setText(obj.getCmAdd() + "��");
        
        if (GeneralUtils.isNullOrZeroLenght(obj.getTotalName()))
        {
            rank_layout.setVisibility(View.GONE);
        }
        
        //��
        if ("1".equals(obj.getType()))
        {
            totalTitle.setText("�Ŀ����ܷ�");
            totalPoint.setText(obj.getChTotal());
            chineseTitle.setText("����+���ӷ�");
            chinesePoint.setText(obj.getChinese() + "��+" + obj.getChAdd() + "��");
        }
        //��
        else if ("2".equals(obj.getType()))
        {
            totalTitle.setText("������ܷ�");
            totalPoint.setText(obj.getMaTotal());
            mathTitle.setText("��ѧ+���ӷ�");
            mathPoint.setText(obj.getMath() + "��+" + obj.getMaAdd() + "��");
        }
        //����       ����
        else if ("3".equals(obj.getType()) || "4".equals(obj.getType()))
        {
            totalTitle.setText("�����Ļ��ܷ�");
            totalPoint.setText(obj.getSaTotal());
            addTitle.setText("�����ཱ����");
            addPoint.setText(obj.getSaAdd() + "��");
            addPic.setImageResource(R.drawable.point_icon_b_add);
            KM_layout.setVisibility(View.GONE);
        }
        //��������       ��������
        else if ("5".equals(obj.getType()) || "7".equals(obj.getType()))
        {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("�Ŀ����ܷ�");
            DOUBLEPoint.setText(obj.getChTotal());
            SAPoint.setText(obj.getSaTotal());
            chineseTitle.setText("����+���ӷ�");
            chinesePoint.setText(obj.getChinese() + "��+" + obj.getChAdd() + "��");
            saAdd.setText(obj.getSaAdd());
        }
        //��������       ��������
        else if ("6".equals(obj.getType()) || "8".equals(obj.getType()))
        {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("������ܷ�");
            DOUBLEPoint.setText(obj.getMaTotal());
            SAPoint.setText(obj.getSaTotal());
            mathTitle.setText("��ѧ+���ӷ�");
            mathPoint.setText(obj.getMath() + "��+" + obj.getMaAdd() + "��");
            saAdd.setText(obj.getSaAdd());
        }
    }
    
    private void filterImg(PointResponse obj)
    {
        if ("����".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_dili);
        }
        else if ("��ѧ".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_huaxue);
        }
        else if ("��ʷ".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_lishi);
        }
        else if ("����".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_shengwu);
        }
        else if ("����".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_wuli);
        }
        else if ("����".equals(obj.getKM4Name()))
        {
            KM4_img.setImageResource(R.drawable.point_icon_zhengzhi);
        }
        
        if ("����".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_dili);
        }
        else if ("��ѧ".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_huaxue);
        }
        else if ("��ʷ".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_lishi);
        }
        else if ("����".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_shengwu);
        }
        else if ("����".equals(obj.getKM5Name()))
        {
            KM5_img.setImageResource(R.drawable.point_icon_wuli);
        }
        else if ("����".equals(obj.getKM5Name()))
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
