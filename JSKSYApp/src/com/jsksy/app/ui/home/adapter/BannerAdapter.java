package com.jsksy.app.ui.home.adapter;

import java.util.ArrayList;

import android.content.Context;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

public class BannerAdapter extends PagerAdapter implements IconPagerAdapter
{
    
    private Context mContext;
    
    private ImageLoader imageLoader = ImageLoader.getInstance();
    
    DisplayImageOptions options;
    
    private ArrayList<BannerDoc> mPaths;
    
    public BannerAdapter(Context context, ArrayList<BannerDoc> paths)
    {
        mContext = context;
        mPaths = paths;
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
//                if (null != mPaths && null != mPaths.get(position))
//                {
//                    
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, AdvertiseDetailActivity.class);
//                    intent.putExtra("id", mPaths.get(position).getId());
//                    mContext.startActivity(intent);
//                }
//                else
//                {
//                    ToastUtil.makeText(mContext,
//                        mContext.getResources().getString(R.string.home_shop_event_none));
//                }
            }
        });
        
        container.addView(pageView, 0);
        
        return pageView;
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
        
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.home_banner) // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.home_banner)
            // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.home_banner)
            // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)
            // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)
            // 设置下载的图片是否缓存在SD卡中
            //      .displayer(new SimpleBitmapDisplayer())
            .build();
    }
    
    @Override
    public int getItemPosition(Object object)
    {
        if(count > 0)//null != mPaths && mPaths.size() == 0)
        {
            count--;
            return  POSITION_NONE;
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
