package com.jsksy.app.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.EncodingUtils;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Parcelable;

public class FileUtil
{
    
    // 获取sdcard的目�?
    public static String getSDPath(Context context)
    {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            // 获取根目�?
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        }
        return "/data/data/" + context.getPackageName();
    }
    
    public static String createNewFile(String path)
    {
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return path;
    }
    
    // 复制文件
    public static void copyFile(InputStream inputStream, File targetFile)
        throws IOException
    {
        BufferedOutputStream outBuff = null;
        try
        {
            
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inputStream.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出�?
            outBuff.flush();
        }
        finally
        {
            // 关闭�?
            if (inputStream != null)
                inputStream.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    /**
     * 文件是否已存�?
     * 
     * @param file
     * @return
     */
    public static boolean isFileExit(File file)
    {
        if (file.exists())
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断指定目录是否有文件存�?
     * 
     * @param path
     * @param fileName
     * @return
     */
    public static File getFiles(String path, String fileName)
    {
        File f = new File(path);
        File[] files = f.listFiles();
        if (files == null)
        {
            return null;
        }
        
        if (null != fileName && !"".equals(fileName))
        {
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                if (fileName.equals(file.getName()))
                {
                    return file;
                }
            }
        }
        return null;
    }
    
    /**
     * 根据文件路径获取文件�?
     * 
     * @return
     */
    public static String getFileName(String path)
    {
        if (path != null && !"".equals(path.trim()))
        {
            return path.substring(path.lastIndexOf("/"));
        }
        
        return "";
    }
    
    // 从asset中读取文�?
    public static String getFromAssets(Context context, String fileName)
    {
        String result = "";
        try
        {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String FileInputStreamDemo(String path)
    {
        try
        {
            File file = new File(path);
            if (!file.exists() || file.isDirectory())
                ;
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((fis.read(buf)) != -1)
            {
                sb.append(new String(buf));
                buf = new byte[1024];// 重新生成，避免和上次读取的数据重�?
            }
            return sb.toString();
            
        }
        catch (Exception e)
        {
        }
        return "";
    }
    
    public static String FileInputStreamDemo(String fileName, Context context)
    {
        try
        {
            
            AssetManager aManager = context.getResources().getAssets();
            InputStream in = aManager.open(fileName); // 从Assets中的文件获取输入�?
            int length = in.available(); // 获取文件的字节数
            byte[] buffer = new byte[length]; // 创建byte数组
            in.read(buffer); // 将文件中的数据读取到byte数组�?
            String result = EncodingUtils.getString(buffer, "UTF-8");
            
            return result;
            
        }
        catch (Exception e)
        {
            // Log.e("", "error:" + e.getMessage());
        }
        return "";
    }
    
    /**
     * 删除目录（文件夹）下的文�?
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static void deleteDirectory(String path)
    {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles();
        if (files != null && files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                // 删除子文�?
                if (files[i].isFile())
                {
                    files[i].delete();
                }
                // 删除子目�?
                else
                {
                    deleteDirectory(files[i].getAbsolutePath());
                }
            }
        }
    }
    
    // 保存序列化的对象到app目录
    public static void saveSeriObj(Context context, String fileName, Object o)
        throws Exception
    {
        
        String path = context.getFilesDir() + "/";
        
        File dir = new File(path);
        dir.mkdirs();
        
        File f = new File(dir, fileName);
        
        if (f.exists())
        {
            f.delete();
        }
        FileOutputStream os = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        os.close();
    }
    
    // 读取序列化的对象
    public static Object readSeriObject(Context context, String fileName)
        throws Exception
    {
        String path = context.getFilesDir() + "/";
        
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, fileName);
        InputStream is = new FileInputStream(file);
        
        ObjectInputStream objectInputStream = new ObjectInputStream(is);
        
        Object o = objectInputStream.readObject();
        
        return o;
        
    }
    
    /**
     * 保存camera capture到file
     * @return 是否成功
     */
    public static boolean savePhoto(Parcelable data, String path)
    {
        boolean rs = false;
        //        Bitmap photo = new Intent().getExtras().getParcelable("data");
        if (null == data)
        {
            return rs;
        }
        
        try
        {
            Bitmap photo = (Bitmap)data;
            File file = new File(path);
            file.createNewFile();
            
            FileOutputStream out = new FileOutputStream(file);
            //            photo.compress(Bitmap.CompressFormat.PNG, 100, out);
            //            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            //TODO 调用BimmapUtil压缩
            rs = photo.compress(Bitmap.CompressFormat.JPEG, 100, out);//PNG
            
            out.flush();
            out.close();
            rs &= true;
            CMLog.w("FileUtil.save", "file size:"+ file.length());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            rs = false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }
    
    /**
     * 下载音频文件，先从本地获得，本地没有 再从网络获得
     * <�?��话功能简�?
     * <功能详细描述>
     * @param url
     * @param context
     * @param userId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getAudiaFile(String url, Context context, String userId)
    {
        String audioName = url.substring(url.lastIndexOf("/") + 1, url.length());
        File file = new File(FileSystemManager.getMallComplaintsVoicePath(context, userId), audioName);
        if (file.exists())
        {
            return file.getPath();
        }
        return loadImageFromUrl(context,url,file);
    }
    
    public static String loadImageFromUrl(Context context,String imageURL, File file)
    {
        try
        {
            URL url = new URL(imageURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(5*1000);
            con.setDoInput(true);
            con.connect();
            if (con.getResponseCode() == 200)
            {
                InputStream inputStream = con.getInputStream();
                
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024*20];
                int length = -1;
                while((length = inputStream.read(buffer)) != -1)
                {
                    byteArrayOutputStream.write(buffer,0,length);
                }
                byteArrayOutputStream.close();
                inputStream.close();
                
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(byteArrayOutputStream.toByteArray());
                outputStream.close();
                return file.getPath();
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
        return "";
    }
}
