package com.jsksy.app.bean.home;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

public class NewsResponse extends BaseResponse
{
    private ArrayList<NewsDoc> doc;

    public ArrayList<NewsDoc> getDoc()
    {
        return doc;
    }

    public void setDoc(ArrayList<NewsDoc> doc)
    {
        this.doc = doc;
    }
}
