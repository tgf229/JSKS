package com.jsksy.app.ui.school.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolDetailInfo;

/**
 * Created by 涂高峰 on 17/4/19.
 */
public class SchoolDetailFragmentOne extends Fragment implements View.OnClickListener{
    private static final String TAG = "SchoolDetailFragmentOne";
    public TextView intro_txt,ratio_txt,type_txt,tel_txt,address_txt;
    private ProgressBar ratio_progress;
    private SchoolDetailInfo detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_detail_one,container,false);
        ImageView btn_call = (ImageView) view.findViewById(R.id.btn_call);
        btn_call.setOnClickListener(this);
        intro_txt = (TextView) view.findViewById(R.id.intro_txt);
        ratio_progress = (ProgressBar) view.findViewById(R.id.ratio_progress);
        ratio_txt = (TextView) view.findViewById(R.id.ratio_txt);
        type_txt = (TextView) view.findViewById(R.id.type_txt);
        tel_txt = (TextView) view.findViewById(R.id.tel_txt);
        address_txt = (TextView) view.findViewById(R.id.address_txt);

        if (savedInstanceState != null){
            showDetail((SchoolDetailInfo)savedInstanceState.getParcelable("detail"));
        }
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            showDetail((SchoolDetailInfo)savedInstanceState.getParcelable("detail"));
        }
    }

    public void showDetail(SchoolDetailInfo bean){
        detail = bean;
        if (TextUtils.isEmpty(bean.getMaleRatio()) || TextUtils.isEmpty(bean.getFemaleRatio())){
            ratio_txt.setText("未知 : 未知");
        }else{
            ratio_progress.setProgress(Integer.parseInt(bean.getMaleRatio()));
            ratio_txt.setText(bean.getMaleRatio()+":"+bean.getFemaleRatio());
        }
        type_txt.setText("类型: "+bean.getType());
        tel_txt.setText("电话: "+bean.getTel());
        address_txt.setText("地址: "+bean.getAddress());
        intro_txt.setText(bean.getIntroduce());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("detail",detail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_call:
                if (!TextUtils.isEmpty(detail.getTel())){
                    String tel = detail.getTel().split(",")[0];
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+tel));
                    startActivity(intent);
                }
                break;

        }
    }
}
