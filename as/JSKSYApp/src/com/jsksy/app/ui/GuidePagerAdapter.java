package com.jsksy.app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jsksy.app.R;
import com.jsksy.app.ui.home.HomeActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;

public class GuidePagerAdapter extends PagerAdapter implements IconPagerAdapter
{
    
    private Context mContext;
    
    private ImageLoader imageLoader = ImageLoader.getInstance();
    
    DisplayImageOptions options;
    
    private WelcomeActivity activity;
    
    private ArrayList<String> mPaths;
    
    public GuidePagerAdapter(Context context, ArrayList<String> paths, WelcomeActivity activity)
    {
        mContext = context;
        mPaths = paths;
        setDisplayImageOptions();
        this.activity = activity;
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View pageView = null;
        if (position == mPaths.size() - 1)
        {
            pageView = LayoutInflater.from(mContext).inflate(R.layout.guide_item_last, null);
            RelativeLayout btn = (RelativeLayout)pageView.findViewById(R.id.btn);
            btn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                    activity.finish();
                }
            });
        }
        else
        {
            pageView = LayoutInflater.from(mContext).inflate(R.layout.guide_item, null);
            
            ImageView adImageView = (ImageView)pageView.findViewById(R.id.iv_banner);
            int image = mContext.getResources().getIdentifier(mPaths.get(position), "drawable", "com.jsksy.app");
            adImageView.setImageResource(image);
        }
        
        //        if (position == mPaths.size() - 1)
        //        {
        //            adImageView.setOnClickListener(new OnClickListener()
        //            {
        //                
        //                @Override
        //                public void onClick(View v)
        //                {
        //                    Intent intent = new Intent(mContext, LoginActivity.class);
        //                    intent.putExtra("from_homepage", "from_homepage");
        //                    mContext.startActivity(intent);
        //                    activity.finish();
        //                }
        //            });
        //        }
        
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
        
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_pic) // ����ͼƬ�����ڼ���ʾ��ͼƬ  
            .showImageForEmptyUri(R.drawable.default_pic)
            .showImageOnFail(R.drawable.default_pic)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            //      .displayer(new SimpleBitmapDisplayer())
            .build();
        
        //        options =
        //            new DisplayImageOptions.Builder().cacheInMemory(true)
        //                .showImageForEmptyUri(R.drawable.default_load9)
        //                .showImageOnFail(R.drawable.default_load9) 
        //                .showImageOnLoading(R.drawable.default_load9)
        //                .cacheOnDisc(true)
        //                .displayer(new SimpleBitmapDisplayer())
        //                .build();
    }
    
    @Override
    public int getItemPosition(Object object)
    {
        return null != mPaths && mPaths.size() == 0 ? POSITION_NONE : super.getItemPosition(object);
    }
}
