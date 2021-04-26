package com.anbang.qipai.admin.plan.service.reportservice;

import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.plan.dao.reportdao.OnlineStateRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YaphetS
 * @date 2018/11/26
 */
@Service
public class OnlineStateRecordService {

    @Autowired
    private OnlineStateRecordDao onlineStateRecordDao;


    public Long countOnlineRecord() {
        return onlineStateRecordDao.countOnlineRecord();
    }

    public Integer countOnlineRecordAfterTime(long createTime){
        return (int)onlineStateRecordDao.countOnlineRecordAfterTime(createTime);
    }

    public void insert(OnlineStateRecord onlineStateRecord) {
        onlineStateRecordDao.insert(onlineStateRecord);
    }

    public List<OnlineStateRecord> findOnlineRecordAfterTime(long dayStartTime) {
        return onlineStateRecordDao.findOnlineRecordAfterTime(dayStartTime);
    }

    /**
     * 根据memberId,createTime去上下线记录表查询
     * 得到大于createTime的上下线记录(注意空指针)
     * 根据上下线记录得到大于createTime的在线时长
     * @param memberId
     * @param createTime
     * @return
     */
    public Long findOnlineTimeByMemberId(String memberId, Long createTime) {
        Long onlineTime=0L;
        //DB操作得到上下线记录的list
        List<OnlineStateRecord> recordList=onlineStateRecordDao.findByMemberIdAfterTime(memberId,createTime);
        //得到上线记录list
        List<OnlineStateRecord> onlineList=new ArrayList<>();
        //得到下线记录list
        List<OnlineStateRecord> offlineList=new ArrayList<>();
        for(OnlineStateRecord record:recordList){
            if(record.getOnlineState()==0){
                onlineList.add(record);
            }else{
                offlineList.add(record);
            }
        }
        int onlineCount=onlineList.size();
        int offlineCount=offlineList.size();
//        1.如果上线记录数,下线记录数都是0
//        当前时间-当日开始时间
        if(onlineCount==0&&offlineCount==0){
            onlineTime=System.currentTimeMillis()-createTime;
            return onlineTime;
        }

        //初始化在线时间:下线记录累加-上线记录累加
        onlineTime=countOnlineTime(onlineList,offlineList);
//        2.如果上线记录数>下线记录数
//        下线记录累加+当前时间-上线记录累加
        if(onlineCount>offlineCount){
            return onlineTime+System.currentTimeMillis();
        }
//        3.如果上线记录数=下线记录数
//        下线记录累加-上线记录累加(求绝对值)
        if(onlineCount==offlineCount){
            return Math.abs(onlineTime);
        }
//        4.如果上线记录数<下线记录数
//        下线记录累加-当前日开始时间-上线记录累加
        if(onlineCount<offlineCount){
            return onlineTime-createTime;
        }
        return 0L;
    }

    public List<String> listIdsByTime(long startTime, long endTime) {
        return onlineStateRecordDao.listIdsByTime(startTime,endTime);
    }


    /**
     * 下线记录累加-上线记录累加
     * @param onlineList
     * @param offlineList
     * @return
     */
    private long countOnlineTime(List<OnlineStateRecord> onlineList,List<OnlineStateRecord> offlineList){
        Long onlineTime=0L;
        for(OnlineStateRecord offline:offlineList){
            onlineTime+=offline.getCreateTime();
        }
        for(OnlineStateRecord online:onlineList){
            onlineTime-=online.getCreateTime();
        }
        return onlineTime;
    }
}



















