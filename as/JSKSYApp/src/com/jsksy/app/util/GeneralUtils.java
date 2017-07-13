package com.jsksy.app.util;

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

import com.jsksy.app.constant.Constants;

import org.ocpsoft.prettytime.PrettyTime;

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

/**
 * <ͨ�ù�����> <������ϸ����>
 * 
 * @author tgf
 * @version [�汾��, 2012-7-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public final class GeneralUtils
{
    
    /**
     * �ж϶����Ƿ�Ϊnull , Ϊnull����true,���򷵻�false
     * 
     * @param obj
     *            ���жϵĶ���
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        return (null == obj) ? true : false;
    }
    
    /**
     * �ж϶����Ƿ�Ϊnull , Ϊnull����false,���򷵻�true
     * 
     * @param obj
     *            ���жϵĶ���
     * @return boolean
     */
    public static boolean isNotNull(Object obj)
    {
        return !isNull(obj);
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊnull����0���ȣ��ַ������жϳ���ʱ����ȥ��ǰ��Ŀո�,�ջ���0���ȷ���true,���򷵻�false
     * 
     * @param str
     *            ���жϵ��ַ���
     * 
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str)
    {
        return (null == str || "".equals(str.trim())) ? true : false;
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊnull����0���ȣ��ַ������жϳ���ʱ����ȥ��ǰ��Ŀո�,�ջ���0���ȷ���false,���򷵻�true
     * 
     * @param str
     *            ���жϵ��ַ���
     * 
     * @return boolean
     */
    public static boolean isNotNullOrZeroLenght(String str)
    {
        return !isNullOrZeroLenght(str);
    }
    
    /**
     * �жϼ��϶����Ƿ�Ϊnull����0��С , Ϊ�ջ�0��С����true ,���򷵻�false
     * 
     * @param c
     *            collection ���Ͻӿ�
     * @return boolean ����ֵ
     * @see [�ࡢ��#��������#��Ա]
     */
    public static boolean isNullOrZeroSize(Collection<? extends Object> c)
    {
        return isNull(c) || c.isEmpty();
    }
    
    /**
     * �жϼ��϶����Ƿ�Ϊnull����0��С , Ϊ�ջ�0��С����false, ���򷵻�true
     * 
     * @param c
     *            collection ���Ͻӿ�
     * @return boolean ����ֵ
     * @see [�ࡢ��#��������#��Ա]
     */
    public static boolean isNotNullOrZeroSize(Collection<? extends Object> c)
    {
        return !isNullOrZeroSize(c);
    }
    
    /**
     * �ж����������Ƿ�Ϊnull����0������Ƿ���true�����򷵻�false
     * 
     * @param number
     *            ���жϵ�����
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
     * �ж����������Ƿ�Ϊnull����0������Ƿ���true�����򷵻�false
     * 
     * @param number
     *            ���жϵ�����
     * @return boolean
     */
    public static boolean isNotNullOrZero(Number number)
    {
        return !isNullOrZero(number);
    }
    
    /**
     * <��ȡ��ǰ���� ��ʽ yyyyMMddHHmmss> <������ϸ����>
     * 
     * @return String
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getRightNowDateString()
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }
    
    /**
     * <��ȡ��ǰʱ�� ��ʽyyyyMMddHHmmss> <������ϸ����>
     * 
     * @return String
     * @see [�ࡢ��#��������#��Ա]
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
     * <������λ��Ч����> <������ϸ����>
     * 
     * @param num
     *            String
     * @return String
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String retained2SignificantFigures(String num)
    {
        return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    /**
     * <�������㲢������λ��Ч����> <������ϸ����>
     * 
     * @param subt1
     *            String
     * @param subt2
     *            String
     * @return String
     * @see [�ࡢ��#��������#��Ա]
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
     * <�ӷ����㲢������λ��Ч����> <������ϸ����>
     * 
     * @param addend1
     * @param addend2
     * @return String
     * @see [�ࡢ��#��������#��Ա]
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
     * <�˷����㲢������λ��Ч����> <������ϸ����>
     * 
     * @param factor1
     *            String
     * @param factor2
     *            String
     * @return String
     * @see [�ࡢ��#��������#��Ա]
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
     * <�������㲢������λ��Ч����> <������ϸ����>
     * 
     * @param divisor1
     *            String
     * @param divisor2
     *            String
     * @return String
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String divide(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <�������㲢������λ��Ч����> <������ϸ����>
     * 
     * @param divisor1
     *            String
     * @param divisor2
     *            String
     * @return String
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String dividePoint1(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 1, RoundingMode.HALF_UP);
        return result.toString();
    }
    
    /**
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY-MM-DD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY-MM-DD hh:mm> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYY-MM-DD ת��Ϊ YYYYMMDD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY-MM-DD hh:mm:ss> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmmss ת��Ϊ YY-MM-DD hh:mm:ss> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY.MM.DD hh:mm:ss> 
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY.MM.DD> 
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
     * <��YYYYMMDDHHmmss ת��Ϊ YY��MM��DD��> 
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
            .append("��")
            .append(str.substring(4, 6))
            .append("��")
            .append(str.substring(6, 8))
            .append("��");
        return strs.toString();
    }
    
    /**
     * ��YYYYMMDDתΪX��X��X��
     * @param time
     * @return
     */
    public static String splitToLocalDate(String time)
    {
        StringBuffer date = new StringBuffer();
        if (!StringUtil.isEmpty(time))
        {
            date.append(time.substring(0, 4)).append("��");
            int month = Integer.valueOf(time.substring(4, 6));
            date.append(month).append("��");
            int day = Integer.valueOf(time.substring(6, 8));
            date.append(day).append("��");
        }
        return date.toString();
    }
    
    /**
     * ��YYYYMMDDתΪX-X-X
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
     * ��YYYYMMDDתΪX��X��X�� HH:mm:ss
     * @param time
     * @return
     */
    public static String splitToLocalDateOfHour(String time)
    {
        StringBuffer date = new StringBuffer();
        if (!StringUtil.isEmpty(time))
        {
            date.append(time.substring(0, 4)).append("��");
            int month = Integer.valueOf(time.substring(4, 6));
            date.append(month).append("��");
            int day = Integer.valueOf(time.substring(6, 8));
            date.append(day)
                .append("��")
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
     * <��YYYYMMDDHHmmss ת��Ϊmm:ss> <������ϸ����> <������ϸ����>
     * 
     * @param time
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmm ת��Ϊ YYYY-MM-DD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * �淶��γ�ȵĳ��ȣ�8λ���֣�����С����󳤶�Ϊ9λ
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
     * <������볬��1000����ת����1km> <������ϸ����>
     * 
     * @param m
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDD��DD��ֵ > <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * ��ȡָ���µ�ǰһ�£��꣩���һ�£��꣩
     * 
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return �����ʱ�ڸ�ʽΪyyyy-MM����������ڸ�ʽΪyyyy-MM
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
     * ��ȡָ���µ�ǰһ�£��꣩���һ�£��꣩
     * 
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return �����ʱ�ڸ�ʽΪyyyy-MM-dd����������ڸ�ʽΪyyyy-MM-dd
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
     * �ַ��������ڸ�ʽ�ļ���
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
     * ���������б�עӦ�õĸ߶� <������ϸ����>
     * 
     * @param timeDistance
     * @param nowHeight
     * @param nextOrLastHeight
     * @param coefficient
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��ϵͳʱ��ת����YYYYMMDDHHmmss> <������ϸ����>
     * 
     * @param time
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String splitToDoubuangDate(String time)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(Long.parseLong(time));
        return dateFormat.format(date);
    }
    
    /**
     * ��ȡ�汾��Ϣ
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
     * <�����ж�>
     * <������ϸ����>
     * @param email
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <�ֻ������ж�>
     * @param tel
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <�ʱ��ж�>
     * <������ϸ����>
     * @param post
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��������ж�>
     * <������ϸ����>
     * @param password
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <����λ���ж�>
     * <������ϸ����>
     * @param password
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <����λ���ж�>
     * <������ϸ����>
     * @param certificate
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��ȡimei>
     * <������ϸ����>
     * @param context
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getDeviceId(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    
    /**
     * ɨ����Ϊ��ά��ʱ,��ʽ�Ƿ���ȷ,�����ȷ����scuId,���򷵻�"";
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
     * <��YYYYMMDD ת��Ϊ YYYY-MM-DD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��MMDD ת��Ϊ MM:DD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��ȡ��Ļ�ߴ�>
     * <������ϸ����>
     * @param context
     * @return DisplayMetrics  displayMetrics.heightPixels �߶�  displayMetrics.widthPixels ���
     * @see [�ࡢ��#��������#��Ա]
     */
    public static DisplayMetrics getScreenDisplay(Context context)
    {
        return context.getResources().getDisplayMetrics();
    }
    
    /**
     * <Ϊnullʱ��ֵ��>
     * <������ϸ����>
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDD ת��Ϊ YYYY.MM.DD> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
    
    // ȥ��textview���Ű�����
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY.MM.DD HH:mm> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <��YYYYMMDDHHmmss ת��Ϊ YYYY-MM-DD hh:mm> <������ϸ����>
     * 
     * @param str
     * @return
     * @see [�ࡢ��#��������#��Ա]
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
     * <������ϸ����>
     * @return
     * @see [�ࡢ��#��������#��Ա]
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

    /**
     * @param url
     * @return   0 前往webview
     *           1 前往院校详情
     *           2 前往院校列表
     */
    public static int urlSchemaForWhat(String url){
        if (url.contains(Constants.URL_SCHEMA_SCHOOLDETAIL)){
            return 1;
        }else if(url.contains(Constants.URL_SCHEMA_SCHOOLLIST)){
            return 2;
        }
        return 0;
    }

    /**
     * 判断Url跳转 是否跳转 webview 还是跳转 院校库详情
     */
    public static String getUrlSchemaParam(String url){
        if (url.contains(Constants.URL_SCHEMA_SCHOOLDETAIL)){
            return url.substring(url.lastIndexOf("/")+1);
        }else if(url.contains(Constants.URL_SCHEMA_SCHOOLLIST)){
            return url.substring(url.lastIndexOf("/")+1);
        }else{
            return null;
        }
    }

}
