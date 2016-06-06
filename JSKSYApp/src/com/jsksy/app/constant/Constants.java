package com.jsksy.app.constant;


/**
 * <全局静态常量>
 * <功能详细描述>
 * 
 * @author  tgf
 * @version  [版本号, 2016-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Constants
{
    /**
     * 是否显示引导页，版本控制,需要引导页时，版本号变大1
     */
    public static final int GUIDE_VERSION_CODE = 1;
    
    public static final String[] BATCH_DATA = new String[]{",全部","1,本科一批","2,本科二批"};
    public static final String[] PROV_DATA = new String[]{",全国","320000,江苏省","110000,北京市","120000,天津市"};
    public static final String[] SCHOOL_DATA = new String[]{",全部","01,综合院校","02,工科院校","03,农业院校","04,林业院校"};
    
    public enum MAJOR_DATA
    {
        DATA1("实验班",new String[]{"0015,文科试验班类","00201,经济管理试验班","00201_1,经济管理实验班","00300,法学试验班","0060,工科试验班类","0070,理科试验班类","0080,医学试验班类","2009,预科班","5009,预科班"}),
        DATA2("哲学",new String[]{"0101,哲学类"}),
        DATA3("经济学",new String[]{"0201,经济学类","0202,财政学类","0203,金融学类","0204,经济与贸易类"}),
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
     * 不加密  
     */
    public static final String ENCRYPT_NONE = "none";
    
    /**
     * 加密方式 - simple
     */
    public static final String ENCRYPT_SIMPLE = "simple";
    
    /**
     * sharedPreferences中保存用户的信息文件名
     */
    public static final String JSKSY_INFO = "jsksy_info";
    
    /**
     * 手机类型   1 安卓  2 苹果
     */
    public static final String clientType = "1";
    
    public static final String SUCESS_CODE = "000000";
    
    public static final String ERROR_MESSAGE = "请求失败，请稍后再试";
    
    /**
     * 推出应用
     */
    public static final String EXIT_APP = "1";
    
    public static final int DOWNLOAD = 5;
    
    public static final int DOWNLOAD_FINISH = 6;
    
    public static final int NO_SD = 9;
}
