package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;

/**
 * <版本更新�?��>
 * <功能详细描述>
 * 
 * @author  WT
 * @version  [版本�? 2014-11-28]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class UpdateVersionResponse extends BaseResponse
{
    /**
     * �?��版本
     */
    private String version;
    
    /**
     * 更新内容
     */
    private String content;
    
    /**
     * 是否强制更新        0不强�?      1强制
     */
    private String isUpdate;
    
    /**
     * 下载地址
     */
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
