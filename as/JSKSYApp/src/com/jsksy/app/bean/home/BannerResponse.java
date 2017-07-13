package com.jsksy.app.bean.home;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

public class BannerResponse extends BaseResponse
{
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
