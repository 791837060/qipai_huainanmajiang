package com.anbang.qipai.admin.web.vo.juprize;

import java.util.PrimitiveIterator;

/**
 * @Description:
 */
public class CommonDropDown {
    private String dropKey;
    private String dropValue;

    public CommonDropDown(String dropKey, String dropValue) {
        this.dropKey = dropKey;
        this.dropValue = dropValue;
    }

    public String getDropKey() {
        return dropKey;
    }

    public void setDropKey(String dropKey) {
        this.dropKey = dropKey;
    }

    public String getDropValue() {
        return dropValue;
    }

    public void setDropValue(String dropValue) {
        this.dropValue = dropValue;
    }
}
