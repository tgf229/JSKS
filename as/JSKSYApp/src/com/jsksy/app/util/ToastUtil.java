package com.jsksy.app.util;

import com.jsksy.app.JSKSYApplication;

import android.content.Context;
import android.widget.Toast;

/**
 * <��ʾ������> <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public final class ToastUtil
{
    /**
     * 
     * <��ʾtoast��ʾ>
     * <������ϸ����>
     * @param context
     * @param msg
     * @see [�ࡢ��#��������#��Ա]
     */
    public static void makeText(Context context, String msg)
    {
        if (JSKSYApplication.currentActivity.equals(context.getClass().getName()))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 
     * <��ʾʧ����Ϣ > <������ϸ����>
     * 
     * @param ctx
     * @see [�ࡢ��#��������#��Ա]
     */
    public static void showError(Context context)
    {
        makeText(context, "����ʧ�ܣ����Ժ�����");
    }
}
