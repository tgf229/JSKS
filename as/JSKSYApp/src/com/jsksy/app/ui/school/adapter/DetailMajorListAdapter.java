package com.jsksy.app.ui.school.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.school.MajorDoc;
import com.jsksy.app.bean.school.MajorItemDoc;
import com.jsksy.app.ui.school.SchoolDetailMajorActivity;

import java.util.List;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class DetailMajorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MajorDoc> mList;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public DetailMajorListAdapter(List<MajorDoc> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = null;
        if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            return new FootViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.major_list_item, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            MajorDoc bean = mList.get(position);
            ((ItemViewHolder) holder).code_txt.setText("专业代号 " + bean.getCode() + "    " + bean.getClazz());
            ((ItemViewHolder) holder).batch_txt.setText(bean.getBatch());

            MajorItemDoc b = bean.getDoc().get(0);
            if (b != null) {
                ((ItemViewHolder) holder).clazz_txt.setText(b.getName());
                ((ItemViewHolder) holder).num_txt.setText(b.getNum());
                ((ItemViewHolder) holder).year_txt.setText("(" + b.getYear() + "年录取人数)");
                ((ItemViewHolder) holder).hScore_txt.setText(b.gethScore());
                ((ItemViewHolder) holder).lScore_txt.setText(b.getlScore());
                ((ItemViewHolder) holder).hBatch_txt.setText(b.gethBatch());
                ((ItemViewHolder) holder).lBatch_txt.setText(b.getlBatch());
                ((ItemViewHolder) holder).pNum_txt.setText("平行志愿人数: " + b.getpNum());
                ((ItemViewHolder) holder).sNum_txt.setText("征求志愿人数: " + b.getsNum());
                ((ItemViewHolder) holder).oNum_txt.setText("服从志愿人数: " + b.getoNum());
            }
        } else if (holder instanceof FootViewHolder) {
            SchoolDetailMajorActivity act = (SchoolDetailMajorActivity) mContext;
            if (!act.hasMore) {
//            if (mList.size()%10 != 0){
                ((FootViewHolder) holder).loading_more.setVisibility(View.GONE);
                ((FootViewHolder) holder).end_tips.setVisibility(View.VISIBLE);
            } else {
                ((FootViewHolder) holder).loading_more.setVisibility(View.VISIBLE);
                ((FootViewHolder) holder).end_tips.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView code_txt;
        TextView batch_txt;
        LinearLayout doc_content;
        TextView clazz_txt;
        TextView num_txt;
        TextView year_txt;
        TextView hScore_txt;
        TextView hBatch_txt;
        TextView lScore_txt;
        TextView lBatch_txt;
        TextView pNum_txt;
        TextView sNum_txt;
        TextView oNum_txt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            code_txt = (TextView) itemView.findViewById(R.id.code_txt);
            batch_txt = (TextView) itemView.findViewById(R.id.batch_txt);
            doc_content = (LinearLayout) itemView.findViewById(R.id.doc_content);
            clazz_txt = (TextView) itemView.findViewById(R.id.clazz_txt);
            num_txt = (TextView) itemView.findViewById(R.id.num_txt);
            year_txt = (TextView) itemView.findViewById(R.id.year_txt);
            hScore_txt = (TextView) itemView.findViewById(R.id.hScore_txt);
            lScore_txt = (TextView) itemView.findViewById(R.id.lScore_txt);
            hBatch_txt = (TextView) itemView.findViewById(R.id.hBatch_txt);
            lBatch_txt = (TextView) itemView.findViewById(R.id.lBatch_txt);
            pNum_txt = (TextView) itemView.findViewById(R.id.pNum_txt);
            sNum_txt = (TextView) itemView.findViewById(R.id.sNum_txt);
            oNum_txt = (TextView) itemView.findViewById(R.id.oNum_txt);
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
