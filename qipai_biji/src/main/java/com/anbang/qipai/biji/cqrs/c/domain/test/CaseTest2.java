package com.anbang.qipai.biji.cqrs.c.domain.test;

import com.anbang.qipai.biji.cqrs.c.domain.BianXingWanFa;
import com.anbang.qipai.biji.cqrs.c.domain.BijiChupaiDaoCalculator;
import com.anbang.qipai.biji.cqrs.c.domain.BijiChupaiPaixingSolutionFilter;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.pai.paixing.comparator.TypeCodeDaoComparator;
//import org.junit.jupiter.api.Test;

import java.util.*;

public class CaseTest2 {
    public static void main(String[] args) {
        Dao dao1 = new Dao();
        Dao dao2 = new Dao();
        Dao dao3 = new Dao();

        dao1.setTypeCode(1000);
        dao2.setTypeCode(2000);
        dao3.setTypeCode(3000);


        List<Dao> daoList = new ArrayList();
        daoList.add(dao3);
        daoList.add(dao1);
        daoList.add(dao2);

        TypeCodeDaoComparator daoComparator = new TypeCodeDaoComparator();

        daoList.sort(daoComparator::compare);

        Dao toudao = daoList.get(0);
        Dao zhongdao = daoList.get(1);
        Dao weidao = daoList.get(2);
    }
}
