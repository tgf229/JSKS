package com.jsksy.app;

import im.fir.sdk.FIR;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import cn.jpush.android.api.JPushInterface;

import com.jsksy.app.constant.Global;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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
//        JPushInterface.init(this);//������������
    }
    
    public static void loadData(Context context)
    {
        ImageLoaderConfiguration config =
            new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(4)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
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
