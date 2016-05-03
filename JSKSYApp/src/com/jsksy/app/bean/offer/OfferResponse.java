/*
 * �� �� ��:  OfferResponse.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-5-3
 
 */
package com.jsksy.app.bean.offer;

import java.io.Serializable;

import com.jsksy.app.bean.BaseResponse;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-5-3]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class OfferResponse extends BaseResponse implements Serializable
{
    /**
     * ע������
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
