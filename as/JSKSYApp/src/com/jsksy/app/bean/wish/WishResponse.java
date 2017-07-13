package com.jsksy.app.bean.wish;

import java.util.ArrayList;

import com.jsksy.app.bean.BaseResponse;

public class WishResponse extends BaseResponse
{
    private ArrayList<WishDoc> doc;

    public ArrayList<WishDoc> getDoc()
    {
        return doc;
    }

    public void setDoc(ArrayList<WishDoc> doc)
    {
        this.doc = doc;
    }
}
