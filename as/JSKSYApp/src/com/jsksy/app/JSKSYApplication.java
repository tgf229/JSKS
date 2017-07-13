package com.jsksy.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.jsksy.app.constant.Global;
import com.jsksy.app.sharepref.SharePref;
import com.jsksy.app.util.GeneralUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * 
 * <Ӧ�ó�ʼ��> <������ϸ����>
 * 
 * @author tgf
 * @version [�汾��, 2014-3-24]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class JSKSYApplication extends Application
{
    /**
     * appʵ��
     */
    public static JSKSYApplication jsksyApplication = null;
    
    /**
     * ����activityջ
     */
    public static List<Activity> activitys = new ArrayList<Activity>();
    
    /**
     * ��ǰavtivity
     */
    public static String currentActivity = "";
    
    @Override
    public void onCreate()
    {
//        FIR.init(this);
        super.onCreate();
        jsksyApplication = this;
        loadData(getApplicationContext());
        JPushInterface.init(this);//jpush init
        ShareSDK.initSDK(this); //shareSDK init
        
        String alias = SharePref.getString(SharePref.STORAGE_ALIAS, null);
        if (GeneralUtils.isNullOrZeroLenght(alias))
        {
            alias = System.currentTimeMillis()+"";
            SharePref.saveString(SharePref.STORAGE_ALIAS, alias);
        }
    }
    
    public static void loadData(Context context)
    {
        ImageLoaderConfiguration config =
            new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(4)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .discCacheSize(50 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .discCache(new UnlimitedDiscCache(new File(FileSystemManager.getCacheImgFilePath(context))))
                .build();
        ImageLoader.getInstance().init(config);
    }
    
    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Global.logoutApplication();
    }
    
    
    
    /**
     * 
     * <���> <������ϸ����>
     * 
     * @param activity
     * @see [�ࡢ��#��������#��Ա]
     */
    public void addActivity(Activity activity)
    {
        activitys.add(activity);
    }
    
    /**
     * 
     * <ɾ��>
     * <������ϸ����>
     * @param activity
     * @see [�ࡢ��#��������#��Ա]
     */
    public void deleteActivity(Activity activity)
    {
        if (activity != null)
        {
            activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    
    /**
     * ��ʼ��option
     * picFail--����ʧ��ʱ��ʾ
     * picLoading--���ڼ���ͼƬʱ��ʾ
     * picEmpty--uriΪ�յ�ʱ����ʾ
     */
    public static DisplayImageOptions setAllDisplayImageOptions(Context context, String picFail, String picLoading,
        String picEmpty)
    {
        DisplayImageOptions options;
        //ͨ��ͼƬ������ͼƬID
        int fail = context.getResources().getIdentifier(picFail, "drawable", "com.jsksy.app");
        int loading = context.getResources().getIdentifier(picLoading, "drawable", "com.jsksy.app");
        int empty = context.getResources().getIdentifier(picEmpty, "drawable", "com.jsksy.app");
        options =
            new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnFail(fail)
                .showImageForEmptyUri(empty)
                .showImageOnLoading(loading)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(0))
                .build();
        
        return options;
    }
    
    /**
     * ��ʼ��option
     * picFail--����ʧ��ʱ��ʾ
     * picLoading--���ڼ���ͼƬʱ��ʾ
     * picEmpty--uriΪ�յ�ʱ����ʾ
     */
    public static DisplayImageOptions setRoundDisplayImageOptions(Context context, String picFail, String picLoading,
        String picEmpty)
    {
        DisplayImageOptions options;
        //ͨ��ͼƬ������ͼƬID
        int fail = context.getResources().getIdentifier(picFail, "drawable", "com.jsksy.app");
        int loading = context.getResources().getIdentifier(picLoading, "drawable", "com.jsksy.app");
        int empty = context.getResources().getIdentifier(picEmpty, "drawable", "com.jsksy.app");
        options =
            new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnFail(fail)
                .showImageForEmptyUri(empty)
                .showImageOnLoading(loading)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                //����ͼƬ����εı��뷽ʽ��ʾ  
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(720))
                .build();
        
        return options;
    }
    
}
