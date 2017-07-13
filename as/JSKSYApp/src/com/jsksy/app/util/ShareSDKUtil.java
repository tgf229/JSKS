package com.jsksy.app.util;

import android.content.Context;
import android.util.Log;

import com.jsksy.app.constant.URLUtil;

import java.net.URLEncoder;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 涂高峰 on 2017/6/12.
 */
public class ShareSDKUtil {

    public static String structureUrl(String type,String sNum){
        String url = null;
        try {
            String a = URLEncoder.encode(SecurityUtils.encode2Str(type),"UTF-8");
            String b = URLEncoder.encode(SecurityUtils.encode2Str(sNum),"UTF-8");
            url = URLUtil.SHARE_URL+"a="+a+"&b="+b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("url", "structureUrl: "+url);
        return url;
    }

    public static void showSDKShare(Context context, String title,String content,String url,String platform){
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(title);  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(content);  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://sdata.jseea.cn:8081/imgs/edu/images/52.png");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ和空间使用
        oks.setTitleUrl(url);  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(context);
    }
}
