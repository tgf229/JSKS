package com.jsksy.app.ui.school.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsksy.app.R;
import com.jsksy.app.bean.school.SchoolDetailClazzDoc;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentTwo;

import java.util.List;

/**
 * Created by 涂高峰 on 17/4/24.
 */
public class DetailClazzListAdapter extends RecyclerView.Adapter<DetailClazzListAdapter.ViewHolder> {
    private static final String TAG = "DetailClazzListAdapter";
    private List<SchoolDetailClazzDoc> mList;
    private SchoolDetailFragmentTwo fragmentContext;

    public DetailClazzListAdapter(List<SchoolDetailClazzDoc> mList,SchoolDetailFragmentTwo context) {
        this.mList = mList;
        this.fragmentContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_clazz_item,parent,false);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                //触发 当前科类被点击事件
                fragmentContext.clazzClicked(position);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SchoolDetailClazzDoc bean = mList.get(position);
        if (bean.isSelected()){
            holder.clazz_layout.setBackgroundResource(R.drawable.btn_tab_blue_press);
            holder.name_txt.setTextColor(fragmentContext.getResources().getColor(R.color.color_ffffff));
        }else{
            holder.clazz_layout.setBackgroundResource(R.drawable.btn_tab_blue);
            holder.name_txt.setTextColor(fragmentContext.getResources().getColor(R.color.color_4990e2));
        }
        holder.name_txt.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name_txt;
        RelativeLayout clazz_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            clazz_layout = (RelativeLayout) itemView.findViewById(R.id.clazz_layout);
            name_txt = (TextView) itemView.findViewById(R.id.name_txt);
        }
    }
}
