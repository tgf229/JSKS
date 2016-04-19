package com.jsksy.app.ui.wish.adapter;

import java.util.List;

import android.content.Context;
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
import com.jsksy.app.bean.wish.WishDoc;
import com.jsksy.app.callback.UICallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WishListAdapter extends BaseAdapter
{
    private Context context;
    
    private List<WishDoc> mList;
    
    private UICallBack callBack;
    
    public WishListAdapter(Context context, List<WishDoc> mList, UICallBack callBack)
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
        final WishDoc entity = mList.get(position);
        HolderView mHolder;
        if (convertView == null)
        {
            mHolder = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.wish_list_item, null);
            mHolder.code_txt = (TextView)convertView.findViewById(R.id.code_txt);
            mHolder.name_txt = (TextView)convertView.findViewById(R.id.name_txt);
            mHolder.batch_txt = (TextView)convertView.findViewById(R.id.batch_txt);
            mHolder.num_txt = (TextView)convertView.findViewById(R.id.num_txt);
            convertView.setTag(mHolder);
        }
        else
        {
            mHolder = (HolderView)convertView.getTag();
        }
        
        mHolder.code_txt.setText("ÔºÐ£´úºÅ "+entity.getCode());
        mHolder.name_txt.setText(entity.getName());
        mHolder.batch_txt.setText(entity.getBatch());
        mHolder.num_txt.setText(entity.getNum());
        convertView.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
//                Intent intent = new Intent(context, ActiveDetailActivity.class);
//                intent.putExtra("id", entity.getActivity_id());
//                context.startActivity(intent);
            }
        });
        return convertView;
    }
    
    class HolderView
    {
        TextView code_txt;
        TextView name_txt;
        TextView batch_txt;
        TextView num_txt;
    }
}
