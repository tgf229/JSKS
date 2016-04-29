package com.jsksy.app.bean.point;

import com.jsksy.app.bean.BaseResponse;

public class PointTimeResponse extends BaseResponse
{
    /**
     * 考分发布时间
     */
    private String exTime;
    
    /**
     * 当前服务器时间
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
