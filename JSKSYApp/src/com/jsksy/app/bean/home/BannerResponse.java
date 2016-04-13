package com.jsksy.app.bean.home;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

/**
 * 
 * <轮播通告—列表查�?
 * <功能详细描述>
 * 
 * @author  sunqing
 * @version  [版本�? 2014�?1�?7日]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class BannerResponse extends BaseResponse
{
    /**
     * 列表数据
     */
    private ArrayList<BannerDoc> doc;

    public ArrayList<BannerDoc> getDoc()
    {
        return doc;
    }

    public void setDoc(ArrayList<BannerDoc> doc)
    {
        this.doc = doc;
    }
}
