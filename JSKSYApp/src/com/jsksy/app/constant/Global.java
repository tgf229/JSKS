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
    private static final String vip = "201412250933001234567890";
    
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
                    CMLog.i("info", "�������óɹ�");
                }
                else
                {
                    CMLog.i("info", "��������ʧ��");
                }
            }
        });
    }
}
