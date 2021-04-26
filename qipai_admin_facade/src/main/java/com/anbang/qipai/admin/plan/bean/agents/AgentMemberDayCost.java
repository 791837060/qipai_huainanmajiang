package com.anbang.qipai.admin.plan.bean.agents;

/**
 * 推广员下辖玩家日消耗统计
 *
 * @author ethan
 */
public class AgentMemberDayCost {
    private String id;          // id:  yyyymmdd-agentId
    private String agentId;     // 代理id
    private String nickName;    // 代理昵称
    private String type;        // 消耗类型  gold
    private int dayTotalCost;   // 消耗数量
    private long createTime;    // 创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDayTotalCost() {
        return dayTotalCost;
    }

    public void setDayTotalCost(int dayTotalCost) {
        this.dayTotalCost = dayTotalCost;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AgentMemberDayCost{" +
                "id='" + id + '\'' +
                ", agentId='" + agentId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", type='" + type + '\'' +
                ", dayTotalCost=" + dayTotalCost +
                ", createTime=" + createTime +
                '}';
    }
}
