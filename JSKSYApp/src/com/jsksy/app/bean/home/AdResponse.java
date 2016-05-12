/*
 * �� �� ��:  AdResponse.java
 * ��    Ȩ:  Linkage Technology Co., Ltd. Copyright 2010-2011,  All rights reserved
 * ��    ��:  <����>
 * ��    ���� <�汾��> 
 * �� �� ��:  tgf
 * ����ʱ��:  2016-5-12
 
 */
package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;
import com.jsksy.app.ui.BaseActivity;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-5-12]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
