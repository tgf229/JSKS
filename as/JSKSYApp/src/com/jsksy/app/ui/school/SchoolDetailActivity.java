package com.jsksy.app.ui.school;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolDetailInfo;
import com.jsksy.app.bean.school.SchoolDetailResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseAppCompatActivity;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.ui.home.HomeActivity;
import com.jsksy.app.ui.school.adapter.DetailFragmentAdapter;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentOne;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentTwo;
import com.jsksy.app.view.MyImageView3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SchoolDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SchoolDetailActivity";
    private MyImageView3 bgImg;
    private CircleImageView logo;
    public SchoolDetailInfo mDetail;
    private TextView name_txt, type_txt, isEyy_txt, isJbw_txt, code_txt;
    private DetailFragmentAdapter adapter;
    private LocalBroadcastManager lbm;
    private String uCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_school_detail);
        uCode = getIntent().getStringExtra("uCode");
        initView();
        reqDetail(uCode);
    }

    public SchoolDetailInfo getmDetail() {
        return mDetail;
    }

    private void initView() {
        lbm = LocalBroadcastManager.getInstance(this);
        bgImg = (MyImageView3) findViewById(R.id.bgImg);
        logo = (CircleImageView) findViewById(R.id.logo);
        ImageView detail_btn_back = (ImageView) findViewById(R.id.detail_btn_back);
        detail_btn_back.setOnClickListener(this);
        ImageView detail_btn_web = (ImageView) findViewById(R.id.detail_btn_web);
        detail_btn_web.setOnClickListener(this);
        name_txt = (TextView) findViewById(R.id.name_txt);
        type_txt = (TextView) findViewById(R.id.type_txt);
        code_txt = (TextView) findViewById(R.id.code_txt);
        isEyy_txt = (TextView) findViewById(R.id.isEyy_txt);
        isJbw_txt = (TextView) findViewById(R.id.isJbw_txt);

        ViewPager view_pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new DetailFragmentAdapter(getSupportFragmentManager(),
                this);
        view_pager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(view_pager);
    }

    private void showDetail(SchoolDetailResponse bean) {
        mDetail = bean.getDetail();
        Glide.with(this).load(bean.getDetail().getPic()).into(bgImg);
        Glide.with(this).load(bean.getDetail().getLogo()).into(logo);
        name_txt.setText(bean.getDetail().getName());
        code_txt.setText("院校代号" + uCode);
        type_txt.setText(bean.getDetail().getType());
        if ("1".equals(bean.getDetail().getIsEyy())) {
            isEyy_txt.setVisibility(View.VISIBLE);
        } else {
            isEyy_txt.setVisibility(View.GONE);
        }
        if ("1".equals(bean.getDetail().getIsJbw())) {
            isJbw_txt.setVisibility(View.VISIBLE);
        } else {
            isJbw_txt.setVisibility(View.GONE);
        }
        SchoolDetailFragmentOne fragment_1 = (SchoolDetailFragmentOne) adapter.getRegisteredFragment(0);
        if (fragment_1 != null) {
            fragment_1.showDetail(bean.getDetail());
        }
        SchoolDetailFragmentTwo fragment_2 = (SchoolDetailFragmentTwo) adapter.getRegisteredFragment(1);
        if (fragment_2 != null) {
            fragment_2.showClazzDetail(bean.getDetail());
        }
    }

    private void reqDetail(String uCode) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("uCode", uCode);
        OkHttpUtil.sendRequestPost(URLUtil.Bus700201, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: " + result);
                final SchoolDetailResponse bean = JSON.parseObject(result, SchoolDetailResponse.class);
                if (Constants.SUCESS_CODE.equals(bean.getRetcode())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDetail(bean);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showError();
                        }
                    });
                }
            }
        });
    }

    private void showError() {
        Toast.makeText(this, Constants.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
            Intent intent = new Intent(SchoolDetailActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_btn_back:
                if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                    Intent intent = new Intent(SchoolDetailActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.detail_btn_web:
                String url = "http://"+mDetail.getWeb();
                Intent intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("wev_view_url", url);
                this.startActivity(intent);
                break;
        }
    }

    // ViewPager切换时NestedScrollView滑动到顶部
//        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                ((NestedScrollView) findViewById(R.id.nestedScrollView)).scrollTo(0, 0);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
}
