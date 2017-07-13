package com.jsksy.app.ui.set;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.util.ShareSDKUtil;

public class SetQrcodeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_qrcode);
        initView();
    }

    private void initView(){
        RelativeLayout app_title_close = (RelativeLayout) findViewById(R.id.app_title_close);
        app_title_close.setVisibility(View.GONE);
        LinearLayout app_title_back = (LinearLayout) findViewById(R.id.app_title_back);
        LinearLayout right_btn_layout = (LinearLayout) findViewById(R.id.right_btn_layout);
        TextView title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("分享");
        app_title_back.setOnClickListener(this);
        right_btn_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.app_title_back:
                finish();
                break;
            case R.id.right_btn_layout:
                String title = "江苏招考APP上线啦!";
                ShareSDKUtil.showSDKShare(this, title, title, Constants.URL_APP_SHARE, null);
                break;
        }
    }
}
