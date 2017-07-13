/*
 * �� �� ��:  ZZPointSearch.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-12-1
 
 */
package com.jsksy.app.ui.zz;

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
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.ToastUtil;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-12-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class ZZPointSearchActivity extends BaseActivity implements OnClickListener
{
    private EditText num, ticket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.zz_point_search);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("中职学考");
        
        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    
    public void onSubmit()
    {
        String sNum = num.getText().toString().trim();
        String sTicket = ticket.getText().toString().trim();
        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "请输入你的考籍号");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket) || sTicket.length()<6)
        {
            ToastUtil.makeText(this, "请输入你的身份证号后6位");
            return;
        }
        
        Intent intent = new Intent(this,ZZPointResultActivity.class);
        intent.putExtra("sNum", sNum);
        intent.putExtra("sTicket", sTicket);
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
