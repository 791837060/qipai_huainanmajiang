package com.anbang.qipai.dalianmeng.plan.service;

import com.anbang.qipai.dalianmeng.cqrs.c.service.PlayBackCodeCmdService;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.PlayBackDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalPanResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalPanResultExpand;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameJuPlayerResult;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalPanResultDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;


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
        List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndMemberIdAndTime(page, size, game,
                lianmengId, memberId, startTime, endTime);
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

    public Map listTotalScore(String memberId, long startTime, long endTime) {
        Integer juCount = 0;
        Double dayScore = 0d;
        Integer dayingjiaCount = 0;

        List<GameHistoricalJuResult> juResultList = gameHistoricalJuResultDao.findGameHistoricalResultByMemberIdAndTime(null, memberId, null, startTime, endTime);
        if (juResultList != null) {
            juCount = juResultList.size();
            for (GameHistoricalJuResult list : juResultList) {
                if (!StringUtils.isEmpty(memberId) && memberId.equals(list.getDayingjiaId())) {
                    dayingjiaCount = dayingjiaCount + 1;
                }
                if (list.getPlayerResultList() != null) {
                    for (GameJuPlayerResult juPlayer : list.getPlayerResultList()) {
                        if (memberId.equals(juPlayer.playerId())) {
                            dayScore = dayScore + juPlayer.totalScore();
                        }
                    }
                }
            }
        }
        Map data = new HashMap<>();
        data.put("juCount", juCount);
        data.put("dayScore", dayScore);
        data.put("dayingjiaCount", dayingjiaCount);
        return data;
    }
    public ListPage findGameHistoricalResultByLianmengIdAndSuperiorIdAndMemberId(int page, int size, String lianmengId,
                                                                                 String searchMemberId,String superiorMemberId, long startTime, long endTime) {
        if (searchMemberId==null){
            List<String> memberIdList = memberLianmengDboDao.listIdsByLianmengIdAndSuperiorMemberId(lianmengId, superiorMemberId);
            long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime( lianmengId, memberIdList,
                    startTime, endTime);
            List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(page, size,
                    lianmengId, memberIdList, startTime, endTime);
            return new ListPage(list, page, size, (int) amount);
        }else {
            List<String> memberIdList = new ArrayList<>();
            memberIdList.add(searchMemberId);
            long amount = gameHistoricalJuResultDao.getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime( lianmengId, memberIdList,
                    startTime, endTime);
            List<GameHistoricalJuResult> list = gameHistoricalJuResultDao.findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(page, size,
                    lianmengId, memberIdList, startTime, endTime);
            return new ListPage(list, page, size, (int) amount);
        }
    }

}
