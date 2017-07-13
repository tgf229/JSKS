package com.jsksy.app.bean.school;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 涂高峰 on 2017/4/28.
 */
public class SchoolDetailInfo implements Parcelable {
    private String logo;
    private String pic;
    private String code;
    private String name;
    private String type;
    private String rank;
    private String isJbw;
    private String isEyy;
    private String maleRatio;
    private String femaleRatio;
    private String tel;
    private String web;
    private String address;
    private String introduce;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    private ArrayList<SchoolDetailClazzDoc> clazzDoc;

    public ArrayList<SchoolDetailClazzDoc> getClazzDoc() {
        return clazzDoc;
    }

    public void setClazzDoc(ArrayList<SchoolDetailClazzDoc> clazzDoc) {
        this.clazzDoc = clazzDoc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFemaleRatio() {
        return femaleRatio;
    }

    public void setFemaleRatio(String femaleRatio) {
        this.femaleRatio = femaleRatio;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIsEyy() {
        return isEyy;
    }

    public void setIsEyy(String isEyy) {
        this.isEyy = isEyy;
    }

    public String getIsJbw() {
        return isJbw;
    }

    public void setIsJbw(String isJbw) {
        this.isJbw = isJbw;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMaleRatio() {
        return maleRatio;
    }

    public void setMaleRatio(String maleRatio) {
        this.maleRatio = maleRatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(logo);
        dest.writeString(pic);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(rank);
        dest.writeString(isJbw);
        dest.writeString(isEyy);
        dest.writeString(maleRatio);
        dest.writeString(femaleRatio);
        dest.writeString(tel);
        dest.writeString(web);
        dest.writeString(address);
        dest.writeString(introduce);
    }

    public static final Parcelable.Creator<SchoolDetailInfo> CREATOR = new Parcelable.Creator<SchoolDetailInfo>(){

        @Override
        public SchoolDetailInfo createFromParcel(Parcel source) {
            SchoolDetailInfo bean = new SchoolDetailInfo();
            bean.code = source.readString();
            bean.logo = source.readString();
            bean.pic = source.readString();
            bean.name = source.readString();
            bean.type = source.readString();
            bean.rank = source.readString();
            bean.isJbw = source.readString();
            bean.isEyy = source.readString();
            bean.maleRatio = source.readString();
            bean.femaleRatio = source.readString();
            bean.tel = source.readString();
            bean.web = source.readString();
            bean.address = source.readString();
            bean.introduce = source.readString();
            return bean;
        }

        @Override
        public SchoolDetailInfo[] newArray(int size) {
            return new SchoolDetailInfo[size];
        }
    };
}
