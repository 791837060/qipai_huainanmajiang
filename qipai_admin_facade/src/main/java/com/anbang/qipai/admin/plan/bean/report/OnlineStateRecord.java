package com.anbang.qipai.admin.plan.bean.report;


import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({ @CompoundIndex(name = "createTime_1_onlineState_1_memberId_1", def = "{'createTime': 1,'onlineState':1,'memberId':1}")})
/**
 * @author YaphetS
 * @date 2018/11/24
 */
public class OnlineStateRecord {
    public static final int ON_LINE=0;
    public static final int OFF_LINE=1;
    /**
     * 				_id,	memberid,	creattime,	onlinestate（0，1),
     */
    private String id;
    /**
     * 用户id
     */
    private String memberId;
    /**
     * 记录时间
     */
    private long createTime;
    /**
     * 上下线状态：0（上线）,1(下线)
     */
    private Integer onlineState;

    public OnlineStateRecord() {
    }

    public OnlineStateRecord(String memberId, long createTime, Integer onlineState) {
        this.memberId = memberId;
        this.createTime = createTime;
        this.onlineState = onlineState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Integer onlineState) {
        this.onlineState = onlineState;
    }


}
