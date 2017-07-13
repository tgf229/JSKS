package com.jsksy.app.ui.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolListDoc;
import com.jsksy.app.bean.school.SchoolListResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.home.HomeActivity;
import com.jsksy.app.ui.school.adapter.SchoolListAdapter;
import com.jsksy.app.ui.wish.WishConditionActivity;
import com.jsksy.app.view.CustomLinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SchoolListActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SchoolListActivity";
    private ArrayList<SchoolListDoc> mList = new ArrayList<SchoolListDoc>();
    private SchoolListAdapter adapter;
    private String batchId = "";
    private String batchVal = "";
    private String provId = "";
    private String provVal = "全国";
    private String schoolId = "";
    private String schoolVal = "";
    private String majorId = "";
    private String majorVal = "";
    private boolean isEYY = false; //是否211
    private boolean isJBW = false; //是否985

    private int page = 1;
    private boolean isRefreshing = false;
    public boolean hasMore = true;
    private SwipeRefreshLayout swipe_refresh;
    private ImageView province_img,batch_img,feature_img,major_img,type_img;
    private EditText search_txt;
    private LinearLayout load_layout;
    private boolean isAdSchoolChannle = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        isAdSchoolChannle = getIntent().getBooleanExtra("isAdSchoolChannle",false);
        initView();
        initConditionView();
        reqSchoolList();
    }

    private void initView(){
        //筛选项栏
        LinearLayout condition_layout = (LinearLayout) findViewById(R.id.condition_layout);

        LinearLayout app_title_back = (LinearLayout) findViewById(R.id.app_title_back);
        app_title_back.setOnClickListener(this);
        TextView title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("院校列表");
        LinearLayout search_layout = (LinearLayout) findViewById(R.id.search_layout);
        search_txt = (EditText) findViewById(R.id.search_txt);
        search_txt.setText(getIntent().getStringExtra("searchKey"));
        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    mList.clear();
                    hasMore = true;
                    page=1;
                    reqSchoolList();
                }
                return false;
            }
        });

        //若为推荐院校列表 不展示筛选条件栏
        if (isAdSchoolChannle){
            condition_layout.setVisibility(View.GONE);
            title_name.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
        }else{
            condition_layout.setVisibility(View.VISIBLE);
            title_name.setVisibility(View.GONE);
            search_layout.setVisibility(View.VISIBLE);
        }

        load_layout = (LinearLayout) findViewById(R.id.load_layout);

        //列表初始化
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.color_67aef7);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                hasMore = true;
                page=1;
                reqSchoolList();
            }
        });
//        final LinearLayoutManager manager = new LinearLayoutManager(this);
        final CustomLinearLayoutManager manager = new CustomLinearLayoutManager(this);
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(manager);
        adapter = new SchoolListAdapter(mList);
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
                        reqSchoolList();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initConditionView(){
        LinearLayout province_layout = (LinearLayout) findViewById(R.id.province_layout);
        LinearLayout batch_layout = (LinearLayout) findViewById(R.id.batch_layout);
        LinearLayout feature_layout = (LinearLayout) findViewById(R.id.feature_layout);
        LinearLayout major_layout = (LinearLayout) findViewById(R.id.major_layout);
        LinearLayout type_layout = (LinearLayout) findViewById(R.id.type_layout);
        province_layout.setOnClickListener(this);
        batch_layout.setOnClickListener(this);
        feature_layout.setOnClickListener(this);
        major_layout.setOnClickListener(this);
        type_layout.setOnClickListener(this);
        province_img = (ImageView) findViewById(R.id.province_img);
        batch_img = (ImageView) findViewById(R.id.batch_img);
        feature_img = (ImageView) findViewById(R.id.feature_img);
        major_img = (ImageView) findViewById(R.id.major_img);
        type_img = (ImageView) findViewById(R.id.type_img);
    }

    private void reqSchoolList(){
        Map<String,String> param = new HashMap<String, String>();
        param.put("encrypt",Constants.ENCRYPT_NONE);
        if (isAdSchoolChannle)
        {
            param.put("channel","1");
        }
        param.put("uName",search_txt.getText().toString().trim());
        param.put("batch",batchId);
        param.put("province",provId);
        param.put("type",schoolId);
        param.put("major",majorId);
        param.put("isJbw",isJBW?"1":"");
        param.put("isEyy",isEYY?"1":"");
        param.put("page",String.valueOf(page));
        param.put("num", Constants.PAGE_NUM);
        OkHttpUtil.sendRequestPost(URLUtil.Bus700101,param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSchoolList();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: "+result);
//                Gson gson = new Gson();
//                SchoolListResponse bean = gson.fromJson(result, SchoolListResponse.class);

                final SchoolListResponse bean = JSON.parseObject(result,SchoolListResponse.class);

                if (bean.getDoc().size() < Integer.parseInt(Constants.PAGE_NUM)){
                    hasMore = false;
                }
                mList.addAll(bean.getDoc());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSchoolList();
                    }
                });
            }
        });
    }

    private void showSchoolList(){
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
    public void onBackPressed() {
        super.onBackPressed();
        if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.app_title_back:
                if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.province_layout:
                province_img.setImageResource(R.drawable.school_triangle_orange);
                conditionClick();
                break;
            case R.id.batch_layout:
                batch_img.setImageResource(R.drawable.school_triangle_orange);
                conditionClick();
                break;
            case R.id.feature_layout:
                feature_img.setImageResource(R.drawable.school_triangle_orange);
                conditionClick();
                break;
            case R.id.major_layout:
                major_img.setImageResource(R.drawable.school_triangle_orange);
                conditionClick();
                break;
            case R.id.type_layout:
                type_img.setImageResource(R.drawable.school_triangle_orange);
                conditionClick();
                break;
        }
    }

    private void conditionClick()
    {
        Intent intent = new Intent(this, WishConditionActivity.class);
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
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onResume() {
        super.onResume();
        conditionImg();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 1001:
                batchId = data.getStringExtra("batchId");
                batchVal = data.getStringExtra("batchVal");
                provId = data.getStringExtra("provId");
                provVal = data.getStringExtra("provVal");
                schoolId = data.getStringExtra("schoolId");
                schoolVal = data.getStringExtra("schoolVal");
                majorId = data.getStringExtra("majorId");
                majorVal = data.getStringExtra("majorVal");
                isEYY = data.getBooleanExtra("isEYY", false);
                isJBW = data.getBooleanExtra("isJBW", false);
                Log.i(TAG, "onActivityResult: batchId= "+batchId);
                Log.i(TAG, "onActivityResult: batchVal= "+batchVal);
                Log.i(TAG, "onActivityResult: provId= "+provId);
                Log.i(TAG, "onActivityResult: provVal= "+provVal);
                Log.i(TAG, "onActivityResult: schoolId= "+schoolId);
                Log.i(TAG, "onActivityResult: schoolVal= "+schoolVal);
                Log.i(TAG, "onActivityResult: majorId= "+majorId);
                Log.i(TAG, "onActivityResult: majorVal= "+majorVal);
                Log.i(TAG, "onActivityResult: isEYY= "+isEYY);
                Log.i(TAG, "onActivityResult: isJBW= "+isJBW);
                Log.i(TAG, "=========================================");

                conditionImg();

                mList.clear();
                hasMore = true;
                page=1;
                reqSchoolList();

                break;
            default:
                break;
        }
    }

    private void conditionImg(){
        if (TextUtils.isEmpty(provId)){
            province_img.setImageResource(R.drawable.school_triangle_grey);
        }else{
            province_img.setImageResource(R.drawable.school_triangle_orange);
        }
        if (TextUtils.isEmpty(batchId)){
            batch_img.setImageResource(R.drawable.school_triangle_grey);
        }else{
            batch_img.setImageResource(R.drawable.school_triangle_orange);
        }
        if (TextUtils.isEmpty(schoolId)){
            type_img.setImageResource(R.drawable.school_triangle_grey);
        }else{
            type_img.setImageResource(R.drawable.school_triangle_orange);
        }
        if (TextUtils.isEmpty(majorId)){
            major_img.setImageResource(R.drawable.school_triangle_grey);
        }else{
            major_img.setImageResource(R.drawable.school_triangle_orange);
        }
        if (isEYY || isJBW){
            feature_img.setImageResource(R.drawable.school_triangle_orange);
        }else{
            feature_img.setImageResource(R.drawable.school_triangle_grey);
        }
    }
}
