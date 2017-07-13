package com.jsksy.app.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mobstat.StatService;
import com.jsksy.app.JSKSYApplication;

public class BaseAppCompatActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        
        JSKSYApplication.jsksyApplication.addActivity(this);
    }

    @Override
    protected void onResume()
    {
        JSKSYApplication.currentActivity = this.getClass().getName();
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        StatService.onPause(this);
    }
    
    @Override
    protected void onDestroy()
    {
        JSKSYApplication.jsksyApplication.deleteActivity(this);
        super.onDestroy();
    }
}
