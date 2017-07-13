package com.jsksy.app.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.callback.UICallBack;

/**
 * <基础类> <功能详细描述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseActivity extends Activity implements UICallBack
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //动态设置屏幕方向 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        
        JSKSYApplication.jsksyApplication.addActivity(this);
    }
    
    @Override
    public void netBack(Object ob)
    {
        
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
