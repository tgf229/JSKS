/*
 * �� �� ��:  PointSearchActivity.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-18
 
 */
package com.jsksy.app.ui.point;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.bean.point.PointAd;
import com.jsksy.app.bean.point.PointResponse;
import com.jsksy.app.bean.point.PointTimeResponse;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.ConnectService;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.BaseActivity;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.ui.wish.WishAgreementActivity;
import com.jsksy.app.util.GeneralUtils;
import com.jsksy.app.util.NetLoadingDailog;
import com.jsksy.app.util.SecurityUtils;
import com.jsksy.app.util.ShareSDKUtil;
import com.jsksy.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 *
 * @author tgf
 * @version [�汾��, 2016-4-18]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class PointResultActivity extends BaseActivity implements OnClickListener {
    private LinearLayout load_layout, content_layout, KM_layout, rank_layout, single_layout, double_layout,
            double_saadd_layout;

    private ProgressBar load_progress;

    private TextView load_txt, sName, totalTitle, totalPoint, totalName, chineseTitle, chinesePoint, mathTitle,
            mathPoint, englishPoint, KM4_level, KM4_name, KM5_level, KM5_name, poAdd, addTitle, addPoint, SAPoint,
            DOUBLETitle, DOUBLEPoint, saAdd, tip_content;

    private ImageView KM4_img, KM5_img, addPic;

    private String sNum, sTicket;

    private String sCheckKeyA, sCheckKeyB;

    private String zf1 = null;

    private String zf2 = null;

    private NetLoadingDailog dailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_result);
        sNum = getIntent().getStringExtra("sNum");
        sTicket = getIntent().getStringExtra("sTicket");
        sCheckKeyA = getIntent().getStringExtra("sCheckKeyA");
        sCheckKeyB = getIntent().getStringExtra("sCheckKeyB");
        init();
        reqPoint();
    }

    private void init() {
        LinearLayout app_title_back = (LinearLayout) findViewById(R.id.app_title_back);
        TextView title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("高考查分");
        app_title_back.setOnClickListener(this);

        load_layout = (LinearLayout) findViewById(R.id.load_layout);
        load_progress = (ProgressBar) findViewById(R.id.load_progress);
        load_txt = (TextView) findViewById(R.id.load_txt);

        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        single_layout = (LinearLayout) findViewById(R.id.single_layout);
        double_layout = (LinearLayout) findViewById(R.id.double_layout);
        sName = (TextView) findViewById(R.id.sName);
        totalTitle = (TextView) findViewById(R.id.totalTitle);
        totalPoint = (TextView) findViewById(R.id.totalPoint);
        totalName = (TextView) findViewById(R.id.totalName);
        DOUBLETitle = (TextView) findViewById(R.id.DOUBLETitle);
        DOUBLEPoint = (TextView) findViewById(R.id.DOUBLEPoint);
        SAPoint = (TextView) findViewById(R.id.SAPoint);
        rank_layout = (LinearLayout) findViewById(R.id.rank_layout);

        chineseTitle = (TextView) findViewById(R.id.chineseTitle);
        chinesePoint = (TextView) findViewById(R.id.chinesePoint);
        mathTitle = (TextView) findViewById(R.id.mathTitle);
        mathPoint = (TextView) findViewById(R.id.mathPoint);
        englishPoint = (TextView) findViewById(R.id.englishPoint);

        KM4_img = (ImageView) findViewById(R.id.KM4_img);
        KM5_img = (ImageView) findViewById(R.id.KM5_img);
        KM4_level = (TextView) findViewById(R.id.KM4_level);
        KM4_name = (TextView) findViewById(R.id.KM4_name);
        KM5_level = (TextView) findViewById(R.id.KM5_level);
        KM5_name = (TextView) findViewById(R.id.KM5_name);
        KM_layout = (LinearLayout) findViewById(R.id.KM_layout);

        poAdd = (TextView) findViewById(R.id.poAdd);
        addTitle = (TextView) findViewById(R.id.addTitle);
        addPoint = (TextView) findViewById(R.id.addPoint);
        addPic = (ImageView) findViewById(R.id.addPic);

        double_saadd_layout = (LinearLayout) findViewById(R.id.double_saadd_layout);
        saAdd = (TextView) findViewById(R.id.saAdd);

        tip_content = (TextView) findViewById(R.id.tip_content);

        LinearLayout wish_pic_layout = (LinearLayout) findViewById(R.id.wish_pic_layout);
        wish_pic_layout.setOnClickListener(this);

        //分享
        LinearLayout share_wx = (LinearLayout) findViewById(R.id.share_wx);
        LinearLayout share_wx_timeline = (LinearLayout) findViewById(R.id.share_wx_timeline);
        LinearLayout share_qq = (LinearLayout) findViewById(R.id.share_qq);
        LinearLayout share_qq_timeline = (LinearLayout) findViewById(R.id.share_qq_timeline);
        share_wx.setOnClickListener(this);
        share_wx_timeline.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qq_timeline.setOnClickListener(this);


    }

    private void reqPoint() {
        Map<String, String> param = new HashMap<String, String>();
        try {
            param.put("sNum", SecurityUtils.encode2Str(sNum));
            param.put("sCheck", SecurityUtils.encode2Str(sTicket));
            param.put("sCheckKeyA", SecurityUtils.encode2Str(sCheckKeyA));
            param.put("sCheckKeyB", SecurityUtils.encode2Str(sCheckKeyB));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectService.instance().connectServiceReturnResponse(this,
                param,
                PointResultActivity.this,
                PointResponse.class,
                URLUtil.Bus200201,
                Constants.ENCRYPT_SIMPLE);
    }

    @Override
    public void netBack(Object ob) {
        super.netBack(ob);
        if (ob instanceof PointResponse) {
            PointResponse resp = (PointResponse) ob;
            if (GeneralUtils.isNotNullOrZeroLenght(resp.getRetcode())) {
                if (Constants.SUCESS_CODE.equals(resp.getRetcode())) {
                    content_layout.setVisibility(View.VISIBLE);
                    load_layout.setVisibility(View.GONE);
                    showPoint(resp);
                    showAd(resp);

                } else {
                    content_layout.setVisibility(View.GONE);
                    load_layout.setVisibility(View.VISIBLE);
                    load_progress.setVisibility(View.GONE);
                    load_txt.setText(resp.getRetinfo());
                }
            } else {
                content_layout.setVisibility(View.GONE);
                load_layout.setVisibility(View.VISIBLE);
                load_progress.setVisibility(View.GONE);
                load_txt.setText(Constants.ERROR_MESSAGE);
            }
        } else if (ob instanceof PointTimeResponse) {
            dailog.dismissDialog();
            PointTimeResponse ptrest = (PointTimeResponse) ob;

            if (GeneralUtils.isNotNullOrZeroLenght(ptrest.getRetcode())) {
                if (Constants.SUCESS_CODE.equals(ptrest.getRetcode())) {
                    if (Double.parseDouble(ptrest.getWsTime()) > Double.parseDouble(ptrest.getCuTime())) {
                        Intent intentPoint = new Intent(this, PointWaitActivity.class);
                        intentPoint.putExtra("cuTime", ptrest.getCuTime());
                        intentPoint.putExtra("exTime", ptrest.getExTime());
                        intentPoint.putExtra("wsTime", ptrest.getWsTime());
                        intentPoint.putExtra("waitType", "2");
                        startActivity(intentPoint);
                    } else {
                        Intent intentPoint = new Intent(this, WishAgreementActivity.class);
                        startActivity(intentPoint);
                    }
                } else {
                    ToastUtil.makeText(this, ptrest.getRetinfo());
                }
            } else {
                ToastUtil.makeText(this, "网络异常,请检查网络");
            }
        }
    }

    private void showPoint(PointResponse obj) {
        sName.setText("姓名:" + obj.getsName() + "    考生号:" + sNum);
        totalName.setText(obj.getTotalName() + obj.getTotalLevel() + "名");
        chinesePoint.setText(obj.getChinese() + "分");
        mathPoint.setText(obj.getMath() + "分");
        englishPoint.setText(obj.getEnglish() + "分");
        tip_content.setText(obj.getTipContent() + "考生成绩以成绩通知单为准");

        //KM4 KM5ͼƬ����
        filterImg(obj);
        KM4_name.setText(obj.getKM4Name());
        KM4_level.setText(obj.getKM4Level());
        KM5_name.setText(obj.getKM5Name());
        KM5_level.setText(obj.getKM5Level());

        poAdd.setText(obj.getPoAdd() + "分");
        addTitle.setText("文理类奖励分");
        addPoint.setText(obj.getCmAdd() + "分");

        if (GeneralUtils.isNullOrZeroLenght(obj.getTotalName())) {
            rank_layout.setVisibility(View.GONE);
        }

        //文
        if ("1".equals(obj.getType())) {
            totalTitle.setText("文科类总分");
            totalPoint.setText(obj.getChTotal());
            chineseTitle.setText("语文+附加分");
            chinesePoint.setText(obj.getChinese() + "分+" + obj.getChAdd() + "分");
            zf1 = obj.getChTotal();
        }
        //理
        else if ("2".equals(obj.getType())) {
            totalTitle.setText("理科类总分");
            totalPoint.setText(obj.getMaTotal());
            mathTitle.setText("数学+附加分");
            mathPoint.setText(obj.getMath() + "分+" + obj.getMaAdd() + "分");
            zf1 = obj.getMaTotal();
        }
        //艺术  体育
        else if ("3".equals(obj.getType()) || "4".equals(obj.getType())) {
            totalTitle.setText("体艺文化总分");
            totalPoint.setText(obj.getSaTotal());
            addTitle.setText("体艺类奖励分");
            addPoint.setText(obj.getSaAdd() + "分");
            addPic.setImageResource(R.drawable.point_icon_b_add);
            KM_layout.setVisibility(View.GONE);
            zf1 = obj.getSaTotal();
        }
        //艺术兼文 体育兼文
        else if ("5".equals(obj.getType()) || "7".equals(obj.getType())) {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("文科类总分");
            DOUBLEPoint.setText(obj.getChTotal());
            SAPoint.setText(obj.getSaTotal());
            chineseTitle.setText("语文+附加分");
            chinesePoint.setText(obj.getChinese() + "分+" + obj.getChAdd() + "分");
            saAdd.setText(obj.getSaAdd()+"分");
            zf1 = obj.getChTotal();
            zf2 = obj.getSaTotal();
        }
        //艺术兼理 体育兼理
        else if ("6".equals(obj.getType()) || "8".equals(obj.getType())) {
            double_layout.setVisibility(View.VISIBLE);
            single_layout.setVisibility(View.GONE);
            double_saadd_layout.setVisibility(View.VISIBLE);
            DOUBLETitle.setText("理科类总分");
            DOUBLEPoint.setText(obj.getMaTotal());
            SAPoint.setText(obj.getSaTotal());
            mathTitle.setText("数学+附加分");
            mathPoint.setText(obj.getMath() + "分+" + obj.getMaAdd() + "分");
            saAdd.setText(obj.getSaAdd()+"分");
            zf1 = obj.getMaTotal();
            zf2 = obj.getSaTotal();
        }
    }

    private void showAd(final PointResponse resp) {
        List<PointAd> adList = resp.getDoc();

        for (final PointAd ad : adList) {
            LinearLayout advLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.point_adv_item, null);
            ImageView point_ad_img = (ImageView) advLayout.findViewById(R.id.point_ad_img);
            ImageLoader.getInstance().displayImage(ad.getImageUrl(),
                    point_ad_img,
                    JSKSYApplication.setAllDisplayImageOptions(this, "default_pic", "default_pic", "default_pic"));
            point_ad_img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ad.getaUrl();
                    urlSchemaJump(url);
                    OkHttpUtil.reqADLog(PointResultActivity.this,ad.getaId(),"7");
                }
            });
            content_layout.addView(advLayout);
        }
    }

    private void urlSchemaJump(String url){
        switch (GeneralUtils.urlSchemaForWhat(url)){
            case 1:
                String urlSchemaDetail = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaDetail != null){
                    Intent intent = new Intent(this, SchoolDetailActivity.class);
                    intent.putExtra("uCode", urlSchemaDetail);
                    startActivity(intent);
                }
                break;
            case 2:
                String urlSchemaList = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaList != null){
                    Intent intent = new Intent(this, SchoolListActivity.class);
                    intent.putExtra("searchKey", urlSchemaList);
                    this.startActivity(intent);
                }
                break;
            default:
                Intent skipIntent = new Intent(this, WebviewActivity.class);
                skipIntent.putExtra("wev_view_url",url);
                this.startActivity(skipIntent);
                break;
        }
    }

    private void filterImg(PointResponse obj) {
        if ("地理".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_dili);
        } else if ("化学".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_huaxue);
        } else if ("历史".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_lishi);
        } else if ("生物".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_shengwu);
        } else if ("物理".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_wuli);
        } else if ("政治".equals(obj.getKM4Name())) {
            KM4_img.setImageResource(R.drawable.point_icon_zhengzhi);
        }

        if ("地理".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_dili);
        } else if ("化学".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_huaxue);
        } else if ("历史".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_lishi);
        } else if ("生物".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_shengwu);
        } else if ("物理".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_wuli);
        } else if ("政治".equals(obj.getKM5Name())) {
            KM5_img.setImageResource(R.drawable.point_icon_zhengzhi);
        }
    }

    /**
     * <����ʱ��У׼�ӿ�>
     * <������ϸ����>
     *
     * @see [�ࡢ��#��������#��Ա]
     */
    private void reqPointTime() {
        dailog = new NetLoadingDailog(this);
        dailog.loading();
        Map<String, String> param = new HashMap<String, String>();
        ConnectService.instance().connectServiceReturnResponse(this,
                param,
                PointResultActivity.this,
                PointTimeResponse.class,
                URLUtil.Bus200101,
                Constants.ENCRYPT_NONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_title_back:
                finish();
                break;
            case R.id.wish_pic_layout:
                reqPointTime();
                break;
            case R.id.share_wx:
                String share_wx_url = ShareSDKUtil.structureUrl("1",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_wx_url)){
                    ShareSDKUtil.showSDKShare(PointResultActivity.this, getString(R.string.share_point_text), getString(R.string.share_point_text),share_wx_url , Wechat.NAME);
                }
                break;
            case R.id.share_wx_timeline:
                String share_wx_timeline_url = ShareSDKUtil.structureUrl("1",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_wx_timeline_url)){
                    ShareSDKUtil.showSDKShare(PointResultActivity.this, getString(R.string.share_point_text), getString(R.string.share_point_text),share_wx_timeline_url,WechatMoments.NAME);
                }
                break;
            case R.id.share_qq:
                String share_qq_url = ShareSDKUtil.structureUrl("1",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_qq_url)){
                    ShareSDKUtil.showSDKShare(PointResultActivity.this, getString(R.string.share_point_text), getString(R.string.share_point_text),share_qq_url, QQ.NAME);
                }
                break;
            case R.id.share_qq_timeline:
                String share_qq_timeline_url = ShareSDKUtil.structureUrl("1",sNum);
                if (GeneralUtils.isNotNullOrZeroLenght(share_qq_timeline_url)){
                    ShareSDKUtil.showSDKShare(PointResultActivity.this, getString(R.string.share_point_text), getString(R.string.share_point_text),share_qq_timeline_url, QZone.NAME);
                }
                break;
            default:
                break;
        }
    }
}
