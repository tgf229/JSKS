package com.jsksy.app.constant;

import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.network.NetWork;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.util.CMLog;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * <ȫ�־�̬��������>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class Global
{
    private static final String TAG = "Global";
    private static final String vip = "2016051616090012345678tm";
    
    /**
     * app�Ƿ���
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
     * ��ȡ����״̬
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
     * ����ҳ
     * <һ�仰���ܼ���>
     * <������ϸ����>
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getUserGuide()
    {
        return SharePref.getString(SharePref.APP_GUIDE, "");
    }
    
    public static void saveUserGuide(String guide)
    {
        SharePref.saveString(SharePref.APP_GUIDE, guide);
    }
    
    /**
     * <�˳�Ӧ��>
     * <������ϸ����>
     * @param context
     * @see [�ࡢ��#��������#��Ա]
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
     * ��ȡ������Կ
     * <һ�仰���ܼ���>
     * <������ϸ����>
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getKey()
    {
//        return JNIInterface.getAppName();
        return vip;
    }
    
    /**
     * ��������
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
//                    CMLog.i("info", "别名设置成功");
                    Log.i(TAG, "别名设置成功");
                }
                else
                {
                    Log.i(TAG, "别名设置失败");
//                    CMLog.i("info", "别名设置失败");
                }
            }
        });
    }
}
