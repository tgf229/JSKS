package com.jsksy.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * <通用工具类> <功能详细描述>
 * 
 * @author tgf
 * @version [版本号, 2012-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class GeneralUtils
{
    
    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     * 
     * @param obj
     *            被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        return (null == obj) ? true : false;
    }
    
    /**
     * 判断对象是否为null , 为null返回false,否则返回true
     * 
     * @param obj
     *            被判断的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj)
    {
        return !isNull(obj);
    }
    
    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     * 
     * @param str
     *            被判断的字符串
     * 
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str)
    {
        return (null == str || "".equals(str.trim())) ? true : false;
    }
    
    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
     * 
     * @param str
     *            被判断的字符串
     * 
     * @return boolean
     */
    public static boolean isNotNullOrZeroLenght(String str)
    {
        return !isNullOrZeroLenght(str);
    }
    
    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回true ,否则返回false
     * 
     * @param c
     *            collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNullOrZeroSize(Collection<? extends Object> c)
    {
        return isNull(c) || c.isEmpty();
    }
    
    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回false, 否则返回true
     * 
     * @param c
     *            collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotNullOrZeroSize(Collection<? extends Object> c)
    {
        return !isNullOrZeroSize(c);
    }
    
    /**
     * 判断数字类型是否为null或者0，如果是返回true，否则返回false
     * 
     * @param number
     *            被判断的数字
     * @return boolean
     */
    public static boolean isNullOrZero(Number number)
    {
        if (GeneralUtils.isNotNull(number))
        {
            return (number.intValue() != 0) ? false : true;
        }
        return true;
    }
    
    /**
     * 判断数字类型是否不为null或者0，如果是返回true，否则返回false
     * 
     * @param number
     *            被判断的数字
     * @return boolean
     */
    public static boolean isNotNullOrZero(Number number)
    {
        return !isNullOrZero(number);
    }
    
    /**
     * <获取当前日期 格式 yyyyMMddHHmmss> <功能详细描述>
     * 
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getRightNowDateString()
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }
    
    /**
     * <获取当前时间 格式yyyyMMddHHmmss> <功能详细描述>
     * 
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static Date getRightNowDateTime()
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try
        {
            return dateFormat.parse(dateFormat.format(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * <保留两位有效数字> <功能详细描述>
     * 
     * @param num
     *            String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String retained2SignificantFigures(String num)
    {
        return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     * 
     * @param subt1
     *            String
     * @param subt2
     *            String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String subtract(String subt1, String subt2)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <加法运算并保留两位有效数字> <功能详细描述>
     * 
     * @param addend1
     * @param addend2
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String add(String addend1, String addend2)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <乘法运算并保留两位有效数字> <功能详细描述>
     * 
     * @param factor1
     *            String
     * @param factor2
     *            String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String multiply(String factor1, String factor2)
    {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     * 
     * @param divisor1
     *            String
     * @param divisor2
     *            String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String divide(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     * 
     * @param divisor1
     *            String
     * @param divisor2
     *            String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String dividePoint1(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 1, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitTodate(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return strs;
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD hh:mm> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToMinute(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        String strs = "";
        strs =
            str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12);
        return strs;
    }
    
    /**
     * <将YYYY-MM-DD 转换为 YYYYMMDD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToMinuteNoLine(String str)
    {
        if (!str.contains("-"))
        {
            return str;
        }
        String strs = "";
        String[] strsArr = str.split("-");
        if (strsArr[1].length() == 1)
        {
            strsArr[1] = "0" + strsArr[1];
        }
        
        if (strsArr[2].length() == 1)
        {
            strsArr[2] = "0" + strsArr[2];
        }
        
        strs = strsArr[0] + strsArr[1] + strsArr[2];
        return strs;
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD hh:mm:ss> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToSecond(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        String strs = "";
        strs =
            str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
        return strs;
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YY-MM-DD hh:mm:ss> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToYear(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        String strs = "";
        strs =
            str.substring(2, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
        return strs;
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY.MM.DD hh:mm:ss> 
     * 
     * @param str
     * @return
     */
    public static String splitToSecondAnother(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        StringBuilder strs = new StringBuilder();
        strs.append(str.substring(0, 4))
            .append(".")
            .append(str.substring(4, 6))
            .append(".")
            .append(str.substring(6, 8))
            .append(" ")
            .append(str.substring(8, 10))
            .append(":")
            .append(str.substring(10, 12))
            .append(":")
            .append(str.substring(12, 14));
        return strs.toString();
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY.MM.DD> 
     * 
     * @param str
     * @return
     */
    public static String splitToSecondCoupon(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        StringBuilder strs = new StringBuilder();
        strs.append(str.substring(0, 4))
            .append(".")
            .append(str.substring(4, 6))
            .append(".")
            .append(str.substring(6, 8));
        return strs.toString();
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YY年MM月DD日> 
     * 
     * @param str
     * @return
     */
    public static String splitToPay(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 14)
        {
            return str;
        }
        
        StringBuilder strs = new StringBuilder();
        strs.append(str.substring(2, 4))
            .append("年")
            .append(str.substring(4, 6))
            .append("月")
            .append(str.substring(6, 8))
            .append("日");
        return strs.toString();
    }
    
    /**
     * 将YYYYMMDD转为X年X月X日
     * @param time
     * @return
     */
    public static String splitToLocalDate(String time)
    {
        StringBuffer date = new StringBuffer();
        if (!StringUtil.isEmpty(time))
        {
            date.append(time.substring(0, 4)).append("年");
            int month = Integer.valueOf(time.substring(4, 6));
            date.append(month).append("月");
            int day = Integer.valueOf(time.substring(6, 8));
            date.append(day).append("日");
        }
        return date.toString();
    }
    
    /**
     * 将YYYYMMDD转为X-X-X
     * @param time
     * @return
     */
    public static String splitToLocalDateOne(String time)
    {
        StringBuffer date = new StringBuffer();
        if (!StringUtil.isEmpty(time))
        {
            date.append(time.substring(0, 4)).append("-");
            int month = Integer.valueOf(time.substring(4, 6));
            date.append(month).append("-");
            int day = Integer.valueOf(time.substring(6, 8));
            date.append(day).append("");
        }
        return date.toString();
    }
    
    /**
     * 将YYYYMMDD转为X年X月X日 HH:mm:ss
     * @param time
     * @return
     */
    public static String splitToLocalDateOfHour(String time)
    {
        StringBuffer date = new StringBuffer();
        if (!StringUtil.isEmpty(time))
        {
            date.append(time.substring(0, 4)).append("年");
            int month = Integer.valueOf(time.substring(4, 6));
            date.append(month).append("月");
            int day = Integer.valueOf(time.substring(6, 8));
            date.append(day)
                .append("日")
                .append(" ")
                .append(time.substring(8, 10))
                .append(":")
                .append(time.substring(10, 12))
                .append(":")
                .append(time.substring(12, 14));
        }
        return date.toString();
    }
    
    /**
     * 
     * <将YYYYMMDDHHmmss 转换为mm:ss> <功能详细描述> <功能详细描述>
     * 
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String hourMinute(String time)
    {
        if (isNullOrZeroLenght(time) || time.length() < 12)
        {
            return time;
        }
        String hm = "";
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);
        hm = hour + ":" + minute;
        return hm;
    }
    
    /**
     * <将YYYYMMDDHHmm 转换为 YYYY-MM-DD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToDapengdate(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 12)
        {
            return str;
        }
        
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return strs;
    }
    
    public static String placeForTitle(String str)
    {
        if (isNotNullOrZeroLenght(str) && str.length() > 16)
        {
            return str.substring(0, 15) + "...";
        }
        return str;
    }
    
    public static String placeForMessage(String str)
    {
        if (isNotNullOrZeroLenght(str) && str.length() > 22)
        {
            return str.substring(0, 21) + "...";
        }
        return str;
    }
    
    /**
     * 规范经纬度的长度，8位数字，加上小数点后长度为9位
     */
    public static String standard(String str)
    {
        String return_string = null;
        StringBuffer sb = new StringBuffer(str);
        int length = sb.length();
        if (length < 9)
        {
            for (int i = length; i < 9; i++)
            {
                sb = sb.append("0");
            }
        }
        if (length > 9)
        {
            sb.delete(9, length);
        }
        return_string = sb.toString();
        return return_string;
    }
    
    /**
     * 
     * <如果距离超过1000米则转换成1km> <功能详细描述>
     * 
     * @param m
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String convertToKm(String m)
    {
        if (m.length() < 4)
        {
            return m;
        }
        else
        {
            return dividePoint1(m, "1000");
            // double data = Double.valueOf(m);
            // String km = String.valueOf(data/1000);
            // return km + "k";
        }
    }
    
    /**
     * <从YYYYMMDD中DD的值 > <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToDay(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 8)
        {
            return str;
        }
        String day = str.substring(6, 8);
        if ("10".compareToIgnoreCase(day) > 0)
        {
            day = day.substring(1, 2);
        }
        return day;
    }
    
    /**
     * 获取指定月的前一月（年）或后一月（年）
     * 
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return 输入的时期格式为yyyy-MM，输出的日期格式为yyyy-MM
     * @throws Exception
     */
    public static String getLastMonth(String dateStr, int addYear, int addMonth, int addDate)
        throws Exception
    {
        try
        {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
            java.util.Date sourceDate = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sourceDate);
            cal.add(Calendar.YEAR, addYear);
            cal.add(Calendar.MONTH, addMonth);
            cal.add(Calendar.DATE, addDate);
            
            java.text.SimpleDateFormat returnSdf = new java.text.SimpleDateFormat("yyyy-MM");
            String dateTmp = returnSdf.format(cal.getTime());
            java.util.Date returnDate = returnSdf.parse(dateTmp);
            return dateTmp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * 获取指定月的前一月（年）或后一月（年）
     * 
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return 输入的时期格式为yyyy-MM-dd，输出的日期格式为yyyy-MM-dd
     * @throws Exception
     */
    public static String getLastDay(String dateStr, int addYear, int addMonth, int addDate)
        throws Exception
    {
        try
        {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date sourceDate = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sourceDate);
            cal.add(Calendar.YEAR, addYear);
            cal.add(Calendar.MONTH, addMonth);
            cal.add(Calendar.DATE, addDate);
            
            java.text.SimpleDateFormat returnSdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String dateTmp = returnSdf.format(cal.getTime());
            java.util.Date returnDate = returnSdf.parse(dateTmp);
            return dateTmp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    /**
     * 
     * 车况分析中标注应该的高度 <功能详细描述>
     * 
     * @param timeDistance
     * @param nowHeight
     * @param nextOrLastHeight
     * @param coefficient
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static double chartYHeight(int timeDistance, double nowHeight, double nextOrLastHeight, double coefficient)
    {
        if (timeDistance == nowHeight)
        {
            return 0;
        }
        return coefficient * (nextOrLastHeight - nowHeight) / timeDistance;
    }
    
    /**
     * 
     * <讲系统时间转化成YYYYMMDDHHmmss> <功能详细描述>
     * 
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToDoubuangDate(String time)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(Long.parseLong(time));
        return dateFormat.format(date);
    }
    
    /**
     * 获取版本信息
     * 
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        }
        catch (NameNotFoundException e)
        {
        }
        return "";
    }
    
    /**
     * 
     * <邮箱判断>
     * <功能详细描述>
     * @param email
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmail(String email)
    {
        String str =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /**
     * 
     * <手机号码判断>
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isTel(String tel)
    {
        String str = "^[0-9]{11}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(tel);
        return m.matches();
    }
    
    /**
     * 
     * <邮编判断>
     * <功能详细描述>
     * @param post
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPost(String post)
    {
        String patrn = "^[1-9][0-9]{5}$";
        Pattern p = Pattern.compile(patrn);
        Matcher m = p.matcher(post);
        return m.matches();
    }
    
    /**
     * 
     * <密码规则判断>
     * <功能详细描述>
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean IsPassword(String password)
    {
        String str = "^[A-Za-z0-9_]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    
    /**
     * 
     * <密码位数判断>
     * <功能详细描述>
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean IsPasswordDigit(String password)
    {
        String str = "^[^ ]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    
    /**
     * 
     * <密码位数判断>
     * <功能详细描述>
     * @param certificate
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean Iscertificate(String certificate)
    {
        String str = "[0-9]{17}([0-9]|[xX])";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(certificate);
        return m.matches();
    }
    
    /**
     * 
     * <获取imei>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDeviceId(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    
    /**
     * 扫描结果为二维码时,格式是否正确,如果正确返回scuId,否则返回"";
     */
    public static String twoDimensionCode(String twoCode)
    {
        String codeNum = "";
        
        int num_1 = twoCode.lastIndexOf("/") + 1;
        codeNum = twoCode.substring(num_1, twoCode.length());
        
        if (codeNum.contains("."))
        {
            String[] arr = codeNum.trim().split("\\.");
            codeNum = arr[0];
        }
        else
        {
            codeNum = "";
        }
        return codeNum;
    }
    
    /**
     * <将YYYYMMDD 转换为 YYYY-MM-DD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitdateToCard(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() < 8)
        {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return strs;
    }
    
    /**
     * <将MMDD 转换为 MM:DD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitdateToSeller(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() < 4)
        {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 2) + ":" + str.substring(2, 4);
        return strs;
    }
    
    /**
     * 
     * <获取屏幕尺寸>
     * <功能详细描述>
     * @param context
     * @return DisplayMetrics  displayMetrics.heightPixels 高度  displayMetrics.widthPixels 宽度
     * @see [类、类#方法、类#成员]
     */
    public static DisplayMetrics getScreenDisplay(Context context)
    {
        return context.getResources().getDisplayMetrics();
    }
    
    /**
     * <为null时赋值空>
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String dealNull(String str)
    {
        if (str == null)
        {
            str = "";
        }
        return str;
    }
    
    /**
     * <将YYYYMMDD 转换为 YYYY.MM.DD> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String personCenterSplitdate(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() < 8)
        {
            return str;
        }
        str = str.substring(0, 4) + "." + str.substring(4, 6) + "." + str.substring(6, 8);
        return str;
    }
    
    /**
     * http://stackoverflow.com/questions/3495890/how-can-i-put-a-listview-into-a-scrollview-without-it-collapsing/3495908#3495908
     * 
     * @param listView
     */
    public static void setListViewHeightBasedOnChildrenExtend(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
            {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    
    // 去除textview的排版问题
    public static String ToDBC(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 12288)
            {
                c[i] = (char)32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char)(c[i] - 65248);
        }
        return new String(c);
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY.MM.DD HH:mm> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitdateToGroup(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() < 14)
        {
            return str;
        }
        String strs = "";
        strs =
            str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
        return strs;
    }
    
    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD hh:mm> <功能详细描述>
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitMinuteToComment(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() < 14)
        {
            return str;
        }
        
        String strs = "";
        strs =
            str.substring(2, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
        return strs;
    }
    
    /**
     * 
     * <prettytime>
     * <功能详细描述>
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String timeStamp(String oldTime)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = format.parse(oldTime);
            Date now = new Date();
            PrettyTime t = new PrettyTime(now);
            return t.format(date);
        }
        catch (ParseException e)
        {
            return oldTime;
        }
    }
}
