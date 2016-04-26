package com.jsksy.app.ui.home.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.bean.home.NewsDoc;
import com.jsksy.app.callback.UICallBack;
import com.jsksy.app.ui.WebviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FreshNewsAdapter extends BaseAdapter
{
    private Context context;
    
    private List<NewsDoc> mList;
    
    private UICallBack callBack;
    
    public FreshNewsAdapter(Context context, List<NewsDoc> mList, UICallBack callBack)
    {
        this.context = context;
        this.mList = mList;
        this.callBack = callBack;
    }
    
    @Override
    public int getCount()
    {
        return mList == null ? 0 : mList.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        return mList == null ? null : mList.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final NewsDoc entity = mList.get(position);
        HolderView mHolder;
        if ("1".equals(entity.getType()))
        {
            if (convertView == null)
            {
                mHolder = new HolderView();
                convertView = LayoutInflater.from(context).inflate(R.layout.home_list_adv_item, null);
                mHolder.img = (ImageView)convertView.findViewById(R.id.img);
                mHolder.title = (TextView)convertView.findViewById(R.id.title);
                convertView.setTag(mHolder);
            }
            else
            {
                mHolder = (HolderView)convertView.getTag();
            }
        }
        else
        {
            if (convertView == null)
            {
                mHolder = new HolderView();
                convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
                mHolder.img = (ImageView)convertView.findViewById(R.id.img);
                mHolder.title = (TextView)convertView.findViewById(R.id.title);
                mHolder.time = (TextView)convertView.findViewById(R.id.time);
                convertView.setTag(mHolder);
            }
            else
            {
                mHolder = (HolderView)convertView.getTag();
            }
            mHolder.time.setText(entity.getTime());
        }
        
        ImageLoader.getInstance().displayImage(entity.getImageUrl(),
            mHolder.img,
            JSKSYApplication.setAllDisplayImageOptions(context, "default_pic", "default_pic", "default_pic"));
        mHolder.title.setText(entity.getName());
        
        convertView.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("wev_view_url", entity.getaUrl());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    
    class HolderView
    {
        ImageView img;
        
        TextView title;
        
        TextView time;
    }
}
