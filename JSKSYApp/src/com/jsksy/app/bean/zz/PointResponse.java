/*
 * 文 件 名:  PointResponse.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-4-29
 
 */
package com.jsksy.app.bean.zz;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PointResponse extends BaseResponse
{
    private String sName;
    private String sNum;
    private String sIdCard;
    private ArrayList<PointDoc> doc;
    public String getsName()
    {
        return sName;
    }
    public void setsName(String sName)
    {
        this.sName = sName;
    }
    public String getsNum()
    {
        return sNum;
    }
    public void setsNum(String sNum)
    {
        this.sNum = sNum;
    }
    public String getsIdCard()
    {
        return sIdCard;
    }
    public void setsIdCard(String sIdCard)
    {
        this.sIdCard = sIdCard;
    }
    public ArrayList<PointDoc> getDoc()
    {
        return doc;
    }
    public void setDoc(ArrayList<PointDoc> doc)
    {
        this.doc = doc;
    }
}
