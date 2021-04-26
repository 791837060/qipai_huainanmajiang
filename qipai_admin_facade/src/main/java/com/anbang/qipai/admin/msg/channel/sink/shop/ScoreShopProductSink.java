package com.anbang.qipai.admin.msg.channel.sink.shop;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ScoreShopProductSink {
    String SCORE_SHOP_PRODUCT_DBO = "scoreShopProductDbo";

    @Input
    SubscribableChannel scoreShopProductDbo();
}
