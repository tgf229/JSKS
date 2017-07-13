/*
 * 文 件 名:  AdResponse.java
 * 版    权:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <版本号> 
 * 创 建 人:  tgf
 * 创建时间:  2016-5-12
 
 */
package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-5-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AdResponse extends BaseResponse
{
    private String aId;
    
    private String imageUrl;
    
    private String aUrl;
    
    public String getaId()
    {
        return aId;
    }
    
    public void setaId(String aId)
    {
        this.aId = aId;
    }
    
    public String getImageUrl()
    {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
    
    public String getaUrl()
    {
        return aUrl;
    }
    
    public void setaUrl(String aUrl)
    {
        this.aUrl = aUrl;
    }
}
