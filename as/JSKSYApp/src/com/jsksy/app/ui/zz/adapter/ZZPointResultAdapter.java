/*
 * �� �� ��:  ZZPointResultAdapter.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-12-5
 
 */
package com.jsksy.app.ui.zz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.zz.PointDoc;
import com.jsksy.app.callback.UICallBack;
import com.jsksy.app.ui.zz.ZZPointResultActivity;
import com.jsksy.app.util.GeneralUtils;

import java.util.List;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-12-5]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class ZZPointResultAdapter extends BaseAdapter
{
    
    private List<PointDoc> mList;
    
    private ZZPointResultActivity context;
    
    private UICallBack callBack;
    
    private final static int[] picList = new int[] {R.drawable.zz_yuwen, R.drawable.zz_shuxue, R.drawable.zz_yingyu,
        R.drawable.zz_deyu, R.drawable.zz_zhuanye, R.drawable.zz_it, R.drawable.zz_jineng};
    
    public ZZPointResultAdapter(ZZPointResultActivity context, List<PointDoc> dataList, UICallBack callBack)
    {
        this.context = context;
        this.mList = dataList;
        this.callBack = callBack;
    }
    
    @Override
    public int getCount()
    {
        return mList == null ? 0 : mList.size();
    }
    
    /** {@inheritDoc} */
    
    @Override
    public Object getItem(int position)
    {
        return mList == null ? null : mList.get(position);
    }
    
    /** {@inheritDoc} */
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PointDoc entity = mList.get(position);
        HolderView mHolder;
        if (convertView == null)
        {
            mHolder = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.zz_point_result_item, null);
            mHolder.img = (LinearLayout)convertView.findViewById(R.id.img);
            mHolder.name = (TextView)convertView.findViewById(R.id.name);
            mHolder.level = (TextView)convertView.findViewById(R.id.level);
            mHolder.level_tag = (TextView)convertView.findViewById(R.id.level_tag);
            convertView.setTag(mHolder);
        }
        else
        {
            mHolder = (HolderView)convertView.getTag();
        }
        
        mHolder.img.setBackgroundResource(picList[position]);
        mHolder.name.setText(entity.getKMName());
        mHolder.level.setText(entity.getKMLevel());
        mHolder.level_tag.setVisibility(View.VISIBLE);
        
        if (GeneralUtils.isNotNullOrZeroLenght(entity.getCheatType()))
        {
            mHolder.level_tag.setVisibility(View.GONE);
            mHolder.img.setBackgroundResource(R.drawable.zz_grey);
            mHolder.level.setTextSize(20);
            if ("0".equals(entity.getCheatType()))
            {
                mHolder.level.setText("违纪");
            }
            else if ("1".equals(entity.getCheatType()))
            {
                mHolder.level.setText("作弊");
            }
            else if ("2".equals(entity.getCheatType()))
            {
                mHolder.level.setText("违规");
            }
            else if ("3".equals(entity.getCheatType()))
            {
                mHolder.level.setText("其他");
            }
        }
        else if ("通过".equals(entity.getKMLevel()) || "不通过".equals(entity.getKMLevel()))
        {
            mHolder.level_tag.setVisibility(View.GONE);
            mHolder.level.setTextSize(15);
        }
        
        return convertView;
    }
    
    class HolderView
    {
        LinearLayout img;
        
        TextView name;
        
        TextView level;
        
        TextView level_tag;
    }
}
