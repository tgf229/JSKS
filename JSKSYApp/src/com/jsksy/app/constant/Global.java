package com.jsksy.app.constant;

import android.app.Activity;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.network.NetWork;
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
     * <�˳�Ӧ��>
     * <������ϸ����>
     * @param context
     * @see [�ࡢ��#��������#��Ա]
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
}
