package com.jsksy.app.ui.school.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolListDoc;
import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.SchoolListActivity;

import java.util.List;

/**
 * Created by 涂高峰 on 17/4/18.
 */
public class SchoolListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "SchoolListAdapter";
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<SchoolListDoc> mList;

    private Context mContext;

    public SchoolListAdapter(List<SchoolListDoc> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = null;
        if (viewType == TYPE_FOOTER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading,parent,false);
            return new FootViewHolder(view);
        }else if(viewType == TYPE_ITEM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_list_item,parent,false);
            final ItemViewHolder vh = new ItemViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = vh.getAdapterPosition();
                    Intent intent = new Intent(mContext,SchoolDetailActivity.class);
                    intent.putExtra("uCode",mList.get(position).getCode());
                    mContext.startActivity(intent);
                }
            });
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            SchoolListDoc doc = mList.get(position);
            Glide.with(mContext).load(doc.getLogo()).into(((ItemViewHolder) holder).img);
            ((ItemViewHolder) holder).name.setText(doc.getName());
            ((ItemViewHolder) holder).code.setText("院校代号"+doc.getCode());
            ((ItemViewHolder) holder).type.setText(doc.getType());
            if ("1".equals(doc.getIsEyy())){
                ((ItemViewHolder) holder).isEyy.setVisibility(View.VISIBLE);
            }else{
                ((ItemViewHolder) holder).isEyy.setVisibility(View.GONE);
            }
            if ("1".equals(doc.getIsJbw())){
                ((ItemViewHolder) holder).isJbw.setVisibility(View.VISIBLE);
            }else{
                ((ItemViewHolder) holder).isJbw.setVisibility(View.GONE);
            }
        }else  if (holder instanceof FootViewHolder){
            SchoolListActivity act  = (SchoolListActivity)mContext;

//            if (mList.size()%10 != 0){
            if (!act.hasMore){
                ((FootViewHolder) holder).loading_more.setVisibility(View.GONE);
                ((FootViewHolder) holder).end_tips.setVisibility(View.VISIBLE);
            }else{
                ((FootViewHolder) holder).loading_more.setVisibility(View.VISIBLE);
                ((FootViewHolder) holder).end_tips.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()==0?0:mList.size()+1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView code;
        TextView type;
        TextView isEyy;
        TextView isJbw;
        public ItemViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            code = (TextView) itemView.findViewById(R.id.code);
            type = (TextView) itemView.findViewById(R.id.type);
            isEyy = (TextView) itemView.findViewById(R.id.isEyy);
            isJbw = (TextView) itemView.findViewById(R.id.isJbw);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout loading_more;
        RelativeLayout end_tips;

        public FootViewHolder(View itemView) {
            super(itemView);
            loading_more = (LinearLayout) itemView.findViewById(R.id.loading_more);
            end_tips = (RelativeLayout) itemView.findViewById(R.id.end_tips);
        }
    }
}
