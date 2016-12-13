/*
 * �� �� ��:  PointResponse.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-4-29
 
 */
package com.jsksy.app.bean.zz;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-29]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
