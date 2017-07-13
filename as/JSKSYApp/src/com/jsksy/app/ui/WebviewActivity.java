package com.jsksy.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.ui.home.HomeActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.ShareSDKUtil;

public class WebviewActivity extends Activity {
    private static final String TAG = "WebviewActivity";
    /**
     * title???
     */
    private LinearLayout llLeft, right_btn_layout;

    private TextView tvTitle;

    private WebView web;

    private String url;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_main);
        init();
        webview();
    }

    private void init() {
        url = getIntent().getStringExtra("wev_view_url");
        boolean isWishChannel = getIntent().getBooleanExtra("isWishChannel", false);
        tvTitle = (TextView) findViewById(R.id.title_name);
        tvTitle.setText("加载中...");
        web = (WebView) findViewById(R.id.webview_helper_web);

        llLeft = (LinearLayout) findViewById(R.id.app_title_back);
        llLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                    Intent intent = new Intent(WebviewActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    if (web.canGoBack()) {
                        web.goBack();
                    } else {
                        WebviewActivity.this.finish();
                    }
                }
            }
        });

        RelativeLayout app_title_close = (RelativeLayout) findViewById(R.id.app_title_close);
        if (isWishChannel){
            app_title_close.setVisibility(View.GONE);
        }else{
            app_title_close.setVisibility(View.VISIBLE);
            app_title_close.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                        Intent intent = new Intent(WebviewActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        WebviewActivity.this.finish();
                    }
                }
            });
        }

        right_btn_layout = (LinearLayout) findViewById(R.id.right_btn_layout);
        ImageView right_btn_img = (ImageView) findViewById(R.id.right_btn_img);
        if (isWishChannel) {
            right_btn_img.setImageResource(R.drawable.icon_btn_cancle);
            right_btn_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                        Intent intent = new Intent(WebviewActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        WebviewActivity.this.finish();
                    }
                }
            });
        } else {
            right_btn_img.setImageResource(R.drawable.btn_share);
            right_btn_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = tvTitle.getText().toString();
                    if (GeneralUtils.isNullOrZeroLenght(title)) {
                        title = "江苏招考";
                    }
                    ShareSDKUtil.showSDKShare(WebviewActivity.this, title, title, url, null);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (web.canGoBack()) {
            web.goBack();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void webview() {
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        //        web.getSettings().setPluginsEnabled(true);
        web.getSettings().setSupportZoom(false);
        web.getSettings().setAppCacheEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setLoadWithOverviewMode(true);
        // web.setInitialScale(25);
        loadurl(web, url);

        web.setWebChromeClient(new CustomWebChromeClient());
        web.addJavascriptInterface(new JavaScriptInterface(), "jsExtend");
        web.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadurl(view, url.trim());// ???????
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    private void loadurl(final WebView view, final String url) {
        view.loadUrl(url);// ???????
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        web.clearView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
                return true;
            } else if ("1".equals(getIntent().getStringExtra("back_to_home"))) {
                Intent intent = new Intent(WebviewActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Provides a hook for calling "alert" from javascript
     */
    final class CustomWebChromeClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // Log.d(TAG, message);
            new AlertDialog.Builder(WebviewActivity.this).setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
            // result.confirm();
            // return true;
        }

        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(WebviewActivity.this).setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                            // finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .create()
                    .show();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, final String title) {
            super.onReceivedTitle(view, title);
            Log.i(TAG, "onReceivedTitle: ");
            tvTitle.setText(title);
            if ("找不到网页".equals(title)) {
                tvTitle.setText("请检查您的网络");
            }
        }

        ;
    }

    final class JavaScriptInterface {
        JavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void clickOnAndroid() {
            mHandler.post(new Runnable() {
                public void run() {
                    web.loadUrl("javascript:wave()");
                }
            });
        }

        public void exit() {
            finish();
        }

        public String getConfirmMsg() {
            return "确定要退出吗?";
        }
    }
}
