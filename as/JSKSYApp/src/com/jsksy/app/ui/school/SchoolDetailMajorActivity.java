package com.jsksy.app.ui.school;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.MajorDoc;
import com.jsksy.app.bean.school.MajorListResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.school.adapter.DetailMajorListAdapter;
import com.jsksy.app.view.CustomLinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SchoolDetailMajorActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SchoolDetailMajor";
    private ArrayList<MajorDoc> mList = new ArrayList<MajorDoc>();
    private DetailMajorListAdapter adapter;
    private LinearLayout load_layout;

    private int page = 1;
    private boolean isRefreshing = false;
    public boolean hasMore = true;
    private SwipeRefreshLayout swipe_refresh;
    private String uCode;
    private String year;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail_major);

        uCode = getIntent().getStringExtra("uCode");
        year = getIntent().getStringExtra("year");

        initView();
        reqMajorList();
    }

    private void initView(){
        LinearLayout app_title_back = (LinearLayout) findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("专业录取分数线");

        load_layout = (LinearLayout) findViewById(R.id.load_layout);

        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.color_67aef7);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                hasMore = true;
                page=1;
                reqMajorList();
            }
        });

//        final LinearLayoutManager manager = new LinearLayoutManager(this);
        final CustomLinearLayoutManager manager = new CustomLinearLayoutManager(this);
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(manager);
        adapter = new DetailMajorListAdapter(mList);
        recycler_view.setAdapter(adapter);
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition+1 == adapter.getItemCount()){
                    if (swipe_refresh.isRefreshing()){
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isRefreshing && hasMore){
                        isRefreshing = true;
                        page++;
                        reqMajorList();
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void reqMajorList(){
        Map<String,String> param = new HashMap<String, String>();
        param.put("encrypt","none");
        param.put("code",uCode);
        param.put("year",year);
        param.put("page",String.valueOf(page));
        param.put("num", Constants.PAGE_NUM);
        OkHttpUtil.sendRequestPost(URLUtil.Bus700401,param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMajorList();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: "+result);

                Gson gson = new Gson();
                MajorListResponse bean = gson.fromJson(result,MajorListResponse.class);
                if (bean.getDoc().size() < 10){
                    hasMore = false;
                }
                mList.addAll(bean.getDoc());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMajorList();
                    }
                });
            }
        });
    }

    private void showMajorList(){
        adapter.notifyDataSetChanged();
        swipe_refresh.setRefreshing(false);
        isRefreshing = false;
        adapter.notifyItemRemoved(adapter.getItemCount());
        if (mList.size() == 0){
            load_layout.setVisibility(View.VISIBLE);
        }else{
            load_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.app_title_back:
                finish();
                break;
        }
    }
}
