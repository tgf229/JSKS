package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;

public class InitResponse extends BaseResponse
{
    /**
     * 0 ����Ӧ��
        1 �˳�Ӧ��
     */
    private String flag;

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }
}
