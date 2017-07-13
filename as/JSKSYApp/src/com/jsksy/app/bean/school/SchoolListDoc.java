package com.jsksy.app.bean.school;

/**
 * Created by 涂高峰 on 17/4/18.
 */
public class SchoolListDoc {

    private String logo;
    private String code;
    private String name;
    private String type;
    private String rank;
    private String isJbw;
    private String isEyy;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
