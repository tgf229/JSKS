package com.jsksy.app.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.jsksy.app.R;
import com.jsksy.app.constant.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * <下载安装apk工具�?
 * <功能详细描述>
 * 
 * @author  wanghl
 * @version  [版本�? 2014�?�?9日]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class DownApkUtil
{
    /**
     * 传入上下文对�?     */
    private Context context;
    
    /**
     * 传入更新UI的handler
     */
    private Handler handler;
    
    /**
     * 下载进度�?     */
    private static ProgressBar mProgressBar;
    
    /**
     * 是否取消更新
     */
    private boolean cancelUpdate = false;
    
    /**
     * 文件存储的路�?     */
    String mSavePath;
    
    /**
     * image的url地址
     */
    private String imagePath;
    
    /**
     * 进度条的进度
     */
    private static int progress = 0;
    
    /**
     * 更新软件显示的对话框
     */
    private Dialog mDownloadDialog;
    
    /**
     * 非强制更新，是否点击返回�?     */
    private boolean isBack = false;
    
    public DownApkUtil(Context context, Handler handler, String imagePath)
    {
        this.context = context;
        this.handler = handler;
        this.imagePath = imagePath;
    }
    
    /**
     * 
     * <是否有新版本>
     * <功能详细描述>
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public boolean isUpdate(String newVersionName, String versionName)
    {
        if (newVersionName.compareTo(versionName) > 0)
        {
            return true;
        }
        
        return false;
        
        //        if (newVersionName > versionName)
        //        {
        //            Log.d("downApk", "isupdate" + true);
        //            
        //            return true;
        //            
        //        }
        //        else
        //        {
        //            Log.d("downApk", "isupdate" + false);
        //            return false;
        //        }
        
    }
    
    /**
     * 
     * <显示进度条并下载 >
     * <功能详细描述>
     * @param isUpdate  0 不强�? 1 强制
     * @see [类�?�?方法、类#成员]
     */
    public void downApk(final String isUpdate)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            AlertDialog.Builder builder = new Builder(context);
            builder.setTitle("软件版本更新");
            
            final LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.softupdate_progress, null);
            mProgressBar = (ProgressBar)v.findViewById(R.id.update_progress);
            
            builder.setView(v);
            
            mDownloadDialog = builder.create();
            mDownloadDialog.setCanceledOnTouchOutside(false);
            
            if ("1".equals(isUpdate))//强制更新
            {
                
            }
            else
            {
                builder.setNegativeButton("取消", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        cancelUpdate = true;
                    }
                });
            }
            
            OnKeyListener key = new DialogInterface.OnKeyListener()
            {
                
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if ("1".equals(isUpdate))//强制更新
                            {
                                isBack = false;
                                return true;
                            }
                            else
                            {
                                isBack = true;
                                return false;
                            }
                            
                        default:
                            break;
                    }
                    return false;
                }
                
            };
            mDownloadDialog.setOnKeyListener(key);
            
            mDownloadDialog.show();
            
            // 启动新线程下载软�? 
            new downloadApkThread().start();
        }
        else
        {
            handler.sendEmptyMessage(Constants.NO_SD);
        }
    }
    
    /** 
     * 显示进度条并下载
     */
    public void downApkNoCancel()
    {
        // 构�?软件下载对话�? 
        AlertDialog.Builder builder = new Builder(context);
        // 给下载对话框增加进度�? 
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgressBar = (ProgressBar)v.findViewById(R.id.update_progress);
        builder.setView(v);
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        mDownloadDialog.setCanceledOnTouchOutside(false);
        // 启动新线程下载软�? 
        new downloadApkThread().start();
    }
    
    /**
     * 
     * <启动线程下载文件>
     * <功能详细描述>
     * 
     * @author  wanghl
     * @version  [版本�? 2014�?�?9日]
     * @see  [相关�?方法]
     * @since  [产品/模块版本]
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限  
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获得存储卡的路径  
                    //                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = FileSystemManager.getVersionUpdatePath(context);//sdpath + "download";
                    URL url = new URL(imagePath);
                    // 创建连接  
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    // 获取文件大小  
                    int length = conn.getContentLength();
                    // 创建输入�? 
                    InputStream is = conn.getInputStream();
                    
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在  
                    if (!file.exists())
                    {
                        boolean a = file.mkdirs();
                        Log.i("test", "run: "+a);
                    }
                    File apkFile = new File(mSavePath+"/JSKSY.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存  
                    byte buf[] = new byte[1024];
                    // 写入到文件中  
                    do
                    {
                        //非强制更新，没有按返回键
                        if (!isBack)
                        {
                            int numread = is.read(buf);
                            count += numread;
                            // 计算进度条位�? 
                            progress = (int)(((float)count / length) * 100);
                            // 更新进度  
                            handler.sendEmptyMessage(Constants.DOWNLOAD);
                            if (numread <= 0)
                            {
                                // 下载完成  
                                handler.sendEmptyMessage(Constants.DOWNLOAD_FINISH);
                                break;
                            }
                            // 写入文件  
                            fos.write(buf, 0, numread);
                        }
                        else
                        {
                            break;
                        }
                        
                    } while (!cancelUpdate);// 点击取消就停止下�?  
                    fos.close();
                    is.close();
                }
                else
                {
                    handler.sendEmptyMessage(Constants.NO_SD);
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // 取消下载对话框显�? 
            mDownloadDialog.dismiss();
        }
    };
    
    /** 
     * 安装APK文件 
     */
    public void installApk()
    {
        File apkfile = new File(mSavePath, "JSKSY.apk");
        if (!apkfile.exists())
        {
            return;
        }
        
        // 通过Intent安装APK文件  
        Intent i = new Intent(Intent.ACTION_VIEW);

        Uri data;
        // 判断版本大于等于7.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
//            data = FileProvider.getUriForFile(context, "com.jsksy.app.fileprovider", apkfile);
//            // 给目标应用一个临时授权
//            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        } else {
            data = Uri.fromFile(apkfile);
//        }
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.setDataAndType(data, "application/vnd.android.package-archive");

        context.startActivity(i);
    }
    
    public void updateProgress()
    { // 设置进度条位�? 
        mProgressBar.setProgress(progress);
        
    }
}
