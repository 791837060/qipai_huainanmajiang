package com.dml.shisanshui.preparedapai.fapai;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.player.ShisanshuiPlayer;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestFapaiStrategy implements FapaiStrategy {

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
        Random r=new Random();
        Map<String, ShisanshuiPlayer> playerIdPlayerMap = currentPan.getPlayerIdPlayerMap();
            int n=1;
            for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
                if(n==1){
                    for(int i=12;i>=0;i--){
                        PukePai pukePai = avaliablePaiList.remove(4*i);
                        player.addShoupai(pukePai);
                    }
                }else if(n==2){
                    PukePai pukePai = avaliablePaiList.remove(avaliablePaiList.size()-1);
                    player.addShoupai(pukePai);
                    player.addShoupai(avaliablePaiList.remove(avaliablePaiList.size()-1));
                    int m=r.nextInt(avaliablePaiList.size());
                    player.addShoupai(avaliablePaiList.remove(m));
                    player.addShoupai(avaliablePaiList.remove(m-1));
                    player.addShoupai(avaliablePaiList.remove(m-2));
                    for(int i=0;i<8;i++){
                        PukePai pukePai1 = avaliablePaiList.remove(r.nextInt(avaliablePaiList.size()));
                        player.addShoupai(pukePai1);
                    }
                }else{
                    for(int i=0;i<13;i++){
                        PukePai pukePai = avaliablePaiList.remove(r.nextInt(avaliablePaiList.size()));
                        player.addShoupai(pukePai);
                    }
                }
                n++;
        }
        currentPan.setAvaliablePaiList(avaliablePaiList);
    }
}
