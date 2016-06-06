package com.jsksy.app.constant;


/**
 * <ȫ�־�̬����>
 * <������ϸ����>
 * 
 * @author  tgf
 * @version  [�汾��, 2016-4-1]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class Constants
{
    /**
     * �Ƿ���ʾ����ҳ���汾����,��Ҫ����ҳʱ���汾�ű��1
     */
    public static final int GUIDE_VERSION_CODE = 1;
    
    public static final String[] BATCH_DATA = new String[]{",ȫ��","1,����һ��","2,���ƶ���"};
    public static final String[] PROV_DATA = new String[]{",ȫ��","320000,����ʡ","110000,������","120000,�����"};
    public static final String[] SCHOOL_DATA = new String[]{",ȫ��","01,�ۺ�ԺУ","02,����ԺУ","03,ũҵԺУ","04,��ҵԺУ"};
    
    public enum MAJOR_DATA
    {
        DATA1("ʵ���",new String[]{"0015,�Ŀ��������","00201,���ù��������","00201_1,���ù���ʵ���","00300,��ѧ�����","0060,�����������","0070,����������","0080,ҽѧ�������","2009,Ԥ�ư�","5009,Ԥ�ư�"}),
        DATA2("��ѧ",new String[]{"0101,��ѧ��"}),
        DATA3("����ѧ",new String[]{"0201,����ѧ��","0202,����ѧ��","0203,����ѧ��","0204,������ó����"}),
        LENGTH("length",new String[]{});
        private String code;
        private String[] info;
        private int length = 3;
        private MAJOR_DATA(String code, String[] info){
            this.code = code;
            this.info = info;
        }
        public int getLength()
        {
            return length;
        }

        public void setLength(int length)
        {
            this.length = length;
        }

        public String getCode()
        {
            return code;
        }
        public void setCode(String code)
        {
            this.code = code;
        }
        public String[] getInfo()
        {
            return info;
        }
        public void setInfo(String[] info)
        {
            this.info = info;
        }
    }
    /**
     * ������  
     */
    public static final String ENCRYPT_NONE = "none";
    
    /**
     * ���ܷ�ʽ - simple
     */
    public static final String ENCRYPT_SIMPLE = "simple";
    
    /**
     * sharedPreferences�б����û�����Ϣ�ļ���
     */
    public static final String JSKSY_INFO = "jsksy_info";
    
    /**
     * �ֻ�����   1 ��׿  2 ƻ��
     */
    public static final String clientType = "1";
    
    public static final String SUCESS_CODE = "000000";
    
    public static final String ERROR_MESSAGE = "����ʧ�ܣ����Ժ�����";
    
    /**
     * �Ƴ�Ӧ��
     */
    public static final String EXIT_APP = "1";
    
    public static final int DOWNLOAD = 5;
    
    public static final int DOWNLOAD_FINISH = 6;
    
    public static final int NO_SD = 9;
}
