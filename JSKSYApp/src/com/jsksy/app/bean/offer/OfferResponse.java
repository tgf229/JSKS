/*
 * 文 件 名:  OfferResponse.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-5-3
 
 */
package com.jsksy.app.bean.offer;

import java.io.Serializable;

import com.jsksy.app.bean.BaseResponse;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-5-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OfferResponse extends BaseResponse implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private String school;
    
    private String major;
    
    private String sName;
    
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
