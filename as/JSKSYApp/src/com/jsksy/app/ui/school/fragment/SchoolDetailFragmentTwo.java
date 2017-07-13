package com.jsksy.app.ui.school.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsksy.app.R;
import com.jsksy.app.bean.school.PointDoc;
import com.jsksy.app.bean.school.PointResponse;
import com.jsksy.app.bean.school.SchoolDetailBatchDoc;
import com.jsksy.app.bean.school.SchoolDetailClazzDoc;
import com.jsksy.app.bean.school.SchoolDetailInfo;
import com.jsksy.app.constant.Constants;
import com.jsksy.app.constant.URLUtil;
import com.jsksy.app.network.OkHttpUtil;
import com.jsksy.app.ui.school.SchoolDetailMajorActivity;
import com.jsksy.app.ui.school.adapter.DetailClazzListAdapter;
import com.jsksy.app.util.GeneralUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 涂高峰 on 17/4/19.
 */
public class SchoolDetailFragmentTwo extends Fragment implements View.OnClickListener{
    private static final String TAG = "SchoolDetailFragmentTwo";
    private LineChartView chart;
    private SchoolDetailInfo mDetail;
    private ArrayList<SchoolDetailClazzDoc> mList = new ArrayList<SchoolDetailClazzDoc>();
    private ArrayList<SchoolDetailBatchDoc> mBatchList = new ArrayList<SchoolDetailBatchDoc>();
    private DetailClazzListAdapter adapter;
    private LinearLayout batch_layout;
    private RelativeLayout progress_circle;
    private View batchView;

    public  int currentClazzIdx;
    private int currentBatchIdx;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_detail_two,container,false);

        RelativeLayout major_layout_2016 = (RelativeLayout) view.findViewById(R.id.major_layout_2016);
        RelativeLayout major_layout_2015 = (RelativeLayout) view.findViewById(R.id.major_layout_2015);
        RelativeLayout major_layout_2014 = (RelativeLayout) view.findViewById(R.id.major_layout_2014);
        RelativeLayout major_layout_2013 = (RelativeLayout) view.findViewById(R.id.major_layout_2013);
        major_layout_2016.setOnClickListener(this);
        major_layout_2015.setOnClickListener(this);
        major_layout_2014.setOnClickListener(this);
        major_layout_2013.setOnClickListener(this);

        chart = (LineChartView)view.findViewById(R.id.chart);
        progress_circle= (RelativeLayout) view.findViewById(R.id.progress_circle);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getContext(),5);
        adapter = new DetailClazzListAdapter(mList,this);
        recycler_view.setLayoutManager(manager);
        recycler_view.setAdapter(adapter);

        batch_layout = (LinearLayout) view.findViewById(R.id.batch_layout);
        return view;
    }

    public void showClazzDetail(SchoolDetailInfo bean){
        mDetail = bean;
        if (bean.getClazzDoc().size() > 0){
            mList.clear();  //清空mList
            bean.getClazzDoc().get(0).setSelected(true); //默认第一个科类选中
            mList.addAll(bean.getClazzDoc());       //加入mList 并刷新
            adapter.notifyDataSetChanged();
            showBatchDetail(mList.get(0).getBatchDoc());  //展示批次

            reqChart();  //查询图表
        }
    }

    private void showBatchDetail(ArrayList<SchoolDetailBatchDoc> doc){
        batch_layout.removeAllViews();
        if (mList.size()>0){
            mBatchList = doc;
            for (int i = 0; i<mBatchList.size(); i++){
                final int j = i;
                LinearLayout batchView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.component_detail_fragment_two_batch, null,false);
                LinearLayout batch_item = (LinearLayout) batchView.findViewById(R.id.batch_item);
                ImageView batch_img = (ImageView) batchView.findViewById(R.id.batch_img);
                TextView batch_txt = (TextView) batchView.findViewById(R.id.batch_txt);
                if (j == currentBatchIdx){
                    batch_img.setImageResource(R.drawable.inputbox_press);
                }
                batch_txt.setText(mBatchList.get(i).getName());
                batch_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        batchClicked(j);
                    }
                });
                batch_layout.addView(batchView);
            }
        }
    }

    public void clazzClicked(int position){
        //赋值当前科类index
        currentClazzIdx = position;
        //科类被点击 批次index重置
        currentBatchIdx = 0;

        //重新渲染科类UI
        for (SchoolDetailClazzDoc b:mList){
            b.setSelected(false);
        }
        mList.get(position).setSelected(true);
        adapter.notifyDataSetChanged();
        showBatchDetail(mList.get(position).getBatchDoc());

        reqChart();
    }

    private void batchClicked(int position){
        //赋值当前批次index
        currentBatchIdx = position;
        //重新渲染批次UI
        for(int i=0 ;i<batch_layout.getChildCount(); i++){
            LinearLayout cLayout = (LinearLayout) batch_layout.getChildAt(i);
            ImageView batch_img = (ImageView) cLayout.findViewById(R.id.batch_img);
            if (position == i){
                batch_img.setImageResource(R.drawable.inputbox_press);
            }else{
                batch_img.setImageResource(R.drawable.inputbox_blank);
            }
        }

        reqChart();
    }

    private void reqChart(){
        progress_circle.setVisibility(View.VISIBLE);
        chart.setVisibility(View.GONE);
        Map<String,String> param = new HashMap<String, String>();
        param.put("encrypt", Constants.ENCRYPT_NONE);
        param.put("code",mDetail.getCode());
        param.put("clazz",mDetail.getClazzDoc().get(currentClazzIdx).getCode());
        param.put("batch",mDetail.getClazzDoc().get(currentClazzIdx).getBatchDoc().get(currentBatchIdx).getCode());
        OkHttpUtil.sendRequestPost(URLUtil.Bus700301, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "reqChart: "+result);
                Gson gson = new Gson();
                final PointResponse bean = gson.fromJson(result,PointResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawChart(bean);
                    }
                });
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {

        }
    }

    private void drawChart(PointResponse bean){
        progress_circle.setVisibility(View.GONE);
        chart.setVisibility(View.VISIBLE);
        int size = bean.getDoc().size();
        String years[] = new String[size];
        String lScores[] = new String[size];
        String hScores[] = new String[size];
        for (int i=0; i<size; i++){
            PointDoc b = bean.getDoc().get(i);
            if (GeneralUtils.isNotNull(b)){
                years[i] = b.getYear();
                lScores[i] = b.getlScore();
                hScores[i] = b.gethScore();
            }
        }

        Line line1 = chartData(lScores,0);
        Line line2 = chartData(hScores,1);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line1);
        lines.add(line2);

        LineChartData data = new LineChartData();
        Axis axisY = new Axis().setHasLines(true);
//        axisY.setName("分数");
        data.setAxisYLeft(axisY);

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i=0; i<years.length; i++){
            axisValues.add(new AxisValue(i+1).setLabel(years[i].substring(2)+"年"));
        }
        if (years.length == 1){
        axisValues.add(new AxisValue(0).setLabel(""));
        }
//        axisValues.add(new AxisValue(1).setLabel("2013"));
//        axisValues.add(new AxisValue(2).setLabel("2014"));
        Axis tempoAxis = new Axis(axisValues).setHasLines(false).setMaxLabelChars(6);
        data.setAxisXBottom(tempoAxis);

        data.setLines(lines);

        chart.setPadding(0,0,20,0);
        chart.setInteractive(false);
        chart.setLineChartData(data);
    }

    private Line chartData(String[] strs,int flag){
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i=0; i<strs.length; i++){
            values.add(new PointValue(i+1, Float.parseFloat(strs[i])));
        }
        if (strs.length == 1){
            values.add(new PointValue(0, 0));
        }
//        if (flag == 0){
//            values = new ArrayList<PointValue>();
//            values.add(new PointValue(0, 0));
//            values.add(new PointValue(1, 391));
//            values.add(new PointValue(2, 393));
//            values.add(new PointValue(3, 395));
//        }else{
//            values = new ArrayList<PointValue>();
//            values.add(new PointValue(0, 0));
//            values.add(new PointValue(1, 403));
//            values.add(new PointValue(2, 400));
//            values.add(new PointValue(3, 402));
//        }

        Line line = new Line(values);
        line.setColor(flag==0 ? getResources().getColor(R.color.color_fc7655):getResources().getColor(R.color.color_8ededc));
        line.setCubic(true);
        line.setShape(ValueShape.CIRCLE);
        line.setPointRadius(4);
        line.setHasPoints(true);
        line.setStrokeWidth(2);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        return line;
    }

    private void intentToMajorList(String year){
        Intent intent = new Intent(getContext(), SchoolDetailMajorActivity.class);
        intent.putExtra("year",year);
        intent.putExtra("uCode",mDetail.getCode());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.major_layout_2016:
                intentToMajorList("2016");
                break;
            case R.id.major_layout_2015:
                intentToMajorList("2015");
                break;
            case R.id.major_layout_2014:
                intentToMajorList("2014");
                break;
            case R.id.major_layout_2013:
                intentToMajorList("2013");
                break;
        }
    }
}
