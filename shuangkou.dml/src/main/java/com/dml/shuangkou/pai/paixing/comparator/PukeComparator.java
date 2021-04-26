package com.dml.shuangkou.pai.paixing.comparator;

import com.dml.puke.pai.PukePai;

public class PukeComparator {

    public int compare(PukePai p1, PukePai p2) {
        if (p1.getPaiMian().ordinal() > p2.getPaiMian().ordinal()) {
            return -1;
        } else if (p1.getPaiMian().ordinal() < p2.getPaiMian().ordinal()) {
            return 1;
        } else {
            return 0;
        }
    }

}
