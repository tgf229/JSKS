package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;

public class UpdateVersionResponse extends BaseResponse
{
    private String version;
    

    private String content;
    

    private String isUpdate;
    

    private String urlAddress;
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getIsUpdate()
    {
        return isUpdate;
    }
    
    public void setIsUpdate(String isUpdate)
    {
        this.isUpdate = isUpdate;
    }
    
    public String getUrlAddress()
    {
        return urlAddress;
    }
    
    public void setUrlAddress(String urlAddress)
    {
        this.urlAddress = urlAddress;
    }
}
