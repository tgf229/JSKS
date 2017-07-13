package com.jsksy.app.network;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.jsksy.app.callback.INetCallBack;
import com.jsksy.app.util.CMLog;
import com.jsksy.app.util.FileExtensionUtil;
import com.jsksy.app.util.MimeTypeUtil;
import com.jsksy.app.util.StringUtil;

/**
 * 
 * <���繤����> <������ϸ����>
 * 
 * @author cyf
 * @version [�汾��, 2014-3-24]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class NetWork
{
    private static final String CHARSET = "UTF-8";
    
    private static final String TAG = "Http";
    
    /**
     * �̳߳�
     */
    private static ExecutorService mExecutor = Executors.newCachedThreadPool();
    
    private HttpTask mRequestHandle;
    
    /**
     * ��������
     */
    private static ConcurrentLinkedQueue<HttpTask> mPendingTasks = new ConcurrentLinkedQueue<HttpTask>();
    
//    public static final void clearPendingTasks()
//    {
//        synchronized (mPendingTasks)
//        {
//            for (HttpTask task : mPendingTasks)
//            {
//                task.cancel();
//            }
//        }
//        mPendingTasks.clear();
//    }
    
    
    /**
     * ȡ����ǰ������������
     */
    public final void cancel()
    {
        if (null != mRequestHandle)
        {
            mRequestHandle.cancel();
        }
    }
    
    /**
     * ��{@link RCSApplication#onTerminate}��ʱ�������̳߳�
     * */
    public synchronized static void shutdown()
    {
        if (mExecutor != null)
        {
            if (!mExecutor.isShutdown())
            {
                mExecutor.shutdown();
            }
            mExecutor = null;
        }
    }
    
    /**
     * 
     * <����㽻�� ���ı�> <������ϸ����>
     * 
     * @param context
     * @param url
     * @param map
     * @param callback
     * @see [�ࡢ��#��������#��Ա]
     */
    public void startPost(final String url, final Map<String, String> map, final INetCallBack callback)
    {
        HttpTask task = new HttpTask(callback, url, map, null);
        mRequestHandle = task;
        mPendingTasks.offer(task);
        task.mFuture = mExecutor.submit(task);
    }
    
    /**
     * 
     * <����㽻�� �ļ��ϴ�> <������ϸ����>
     * 
     * @param context
     * @param url
     * @param map
     * @param callback
     * @see [�ࡢ��#��������#��Ա]
     */
    public void startPost(final String url, final Map<String, String> map,
        final Map<String, List<File>> fileParameters, final INetCallBack callback)
    {
        HttpTask task = new HttpTask(callback, url, map, fileParameters);
        mRequestHandle = task;
        mPendingTasks.offer(task);
        task.mFuture = mExecutor.submit(task);
    }
    
    /**
     * 
     * <����㽻�� ������־�ļ��ϴ�-��Զ�ں�̨>
     * <������ϸ����>
     * @param url
     * @param map
     * @param fileParameters
     * @param callback
     * @see [�ࡢ��#��������#��Ա]
     */
    public void startPostForCrash(final String url, final Map<String, String> map,
        final Map<String, List<File>> fileParameters, final INetCallBack callback)
    {
        HttpTask task = new HttpTask(callback, url, map, fileParameters);
        task.mFuture = mExecutor.submit(task);
    }
    
    private static final class HttpTask implements Runnable
    {
        
        private INetCallBack mCallback;
        
        private Map<String, String> mParamMap;
        
        private String mUri;
        
        private Future<?> mFuture;
        
        private DefaultHttpClient defaultHttpClient;
        
        private HttpPost httpPost;
        
        private Map<String, List<File>> mfileParameters;
        
        private HttpTask(INetCallBack callback, String uri, Map<String, String> paramMap,
            Map<String, List<File>> fileParameters)
        {
            mCallback = callback;
            mUri = uri;
            mParamMap = paramMap;
            mfileParameters = fileParameters;
        }
        
        private void cancel()
        {
            if (null != mFuture)
            {
                mFuture.cancel(true);
            }
            
            if (null != defaultHttpClient)
            {
                defaultHttpClient.getConnectionManager().closeExpiredConnections();
                defaultHttpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
            }
        }
        
        @Override
        public void run()
        {
            String result = null;
            try
            {
                result = doPost(mUri, mParamMap, mfileParameters);
            }
            catch (ConnectTimeoutException e)
            {
                CMLog.i(TAG, "ConnectTimeoutException" + e.getMessage());
            }
            catch (IOException e)
            {
                CMLog.i(TAG, "IOException" + e.getMessage());
            }
            catch (RuntimeException e)
            {
                CMLog.i(TAG, "RuntimeException" + e.getMessage());
            }
            catch (InterruptedException e)
            {
                CMLog.i(TAG, "InterruptedException" + e.getMessage());
            }
            finally
            {
                mPendingTasks.remove(this);
                if (!Thread.interrupted())
                {
                    mCallback.onComplete(result);
                }
            }
        }
        
        /**
         * 
         * <��������> <������ϸ����>
         * 
         * @param url
         * @param map
         * @return
         * @see [�ࡢ��#��������#��Ա]
         */
        private String doPost(String url, Map<String, String> map, Map<String, List<File>> fileParameters)
            throws ConnectTimeoutException, IOException, InterruptedException
        {
            CMLog.i(TAG, "参数名     " + url);
            try
            {
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                HttpParams httpParams = new BasicHttpParams();
                HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(httpParams, CHARSET);
                HttpProtocolParams.setUseExpectContinue(httpParams, false);
                
                HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);
                httpPost.setParams(httpParams);
                SchemeRegistry schReg = new SchemeRegistry();
                schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
                defaultHttpClient = new DefaultHttpClient(conMgr, httpParams);
                
                if (fileParameters != null && fileParameters.size() > 0)
                {
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));;
                    
                    if (map != null)
                    {
                        for (Map.Entry<String, String> entry : map.entrySet())
                        {
                            CMLog.i(TAG, "参数名     " + entry.getKey());
                            CMLog.i(TAG, "参数值    " + entry.getValue());
                            BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                            StringBody strBody =
                                new StringBody(String.valueOf(valuePair.getValue()), Charset.forName("UTF-8"));
                            String key = valuePair.getName();
                            multipartEntity.addPart(valuePair.getName(), strBody);
                        }
                    }
                    
                    String fileExtension = null;
                    String mineType = null;
                    for (Map.Entry<String, List<File>> entry : fileParameters.entrySet())
                    {
                        List<File> files = entry.getValue();
                        for (File file : files)
                        {
                            fileExtension = FileExtensionUtil.getFileExtensionFromName(file.getName());
                            mineType = MimeTypeUtil.getSingleton().getMimeTypeFromExtension(fileExtension);
                            if (StringUtil.isNotEmpty(mineType))
                            {
                                multipartEntity.addPart(entry.getKey(), new FileBody(file, mineType)); // ����ļ�����,����mimeType
                            }
                            else
                            {
                                multipartEntity.addPart(entry.getKey(), new FileBody(file)); // ����ļ�����
                            }
                        }
                    }
                    httpPost.setEntity(multipartEntity);
                    
                }
                else
                {
//                    httpPost.setHeader("Content-Type", "multipart/form-data; charset=utf-8");
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                    if (map != null)
                    {
                        for (Map.Entry<String, String> entry : map.entrySet())
                        {
                            CMLog.i(TAG, "参数名     " + entry.getKey());
                            CMLog.i(TAG, "参数值     " + entry.getValue());
                            BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                            nameValuePairs.add(valuePair);
                        }
                    }
                    
                    // ���ֿͻ������� 0���ֻ���1��pad
                    BasicNameValuePair valuePair = new BasicNameValuePair("clientType", "0");
                    nameValuePairs.add(valuePair);
                    
                    HttpEntity httpEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
                    httpPost.setEntity(httpEntity);
                }
                
                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                if (httpResponse == null)
                {
                    CMLog.i(TAG, "http response result null");
                    return null;
                }
                
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    CMLog.i(TAG, "http response sucessful");
                    if (result == null || "".equals(result) || result.indexOf("<html>") > -1)
                    {
                        CMLog.i(TAG, "request was sucessful, but paser value was null or empty");
                        return null;
                    }
                    CMLog.i(TAG, "respnse result:" + result);
                    return result;
                }
                else
                {
                    CMLog.i(TAG, "http response code:" + httpResponse.getStatusLine().getStatusCode());
                    return null;
                }
            }
            catch (ClientProtocolException e)
            {
                CMLog.i(TAG, "client protocol exception" + e.getMessage());
            }
            return null;
        }
        
    }
    
}
