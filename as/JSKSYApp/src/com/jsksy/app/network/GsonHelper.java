package com.jsksy.app.network;

import com.google.gson.Gson;
import com.jsksy.app.util.CMLog;



public class GsonHelper
{
    
    private static Gson gson = new Gson();
    
    /**
     * ��json string ת���������
     * 
     * @param str
     * @param t
     * @return
     */
    public static <T> T toType(String str, Class<T> t)
    {
        try
        {
            if (str != null && !"".equals(str.trim()))
            {
                T res = gson.fromJson(str.trim(), t);
                return res;
            }
        }
        catch (Exception e)
        {
            CMLog.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }
    
    /**
     * �������ת����json string
     * 
     * @param t
     * @return
     */
    public static <T> String toJson(T t)
    {
        return gson.toJson(t);
    }
    
}
