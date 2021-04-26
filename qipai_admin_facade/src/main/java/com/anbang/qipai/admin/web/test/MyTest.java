package com.anbang.qipai.admin.web.test;

import com.anbang.qipai.admin.util.MD5Util;

public class MyTest {
    public static void main(String[] args) {
        String scs = MD5Util.getMD5("scs", "utf-8");
        System.out.println(scs);
    }
}
