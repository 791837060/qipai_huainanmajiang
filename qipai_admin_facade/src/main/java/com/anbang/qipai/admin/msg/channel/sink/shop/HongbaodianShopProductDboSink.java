package com.anbang.qipai.admin.msg.channel.sink.shop;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface HongbaodianShopProductDboSink {
    String HONGBAODIAN_SHOP_PRODUCT_DBO = "hongbaodianShopProductDbo";

    @Input
    SubscribableChannel hongbaodianShopProductDbo();
}
