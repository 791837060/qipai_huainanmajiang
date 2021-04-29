package com.anbang.qipai.dalianmeng.cqrs.q.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.LianmengYushiAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.LianmengYushiAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountDbo;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountingRecord;
import com.anbang.qipai.dalianmeng.plan.bean.GameCountAndCost;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengDiamondDayCostDao;
import com.anbang.qipai.dalianmeng.util.TimeUtil;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LianmengYushiService {

    @Autowired
    private LianmengYushiAccountDboDao lianmengYushiAccountDboDao;

    @Autowired
    private LianmengYushiAccountingRecordDao lianmengYushiAccountingRecordDao;

    @Autowired
    private LianmengDiamondDayCostDao lianmengDiamondDayCostDao;

    public LianmengYushiAccountDbo findYushiAccountDbo(String agentId) {
        return lianmengYushiAccountDboDao.findByAgentId(agentId);
    }

    public void newLianmengYushiAccount(String accountId, String agentId) {
        LianmengYushiAccountDbo account = new LianmengYushiAccountDbo();
        account.setId(accountId);
        account.setAgentId(agentId);
        lianmengYushiAccountDboDao.save(account);
    }

    /**
     * 玉石流水记录
     */
    public LianmengYushiAccountingRecord withdraw(String mengzhuId,   AccountingRecord accountingRecord, String lianmengId, GameTable gameTable) {
        LianmengYushiAccountingRecord record = new LianmengYushiAccountingRecord();
        record.setAccountId(accountingRecord.getAccountId());
        record.setMengzhuId(mengzhuId);
        if (gameTable!=null){
            record.setWanfaName(gameTable.getWanfa().getWanfaName());
            record.setGame(gameTable.getGame());
        }
        record.setLianmengId(lianmengId);
        record.setAccountAmount((int) accountingRecord.getAccountingAmount());
        record.setBalance((int) accountingRecord.getBalanceAfter());
        record.setSummary(accountingRecord.getSummary());
        record.setNo((int) accountingRecord.getAccountingNo());
        record.setAccountingTime(accountingRecord.getAccountingTime());
        lianmengYushiAccountingRecordDao.save(record);
        lianmengYushiAccountDboDao.updateBalance(record.getAccountId(), record.getBalance());
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        LianmengDiamondDayCost lianmengDiamondDayCost = lianmengDiamondDayCostDao.findByLianmengId(lianmengId, startTime, endTime);
        if (lianmengDiamondDayCost!=null){
            lianmengDiamondDayCostDao.updateCostByLianmengId(lianmengId, startTime, endTime, lianmengDiamondDayCost.getCost() - (int) accountingRecord.getAccountingAmount());
            if (lianmengDiamondDayCost.getGameCost()==null){
                Map<Game,GameCountAndCost> gameCountAndCostMap = new HashMap<>();
                for (Game game: Game.values()) {
                    GameCountAndCost gameCountAndCost = new GameCountAndCost();
                    gameCountAndCostMap.put(game,gameCountAndCost);
                }
                lianmengDiamondDayCost.setGameCost(gameCountAndCostMap);
            }
            if (gameTable!=null){
                GameCountAndCost gameCountAndCost = lianmengDiamondDayCost.getGameCost().get(gameTable.getGame());
                gameCountAndCost.setCost(gameCountAndCost.getCost()- (int) accountingRecord.getAccountingAmount());
                gameCountAndCost.setCount(gameCountAndCost.getCount()+1);
                lianmengDiamondDayCost.getGameCost().put(gameTable.getGame(),gameCountAndCost);
                lianmengDiamondDayCostDao.updateGameCostByLianmengId(lianmengId,startTime,endTime,lianmengDiamondDayCost.getGameCost());
            }
        }

        return record;
    }

    public ListPage queryRecordByMemberIdAndLianmengId(int page,int size,String mengzhuId,String lianmengId,long startTime,long endTime){
        int amount= (int) lianmengYushiAccountingRecordDao.countByMemberIdAndLianmengId(mengzhuId,lianmengId,startTime,endTime);

        List<LianmengYushiAccountingRecord> lianmengYushiAccountingRecord = lianmengYushiAccountingRecordDao.findByAgentId(page, size, mengzhuId,lianmengId,startTime,endTime);

        return new ListPage(lianmengYushiAccountingRecord,page,size,amount);
    }



}
