package com.jsksy.app.ui.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.offer.OfferDoc;
import com.jsksy.app.bean.offer.OfferResponse;
import com.jsksy.app.util.GeneralUtils;

import java.util.List;

public class OfferResultActivity extends AppCompatActivity implements View.OnClickListener{

    private String sNum;
    private OfferResponse offerResponse;
    private LinearLayout doc_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_result);
        sNum = getIntent().getStringExtra("sNum");
        offerResponse = (OfferResponse)getIntent().getSerializableExtra("offerResponse");
        init();
        showDoc(offerResponse.getDoc());
    }
    private void init()
    {
        LinearLayout app_title_back = (LinearLayout)findViewById(R.id.app_title_back);
        TextView title_name = (TextView)findViewById(R.id.title_name);
        title_name.setText("录取结果");
        app_title_back.setOnClickListener(this);

        TextView sNum_txt = (TextView)findViewById(R.id.sNum_txt);
        TextView name_txt = (TextView)findViewById(R.id.name_txt);
        sNum_txt.setText(sNum);
        name_txt.setText(offerResponse.getsName());

        ImageView offer_success_img = (ImageView) findViewById(R.id.offer_success_img);
        offer_success_img.setOnClickListener(this);

        doc_content = (LinearLayout) findViewById(R.id.doc_content);
    }

    private void showDoc(List<OfferDoc> doc){
        doc_content.removeAllViews();
        for (OfferDoc bean: doc){
            View view = LayoutInflater.from(this).inflate(R.layout.component_offer_list_item,null,false);
            TextView time_txt = (TextView) view.findViewById(R.id.time_txt);
            TextView school_txt = (TextView) view.findViewById(R.id.school_txt);
            TextView batch_txt = (TextView) view.findViewById(R.id.batch_txt);
            TextView clazz_txt = (TextView) view.findViewById(R.id.clazz_txt);
            TextView reason_txt = (TextView) view.findViewById(R.id.reason_txt);
            TextView ps_txt = (TextView) view.findViewById(R.id.ps_txt);
            if (GeneralUtils.isNotNull(bean)){
                time_txt.setText(bean.getTime());
                school_txt.setText(bean.getSchoolName());
                batch_txt.setText(bean.getBatch());
                clazz_txt.setText(bean.getClazz());
                reason_txt.setText(bean.getReason());
                ps_txt.setText("备注:"+bean.getPs());
                doc_content.addView(view);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.offer_success_img:
                Intent intent = new Intent(this, OfferSuccessActivity.class);
                intent.putExtra("sNum", sNum);
                intent.putExtra("offerResponse", offerResponse);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
