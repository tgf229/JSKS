package com.jsksy.app.bean.point;

import com.jsksy.app.bean.BaseResponse;

import java.util.List;

public class PointResponse extends BaseResponse
{
    private String type;
    private String chinese;
    private String math;
    private String english;
    private String chAdd;
    private String maAdd;
    private String poAdd;
    private String cmAdd;
    private String saAdd;
    private String saTotal;
    private String chTotal;
    private String maTotal;
    private String KM4Name;
    private String KM4Level;
    private String KM5Name;
    private String KM5Level;
    private String totalName;
    private String totalLevel;
    private String sName;
    private String tipContent;
    private List<PointAd> doc;
    
    public String getTipContent()
    {
        return tipContent;
    }
    public void setTipContent(String tipContent)
    {
        this.tipContent = tipContent;
    }
    public List<PointAd> getDoc()
    {
        return doc;
    }
    public void setDoc(List<PointAd> doc)
    {
        this.doc = doc;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getChinese()
    {
        return chinese;
    }
    public void setChinese(String chinese)
    {
        this.chinese = chinese;
    }
    public String getMath()
    {
        return math;
    }
    public void setMath(String math)
    {
        this.math = math;
    }
    public String getEnglish()
    {
        return english;
    }
    public void setEnglish(String english)
    {
        this.english = english;
    }
    public String getChAdd()
    {
        return chAdd;
    }
    public void setChAdd(String chAdd)
    {
        this.chAdd = chAdd;
    }
    public String getMaAdd()
    {
        return maAdd;
    }
    public void setMaAdd(String maAdd)
    {
        this.maAdd = maAdd;
    }
    public String getPoAdd()
    {
        return poAdd;
    }
    public void setPoAdd(String poAdd)
    {
        this.poAdd = poAdd;
    }
    public String getCmAdd()
    {
        return cmAdd;
    }
    public void setCmAdd(String cmAdd)
    {
        this.cmAdd = cmAdd;
    }
    public String getSaAdd()
    {
        return saAdd;
    }
    public void setSaAdd(String saAdd)
    {
        this.saAdd = saAdd;
    }
    public String getSaTotal()
    {
        return saTotal;
    }
    public void setSaTotal(String saTotal)
    {
        this.saTotal = saTotal;
    }
    public String getChTotal()
    {
        return chTotal;
    }
    public void setChTotal(String chTotal)
    {
        this.chTotal = chTotal;
    }
    public String getMaTotal()
    {
        return maTotal;
    }
    public void setMaTotal(String maTotal)
    {
        this.maTotal = maTotal;
    }
    public String getKM4Name()
    {
        return KM4Name;
    }
    public void setKM4Name(String kM4Name)
    {
        KM4Name = kM4Name;
    }
    public String getKM4Level()
    {
        return KM4Level;
    }
    public void setKM4Level(String kM4Level)
    {
        KM4Level = kM4Level;
    }
    public String getKM5Name()
    {
        return KM5Name;
    }
    public void setKM5Name(String kM5Name)
    {
        KM5Name = kM5Name;
    }
    public String getKM5Level()
    {
        return KM5Level;
    }
    public void setKM5Level(String kM5Level)
    {
        KM5Level = kM5Level;
    }
    public String getTotalName()
    {
        return totalName;
    }
    public void setTotalName(String totalName)
    {
        this.totalName = totalName;
    }
    public String getTotalLevel()
    {
        return totalLevel;
    }
    public void setTotalLevel(String totalLevel)
    {
        this.totalLevel = totalLevel;
    }
    public String getsName()
    {
        return sName;
    }
    public void setsName(String sName)
    {
        this.sName = sName;
    }
}
