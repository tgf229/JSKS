package com.jsksy.app.bean.school;

import com.jsksy.app.bean.BaseResponse;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class EnrollListResponse extends BaseResponse {

    private ArrayList<EnrollDoc> doc;

    public ArrayList<EnrollDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<EnrollDoc> doc) {
        this.doc = doc;
    }
}
