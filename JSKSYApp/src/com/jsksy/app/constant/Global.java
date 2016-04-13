package com.jsksy.app.constant;

import android.app.Activity;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.network.NetWork;
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
     * <退出应用>
     * <功能详细描述>
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void logoutApplication()
    {
        try
        {
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
}
