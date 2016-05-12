package com.jsksy.app.ui;

import java.util.ArrayList;

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
import com.jsksy.app.ui.home.HomeActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * 
 * <����ͼƬ������> 
 * <������ϸ����>
 * 
 * @author  qiuqiaohua
 * @version  [�汾��, Apr 30, 2014]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class GuidePagerAdapter extends PagerAdapter implements IconPagerAdapter
{
    
    private Context mContext;
    
    private ImageLoader imageLoader = ImageLoader.getInstance();
    
    DisplayImageOptions options;
    
    private WelcomeActivity activity;
    
    /**
     * ͼƬ���·��
     */
    private ArrayList<String> mPaths;
    
    public GuidePagerAdapter(Context context, ArrayList<String> paths, WelcomeActivity activity)
    {
        mContext = context;
        mPaths = paths;
        setDisplayImageOptions();
        this.activity = activity;
    }
    
    /** {ʵ������Ŀ}*/
    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View pageView = null;
        if (position == mPaths.size() - 1)
        {
            pageView = LayoutInflater.from(mContext).inflate(R.layout.guide_item_last, null);
            TextView btn = (TextView)pageView.findViewById(R.id.btn);
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
            //ͨ��ͼƬ������ͼƬID
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
    
    /** {��ȡ��Ŀ����} */
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
    
    /** {������Ŀ} */
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
    
    /**
     * banner��ʾ����
     */
    void setDisplayImageOptions()
    {
        
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_pic) // ����ͼƬ�����ڼ���ʾ��ͼƬ  
            .showImageForEmptyUri(R.drawable.default_pic)
            // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
            .showImageOnFail(R.drawable.default_pic)
            // ����ͼƬ���ػ��������з���������ʾ��ͼƬ      
            .cacheInMemory(true)
            // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
            .cacheOnDisc(true)
            // �������ص�ͼƬ�Ƿ񻺴���SD����
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
    
    /**
     * ���list������պ���ͼ�����ٵ�bug
     */
    @Override
    public int getItemPosition(Object object)
    {
        return null != mPaths && mPaths.size() == 0 ? POSITION_NONE : super.getItemPosition(object);
    }
}
