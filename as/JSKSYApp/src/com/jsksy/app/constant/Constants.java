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
    public static final String URL_SCHEMA_SCHOOLDETAIL = "jsksy://schoolDetail";
    public static final String URL_SCHEMA_SCHOOLLIST = "jsksy://schoolList/searchKey";
    public static final String URL_APP_SHARE = "http://sdata.jseea.cn:8081/imgs/edu/html/17061301/2017333.html";

    public static final String PAGE_NUM = "10";

    public static final String BROAD_CAST_SCHOOLDETAIL = "com.jsksy.app.broadcast.school.detail";
    
    public static final String[] POINT_CHECK_NUM = new String[]{"A","B","C","D","E","F","G","H"};
    public static final String[] POINT_CHECK_POINT = new String[]{"1","2","3","4","5","6","7","8"};

    public static final String[] BATCH_DATA = new String[]{"2,本科一批","3,本科二批"};
    public static final String[] PROV_DATA = new String[]{",全国","320000,江苏省","110000,北京市","120000,天津市","130000,河北省","140000,山西省","150000,内蒙古自治区","210000,辽宁省","220000,吉林省","230000,黑龙江省","310000,上海市","330000,浙江省","340000,安徽省","350000,福建省","360000,江西省","370000,山东省","410000,河南省","420000,湖北省","430000,湖南省","440000,广东省","450000,广西壮族自治区","460000,海南省","500000,重庆市","510000,四川省","520000,贵州省","530000,云南省","540000,西藏自治区","610000,陕西省","620000,甘肃省","630000,青海省","640000,宁夏回族自治区","650000,新疆维吾尔自治区","710000,台湾省","810000,香港特别行政区","820000,澳门特别行政区"};
    public static final String[] SCHOOL_DATA = new String[]{",全部","01,综合院校","02,工科院校","03,农业院校","04,林业院校","05,医药院校","06,师范院校","07,语言院校","08,财经院校","09,政法院校","10,体育院校","11,艺术院校","12,民族院校","13,军事院校"};

    public enum MAJOR_DATA
    {
        DATA1("实验班",new String[]{"0015,文科试验班类","00201,经济管理试验班","00201_1,经济管理实验班","00300,法学试验班","0060,工科试验班类","0070,理科试验班类","0080,医学试验班类","2009,预科班","5009,预科班"}),
        DATA2("哲学",new String[]{"0101,哲学类"}),
        DATA3("经济学",new String[]{"0201,经济学类","0202,财政学类","0203,金融学类","0204,经济与贸易类"}),
        DATA4("法学",new String[]{"0301,法学类","0302,政治学类","0303,社会学类","0304,民族学类","0305,马克思主义理论类","0306,公安学类"}),
        DATA5("教育学",new String[]{"0401,教育学类","0402,体育学类"}),
        DATA6("文学",new String[]{"0501,中国语言文学类","0502,外国语言文学类","0503,新闻传播学类"}),
        DATA7("历史学",new String[]{"0601,历史学类"}),
        DATA8("理学",new String[]{"0701,数学类","0702,物理学类","0703,化学类","0704,天文学类","0705,地理科学类","0706,大气科学类","0707,海洋科学类","0708,地球物理学类","0709,地质学类","0710,生物科学类","0711,心理学类","0712,统计学类"}),
        DATA9("工学",new String[]{"0801,力学类","0802,机械类","0803,仪器类","0804,材料类","0805,能源动力类","0806,电气类","0807,电子信息类","0808,自动化类","0809,计算机类","0810,土木类","0811,水利类","0812,测绘类","0813,化工与制药类","0814,地质类","0815,矿业类","0816,纺织类","0817,轻工类","0818,交通运输类","0819,海洋工程类","0820,航空航天类","0821,兵器类","0822,核工程类","0823,农业工程类","0824,林业工程类","0825,环境科学与工程类","0826,生物医学工程类","0827,食品科学与工程类","0828,建筑类","0829,安全科学与工程类","0830,生物工程类","0831,公安技术类"}),
        DATA10("农学",new String[]{"0901,植物生产类","0902,自然保护与环境生态类","0903,动物生产类","0904,动物医学类","0905,林学类","0906,水产类","0907,草学类"}),
        DATA11("医学",new String[]{"1001,基础医学类","1002,临床医学类","1003,口腔医学类","1004,公共卫生与预防医学类","1005,中医学类","1006,中西医结合类","1007,药学类","1008,中药学类","1009,法医学类","1010,医学技术类","1011,护理学类"}),
        DATA12("管理学",new String[]{"1201,管理科学与工程类","1202,工商管理类","1203,农业经济管理类","1204,公共管理类","1205,图书情报与档案管理类","1206,物流管理与工程类","1207,工业工程类","1208,电子商务类","1209,旅游管理类"}),
        DATA13("艺术学",new String[]{"1301,艺术学理论类","1302,音乐与舞蹈学类","1303,戏剧与影视学类","1304,美术学类","1305,设计学类"}),

        LENGTH("length",new String[]{});
        private int length = 13;
        
        private String code;
        private String[] info;
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
    
    public static final String ERROR_MESSAGE = "请求失败,请稍后再试";

    public static final int SKIP_TIME = 3*1000;
    
    /**
     * �Ƴ�Ӧ��
     */
    public static final String EXIT_APP = "1";
    
    public static final int DOWNLOAD = 5;
    
    public static final int DOWNLOAD_FINISH = 6;
    
    public static final int NO_SD = 9;
}
