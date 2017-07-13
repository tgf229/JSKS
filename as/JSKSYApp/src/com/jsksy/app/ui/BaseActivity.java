package com.jsksy.app.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.callback.UICallBack;

/**
 * <������> <������ϸ����>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class BaseActivity extends Activity implements UICallBack
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //��̬������Ļ���� ǿ������
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
