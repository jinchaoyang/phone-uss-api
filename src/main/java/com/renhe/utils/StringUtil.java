package com.renhe.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by jinchaoyang on 2018/12/21.
 */
public class StringUtil {

    /**
     * 将字符串的左侧填充某个值
     *
     * @param scale
     * @return
     */
    public static String leftJoin(Object obj, int scale, String val) {
        String str = String.valueOf(obj);
        int len = str.length();
        if (len < scale) {
            int diff = scale - len;
            String prefix = "";
            for (int i = 0; i < diff; i++) {
                prefix += val;
            }
            str = prefix + str;
        }
        return str;
    }

    /**
     * 判断字符串是否为空或null
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        str = str == null ? "" : str.trim();
        return str.equals("");
    }

    /**
     * 判断字符串是否为空或null
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.trim().equals("")
                || str.toLowerCase().equals("null");
    }

    /**
     * 去除字符串两边的空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        str = str == null ? "" : str.trim();
        return str;
    }

    public static String trim(Object obj) {
        String str = String.valueOf(obj);
        return trim(str);
    }

    public static String trimToNull(Object obj) {
        String str = String.valueOf(obj);
        str = (str == null) ? null : str.trim();
        return str;
    }

    /**
     * 字符串去重,如果字符串为空返回默认值
     *
     * @param defaultValue
     * @return
     */
    public static String trim(String str, String defaultValue) {
        if (!isBlank(str)) {
            return str.trim();
        }
        return defaultValue;
    }

    /**
     * 驼峰转换
     *
     * @param param
     * @return
     */
    public static String humpTransition(String param) {
        if (isBlank(param)) {
            return param;
        }
        List<Integer> index = new ArrayList<Integer>();
        for (int i = 0; i < param.length(); i++) {
            char tmp = param.charAt(i);
            if (Character.isUpperCase(tmp)) {
                index.add(i);
            }
        }
        if (index.size() > 0) {
            List<String> upper = new ArrayList<String>();
            for (int i = 0; i < index.size(); i++) {
                upper.add(param.substring(index.get(i), index.get(i) + 1));
            }
            for (int i = 0; i < upper.size(); i++) {
                param = param.replace(upper.get(i), "_" + upper.get(i));
            }
            return param.toLowerCase();
        } else {
            return param;
        }
    }

    /**
     * 字符串如果为空字符串，NULL，或"null" 则返回false 否则为true
     *
     * @param str
     * @return
     */
    public static boolean isPresent(String str) {
        str = StringUtil.trim(str);
        if (!"".equals(str) && !"null".equals(str.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 将毫秒转化为 hh:mm:ss 格式
     *
     * @param duration
     * @return
     */
    public static String timeFomart(long duration) {
        int hour = (int) (duration / (1000 * 60 * 60));
        int time = (int) duration - hour * (1000 * 60 * 60);
        int minute = (int) time / (1000 * 60);
        time = time - minute * (1000 * 60);
        int second = (int) time / 1000;
        String result = StringUtil.leftJoin(hour, 2, "0") + ":"
                + StringUtil.leftJoin(minute, 2, "0") + ":"
                + StringUtil.leftJoin(second, 2, "0");
        return result;
    }

    public static String scale(BigDecimal decimal, int scale) {
        decimal = decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return decimal.toString();
    }

    public static String toPercent(double value) {
        value = value * 100;
        String result = StringUtil.scale(new BigDecimal(value), 2);
        return result + "%";

    }

    public static boolean isNumber(String str) {
        str = trim(str);
        String datePattern = "^[0-9]+$";
        Pattern p = Pattern.compile(datePattern);
        return p.matcher(str).matches();
    }

}
