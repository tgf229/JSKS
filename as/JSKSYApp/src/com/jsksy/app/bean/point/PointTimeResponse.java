package com.jsksy.app.bean.point;

import com.jsksy.app.bean.BaseResponse;

public class PointTimeResponse extends BaseResponse
{
    private String exTime;
    
    private String cuTime;
    
    private String wsTime;
    
    public String getWsTime()
    {
        return wsTime;
    }

    public void setWsTime(String wsTime)
    {
        this.wsTime = wsTime;
    }

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
