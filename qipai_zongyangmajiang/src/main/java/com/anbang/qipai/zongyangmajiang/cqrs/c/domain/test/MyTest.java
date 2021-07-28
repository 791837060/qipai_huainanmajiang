package com.anbang.qipai.zongyangmajiang.cqrs.c.domain.test;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.player.MajiangPlayer;

import java.util.*;

public class MyTest {

    public static void main(String[] args) {
        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = test4();

//        List<MajiangPai> shoupaiList = new ArrayList<>();
//        shoupaiList.add(MajiangPai.yiwan);
//        shoupaiList.add(MajiangPai.jiuwan);
//        shoupaiList.add(MajiangPai.yitiao);
//        shoupaiList.add(MajiangPai.jiutiao);
//        shoupaiList.add(MajiangPai.yitong);
//        shoupaiList.add(MajiangPai.jiutong);
//        shoupaiList.add(MajiangPai.dongfeng);
//        shoupaiList.add(MajiangPai.nanfeng);
//        shoupaiList.add(MajiangPai.xifeng);
//        shoupaiList.add(MajiangPai.beifeng);
//        shoupaiList.add(MajiangPai.hongzhong);
//        shoupaiList.add(MajiangPai.baiban);
//        shoupaiList.add(MajiangPai.baiban);
//
//        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = calculateShisanyaoMoPaiHupaiTips(shoupaiList, MajiangPai.baiban, 1);
//        if (majiangPaiListMap != null) {
        for (Map.Entry<MajiangPai, List<MajiangPai>> entry : majiangPaiListMap.entrySet()) {
            for (MajiangPai majiangPais : entry.getValue()) {
                System.out.println(entry.getKey().name() + "|" + majiangPais.name());
            }
        }
//        }
    }


    public static Map<MajiangPai, List<MajiangPai>> calculateShisanyaoMoPaiHupaiTips(List<MajiangPai> shoupaiList, MajiangPai gangmoshoupai, int guipaiCount) {
        List<MajiangPai> shoupai = new ArrayList<>(shoupaiList);
        shoupai.add(gangmoshoupai);
        List<MajiangPai> hupaiList = new ArrayList<>();
        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        Integer[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        Set<MajiangPai> guipaiTypeSet = new HashSet<>();
        guipaiTypeSet.add(MajiangPai.qitiao);
        guipaiTypeSet.add(MajiangPai.qiwan);
        Map<MajiangPai, List<MajiangPai>> tips = new HashMap<>();
        for (MajiangPai majiangPai : shoupai) {//剔除已有麻将牌
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (majiangPai.ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }
        for (int i = 0; i < shisanyaoPaixin.length; i++) {//计算相差牌数
            if (shisanyaoPaixin[i] >= 0) {
                hupaiList.add(MajiangPai.valueOf(shisanyaoPaixin[i]));//提示缺少手牌集合
            } else {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));//剩余手牌
            }
        }
        List<Integer> ints = Arrays.asList(shisanyaoPaixin2);
        if (hupaiList.size() <= guipaiCount) {  //能胡十三幺
            if (shoupai.size() <= 1 && hupaiList.size() == 0) { //差一张牌并且不缺少十三幺的牌
                if (shoupai.size() == 0 || ints.contains(shoupai.get(0).ordinal())) {//剩余手牌是十三幺牌型内的牌 提示打所有牌 胡十三幺牌型加金牌
                    shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
                    System.out.println(1);
                } else {//缺凑成对子的那张牌 提示打剩余的那张牌 胡十三幺牌型加金牌
                    List<MajiangPai> list = new ArrayList<>();
                    for (Integer i2 : shisanyaoPaixin2) {
                        list.add(MajiangPai.valueOf(i2));
                    }
                    list.addAll(guipaiTypeSet);
                    tips.put(shoupai.get(0), list);
                    System.out.println(2);
                }
            } else if (shoupai.size() == 1) {   //只差一张胡十三幺 提示打剩余的那张手牌 胡十三幺缺少的牌加金牌
                boolean all = true;
                for (MajiangPai majiangPai : shoupai) {
                    if (!ints.contains(majiangPai.ordinal())) {
                        all = false;
                        break;
                    }
                }
                if (all) {
                    shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
                    System.out.println(3);
                } else {
                    List<MajiangPai> list = new ArrayList<>();
                    for (Integer i2 : shisanyaoPaixin2) {
                        list.add(MajiangPai.valueOf(i2));
                    }
                    list.addAll(guipaiTypeSet);
                    tips.put(shoupai.get(0), list);
                    System.out.println(4);
                }
            } else if (shoupai.size() == 0) {
                shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
                System.out.println(5);
            } else {
                if (shoupai.size() == 2) {
                    hupaiList.addAll(guipaiTypeSet);
                    if (ints.contains(shoupai.get(0).ordinal())) {
                        tips.put(shoupai.get(1), hupaiList);
                    } else if (ints.contains(shoupai.get(1).ordinal())) {
                        tips.put(shoupai.get(0), hupaiList);
                    }
                    System.out.println(6);
                }
                return tips;
            }
        } else if (shoupai.size() == 2) {   //如果剩余2张手牌
            hupaiList.addAll(guipaiTypeSet);
            if (ints.contains(shoupai.get(0).ordinal())) {
                tips.put(shoupai.get(1), hupaiList);
            } else if (ints.contains(shoupai.get(1).ordinal())) {
                tips.put(shoupai.get(0), hupaiList);
            }
            System.out.println(7);
            return tips;
        }
        return null;
    }

    public static Map<MajiangPai, List<MajiangPai>> test4() {
        Map<MajiangPai, List<MajiangPai>> tipsList = new HashMap<>();
        List<MajiangPai> shoupaiList = new ArrayList<>();//模拟手牌集合
        shoupaiList.add(MajiangPai.yiwan);
        shoupaiList.add(MajiangPai.yiwan);
        shoupaiList.add(MajiangPai.yiwan);
        shoupaiList.add(MajiangPai.sanwan);
        shoupaiList.add(MajiangPai.sanwan);
        shoupaiList.add(MajiangPai.sanwan);
        shoupaiList.add(MajiangPai.yitiao);
        shoupaiList.add(MajiangPai.ertiao);
        shoupaiList.add(MajiangPai.santiao);
        shoupaiList.add(MajiangPai.yitong);
        shoupaiList.add(MajiangPai.yitong);
        shoupaiList.add(MajiangPai.yitong);
        shoupaiList.add(MajiangPai.wutong);
        shoupaiList.add(MajiangPai.liutong);

        List<MajiangPai> keziList = new ArrayList<>();//拥有刻子集合
        List<MajiangPai> shunziList = new ArrayList<>();//拥有刻子集合
        List<MajiangPai> duiziList = new ArrayList<>();//拥有对子集合
        List<MajiangPai> danpaiList = new ArrayList<>();//拥有对子集合

        int[] majiangPaiarr = new int[34];
        for (MajiangPai majiangPai : shoupaiList) {
            majiangPaiarr[majiangPai.ordinal()]++;
        }

        for (int i = 0; i < majiangPaiarr.length; i++) {
            if (majiangPaiarr[i] == 0) continue;
            if (majiangPaiarr[i] == 3) {
                for (int j = 0; j < 3; j++) {
                    keziList.add(MajiangPai.valueOf(i));
                    shoupaiList.remove(MajiangPai.valueOf(i));
                }
            } else if (majiangPaiarr[i] == 2) {
                for (int j = 0; j < 2; j++) {
                    duiziList.add(MajiangPai.valueOf(i));
                    shoupaiList.remove(MajiangPai.valueOf(i));
                }
            }
        }
        for (int i = 0; i < shoupaiList.size(); i++) {
            if (i + 2 <= shoupaiList.size()) {
                int ordinal1 = shoupaiList.get(i).ordinal();
                int ordinal2 = shoupaiList.get(i + 1).ordinal();
                int ordinal3 = shoupaiList.get(i + 2).ordinal();
                if (ordinal1 + 2 == ordinal3 && ordinal2 + 1 == ordinal3) {
                    shunziList.add(shoupaiList.remove(i));
                    shunziList.add(shoupaiList.remove(i));
                    shunziList.add(shoupaiList.remove(i));
                }
            }
        }
        for (int i = 0; i < majiangPaiarr.length; i++) {
            if (majiangPaiarr[i] == 0) continue;
            if (majiangPaiarr[i] == 1) {
                danpaiList.add(MajiangPai.valueOf(i));
                shoupaiList.remove(MajiangPai.valueOf(i));
            }
        }


        System.out.println(keziList.size() + "|" + duiziList.size() + "|" + danpaiList.size());
//        int shoupaiCount = shoupaiList.size();
//        int kezi = ((shoupaiCount - 1) / 4) - 1;
//        int keziCount = keziList.size() / 3;
//        int duiziCount = duiziList.size() / 2;

        if (danpaiList.size() == 2) {//差一个对子（将）
            List<MajiangPai> list = new ArrayList<>();
            list.add(danpaiList.get(0));
            tipsList.put(danpaiList.get(1), list);
            list.clear();
            list.add(danpaiList.get(1));
            tipsList.put(danpaiList.get(0), list);
        } else if (danpaiList.size() == 1) {
            List<MajiangPai> list = new ArrayList<>();
            list.add(duiziList.get(0));
            list.add(duiziList.get(1));
            tipsList.put(danpaiList.get(0), list);
        }

        return tipsList;
    }

    public static void test3() {
        Integer gangCount = -100;
        gangCount = Math.max(gangCount - 1, 0);
        System.out.println(gangCount);
    }

    public static void test2() {
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiArray();
        List<MajiangPai> majiangPais = new ArrayList<>();
        Collections.addAll(majiangPais, xushupaiArray);
        if (false) {
            for (int i = 27; i < 31; i++) {
                majiangPais.add(MajiangPai.valueOf(i));
            }
        }
        if (true) {
            for (int i = 32; i < 34; i++) {
                majiangPais.add(MajiangPai.valueOf(i));
            }
        }
        MajiangPai[] paiTypesForGuipaiAct = new MajiangPai[majiangPais.size()];
        majiangPais.toArray(paiTypesForGuipaiAct);
        System.out.println(Arrays.toString(paiTypesForGuipaiAct));

    }

    public static void test1() {
        List<MajiangPai> shoupaiList = new ArrayList();
        shoupaiList.add(MajiangPai.yiwan);
        shoupaiList.add(MajiangPai.jiuwan);
        shoupaiList.add(MajiangPai.yitong);
        shoupaiList.add(MajiangPai.jiutong);
        shoupaiList.add(MajiangPai.yitiao);
        shoupaiList.add(MajiangPai.jiutiao);
        shoupaiList.add(MajiangPai.dongfeng);
        shoupaiList.add(MajiangPai.nanfeng);
        shoupaiList.add(MajiangPai.xifeng);
        shoupaiList.add(MajiangPai.beifeng);
        shoupaiList.add(MajiangPai.baiban);
        shoupaiList.add(MajiangPai.batong);

        MajiangPai moruShoupai = MajiangPai.batong;


//        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = calculateShisanyaoMoPaiHupaiTips(shoupaiList, moruShoupai, 1);
//        if (majiangPaiListMap != null) {
//            for (Map.Entry<MajiangPai, List<MajiangPai>> entry : majiangPaiListMap.entrySet()) {
//                System.out.println(entry.getKey() + "|" + entry.getValue());
//            }
//        }

        List<MajiangPai> majiangPais = calculateShisanyaoHupaiTips(shoupaiList, 3);
        for (MajiangPai majiangPai : majiangPais) {
            System.out.println(majiangPai);
        }
    }

    public static Map<MajiangPai, List<MajiangPai>> calculateShisanyaoMoPaiHupaiTips2(MajiangPlayer majiangPlayer) {
        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        Integer[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int guipaiCount = majiangPlayer.getFangruGuipaiList().size();
        Set<MajiangPai> guipaiTypeSet = majiangPlayer.getGuipaiTypeSet();
        List<MajiangPai> shoupai = new ArrayList<>(majiangPlayer.getFangruShoupaiList());
        MajiangPai gangmoShoupai = majiangPlayer.getGangmoShoupai();
        List<MajiangPai> hupaiList = new ArrayList<>();
        Map<MajiangPai, List<MajiangPai>> tips = new HashMap<>();
        if (guipaiTypeSet.contains(gangmoShoupai)) {
            guipaiCount++;
        } else {
            shoupai.add(gangmoShoupai);
        }
        for (MajiangPai majiangPai : shoupai) {//剔除已有麻将牌
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (majiangPai.ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }
        for (int i = 0; i < shisanyaoPaixin.length; i++) {//计算相差牌数
            if (shisanyaoPaixin[i] >= 0) {
                hupaiList.add(MajiangPai.valueOf(shisanyaoPaixin[i]));//提示缺少手牌集合
            } else {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));//剩余手牌
            }
        }
        List<Integer> ints = Arrays.asList(shisanyaoPaixin2);
        if (hupaiList.size() <= guipaiCount) {
            if (shoupai.size() <= 1 && hupaiList.size() == 0) {
                if (shoupai.size() == 0 || ints.contains(shoupai.get(0).ordinal())) {
                    shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
                } else {
                    List<MajiangPai> list = new ArrayList<>();
                    for (Integer i2 : shisanyaoPaixin2) {
                        list.add(MajiangPai.valueOf(i2));
                    }
                    list.addAll(guipaiTypeSet);
                    tips.put(shoupai.get(0), list);
                }
            } else if (shoupai.size() == 1) {
                boolean all = true;
                for (MajiangPai majiangPai : shoupai) {
                    if (!ints.contains(majiangPai.ordinal())) {
                        all = false;
                        break;
                    }
                }
                if (all) {
                    shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
                } else {
                    List<MajiangPai> list = new ArrayList<>();
                    for (Integer i2 : shisanyaoPaixin2) {
                        list.add(MajiangPai.valueOf(i2));
                    }
                    list.addAll(guipaiTypeSet);
                    tips.put(shoupai.get(0), list);
                }
            } else if (shoupai.size() == 0) {
                shisanyaoAllTips(shisanyaoPaixin2, guipaiTypeSet, tips);
            } else {
                if (shoupai.size() == 2) {
                    hupaiList.addAll(guipaiTypeSet);
                    if (ints.contains(shoupai.get(0).ordinal())) {
                        tips.put(shoupai.get(1), hupaiList);
                    } else if (ints.contains(shoupai.get(1).ordinal())) {
                        tips.put(shoupai.get(0), hupaiList);
                    }
                }
                return tips;
            }
        } else if (shoupai.size() == 2) {
            hupaiList.addAll(guipaiTypeSet);
            if (ints.contains(shoupai.get(0).ordinal())) {
                tips.put(shoupai.get(1), hupaiList);
            } else if (ints.contains(shoupai.get(1).ordinal())) {
                tips.put(shoupai.get(0), hupaiList);
            }
            return tips;
        }
        return null;
    }

    private static void shisanyaoAllTips(Integer[] shisanyaoPaixin2, Set<MajiangPai> guipaiTypeSet, Map<MajiangPai, List<MajiangPai>> tips) {
        for (Integer i : shisanyaoPaixin2) {
            List<MajiangPai> list = new ArrayList<>();
            for (Integer i2 : shisanyaoPaixin2) {
                list.add(MajiangPai.valueOf(i2));
            }
            list.addAll(guipaiTypeSet);
            tips.put(MajiangPai.valueOf(i), list);
        }
    }

    public static List<MajiangPai> calculateShisanyaoHupaiTips(List<MajiangPai> shoupai, int guipaiCount) {
//        ArrayList<MajiangPai> shoupai = new ArrayList(majiangPlayer.getFangruShoupaiList());
//        int guipaiCount = majiangPlayer.getFangruGuipaiList().size();
        List<MajiangPai> hupaiList = new ArrayList<>();
        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int count = 0;//相差数量

        for (int i = 0; i < shoupai.size(); i++) {//剔除已有麻将牌
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (shoupai.get(i).ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }
        for (int i = 0; i < shisanyaoPaixin.length; i++) {//计算相差牌数
            if (shisanyaoPaixin[i] >= 0) {
                hupaiList.add(MajiangPai.valueOf(shisanyaoPaixin[i]));
                count++;
            } else {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));
            }
        }
        if (shoupai.size() > 0) {
            if (hupaiList.size() - guipaiCount <= 1) {
                return hupaiList;
            }
        } else {
            if (guipaiCount >= count) {//如果十三幺牌型都有 返回所有可胡牌型（差一张牌凑成对子）
                hupaiList.clear();
                for (int i : shisanyaoPaixin2) {
                    hupaiList.add(MajiangPai.valueOf(i));
                }
                return hupaiList;
            }
        }
        return null;
    }
}
