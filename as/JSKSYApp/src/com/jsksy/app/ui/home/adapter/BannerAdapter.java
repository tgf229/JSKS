package com.jsksy.app.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.home.BannerDoc;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.WebviewActivity;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;
import com.jsksy.app.util.GeneralUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter implements IconPagerAdapter
{
    private static final String TAG = "BannerAdapter";
    private Context mContext;
    
    private ImageLoader imageLoader = ImageLoader.getInstance();
    
    DisplayImageOptions options;
    
    private ArrayList<BannerDoc> mPaths;
    private String mADChannle;
    
    public BannerAdapter(Context context, ArrayList<BannerDoc> paths,String ADChannle)
    {
        mContext = context;
        mPaths = paths;
        mADChannle = ADChannle;
        setDisplayImageOptions();
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View pageView = LayoutInflater.from(mContext).inflate(R.layout.home_banner_item, null);
        
        final ImageView adImageView = (ImageView)pageView.findViewById(R.id.iv_banner);
        TextView banner_text = (TextView)pageView.findViewById(R.id.banner_text);
        imageLoader.displayImage(mPaths.get(position).getImageUrl(), adImageView, options);
        banner_text.setText(mPaths.get(position).getName());
        pageView.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                String url = mPaths.get(position).getaUrl();
                urlSchemaJump(url);
                OkHttpUtil.reqADLog(mContext,mPaths.get(position).getaId(),mADChannle);
            }
        });
        
        container.addView(pageView, 0);
        
        return pageView;
    }

    private void urlSchemaJump(String url){
        switch (GeneralUtils.urlSchemaForWhat(url)){
            case 1:
                String urlSchemaDetail = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaDetail != null){
                    Intent intent = new Intent(mContext, SchoolDetailActivity.class);
                    intent.putExtra("uCode", urlSchemaDetail);
                    mContext.startActivity(intent);
                }
                break;
            case 2:
                String urlSchemaList = GeneralUtils.getUrlSchemaParam(url);
                if (urlSchemaList != null){
                    Intent intent = new Intent(mContext, SchoolListActivity.class);
                    intent.putExtra("searchKey", urlSchemaList);
                    mContext.startActivity(intent);
                }
                break;
            default:
                Intent skipIntent = new Intent(mContext, WebviewActivity.class);
                skipIntent.putExtra("wev_view_url",url);
                mContext.startActivity(skipIntent);
                break;
        }
    }
    
    @Override
    public int getCount()
    {
        return null != mPaths ? mPaths.size() : 0;
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == (View)object;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager)container).removeView((View)object);
    }
    
    @Override
    public int getIconResId(int index)
    {
        return index;
    }
    
    void setDisplayImageOptions()
    {
        
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.home_banner) // ����ͼƬ�����ڼ���ʾ��ͼƬ  
            .showImageForEmptyUri(R.drawable.home_banner)
            // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
            .showImageOnFail(R.drawable.home_banner)
            // ����ͼƬ���ػ��������з���������ʾ��ͼƬ      
            .cacheInMemory(true)
            // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
            .cacheOnDisc(true)
            // �������ص�ͼƬ�Ƿ񻺴���SD����
            //      .displayer(new SimpleBitmapDisplayer())
            .build();
    }
    
    @Override
    public int getItemPosition(Object object)
    {
        if (count > 0)//null != mPaths && mPaths.size() == 0)
        {
            count--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
    
    private int count;
    
    @Override
    public void notifyDataSetChanged()
    {
        count = getCount();
        super.notifyDataSetChanged();
    }
}
