package com.anbang.qipai.admin.plan.bean.members;

public class MemberLoginIPLimit{
    private String id;
    private String loginIp;
    private String desc;
    private long createTime;
    private boolean efficient;// 是否生效

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isEfficient() {
        return efficient;
    }

    public void setEfficient(boolean efficient) {
        this.efficient = efficient;
    }

}
