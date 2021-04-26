package com.anbang.qipai.dalianmeng.plan.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.AllianceDbo;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengWanfa;
import com.anbang.qipai.dalianmeng.plan.bean.game.*;
import com.anbang.qipai.dalianmeng.plan.dao.*;
import com.anbang.qipai.dalianmeng.web.vo.GameTableVO;
import com.anbang.qipai.dalianmeng.web.vo.PlayerVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayService {
    @Autowired
    private GameTableDao gameTableDao;
    @Autowired
    private GameMemberTableDao gameMemberTableDao;
    @Autowired
    private GameServerDao gameServerDao;
    @Autowired
    private GameLawDao gameLawDao;
    @Autowired
    private MemberDboDao memberDboDao;
    @Autowired
    private LianmengWanfaDao lianmengWanfaDao;
    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;


    public GameServer getRandomGameServer(Game game) throws NoServerAvailableForGameException {
        List<GameServer> allServers = gameServerDao.findServersByState(game, GameService.GAME_SERVER_STATE_RUNNING);
        if (allServers == null || allServers.isEmpty()) {
            throw new NoServerAvailableForGameException();
        }
        Random r = new Random();
        GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
        return gameServer;
    }

    /**
     * 创建游戏桌子
     */
    public GameTable buildGameTable(String memberId, String lianmengId, Game game, LianmengWanfa wanfa)
            throws IllegalGameLawsException, NoServerAvailableForGameException {
        List<GameServer> allServers = gameServerDao.findServersByState(game,
                GameService.GAME_SERVER_STATE_RUNNING);
        if (allServers == null || allServers.isEmpty()) {
            throw new NoServerAvailableForGameException();
        }
        Random r = new Random();
        GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
        ServerGame serverGame = new ServerGame();
        serverGame.setServer(gameServer);
        GameTable gameTable = new GameTable();
        gameTable.setWanfa(wanfa);
        gameTable.setLianmengId(lianmengId);
        gameTable.setServerGame(serverGame);
        gameTable.setGame(game);
        gameTable.setState(GameTableStateConfig.WAITING);
        List<GameLaw> laws = new ArrayList<>();
        List<String> lawNames = wanfa.getLaws();
        lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(game, name)));
        if (lawNames.contains("gps")){
            gameTable.setGps(true);
        }else {
            gameTable.setGps(false);
        }
        gameTable.setLaws(laws);
        if (!gameTable.validateLaws()) {
            throw new IllegalGameLawsException();
        }
        if (lawNames.contains("sj")) {
            gameTable.setPanCountPerJu(4);
        } else if (lawNames.contains("lj")) {
            gameTable.setPanCountPerJu(6);
        } else if (lawNames.contains("bj")) {
            gameTable.setPanCountPerJu(8);
        } else if (lawNames.contains("shj")) {
            gameTable.setPanCountPerJu(10);
        } else if (lawNames.contains("sej")) {
            gameTable.setPanCountPerJu(12);
        } else if (lawNames.contains("swj")) {
            gameTable.setPanCountPerJu(15);
        } else if (lawNames.contains("slj")) {
            gameTable.setPanCountPerJu(16);
        } else if (lawNames.contains("esj")) {
            gameTable.setPanCountPerJu(20);
        } else {
            gameTable.setPanCountPerJu(4);
        }

        if (lawNames.contains("er")) {
            gameTable.setPlayersCount(2);
        } else if (lawNames.contains("sanr")) {
            gameTable.setPlayersCount(3);
        } else if (lawNames.contains("sir")) {
            gameTable.setPlayersCount(4);
        } else if (lawNames.contains("wr")) {
            gameTable.setPlayersCount(5);
        } else if (lawNames.contains("lr")) {
            gameTable.setPlayersCount(6);
        } else if (lawNames.contains("qr")) {
            gameTable.setPlayersCount(7);
        } else if (lawNames.contains("br")) {
            gameTable.setPlayersCount(8);
        } else {
            gameTable.setPlayersCount(4);
        }
        gameTable.setCreateTime(System.currentTimeMillis());
        gameTable.setDeadlineTime(System.currentTimeMillis() + 60 * 60 * 1000);
        return gameTable;
    }

    public List<GameTableVO> findGameTableByLianmengId(String lianmengId, int page, int size, AllianceDbo allianceDbo) {
        List<GameTableVO> tableList = new ArrayList<>();
        List<GameTable> gameTableList = gameTableDao.findGameTableByLianmengId(lianmengId, page, size);
        for (GameTable gameTable : gameTableList) {
            GameTableVO table = new GameTableVO();
            table.setLianmengId(gameTable.getLianmengId());
            table.setTableName(gameTable.getWanfa().getWanfaName());
            table.setWanfaId(gameTable.getWanfa().getId());
            table.setGame(gameTable.getGame());
            table.setLaws(gameTable.getLaws());
            table.setNo(gameTable.getNo());
            table.setCurrentPanNum(gameTable.getCurrentPanNum());
            table.setPanCountPerJu(gameTable.getPanCountPerJu());
            table.setPlayersCount(gameTable.getPlayersCount());
            table.setCurrentPanNum(gameTable.getCurrentPanNum());
            table.setState(gameTable.getState());
            table.setCreateTime(gameTable.getCreateTime());
            List<PlayerVO> playerList = new ArrayList<>();
            table.setPlayerList(playerList);
            List<GameMemberTable> memberGameTableList = gameMemberTableDao.findMemberGameTableByGameAndServerGameId(
                    gameTable.getGame(), gameTable.getServerGame().getGameId());
            for (GameMemberTable memberGameTable : memberGameTableList) {
                PlayerVO player = new PlayerVO();
                if (allianceDbo.isIdHide()) {
                    player.setMemberId("");
                } else {
                    player.setMemberId(memberGameTable.getMemberId());
                }
                if (allianceDbo.isNicknameHide()) {
                    player.setNickname("");
                    player.setHeadimgurl("http://qiniu.3cscy.com/0547d326456025bdfc703238778e15e.png");
                } else {
                    player.setNickname(memberGameTable.getNickname());
                    player.setHeadimgurl(memberGameTable.getHeadimgurl());
                }


                player.setOnlineState(memberLianmengDboDao.findByMemberIdAndLianmengId(memberGameTable.getMemberId(), lianmengId).getOnlineState());
                playerList.add(player);
            }
            tableList.add(table);
        }
        return tableList;
    }

    public void createGameTable(GameTable gameTable, String memberId) {
        List<String> players = new ArrayList<>();
        players.add(memberId);
        gameTable.setPlayers(players);
        gameTableDao.save(gameTable);
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        GameMemberTable gameMemberTable = new GameMemberTable();
        gameMemberTable.setMemberId(memberId);
        gameMemberTable.setNickname(memberDbo.getNickname());
        gameMemberTable.setHeadimgurl(memberDbo.getHeadimgurl());
        gameMemberTable.setGameTable(gameTable);
        gameMemberTableDao.save(gameMemberTable);
    }

    public GameTable findOpenGameTable(String no) {
        return gameTableDao.findTableOpen(no);
    }

    public GameTable findTableByGameAndServerGameGameId(Game game, String serverGameId) {
        return gameTableDao.findTableByGameAndServerGameGameId(game, serverGameId);
    }

    public void updateGameMemberTable(String serverGameId) {
        gameTableDao.updateGameMemberTable(serverGameId);
    }

    public GameMemberTable findMemberGameTable(String memberId, String gameTableId) {
        return gameMemberTableDao.findByMemberIdAndGameTableId(memberId, gameTableId);
    }

    public void joinGameTable(GameTable gameTable, String memberId) {
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        GameMemberTable gameMemberTable = new GameMemberTable();
        gameMemberTable.setMemberId(memberId);
        gameMemberTable.setNickname(memberDbo.getNickname());
        gameMemberTable.setHeadimgurl(memberDbo.getHeadimgurl());
        gameMemberTable.setGameTable(gameTable);
        gameMemberTableDao.save(gameMemberTable);

        gameTable.getPlayers().add(memberId);
        gameTableDao.save(gameTable);
    }

    public List<GameTable> findExpireGameTable(long deadlineTime) {
        List<GameTable> tableList = gameTableDao.findExpireGameTable(deadlineTime, GameTableStateConfig.PLAYING);
        tableList.addAll(gameTableDao.findExpireGameTable(deadlineTime, GameTableStateConfig.WAITING));
        return tableList;
    }

    public void gamePlayerQuitQame(Game game, String serverGameId, String playerId) {
        gameMemberTableDao.remove(game, serverGameId, playerId);
        if (gameMemberTableDao.countMemberByGameAndServerGameId(game, serverGameId) == 0) {
            gameTableDao.updateStateGameTable(game, serverGameId, GameTableStateConfig.FINISH);
        }
        GameTable gameTable = gameTableDao.findTableByGameAndServerGameGameId(game, serverGameId);
        gameTable.getPlayers().remove(playerId);
        gameTableDao.save(gameTable);
    }

    public void gameTableFinished(Game game, String serverGameId) {
        gameTableDao.updateStateGameTable(game, serverGameId, GameTableStateConfig.FINISH);
        GameTable gameTable = gameTableDao.findTableByGameAndServerGameGameId(game, serverGameId);
        gameMemberTableDao.removeExpireRoom(game, serverGameId);
    }

    public void panFinished(Game game, String serverGameId, int no, List<String> playerIds) {
        gameTableDao.updateGameTableCurrentPanNum(game, serverGameId, no);
        GameTable gameTable = gameTableDao.findTableByGameAndServerGameGameId(game, serverGameId);
//		gameTableMsgService.updateGameTable(gameTable);
        gameMemberTableDao.updateMemberGameTableCurrentPanNum(game, serverGameId, playerIds, no);
    }

    public void gameTablePlaying(Game game, String serverGameId) {
        gameTableDao.updateStateGameTable(game, serverGameId, GameTableStateConfig.PLAYING);
        GameTable gameTable = gameTableDao.findTableByGameAndServerGameGameId(game, serverGameId);
    }

    public ListPage queryGameTableByMemberIdAndLianmengIdAndGameAndTime(int page, int size, String memberId, String lianmengId, Game game,
                                                                        long startTime, long endTime,String no,String state) {
        int amount = (int) gameTableDao.countGameTableByMemberIdAndLianmengIdAndGameAndTime(memberId, lianmengId, startTime, endTime,no,state);
        List<GameTable> gameTableList = gameTableDao.findGameTableByMemberIdAndLianmengIdAndGameAndTime(page, size, memberId, lianmengId, game, startTime, endTime,no,state);
        return new ListPage(gameTableList, page, size, amount);
    }

    /**
     * 延长游戏房间
     */
    public void delayGameTable(Game game, String serverGameId, long deadlineTime) {
        gameTableDao.updateGameTableDeadlineTime(game, serverGameId, deadlineTime);
    }

    public void deleteGameTableByNo(String roomNo) {
        gameTableDao.deleteGameTableByNo(roomNo);
        gameMemberTableDao.deleteGameTableByNo(roomNo);
    }

    /**
     * 在桌子上的玩家
     *
     * @param memberId 玩家ID
     * @return
     */
    public GameMemberTable findByMemberId(String memberId) {
        return gameMemberTableDao.findByMemberId(memberId);
    }

    public GameMemberTable findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        return gameMemberTableDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }

}
