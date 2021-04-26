package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.gang.MajiangPlayerGangActionProcessor;

/**
 * 胡优先的杠，有胡的情况下不能杠，但要记录杠操作
 *
 * @author lsc
 */
public class MaanshanMajiangGangActionProcessor implements MajiangPlayerGangActionProcessor {

    @Override
    public void process(MajiangGangAction action, Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();

        if (!action.isDisabledByHigherPriorityAction()) {// 没有被阻塞
            GangType gangType = action.getGangType();
            if (gangType.equals(GangType.gangdachu)) {
                currentPan.playerGangDachupai(action.getActionPlayerId(), action.getDachupaiPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.shoupaigangmo)) {
                currentPan.playerShoupaiGangMo(action.getActionPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.gangsigeshoupai)) {
                currentPan.playerGangSigeshoupai(action.getActionPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.kezigangmo)) {
                currentPan.playerKeziGangMo(action.getActionPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.kezigangshoupai)) {
                currentPan.playerKeziGangShoupai(action.getActionPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.sanbanziminggang)) {    //三搬子明杠（手牌有2个去杠别人打出的搬子）
                currentPan.playerSanbanziGangDachupai(action.getActionPlayerId(), action.getDachupaiPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.sanbanziangangmo)) {      //三搬子暗杠（手牌有2个,刚摸1个）
                currentPan.playerSanbanziShoupaiGangMo(action.getActionPlayerId(), action.getPai());
            } else if (gangType.equals(GangType.sanbanziangangshoupai)) {      //三搬子暗杠（手牌有3个）
                currentPan.playerSanbanzishoupaiGang(action.getActionPlayerId(), action.getPai());
            } else {
            }
        }
    }

}
