/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.ToastUtil;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-18]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class PointSearchActivity extends BaseActivity implements OnClickListener
{
    private EditText num, ticket;
    
    private String sCheckKeyA, sCheckKeyB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.point_search);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("�߿����");
        app_title_back.setOnClickListener(this);
        
        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        TextView check_txt = (TextView)findViewById(R.id.check_txt);
        
        Random random = new Random();//�����������
        String checkA_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkA_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        String checkB_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkB_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        
        sCheckKeyA = checkA_Num + checkA_Point;
        sCheckKeyB = checkB_Num + checkB_Point;
        check_txt.setText(sCheckKeyA + " : " + sCheckKeyB);
        
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    
    private void onSubmit()
    {
        String sNum = num.getText().toString().trim();
        String sTicket = ticket.getText().toString().trim();
        
        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "��������Ŀ�����");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket))
        {
            ToastUtil.makeText(this, "��������Ķ�̬����");
            return;
        }
        
        Intent intent = new Intent(this, PointResultActivity.class);
        intent.putExtra("sNum", sNum);
        intent.putExtra("sTicket", sTicket);
        intent.putExtra("sCheckKeyA", sCheckKeyA);
        intent.putExtra("sCheckKeyB", sCheckKeyB);
        startActivity(intent);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.btn:
                onSubmit();
                break;
            default:
                break;
        }
    }
}
