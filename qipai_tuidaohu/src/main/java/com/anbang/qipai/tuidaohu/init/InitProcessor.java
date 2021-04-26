package com.anbang.qipai.tuidaohu.init;

import com.dml.majiang.player.shoupai.gouxing.GouXingCalculator;
import com.dml.majiang.player.shoupai.gouxing.GouXingCalculatorHelper;
import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.context.ApplicationContext;

public class InitProcessor {

    private SingletonEntityRepository singletonEntityRepository;

    private ApplicationContext applicationContext;


    public InitProcessor(SingletonEntityRepository singletonEntityRepository, ApplicationContext applicationContext) {
        this.singletonEntityRepository = singletonEntityRepository;
        this.applicationContext = applicationContext;
    }

    public void init() {
        //内存共享模式要注释次行
        GouXingCalculatorHelper.gouXingCalculator = new GouXingCalculator(14, 4);//最大手牌数 最大鬼牌数
    }

}
