package com.jsksy.app.bean.home;

/**
 * 
 * <轮播通告列表>
 * <功能详细描述>
 * 
 * @author  sunqing
 * @version  [版本�? 2014�?1�?7日]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class BannerDoc
{
    /**
     * 通告活动ID
     */
    private String id;
    
    /**
     * 通告活动名称
     */
    private String name;
    
    /**
     * 通告活动描述
     */
    private String desc;
    
    /**
     * 活动图片
     */
    private String imageUrl;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}
