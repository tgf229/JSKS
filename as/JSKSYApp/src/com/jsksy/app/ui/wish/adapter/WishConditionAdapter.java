package com.jsksy.app.ui.wish.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.callback.UICallBack;
import com.jsksy.app.ui.wish.WishConditionActivity;

public class WishConditionAdapter extends BaseAdapter
{
    private Context context;
    
    private String[] mList;
    
    private UICallBack callBack;
    
    private String choise;
    
    private String type;
    
    public WishConditionAdapter(Context context, String[] mList, UICallBack callBack, String choise, String type)
    {
        this.context = context;
        this.mList = mList;
        this.callBack = callBack;
        this.choise = choise;
        this.type = type;
    }
    
    @Override
    public int getCount()
    {
        return mList == null ? 0 : mList.length;
    }
    
    @Override
    public Object getItem(int position)
    {
        return mList == null ? null : mList[position];
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final String entityId = mList[position].split(",")[0];
        final String entity = mList[position].split(",")[1];
        final HolderView mHolder;
        if (convertView == null)
        {
            mHolder = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.wish_condition_list_item, null);
            mHolder.name_txt = (TextView)convertView.findViewById(R.id.name_txt);
            mHolder.img = (ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(mHolder);
        }
        else
        {
            mHolder = (HolderView)convertView.getTag();
        }
        
        if (entityId.equals(choise))
        {
            mHolder.img.setVisibility(View.VISIBLE);
            mHolder.name_txt.setTextColor(R.color.color_ff902d);
        }
        else
        {
            mHolder.img.setVisibility(View.GONE);
            mHolder.name_txt.setTextColor(R.color.color_7192b5);
        }
        
        mHolder.name_txt.setText(entity);
        convertView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mHolder.img.setVisibility(View.VISIBLE);
                mHolder.name_txt.setTextColor(R.color.color_ff902d);
                
                Intent intent = new Intent(context, WishConditionActivity.class);
                intent.putExtra("id", entityId);
                intent.putExtra("val", entity);
                if ("1".equals(type))
                {
                    ((Activity)context).setResult(1001, intent);
                }
                else if ("2".equals(type))
                {
                    ((Activity)context).setResult(1002, intent);
                }
                else if ("3".equals(type))
                {
                    ((Activity)context).setResult(1003, intent);
                }
                else if ("4".equals(type))
                {
                    ((Activity)context).setResult(1004, intent);
                }
                
                ((Activity)context).finish();
            }
        });
        return convertView;
    }
    
    class HolderView
    {
        TextView name_txt;
        
        ImageView img;
    }
}
