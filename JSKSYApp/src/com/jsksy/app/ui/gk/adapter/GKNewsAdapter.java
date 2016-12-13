package com.jsksy.app.ui.gk.adapter;

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
import com.jsksy.app.bean.gk.NewsDoc;
import com.jsksy.app.callback.UICallBack;
import com.jsksy.app.ui.WebviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GKNewsAdapter extends BaseAdapter
{
    private Context context;
    
    private List<NewsDoc> mList;
    
    private UICallBack callBack;
    
    public GKNewsAdapter(Context context, List<NewsDoc> mList, UICallBack callBack)
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
    public View getView(final int position, View convertView123, ViewGroup parent)
    {
        final NewsDoc entity = mList.get(position);
        View convertView1 = null;
        View convertView2 = null;
        HolderView mHolder1;
        HolderView mHolder2;
        if ("1".equals(entity.getType()))
        {
            if (convertView1 == null)
            {
                mHolder1 = new HolderView();
                convertView1 = LayoutInflater.from(context).inflate(R.layout.gk_home_list_adv_item, null);
                mHolder1.img = (ImageView)convertView1.findViewById(R.id.img);
                mHolder1.title = (TextView)convertView1.findViewById(R.id.title);
                convertView1.setTag(mHolder1);
            }
            else
            {
                mHolder1 = (HolderView)convertView1.getTag();
            }
            
            ImageLoader.getInstance().displayImage(entity.getImageUrl(),
                mHolder1.img,
                JSKSYApplication.setAllDisplayImageOptions(context, "default_pic", "default_pic", "default_pic"));
            mHolder1.title.setText(entity.getName());
            convertView1.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("wev_view_url", entity.getaUrl());
                    context.startActivity(intent);
                }
            });
            return convertView1;
        }
        else
        {
            if (convertView2 == null)
            {
                mHolder2 = new HolderView();
                convertView2 = LayoutInflater.from(context).inflate(R.layout.gk_home_list_item, null);
                mHolder2.img = (ImageView)convertView2.findViewById(R.id.img);
                mHolder2.title = (TextView)convertView2.findViewById(R.id.title);
                mHolder2.time = (TextView)convertView2.findViewById(R.id.time);
                
                mHolder2.time.setText(entity.getTime());
            }
            else
            {
                mHolder2 = (HolderView)convertView2.getTag();
            }
            
            convertView2.setTag(mHolder2);
            ImageLoader.getInstance().displayImage(entity.getImageUrl(),
                mHolder2.img,
                JSKSYApplication.setAllDisplayImageOptions(context, "default_pic", "default_pic", "default_pic"));
            mHolder2.title.setText(entity.getName());
            convertView2.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("wev_view_url", entity.getaUrl());
                    context.startActivity(intent);
                }
            });
            return convertView2;
        }
    }
    
    class HolderView
    {
        ImageView img;
        
        TextView title;
        
        TextView time;
    }
}
