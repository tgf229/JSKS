package com.jsksy.app.bean.zz;

import com.jsksy.app.bean.BaseResponse;

import java.util.ArrayList;

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
