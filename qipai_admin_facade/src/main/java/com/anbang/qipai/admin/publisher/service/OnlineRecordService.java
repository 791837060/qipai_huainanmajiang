package com.anbang.qipai.admin.publisher.service;

import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.publisher.EntityCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 用于springEvent的上下线记录服务
 * @author YaphetS
 * @date 2018/11/24
 */
@Service
public class OnlineRecordService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }


    public void sendRecord(OnlineStateRecord onlineStateRecord){
        publisher.publishEvent(new EntityCreatedEvent<>(onlineStateRecord));
    }
}
