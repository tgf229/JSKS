package com.jsksy.app.bean.school;

/**
 * Created by 涂高峰 on 2017/5/4.
 */
public class PointDoc {

    private String year;
    private String clazz;
    private String batch;
    private String num;
    private String hScore;
    private String hBatch;
    private String lScore;
    private String lBatch;

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

    public String gethBatch() {
        return hBatch;
    }

    public void sethBatch(String hBatch) {
        this.hBatch = hBatch;
    }

    public String gethScore() {
        return hScore;
    }

    public void sethScore(String hScore) {
        this.hScore = hScore;
    }

    public String getlBatch() {
        return lBatch;
    }

    public void setlBatch(String lBatch) {
        this.lBatch = lBatch;
    }

    public String getlScore() {
        return lScore;
    }

    public void setlScore(String lScore) {
        this.lScore = lScore;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
