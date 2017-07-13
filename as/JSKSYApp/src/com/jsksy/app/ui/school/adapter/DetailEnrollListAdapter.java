package com.jsksy.app.ui.school.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.school.EnrollItemDoc;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class DetailEnrollListAdapter extends RecyclerView.Adapter<DetailEnrollListAdapter.ViewHolder> {

    private ArrayList<EnrollItemDoc> mList;

    public DetailEnrollListAdapter(ArrayList<EnrollItemDoc> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enroll_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EnrollItemDoc bean = mList.get(position);
        if (bean.isHead()){
            holder.head_layout.setVisibility(View.VISIBLE);
            holder.item_layout.setVisibility(View.GONE);
            holder.rank_txt.setText("选测科目等级要求: "+bean.getRank());
            holder.cb_txt.setText(bean.getClazz()+bean.getBatch());
        }else{
            holder.head_layout.setVisibility(View.GONE);
            holder.item_layout.setVisibility(View.VISIBLE);
            holder.code_txt.setText("专业代号 "+bean.getCode());
            holder.name_txt.setText(bean.getName());
            holder.num_txt.setText(bean.getNum()+"人");
            holder.year_txt.setText(bean.getYear());
            holder.cost_txt.setText(bean.getCost());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout head_layout;
        TextView cb_txt;
        TextView rank_txt;

        LinearLayout item_layout;
        TextView code_txt;
        TextView name_txt;
        TextView num_txt;
        TextView year_txt;
        TextView cost_txt;
        public ViewHolder(View itemView) {
            super(itemView);
            head_layout = (LinearLayout) itemView.findViewById(R.id.head_layout);
            cb_txt = (TextView) itemView.findViewById(R.id.cb_txt);
            rank_txt = (TextView) itemView.findViewById(R.id.rank_txt);

            item_layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            code_txt = (TextView) itemView.findViewById(R.id.code_txt);
            name_txt = (TextView) itemView.findViewById(R.id.name_txt);
            num_txt = (TextView) itemView.findViewById(R.id.num_txt);
            year_txt = (TextView) itemView.findViewById(R.id.year_txt);
            cost_txt = (TextView) itemView.findViewById(R.id.cost_txt);
        }
    }
}
