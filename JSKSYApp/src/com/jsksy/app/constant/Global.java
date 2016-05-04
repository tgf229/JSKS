package com.jsksy.app.constant;

import java.util.Set;

import android.app.Activity;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.network.NetWork;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.util.CMLog;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * <全局静态缓存数据>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Global
{
    private static final String vip = "201412250933001234567890";
    
    /**
     * app是否开启
     */
    public static boolean getAppOpen()
    {
        return SharePref.getBoolean(SharePref.APP_OPEN, false);
    }
    
    public static void setAppOpen(boolean isOpen)
    {
        SharePref.saveBoolean(SharePref.APP_OPEN, isOpen);
    }
    
    /**
     * 获取推送状态
     */
    public static boolean getPush()
    {
        return SharePref.getBoolean(SharePref.SET_MESSAGE, true);
    }
    
    public static void savePush(boolean push)
    {
        SharePref.saveBoolean(SharePref.SET_MESSAGE, push);
    }
    
    /**
     * <退出应用>
     * <功能详细描述>
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void logoutApplication()
    {
        try
        {
            Global.setAppOpen(false);
            for (Activity activity : JSKSYApplication.activitys)
            {
                activity.finish();
                activity = null;
            }
            JSKSYApplication.activitys.clear();
        }
        catch (Exception e)
        {
            CMLog.e("", "finish activity exception:" + e.getMessage());
        }
        finally
        {
            NetWork.shutdown();
            ImageLoader.getInstance().clearMemoryCache();
            System.exit(0);
        }
    }
    
    /**
     * 获取本地密钥
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getKey()
    {
//        return JNIInterface.getAppName();
        return vip;
    }
    
    /**
     * 别名设置
     */
    public static void setAliasApp(Context context, String alias)
    {
        JPushInterface.setAlias(context, alias, new TagAliasCallback()
        {
            
            @Override
            public void gotResult(int result, String arg1, Set<String> arg2)
            {
                if (result == 0)
                {
                    CMLog.i("info", "别名设置成功");
                }
                else
                {
                    CMLog.i("info", "别名设置失败");
                }
            }
        });
    }
}
