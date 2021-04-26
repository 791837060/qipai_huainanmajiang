package com.anbang.qipai.dalianmeng.plan.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.LianmengYushiAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.PowerAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.ScoreAccountingRecordDao;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalPanResultDao;
import com.anbang.qipai.dalianmeng.plan.dao.GameTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameQueryService {

    @Autowired
    private ScoreAccountingRecordDao scoreAccountingRecordDao;

    @Autowired
    private GameHistoricalPanResultDao gameHistoricalPanResultDao;

    @Autowired
    private GameTableDao gameTableDao;

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

    @Autowired
    private LianmengYushiAccountingRecordDao lianmengYushiAccountingRecordDao;

    @Autowired
    private PowerAccountingRecordDao powerAccountingRecordDao;

    public void removeGameData(long endTime) {
        new Thread() {
            @Override
            public void run() {
                scoreAccountingRecordDao.removeByTime(endTime);
                gameHistoricalPanResultDao.removeByTime(endTime);
                gameTableDao.removeByTime(endTime);
                gameHistoricalJuResultDao.removeByTime(endTime);
                lianmengYushiAccountingRecordDao.removeByTime(endTime);
                powerAccountingRecordDao.removeByTime(endTime);
            }
        }.start();
    }

}
