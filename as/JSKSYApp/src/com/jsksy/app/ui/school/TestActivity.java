package com.jsksy.app.ui.school;

import android.app.Activity;
import android.os.Bundle;

import com.jsksy.app.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class TestActivity extends Activity {
    private LineChartView chart;

    private Line createLine(int flag){
        List<PointValue> values = null;
        if (flag == 0){
            values = new ArrayList<PointValue>();
            values.add(new PointValue(1, 365));
            values.add(new PointValue(2, 370));
            values.add(new PointValue(3, 375));
            values.add(new PointValue(4, 386));
        }else{
            values = new ArrayList<PointValue>();
            values.add(new PointValue(1, 385));
            values.add(new PointValue(2, 470));
            values.add(new PointValue(3, 475));
            values.add(new PointValue(4, 486));
        }

        Line line = new Line(values);
        line.setColor(flag==0 ? getResources().getColor(R.color.color_fc7655):getResources().getColor(R.color.color_8ededc));
        line.setCubic(true);
        line.setShape(ValueShape.CIRCLE);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        line.setHasPoints(true);
        return line;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        chart = (LineChartView)findViewById(R.id.chart);

//        line.setHasGradientToTransparent(false);
        Line line1 = createLine(0);
        Line line2 = createLine(1);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line1);
        lines.add(line2);

        LineChartData data = new LineChartData();
        Axis axisY = new Axis().setHasLines(false);
//        axisY.setName("分数");
        data.setAxisYLeft(axisY);

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        axisValues.add(new AxisValue(0).setLabel("2012"));
        axisValues.add(new AxisValue(1).setLabel("2013"));
        axisValues.add(new AxisValue(2).setLabel("2014"));
        axisValues.add(new AxisValue(3).setLabel("2015"));
        axisValues.add(new AxisValue(4).setLabel("2016"));
        axisValues.add(new AxisValue(5).setLabel("2017"));
        Axis tempoAxis = new Axis(axisValues).setHasLines(false).setMaxLabelChars(5);
        data.setAxisXBottom(tempoAxis);

        data.setLines(lines);

        chart.setLineChartData(data);
    }


    private String formatMinutes(float value) {
        StringBuilder sb = new StringBuilder();

        // translate value to seconds, for example
        int valueInSeconds = (int) (value * 60);
        int minutes = (int) Math.floor(valueInSeconds / 60);
        int seconds = (int) valueInSeconds % 60;

        sb.append(String.valueOf(minutes)).append(':');
        if (seconds < 10) {
            sb.append('0');
        }
        sb.append(String.valueOf(seconds));
        return sb.toString();
    }
}













//    private LineChartView chart;
//    private ValueShape shape = ValueShape.CIRCLE;
//    private int numberOfLines = 1;
//    private int maxNumberOfLines = 4;
//    private int numberOfPoints = 12;
//    private boolean isFilled = false;
//    private boolean hasLabels = false;
//    private boolean isCubic = false;
//    private boolean hasLabelForSelected = false;
//    private boolean hasGradientToTransparent = false;
//    private boolean hasLines = true;
//    private boolean hasPoints = true;
//    private boolean pointsHaveDifferentColor;
//
//    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        chart = (LineChartView)findViewById(R.id.chart);
//
//        generateValues();
//        chart.setViewportCalculationEnabled(false);
//        resetViewport();
//
//    }
//
//    private void generateValues() {
//        for (int i = 0; i < maxNumberOfLines; ++i) {
//            for (int j = 0; j < numberOfPoints; ++j) {
//                randomNumbersTab[i][j] = (float) Math.random() * 100f;
//            }
//        }
//    }
//
//    private void resetViewport() {
//        // Reset viewport height range to (0,100)
//        final Viewport v = new Viewport(chart.getMaximumViewport());
//        v.bottom = 0;
//        v.top = 100;
//        v.left = 0;
//        v.right = numberOfPoints - 1;
//        chart.setMaximumViewport(v);
//        chart.setCurrentViewport(v);
//    }
//
//    private void generateData() {
//
//        List<Line> lines = new ArrayList<Line>();
//        for (int i = 0; i < numberOfLines; ++i) {
//
//            List<PointValue> values = new ArrayList<PointValue>();
//            for (int j = 0; j < numberOfPoints; ++j) {
//                values.add(new PointValue(j, randomNumbersTab[i][j]));
//            }
//
//            Line line = new Line(values);
//            line.setColor(ChartUtils.COLORS[i]);
//            line.setShape(shape);
//            line.setCubic(isCubic);
//            line.setFilled(isFilled);
//            line.setHasLabels(hasLabels);
//            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            line.setHasLines(hasLines);
//            line.setHasPoints(hasPoints);
////            line.setHasGradientToTransparent(hasGradientToTransparent);
//            if (pointsHaveDifferentColor){
//                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
//            }
//            lines.add(line);
//        }
//
//        LineChartData data = new LineChartData(lines);
//
//        Axis axisX = new Axis();
//        Axis axisY = new Axis().setHasLines(true);
//        axisX.setName("Axis X");
//        axisY.setName("Axis Y");
//        data.setAxisXBottom(axisX);
//        data.setAxisYLeft(axisY);
//
//        data.setBaseValue(Float.NEGATIVE_INFINITY);
//        chart.setLineChartData(data);
//    }
//}
