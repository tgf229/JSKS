package com.jsksy.app.bean.wish;

public class WishDoc
{
    /**
     * 学校代号
     */
    private String code;
    
    /**
     * 学校名称
     */
    private String name;
    
    /**
     *录取批次
     */
    private String batch;
    
    /**
     * 计划数
     */
    private String num;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBatch()
    {
        return batch;
    }
    
    public void setBatch(String batch)
    {
        this.batch = batch;
    }
    
    public String getNum()
    {
        return num;
    }
    
    public void setNum(String num)
    {
        this.num = num;
    }
}
