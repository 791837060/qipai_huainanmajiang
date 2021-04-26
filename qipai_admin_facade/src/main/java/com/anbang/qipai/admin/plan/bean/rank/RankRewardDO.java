package com.anbang.qipai.admin.plan.bean.rank;

/**
 * @Description: 排行榜奖品
 */
public class RankRewardDO {
    private String id;
    private int start;                    //排名区间
    private int end;                      //排名区间
    private String remark;                //备注
    private RewardTypeEnum rewardType;    //奖励类型
    private int rewardNum;                //奖品数量
    private int remain;                   //库存数量
    private String iconUrl;               //icon图

    private RankType type;                  //榜单类型

    public RankRewardDO() {
    }

    public RankRewardDO(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RewardTypeEnum getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardTypeEnum rewardType) {
        this.rewardType = rewardType;
    }

    public int getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(int rewardNum) {
        this.rewardNum = rewardNum;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public RankType getType() {
        return type;
    }

    public void setType(RankType type) {
        this.type = type;
    }
}
