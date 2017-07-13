package com.jsksy.app.ui.school;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.EnrollDoc;
import com.jsksy.app.bean.school.EnrollItemDoc;
import com.jsksy.app.bean.school.EnrollListResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.school.adapter.DetailEnrollListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SchoolDetailEnrollActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SchoolDetailEnroll";
    private ArrayList<EnrollItemDoc> mList = new ArrayList<EnrollItemDoc>();
    private DetailEnrollListAdapter adapter;
    private String uCode;
    private String year;
    private LinearLayout load_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail_enroll);

        uCode = getIntent().getStringExtra("uCode");
        year = getIntent().getStringExtra("year");

        initView();
        reqEnrollList();
    }

    private void initView(){
        LinearLayout app_title_back = (LinearLayout) findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("招生计划");

        load_layout = (LinearLayout) findViewById(R.id.load_layout);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(manager);
        adapter = new DetailEnrollListAdapter(mList);
        recycler_view.setAdapter(adapter);
    }

    private void reqEnrollList(){
        Map<String,String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("code",uCode);
        param.put("year",year);
        OkHttpUtil.sendRequestPost(URLUtil.Bus700501,param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: "+result);

                Gson gson = new Gson();
                final EnrollListResponse bean = gson.fromJson(result,EnrollListResponse.class);
                for(EnrollDoc b: bean.getDoc()){
                    EnrollItemDoc item = new EnrollItemDoc();
                    item.setHead(true);
                    item.setClazz(b.getClazz());
                    item.setBatch(b.getBatch());
                    if (b.getDoc().size()>0){
                        item.setRank(b.getDoc().get(0).getRank());
                    }
                    mList.add(item);
                    mList.addAll(b.getDoc());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        if (mList.size() == 0){
                            load_layout.setVisibility(View.VISIBLE);
                        }else{
                            load_layout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
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
