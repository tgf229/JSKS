package com.jsksy.app.network;

import android.content.Context;

import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.util.GeneralUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 涂高峰 on 17/4/18.
 */
public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";
//    public static final String URL_ADDR = "http://192.168.0.107/jszk/Bus700101.json";
//    public static final String URL_ADDR_700101 = "http://192.168.0.107/jszk/Bus700101.json";
//    public static final String URL_ADDR_700201 = "http://192.168.0.107/jszk/Bus700201.json";
//    public static final String URL_ADDR_700301 = "http://192.168.0.107/jszk/Bus700301.json";
//    public static final String URL_ADDR_700401 = "http://192.168.0.107/jszk/Bus700401.json";
//    public static final String URL_ADDR_700501 = "http://192.168.0.107/jszk/Bus700501.json";

    public static void sendRequestPost(String url, Map<String, String> param, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(URLUtil.SERVER+url).post(body).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendRequest(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void reqADLog(Context context, String aId,String aType) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("imei", GeneralUtils.getDeviceId(context));
        param.put("aType", aType);
        param.put("aId", aId);
        OkHttpUtil.sendRequestPost(URLUtil.Bus100601, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
}

