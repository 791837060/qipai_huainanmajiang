package com.anbang.qipai.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description: 输入格式校验
 */
public class FormatUtils {
    /**
     * 输入数据为数值型6位月份(201901)
     */
    public static boolean monthCheck(int yearMonth) {
        String str = String.valueOf(yearMonth);
        if (str.length() != 6){
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        try {
            format.setLenient(false);   //设置为严格校验
            format.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
