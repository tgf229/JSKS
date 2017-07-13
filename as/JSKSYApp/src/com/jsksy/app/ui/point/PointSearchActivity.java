/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jsksy.app.R;
import com.jsksy.app.bean.gk.NewsDoc;
import com.jsksy.app.bean.gk.NewsResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private LinearLayout point_ad_layout,point_ad_tips;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.point_search);
        init();
        reqAdList();
    }
    
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("高考查分");
        app_title_back.setOnClickListener(this);
        
        num = (EditText)findViewById(R.id.num);
        ticket = (EditText)findViewById(R.id.ticket);
        TextView check_txt = (TextView)findViewById(R.id.check_txt);
        
        Random random = new Random();
        String checkA_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkA_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        String checkB_Num = Constants.POINT_CHECK_NUM[random.nextInt(8)];
        String checkB_Point = Constants.POINT_CHECK_POINT[random.nextInt(8)];
        
        sCheckKeyA = checkA_Num + checkA_Point;
        sCheckKeyB = checkB_Num + checkB_Point;
        check_txt.setText(sCheckKeyA + " : " + sCheckKeyB);
        
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);

        point_ad_layout = (LinearLayout) findViewById(R.id.point_ad_layout);
        point_ad_tips = (LinearLayout) findViewById(R.id.point_ad_tips);
    }
    
    private void onSubmit()
    {
        String sNum = num.getText().toString().trim();
        String sTicket = ticket.getText().toString().trim();

//        sNum = "16320102000006";
//        sTicket = "178847";
//        sCheckKeyA = "A1";
//        sCheckKeyB = "A2";

        if (GeneralUtils.isNullOrZeroLenght(sNum))
        {
            ToastUtil.makeText(this, "请输入你的考生号");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(sTicket))
        {
            ToastUtil.makeText(this, "请输入你的动态口令");
            return;
        }
        
        Intent intent = new Intent(this, PointResultActivity.class);
        intent.putExtra("sNum", sNum);
        intent.putExtra("sTicket", sTicket);
        intent.putExtra("sCheckKeyA", sCheckKeyA);
        intent.putExtra("sCheckKeyB", sCheckKeyB);
        startActivity(intent);
    }

    private void reqAdList(){
        Map<String, String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("type", "2");
        OkHttpUtil.sendRequestPost(URLUtil.Bus100501, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final NewsResponse newsResponse = JSON.parseObject(result,NewsResponse.class);
                if (Constants.SUCESS_CODE.equals(newsResponse.getRetcode())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showList(newsResponse);
                        }
                    });
                }
            }
        });
    }

    private void showList(final NewsResponse newsResponse){
        point_ad_layout.removeAllViews();
        if (newsResponse.getDoc().size() > 0){
            point_ad_tips.setVisibility(View.VISIBLE);
        }else{
            point_ad_tips.setVisibility(View.GONE);
        }
        for (final NewsDoc bean:newsResponse.getDoc()){
            View view = LayoutInflater.from(this).inflate(R.layout.gk_home_list_adv_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = bean.getaUrl();
                    urlSchemaJump(url);
                    OkHttpUtil.reqADLog(PointSearchActivity.this,bean.getaId(),"6");
                }
            });
            Glide.with(this).load(bean.getImageUrl()).into(img);
            point_ad_layout.addView(view);
        }
    }


    private void urlSchemaJump(String url){
        switch (GeneralUtils.urlSchemaForWhat(url)){
            case 1:
                String urlSchemaDetail = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaDetail != null){
                    Intent intent = new Intent(this, SchoolDetailActivity.class);
                    intent.putExtra("uCode", urlSchemaDetail);
                    startActivity(intent);
                }
                break;
            case 2:
                String urlSchemaList = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaList != null){
                    Intent intent = new Intent(this, SchoolListActivity.class);
                    intent.putExtra("searchKey", urlSchemaList);
                    this.startActivity(intent);
                }
                break;
            default:
                Intent skipIntent = new Intent(this, WebviewActivity.class);
                skipIntent.putExtra("wev_view_url",url);
                this.startActivity(skipIntent);
                break;
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
            case R.id.btn:
                onSubmit();
                break;
            default:
                break;
        }
    }
}
