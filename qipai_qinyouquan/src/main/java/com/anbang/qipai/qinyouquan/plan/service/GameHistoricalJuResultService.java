package com.anbang.qipai.qinyouquan.plan.service;

import com.anbang.qipai.qinyouquan.cqrs.c.service.PlayBackCodeCmdService;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.PlayBackDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GamePayType;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalPanResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalPanResultExpand;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameJuPlayerResult;
import com.anbang.qipai.qinyouquan.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.qinyouquan.plan.dao.GameHistoricalPanResultDao;
import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameHistoricalJuResultService {

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

    @Autowired
    private GameHistoricalPanResultDao gameHistoricalPanResultDao;

    @Autowired
    private PlayBackCodeCmdService playBackCodeCmdService;

    @Autowired
    private PlayBackDboDao playBackDboDao;

    @Autowired MemberDayResultDataService memberDayResultDataService;

    public void addGameHistoricalResult(GameHistoricalJuResult result) {
        gameHistoricalJuResultDao.addGameHistoricalResult(result);
    }


    public ListPage findGameHistoricalResultByMemberId(int page, int size, String memberId, long startTime, long endTime) {
        long amount = gameHistoricalJuResultDao.getAmountByMemberIdAndTime(memberId, startTime, endTime);
        List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByMemberIdAndTime(page, size,
                memberId, startTime, endTime);
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }

    public long countDayingjiaByMemberIdAndTime(String memberId, String lianmengId, long startTime, long endTime) {
        return gameHistoricalJuResultDao.countDayingjiaByMemberIdAndTime(memberId, lianmengId, startTime, endTime);
    }

    public int countPower(Game game, String lianmengId, String memberId, long startTime, long endTime) {
        List<GameHistoricalJuResult> resultList = gameHistoricalJuResultDao.findGameHistoricalResultByMemberIdAndTime(game, memberId, lianmengId, startTime, endTime);
        int power = 0;
        for (GameHistoricalJuResult result : resultList) {
            List<GameJuPlayerResult> playerResults = result.getPlayerResultList();
            for (GameJuPlayerResult playerResult : playerResults) {
                if (memberId.equals(playerResult.playerId())) {
                    power += playerResult.totalScore();
                }
            }
        }
        return power;
    }

    public ListPage findGameHistoricalResultByLianmengId(int page, int size, Game game, String lianmengId, String memberId, long startTime, long endTime) {
        long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(game, lianmengId, memberId, startTime, endTime);
        List<GameHistoricalJuResult> gameHistoricalJuResultList = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndMemberIdAndTime(page, size, game,
                lianmengId, memberId, startTime, endTime);
        List list=new ArrayList();
        for (GameHistoricalJuResult gameHistoricalJuResult : gameHistoricalJuResultList) {
            Map data = new HashMap();
            data.put("id",gameHistoricalJuResult.getId());
            data.put("gameId",gameHistoricalJuResult.getGameId());
            data.put("game",gameHistoricalJuResult.getGame());
            data.put("roomNo",gameHistoricalJuResult.getRoomNo());
            data.put("dayingjiaId",gameHistoricalJuResult.getDayingjiaId());
            data.put("datuhaoId",gameHistoricalJuResult.getDatuhaoId());
            data.put("lianmengId",gameHistoricalJuResult.getLianmengId());
            data.put("playerResultList",gameHistoricalJuResult.getPlayerResultList());
            data.put("lastPanNo",gameHistoricalJuResult.getLastPanNo());
            data.put("panshu",gameHistoricalJuResult.getPanshu());
            data.put("finishTime",gameHistoricalJuResult.getFinishTime());
            data.put("finish",gameHistoricalJuResult.isFinish());
            if (gameHistoricalJuResult.getPayType().equals(GamePayType.DAYINGJIA)) {
                if (memberId.equals(gameHistoricalJuResult.getDayingjiaId())){
                    data.put("diamondCost",gameHistoricalJuResult.getDiamondCost());
                }else {
                    data.put("diamondCost",0);
                }
            }else {
                data.put("diamondCost",gameHistoricalJuResult.getDiamondCost()/gameHistoricalJuResult.getPlayerResultList().size());
            }
            list.add(data);
        }
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }


    public GameHistoricalJuResult findGameHistoricalResultById(String id) {
        return gameHistoricalJuResultDao.findGameHistoricalResultById(id);
    }

    public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
        return (int) gameHistoricalJuResultDao.countGameNumByGameAndTime(game, startTime, endTime);
    }

    public GameHistoricalJuResult getJuResultByGameId(String gameId) {
        return gameHistoricalJuResultDao.getJuResultByGameId(gameId);
    }

    public Map queryRoomDetail(String gameId) {
        GameHistoricalJuResult juResult = gameHistoricalJuResultDao.getJuResultByGameId(gameId);
        List<GameHistoricalPanResult> panResults = gameHistoricalPanResultDao.findPanResultByGameId(gameId);
        List<GameHistoricalPanResultExpand> result = new ArrayList<>();
        for (GameHistoricalPanResult list : panResults) {
            GameHistoricalPanResultExpand expand = new GameHistoricalPanResultExpand();
            BeanUtils.copyProperties(list, expand);
            // 获取返回码
            PlayBackDbo playBackDbo=playBackDboDao.findByGameAndGameIdAndPanNo(list.getGame(), list.getGameId(),
                    list.getNo());
            if (playBackDbo == null) {
                PlayBackDbo playBackDbo1=new PlayBackDbo();
                expand.setCodeStatus(1);
                Integer code = playBackCodeCmdService.getPlayBackCode();
                int size1 = code.toString().length();
                String newCode = "";
                int i = 6 - size1;
                while (i > 0) {
                    newCode += "0";
                    i--;
                }
                newCode += code.toString();
                playBackDbo1.setGame(list.getGame());
                playBackDbo1.setGameId(list.getGameId());
                playBackDbo1.setId(newCode);
                playBackDbo1.setPanNo(list.getNo());
                playBackDboDao.save(playBackDbo1);
                expand.setCode(newCode);
            } else {
                expand.setCodeStatus(1);
                expand.setCode(playBackDbo.getId());
            }
            result.add(expand);
        }

        Map map = new HashMap();
        if (juResult != null) {
            map.put("totalScore", juResult.getPlayerResultList());
        }
        map.put("list", result);
        return map;
    }



    public void memberJuResultFinish(String memberId, String lianmengId, long startTime, long endTime) {
        gameHistoricalJuResultDao.memberJuResultFinish(memberId,lianmengId,startTime,endTime);
    }

    public ListPage findGameHistoricalResultByLianmengIdAndSuperiorId(int page, int size, String lianmengId, String memberId,
                                                                      long startTime, long endTime,boolean finish,String searchMemberId) {
        if (searchMemberId!=null){
            long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime( lianmengId, searchMemberId, startTime, endTime,finish);
            List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndMemberIdAndTime(page, size, lianmengId, searchMemberId, startTime, endTime,finish);
            return new ListPage(list, page, size, (int) amount);
        }else {
            long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndSuperiorMemberIdAndTime( lianmengId, memberId, startTime, endTime,finish);
            List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndTime(page, size, lianmengId, memberId, startTime, endTime,finish);
            return new ListPage(list, page, size, (int) amount);
        }

    }

    public void juResultFinish(String gameId,String memberId,String lianmengId) {
        gameHistoricalJuResultDao.juResultFinish(gameId);
        memberDayResultDataService.increaseFinishCount(memberId,lianmengId,1);
    }

    public ListPage findGameHistoricalResultByLianmengIdAndSuperiorIdAndMemberId(int page, int size, String lianmengId,
                                                                      String searchMemberId,String superiorMemberId, long startTime, long endTime) {
        long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime( lianmengId, searchMemberId,superiorMemberId,
                startTime, endTime);
        List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(page, size,
                lianmengId, searchMemberId,superiorMemberId, startTime, endTime);
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }

    public Map queryLianmengDiamondCost(long dayTime,  String lianmengId) {
        long startTime = TimeUtil.getDayStartTime(dayTime);
        Map<Game,Map<String,Integer>> data=new HashMap();
        for (Game value : Game.values()) {
            Map<String,Integer> gameCost =new HashMap<>();
            List<GameHistoricalJuResult> juResultList = gameHistoricalJuResultDao.findGameHistoricalResultByMemberIdAndTime(value,null,lianmengId, startTime, dayTime);
            gameCost.put("juCount",juResultList.size());
            int diamondCost= juResultList.stream().mapToInt(GameHistoricalJuResult::getDiamondCost).sum();
            gameCost.put("diamondCost",diamondCost);
            data.put(value,gameCost);
        }
        return data;
    }


}
