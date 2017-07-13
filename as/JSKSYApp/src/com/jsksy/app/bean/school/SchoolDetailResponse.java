package com.jsksy.app.bean.school;

import com.jsksy.app.bean.BaseResponse;

/**
 * Created by 涂高峰 on 2017/4/28.
 */
public class SchoolDetailResponse extends BaseResponse {

    private SchoolDetailInfo detail;
    
    public SchoolDetailInfo getDetail() {
        return detail;
    }

    public void setDetail(SchoolDetailInfo detail) {
        this.detail = detail;
    }
}
