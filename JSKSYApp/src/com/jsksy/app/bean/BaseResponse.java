package com.jsksy.app.bean;

/**
 * <�������󷵻���>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class BaseResponse
{
    /**
     * ����״̬��
     */
    protected String retcode;
    
    /**
     * ������Ϣ����
     */
    protected String retinfo;
    
    public String getRetcode()
    {
        return retcode;
    }
    
    public void setRetcode(String retcode)
    {
        this.retcode = retcode;
    }
    
    public String getRetinfo()
    {
        return retinfo;
    }
    
    public void setRetinfo(String retinfo)
    {
        this.retinfo = retinfo;
    }
}
