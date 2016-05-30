package com.jsksy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class MyImageView2 extends ImageView
{

    public MyImageView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float density = getResources().getDisplayMetrics().density;  // ÆÁÄ»ÃÜ¶È£¨0.75 / 1.0 / 1.5£©
        int width=(int)(getResources().getDisplayMetrics().widthPixels-28*density);
        int height=width * 2 / 7;
        setMeasuredDimension(width, height);
    }
}
