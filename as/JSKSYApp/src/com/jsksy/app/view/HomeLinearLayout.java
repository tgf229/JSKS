/*
 * �� �� ��:  HomeLinearLayout.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-12-1
 
 */
package com.jsksy.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-12-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
//        int height = getResources().getDisplayMetrics().heightPixels;
        setMeasuredDimension(width, height);
    }
}
