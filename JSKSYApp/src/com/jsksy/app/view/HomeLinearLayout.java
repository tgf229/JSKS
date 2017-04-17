/*
 * 文 件 名:  HomeLinearLayout.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-12-1
 
 */
package com.jsksy.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-12-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressLint("NewApi")
public class HomeLinearLayout extends LinearLayout
{
    
    public HomeLinearLayout(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public HomeLinearLayout(Context context, AttributeSet attrs)
    {
        super(context);
    }
    
    public HomeLinearLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = width * 39 / 100;
        setMeasuredDimension(width, height);
    }
}
