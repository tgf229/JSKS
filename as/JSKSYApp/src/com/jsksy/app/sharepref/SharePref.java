package com.jsksy.app.sharepref;

import android.content.Context;
import android.content.SharedPreferences;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.constant.Constants;

/**
 * 
 * <�����洢��>
 * <������ϸ����>
 * 
 * @author  cyf
 * @version  [�汾��, 2014-11-4]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class SharePref
{
    public static final String STORAGE_ALIAS = "STORAGE_ALIAS";
    public static final String STORAGE_CONF_GKADSCHOOL = "STORAGE_CONF_GKADSCHOOL";
    
    public static final String STORAGE_SNUM = "STORAGE_SNUM";
    public static final String STORAGE_STICKET = "STORAGE_STICKET";
    public static final String STORAGE_SCHECKA = "STORAGE_SCHECKA";
    public static final String STORAGE_SCHECKB = "STORAGE_SCHECKB";
    /**
     * app�Ƿ���
     */
    public static final String APP_OPEN = "app_open";
    
    /**
     * ������������
     */
    public static final String SET_MESSAGE = "set_push";
   
    /**
     * app�Ƿ�������ҳ
     */
    public static final String APP_GUIDE = "app_guide";
//    
//    /**
//     * �û�ID
//     */
//    public static final String USER_ID = "userId";
    
    public static void saveBoolean(String key, boolean value)
    {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }
    
    public static boolean getBoolean(String key, boolean defvalue)
    {
        return getSharedPreferences().getBoolean(key, defvalue);
    }
    
    /**
     * Save a string value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveString(String key, String value)
    {
        getSharedPreferences().edit().putString(key, value).commit();
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the string value returned.
     */
    public static String getString(String key, String def)
    {
        return getSharedPreferences().getString(key, def);
    }
    
    /**
     * Save a integer value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveInt(String key, int value)
    {
        getSharedPreferences().edit().putInt(key, value).commit();
        
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the integer value returned.
     */
    public static int getInt(String key, int def)
    {
        return getSharedPreferences().getInt(key, def);
    }
    
    /**
     * Save a Long value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveLong(String key, long value)
    {
        getSharedPreferences().edit().putLong(key, value).commit();
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the integer value returned.
     */
    public static long getLong(String key, long def)
    {
        return getSharedPreferences().getLong(key, def);
    }
    
    /**
     * Retrieve the package shared preferences object.
     * 
     * @param MyApp
     *            .appContext
     * @return
     */
    private static SharedPreferences getSharedPreferences()
    {
        return JSKSYApplication.jsksyApplication.getSharedPreferences(Constants.JSKSY_INFO, Context.MODE_PRIVATE);
    }
}
