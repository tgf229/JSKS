package com.jsksy.app.bean.point;

import com.jsksy.app.bean.BaseResponse;

public class PointTimeResponse extends BaseResponse
{
    /**
     * ���ַ���ʱ��
     */
    private String exTime;
    
    /**
     * ��ǰ������ʱ��
     */
    private String cuTime;
    
    public String getExTime()
    {
        return exTime;
    }
    
    public void setExTime(String exTime)
    {
        this.exTime = exTime;
    }
    
    public String getCuTime()
    {
        return cuTime;
    }
    
    public void setCuTime(String cuTime)
    {
        this.cuTime = cuTime;
    }
}
