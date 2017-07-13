package com.jsksy.app.bean.school;

import com.jsksy.app.bean.BaseResponse;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class MajorListResponse extends BaseResponse {
    private ArrayList<MajorDoc> doc;

    public ArrayList<MajorDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<MajorDoc> doc) {
        this.doc = doc;
    }
}
