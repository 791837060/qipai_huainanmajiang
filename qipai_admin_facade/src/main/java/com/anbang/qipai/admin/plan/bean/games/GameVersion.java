package com.anbang.qipai.admin.plan.bean.games;

import java.util.Objects;

/**
 * 游戏版本bean
 * @author yins
 */
public class GameVersion {
    private String id;
    private String gameType;  //游戏类型
    private String versionNo;  //版本号
    private long createTime;  //创建时间
    private String remark;  //备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameVersion that = (GameVersion) o;
        return createTime == that.createTime &&
                Objects.equals(id, that.id) &&
                Objects.equals(gameType, that.gameType) &&
                Objects.equals(versionNo, that.versionNo) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameType, versionNo, createTime, remark);
    }
}
