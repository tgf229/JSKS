package com.jsksy.app.network;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;

import com.jsksy.app.callback.INetCallBack;
import com.jsksy.app.callback.UICallBack;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.Global;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.util.CMLog;
import com.jsksy.app.util.SecurityUtils;


/**
 * <���ݷ�װ��> <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class ConnectService
{
    /**
     * ��־
     */
    
    private static ConnectService connectServiceSingleton = null;
    
    private ConnectService()
    {
    }
    
    public static ConnectService instance()
    {
        if (connectServiceSingleton == null)
        {
            connectServiceSingleton = new ConnectService();
        }
        return connectServiceSingleton;
    }
    
    /**
     * 
     * <��������,������ΪT������T��ȥ�ж�T����retcode�Ƿ�Ϊ��/NULL> 
     * <���÷������Բο�MainFragment>
     * 
     * @param param
     * @param handler
     * @param t
     * @see [�ࡢ��#��������#��Ա]
     */
    public <T> void connectServiceReturnResponse(final Context context, Map<String, String> param,
        final UICallBack callback, final Class<T> t, final String serviceName, final String encrypt)
    {
        try
        {
            // ��װ�������
            NetWork netWork = new NetWork();
            String url = URLUtil.SERVER + serviceName + "?encrypt=" + encrypt;// ���Ͼ���ķ��ʵ�ַ
            netWork.startPost(url, param, new INetCallBack()
            {
                @Override
                public void onComplete(String result)
                {
                    try
                    {
                        if (result != null && !"".equals(result.trim()))
                        {
                            if (Constants.ENCRYPT_SIMPLE.equals(encrypt))
                            {
                                result = SecurityUtils.decode2Str(result, Global.getKey());//����
                            }
                            CMLog.i("info", "result:" + result);
                        }
                        final T res = GsonHelper.toType(result, t);
                        ((Activity)context).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                if (res != null)
                                {
                                    callback.netBack(res);
                                }
                                else
                                {
                                    try
                                    {
                                        callback.netBack(t.newInstance());
                                    }
                                    catch (InstantiationException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    catch (IllegalAccessException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        ((Activity)context).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                try
                                {
                                    callback.netBack(t.newInstance());
                                }
                                catch (InstantiationException e)
                                {
                                    e.printStackTrace();
                                }
                                catch (IllegalAccessException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                        e.printStackTrace();
                    }
                    
                }
            });
        }
        catch (Exception e)
        {
            ((Activity)context).runOnUiThread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        callback.netBack(t.newInstance());
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * <��������,�ϴ��ļ�> <������ϸ����>
     * 
     * @param param
     * @param handler
     * @param t
     * @see [�ࡢ��#��������#��Ա]
     */
    public <T> void connectServiceUploadFile(final Context context, Map<String, String> param,
        Map<String, List<File>> fileParameters, final UICallBack callback, final Class<T> t, final String serviceName,
        final String encrypt)
    {
        try
        {
            // ��װ�������
            NetWork netWork = new NetWork();
            String url = URLUtil.SERVER + serviceName + "?encrypt=" + encrypt;// ���Ͼ���ķ��ʵ�ַ
            netWork.startPost(url, param, fileParameters, new INetCallBack()
            {
                @Override
                public void onComplete(String result)
                {
                    try
                    {
                        if (result != null && !"".equals(result.trim()))
                        {
                            if (Constants.ENCRYPT_SIMPLE.equals(encrypt))
                            {
                                result = SecurityUtils.decode2Str(result, Global.getKey());//����
                            }
                            CMLog.i("info", "result:" + result);
                        }
                        final T res = GsonHelper.toType(result, t);
                        ((Activity)context).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                if (res != null)
                                {
                                    callback.netBack(res);
                                }
                                else
                                {
                                    try
                                    {
                                        callback.netBack(t.newInstance());
                                    }
                                    catch (InstantiationException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    catch (IllegalAccessException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        ((Activity)context).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                try
                                {
                                    callback.netBack(t.newInstance());
                                }
                                catch (InstantiationException e)
                                {
                                    e.printStackTrace();
                                }
                                catch (IllegalAccessException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                        e.printStackTrace();
                    }
                    
                }
            });
        }
        catch (Exception e)
        {
            ((Activity)context).runOnUiThread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        callback.netBack(t.newInstance());
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            e.printStackTrace();
        }
    }
    
    
    
}
