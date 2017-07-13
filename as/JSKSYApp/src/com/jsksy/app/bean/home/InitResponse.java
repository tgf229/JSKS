package com.jsksy.app.bean.home;

import com.jsksy.app.bean.BaseResponse;

public class InitResponse extends BaseResponse
{
    private String flag;
    private InitConfBean conf;

    public InitConfBean getConf() {
        return conf;
    }

    public void setConf(InitConfBean conf) {
        this.conf = conf;
    }

    /**
     * @return
     */
    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }
}
