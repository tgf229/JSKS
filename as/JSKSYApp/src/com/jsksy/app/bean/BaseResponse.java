package com.jsksy.app.bean;
public class BaseResponse
{
    protected String retcode;

    protected String retinfo;
    
    public String getRetcode()
    {
        return retcode;
    }
    
    public void setRetcode(String retcode)
    {
        this.retcode = retcode;
    }
    
    public String getRetinfo()
    {
        return retinfo;
    }
    
    public void setRetinfo(String retinfo)
    {
        this.retinfo = retinfo;
    }
}
