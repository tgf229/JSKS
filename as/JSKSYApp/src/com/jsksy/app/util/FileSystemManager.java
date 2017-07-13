package com.jsksy.app.util;

import android.content.Context;

import java.io.File;

/**
 * 
 * <管理本地文件目录>
 * <功能详细描述>
 * 
 * @author  cyf
 * @version  [版本�? 2014-6-30]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class FileSystemManager
{
    /**
     * 根目录缓存目�?
     */
    private static String cacheFilePath;
    
    /**
     * 列表页面图片缓存目录
     */
    private static String cacheImgFilePath;
    
    /**
     * 用户头像缓存目录
     */
    private static String userHeadPath;
    
    /**
     * 省市区数据库缓存目录
     */
    private static String dbPath;
    
    /**
     * 投诉维权图片缓存目录
     */
    private static String mallComplaintsPicPath;
    
    /**
     * 投诉维权语音缓存目录
     */
    private static String mallComplaintsVoicePath;
    
    /**
     * 崩溃日志缓存目录（上传成功则删除�?
     */
    private static String crashPath;
    
    /**
     * 圈子发帖缓存目录（删除上次发帖缓存，保存本次发帖纪录�?
     */
    private static String postPath;
    
    /**
     * 临时目录
     */
    private static String temporaryPath;
    
    /**
     * 版本更新的目�?
     */
    private static String versionUpdate;
    
    /**
     * 社区图标目录
     */
    private static String communityIconPath;
    
    /**
     * 
     * <根目录缓存目�?
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getCacheFilePath(Context context)
    {
        cacheFilePath = FileUtil.getSDPath(context) + File.separator + "jsksy" + File.separator;
        return cacheFilePath;
    }
    
    /**
     * 
     * <列表页面图片缓存目录>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getCacheImgFilePath(Context context)
    {
        cacheImgFilePath = FileUtil.createNewFile(getCacheFilePath(context) + "img" + File.separator);
        return cacheImgFilePath;
    }
    
    /**
     * 
     * <用户头像地址>
     * <功能详细描述>
     * @param context
     * @param userId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getUserHeadPath(Context context, String userId)
    {
        userHeadPath =
            FileUtil.createNewFile(getCacheFilePath(context) + "head" + File.separator + userId + File.separator);
        return userHeadPath;
    }
    
    /**
     * 
     * <用户头像缓存目录>
     * <功能详细描述>
     * @param context
     * @param userId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getUserHeadPathTemp(Context context, String userId)
    {
        userHeadPath =
            FileUtil.createNewFile(getCacheFilePath(context) + "head_temp" + File.separator + userId + File.separator);
        return userHeadPath;
    }
    
    /**
     * 
     * <省市区数据库缓存目录>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getDbPath(Context context)
    {
        dbPath = FileUtil.createNewFile(getCacheFilePath(context) + "database" + File.separator);
        return dbPath;
    }
    
    /**
     * 
     * <投诉维权图片缓存目录>
     * <功能详细描述>
     * @param context
     * @param userId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getMallComplaintsPicPath(Context context, String userId)
    {
        mallComplaintsPicPath =
            FileUtil.createNewFile(getCacheFilePath(context) + "mallcomplaints" + File.separator + userId
                + File.separator + "pic" + File.separator);
        return mallComplaintsPicPath;
    }
    
    /**
     * 
     * <投诉维权语音缓存目录>
     * <功能详细描述>
     * @param context
     * @param userId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getMallComplaintsVoicePath(Context context, String userId)
    {
        mallComplaintsVoicePath =
            FileUtil.createNewFile(getCacheFilePath(context) + "mallcomplaints" + File.separator + userId
                + File.separator + "voice" + File.separator);
        return mallComplaintsVoicePath;
    }
    
    /**
     * 
     * <崩溃日志缓存目录（上传成功则删除�?
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getCrashPath(Context context)
    {
        crashPath = FileUtil.createNewFile(getCacheFilePath(context) + "crash" + File.separator);
        return crashPath;
    }
    
    /**
     * 
     * <圈子发帖缓存目录（删除上次发帖缓存，保存本次发帖纪录�?
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getPostPath(Context context)
    {
        postPath = FileUtil.createNewFile(getCacheFilePath(context) + "post" + File.separator);
        return postPath;
    }
    
    /**
     * 
     * <临时图片缓存目录>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getTemporaryPath(Context context)
    {
        temporaryPath = FileUtil.createNewFile(getCacheFilePath(context) + "temp" + File.separator);
        return temporaryPath;
    }
    
    /**
     * 
     * <版本更新目录>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getVersionUpdatePath(Context context)
    {
        versionUpdate = FileUtil.createNewFile(getCacheFilePath(context) + "versionupdate" + File.separator);
        return versionUpdate;
    }
    
    /**
     * 
     * <社区图标目录>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getCommunityIconPath(Context context)
    {
        communityIconPath = FileUtil.createNewFile(getCacheFilePath(context) + "communityIconPath" + File.separator);
        return communityIconPath;
    }
    
}