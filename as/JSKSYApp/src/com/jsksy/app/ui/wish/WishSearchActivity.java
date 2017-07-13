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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
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
public class WishSearchActivity extends BaseActivity implements OnClickListener
{
    /**
     * ��������ȴ���
     */
    private NetLoadingDailog dailog = new NetLoadingDailog(this);
    private EditText num, ticket;
    private String sNum, sTicket;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.wish_search);
        init();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("志愿参考");
        app_title_back.setOnClickListener(this);
        
        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    
    private void onSubmit()
    {
        sNum = num.getText().toString().trim();
        sTicket = ticket.getText().toString().trim();
        
        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "请输入你的考生号");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket))
        {
            ToastUtil.makeText(this, "请输入你的准考证号");
            return;
        }

        // V2.3.0跳转H5版本入口
        String url = URLUtil.WISH_ENTRY+"sNum="+sNum+"&sTicket="+sTicket;
        Intent intent = new Intent(WishSearchActivity.this, WebviewActivity.class);
        intent.putExtra("wev_view_url", url);
        intent.putExtra("isWishChannel",true);
        startActivity(intent);
        
//        Intent intent = new Intent(this, WishListActivity.class);
//        intent.putExtra("sNum", sNum);
//        intent.putExtra("sTicket", sTicket);
//        startActivity(intent);
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
//                ToastUtil.makeText(this, "暂未开放,请耐心等待");
                break;
            default:
                break;
        }
    }
}
