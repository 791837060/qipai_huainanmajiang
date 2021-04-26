package com.dml.puke.pai;

public enum DianShu {
    san, si, wu, liu, qi, ba, jiu, shi, J, Q, K, A, er, xiaowang, dawang, hongxiner2;

    public static DianShu getDianShuByOrdinal(int ordinal) {
        DianShu[] dianshuZu = DianShu.values();
        return dianshuZu[ordinal];
    }
}
