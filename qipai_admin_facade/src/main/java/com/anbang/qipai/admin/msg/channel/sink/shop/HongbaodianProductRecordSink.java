package com.anbang.qipai.admin.msg.channel.sink.shop;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface HongbaodianProductRecordSink {
    String HONGBAODIAN_PRODUCT_RECORD = "hongbaodianProductRecord";

    @Input
    SubscribableChannel hongbaodianProductRecord();
}
