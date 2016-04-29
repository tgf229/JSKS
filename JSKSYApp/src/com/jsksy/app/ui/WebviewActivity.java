package com.jsksy.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.util.NetLoadingDailog;

public class WebviewActivity extends Activity
{
    /**
     * title控件
     */
    private LinearLayout llLeft;
    
    private TextView tvTitle;
    
    private WebView web;
    
    /**
     * 网络请求等待框
     */
    private NetLoadingDailog dailog;
    
    private String url;
    
    private Handler mHandler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_main);
        initTitle();
        init();
        webview();
    }
    
    /**
     * 初始化TITLE
     */
    private void initTitle()
    {
        llLeft = (LinearLayout)findViewById(R.id.app_title_back);
        tvTitle = (TextView)findViewById(R.id.title_name);
        llLeft.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WebviewActivity.this.finish();
            }
        });
    }
    
    private void init()
    {
        dailog = new NetLoadingDailog(this);
        dailog.loading();
        url = getIntent().getStringExtra("wev_view_url");
        tvTitle.setText("加载中...");
        web = (WebView)findViewById(R.id.webview_helper_web);
    }
    
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void webview()
    {
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        //        web.getSettings().setPluginsEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setAppCacheEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        // web.setInitialScale(25);
        loadurl(web, url);
        
        web.setWebChromeClient(new CustomWebChromeClient());
        web.addJavascriptInterface(new JavaScriptInterface(), "jsExtend");
        web.setWebViewClient(new WebViewClient()
        {
            
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                loadurl(view, url.trim());// 载入网页
                return true;
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                dailog.dismissDialog();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
            
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                dailog.dismissDialog();
            }
            
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                dailog.dismissDialog();
            }
        });
        
    }
    
    private void loadurl(final WebView view, final String url)
    {
        view.loadUrl(url);// 载入网页
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        web.clearView();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (web.canGoBack())
            {
                web.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    
    /**
     * Provides a hook for calling "alert" from javascript
     */
    final class CustomWebChromeClient extends WebChromeClient
    {
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
        {
            // Log.d(TAG, message);
            new AlertDialog.Builder(WebviewActivity.this).setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
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
        
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result)
        {
            new AlertDialog.Builder(WebviewActivity.this).setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        result.confirm();
                        // finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        result.cancel();
                    }
                })
                .create()
                .show();
            return true;
        }
        
        @Override
        public void onReceivedTitle(WebView view, String title)
        {
            super.onReceivedTitle(view, title);
            tvTitle.setText(title);
        };
    }
    
    final class JavaScriptInterface
    {
        JavaScriptInterface()
        {
        }
        
        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void clickOnAndroid()
        {
            mHandler.post(new Runnable()
            {
                public void run()
                {
                    web.loadUrl("javascript:wave()");
                }
            });
        }
        
        public void exit()
        {
            finish();
        }
        
        public String getConfirmMsg()
        {
            return "确定要退出吗?";
        }
    }
}
