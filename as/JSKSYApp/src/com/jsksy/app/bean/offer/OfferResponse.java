package com.jsksy.app.bean.offer;

import com.jsksy.app.bean.BaseResponse;

import java.io.Serializable;
import java.util.List;


public class OfferResponse extends BaseResponse implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String school;
    
    private String major;
    
    private String sName;
    private List<OfferDoc> doc;

    public List<OfferDoc> getDoc() {
        return doc;
    }

    public void setDoc(List<OfferDoc> doc) {
        this.doc = doc;
    }

    public String getSchool()
    {
        return school;
    }
    
    public void setSchool(String school)
    {
        this.school = school;
    }
    
    public String getMajor()
    {
        return major;
    }
    
    public void setMajor(String major)
    {
        this.major = major;
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
