package com.jsksy.app.bean.school;

import com.jsksy.app.bean.BaseResponse;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/18.
 */
public class SchoolListResponse extends BaseResponse{
    private ArrayList<SchoolListDoc> doc;

    public ArrayList<SchoolListDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<SchoolListDoc> doc) {
        this.doc = doc;
    }
}
