package com.dml.shuangkou.preparedapai.luanpai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.pan.PanResult;
import com.dml.shuangkou.player.ShuangkouPlayerValueObject;

/**
 * 按照上一盘的出牌的顺序将牌叠在一起
 *
 * @author lsc
 */
public class LastPanChuPaiOrdinalLuanpaiStrategy implements LuanpaiStrategy {

    public LastPanChuPaiOrdinalLuanpaiStrategy() {

    }

    @Override
    public void luanpai(Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        PanResult lastPanResult = ju.findLatestFinishedPanResult();
        List<DianShuZuPaiZu> dachuPaiZuList = lastPanResult.getPan().getDachuPaiZuList();//打出牌历史集合
        List<String> playerIds = lastPanResult.allPlayerIds();//玩家集合
        List<PukePai> finalPaiList = new ArrayList<>();
        for (DianShuZuPaiZu paizu : dachuPaiZuList) {
            finalPaiList.addAll(Arrays.asList(paizu.getPaiArray()));
        }

        finalPaiList.addAll(lastPanResult.getPan().getPaiListValueObject().getPaiList());

        playerIds.forEach((playerId) -> {
            ShuangkouPlayerValueObject player = lastPanResult.findPlayer(playerId);
            finalPaiList.addAll(player.getAllShoupai().values());
        });

        currentPan.setAvaliablePaiList(finalPaiList);
    }

}
