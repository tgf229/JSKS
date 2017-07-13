package com.jsksy.app.ui.school.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolDetailInfo;
import com.jsksy.app.ui.school.SchoolDetailEnrollActivity;
import com.jsksy.app.util.GeneralUtils;

/**
 * Created by 涂高峰 on 17/4/19.
 */
public class SchoolDetailFragmentThree extends Fragment implements View.OnClickListener {

    private TextView name_txt, code_txt, tel_txt, web_txt, address_txt;
    private SchoolDetailInfo mDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_detail_three, container, false);

        name_txt = (TextView) view.findViewById(R.id.name_txt);
        code_txt = (TextView) view.findViewById(R.id.code_txt);
        tel_txt = (TextView) view.findViewById(R.id.tel_txt);
        web_txt = (TextView) view.findViewById(R.id.web_txt);
        address_txt = (TextView) view.findViewById(R.id.address_txt);

        RelativeLayout enroll_layout_2017 = (RelativeLayout) view.findViewById(R.id.enroll_layout_2017);
        RelativeLayout enroll_layout_2016 = (RelativeLayout) view.findViewById(R.id.enroll_layout_2016);
        RelativeLayout enroll_layout_2015 = (RelativeLayout) view.findViewById(R.id.enroll_layout_2015);
        RelativeLayout enroll_layout_2014 = (RelativeLayout) view.findViewById(R.id.enroll_layout_2014);
        RelativeLayout enroll_layout_2013 = (RelativeLayout) view.findViewById(R.id.enroll_layout_2013);
        enroll_layout_2017.setOnClickListener(this);
        enroll_layout_2016.setOnClickListener(this);
        enroll_layout_2015.setOnClickListener(this);
        enroll_layout_2014.setOnClickListener(this);
        enroll_layout_2013.setOnClickListener(this);

        Bundle bundle = getArguments();
        mDetail = (SchoolDetailInfo) bundle.getParcelable("detail");

        showDetail();

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mDetail = savedInstanceState.getParcelable("data");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("data", mDetail);
    }

    private void showDetail() {
        if (GeneralUtils.isNotNull(mDetail)) {
            name_txt.setText(mDetail.getName());
            code_txt.setText("学院代号 " + mDetail.getCode());
            tel_txt.setText("联系电话: " + mDetail.getTel());
            web_txt.setText("联系网址: " + mDetail.getWeb());
            address_txt.setText("学院地址: " + mDetail.getAddress());
        }
    }

    private void intentToEnrollList(String year) {
        if (GeneralUtils.isNotNull(mDetail)) {
            Intent intent = new Intent(getContext(), SchoolDetailEnrollActivity.class);
            intent.putExtra("uCode", mDetail.getCode());
            intent.putExtra("year", year);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enroll_layout_2017:
                intentToEnrollList("2017");
                break;
            case R.id.enroll_layout_2016:
                intentToEnrollList("2016");
                break;
            case R.id.enroll_layout_2015:
                intentToEnrollList("2015");
                break;
            case R.id.enroll_layout_2014:
                intentToEnrollList("2014");
                break;
            case R.id.enroll_layout_2013:
                intentToEnrollList("2013");
                break;
        }
    }
}
