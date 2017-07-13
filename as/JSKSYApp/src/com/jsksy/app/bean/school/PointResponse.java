package com.jsksy.app.bean.school;

import com.jsksy.app.bean.BaseResponse;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 2017/5/4.
 */
public class PointResponse extends BaseResponse {
    private ArrayList<PointDoc> doc;

    public ArrayList<PointDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<PointDoc> doc) {
        this.doc = doc;
    }
}
