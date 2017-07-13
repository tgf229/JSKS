package com.jsksy.app.bean.school;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 17/4/24.
 */
public class SchoolDetailClazzDoc {
    private String code;
    private String name;
    private boolean isSelected = false;
    private ArrayList<SchoolDetailBatchDoc> batchDoc;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ArrayList<SchoolDetailBatchDoc> getBatchDoc() {
        return batchDoc;
    }

    public void setBatchDoc(ArrayList<SchoolDetailBatchDoc> batchDoc) {
        this.batchDoc = batchDoc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
