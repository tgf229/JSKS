package com.jsksy.app.bean.school;

/**
 * Created by 涂高峰 on 17/4/20.
 */
public class MajorItemDoc {

    private String year;
    private String name;
    private String num;
    private String hScore;
    private String hBatch;
    private String lScore;
    private String lBatch;
    private String pNum;
    private String sNum;
    private String oNum;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getoNum() {
        return oNum;
    }

    public void setoNum(String oNum) {
        this.oNum = oNum;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getsNum() {
        return sNum;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
