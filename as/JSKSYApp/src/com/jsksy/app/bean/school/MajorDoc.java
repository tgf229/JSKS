package com.jsksy.app.bean.school;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class MajorDoc {

    private String code;
    private String clazz;
    private String batch;
    private ArrayList<MajorItemDoc> doc;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<MajorItemDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<MajorItemDoc> doc) {
        this.doc = doc;
    }
}
