package com.jsksy.app.bean.school;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class EnrollDoc {

    private String clazz;
    private String batch;
    private ArrayList<EnrollItemDoc> doc;

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

    public ArrayList<EnrollItemDoc> getDoc() {
        return doc;
    }

    public void setDoc(ArrayList<EnrollItemDoc> doc) {
        this.doc = doc;
    }
}
