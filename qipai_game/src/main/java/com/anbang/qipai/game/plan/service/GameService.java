package com.anbang.qipai.game.plan.service;

import com.anbang.qipai.game.msg.GameServerMsgConstant;
import com.anbang.qipai.game.msg.channel.source.GameServerSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.*;
import com.anbang.qipai.game.plan.dao.*;
import com.anbang.qipai.game.util.TimeUtil;
import com.anbang.qipai.game.web.vo.RobotRoomVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@EnableBinding(GameServerSource.class)
public class GameService {

    public static final int GAME_SERVER_STATE_RUNNINT = 0;
    public static final int GAME_SERVER_STATE_STOP = 1;

    @Autowired
    private GameLawDao gameLawDao;

    @Autowired
    private GameServerDao gameServerDao;

    @Autowired
    private GameRoomDao gameRoomDao;

    @Autowired
    private MemberGameRoomDao memberGameRoomDao;

    @Autowired
    private LawsMutexGroupDao lawsMutexGroupDao;

    @Autowired
    private GameServerSource gameServerSource;

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

    @Autowired
    private GameHistoricalPanResultDao gameHistoricalPanResultDao;

    public void removeGameData(long endTime) {
        new Thread() {
            @Override
            public void run() {
                gameRoomDao.removeByTime(endTime);
                gameHistoricalJuResultDao.removeByTime(endTime);
                gameHistoricalPanResultDao.removeByTime(endTime);
            }
        }.start();
    }

    public GameLaw findGameLaw(Game game, String lawName) {
        return gameLawDao.findByGameAndName(game, lawName);
    }

    public void saveGameRoom(GameRoom gameRoom) {
        gameRoomDao.save(gameRoom);
    }

    public GameServer getRandomGameServer(Game game) throws NoServerAvailableForGameException {
        List<GameServer> allServers = gameServerDao.findServersByState(game, GameService.GAME_SERVER_STATE_RUNNINT);
        if (allServers == null || allServers.isEmpty()) {
            throw new NoServerAvailableForGameException();
        }
        Random r = new Random();
        GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
        return gameServer;
    }

    /**
     * 创建游戏房间
     */
    public GameRoom buildGameRoom(Game game, String memberId, List<String> lawNames) throws IllegalGameLawsException,
            NoServerAvailableForGameException {

        List<GameServer> allServers = gameServerDao.findServersByState(game,
                GameService.GAME_SERVER_STATE_RUNNINT);
        if (allServers == null || allServers.isEmpty()) {
            throw new NoServerAvailableForGameException();
        }
        Random r = new Random();
        GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
        ServerGame serverGame = new ServerGame();
        serverGame.setServer(gameServer);
        GameRoom gameRoom = new GameRoom();
        gameRoom.setServerGame(serverGame);

        List<GameLaw> laws = new ArrayList<>();
        lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(game, name)));
        gameRoom.setLaws(laws);
        if (!gameRoom.validateLaws()) {
            throw new IllegalGameLawsException();
        }

        gameRoom.setCurrentPanNum(0);
        gameRoom.setGame(game);
        if (lawNames.contains("yj")) {
            gameRoom.setPanCountPerJu(1);
        } else if (lawNames.contains("sj")) {
            gameRoom.setPanCountPerJu(4);
        } else if (lawNames.contains("lj")) {
            gameRoom.setPanCountPerJu(6);
        } else if (lawNames.contains("bj")) {
            gameRoom.setPanCountPerJu(8);
        } else if (lawNames.contains("shj")) {
            gameRoom.setPanCountPerJu(10);
        } else if (lawNames.contains("sej")) {
            gameRoom.setPanCountPerJu(12);
        } else if (lawNames.contains("slj")) {
            gameRoom.setPanCountPerJu(16);
        } else if (lawNames.contains("esj")) {
            gameRoom.setPanCountPerJu(20);
        } else if (lawNames.contains("sansj")) {
            gameRoom.setPanCountPerJu(30);
        } else if (lawNames.contains("sisj")) {
            gameRoom.setPanCountPerJu(40);
        } else {
            gameRoom.setPanCountPerJu(4);
        }

        if (lawNames.contains("er")) {
            gameRoom.setPlayersCount(2);
        } else if (lawNames.contains("sanr")) {
            gameRoom.setPlayersCount(3);
        } else if (lawNames.contains("sir")) {
            gameRoom.setPlayersCount(4);
        } else if (lawNames.contains("wr")) {
            gameRoom.setPlayersCount(5);
        } else if (lawNames.contains("lr")) {
            gameRoom.setPlayersCount(6);
        } else if (lawNames.contains("qr")) {
            gameRoom.setPlayersCount(7);
        } else if (lawNames.contains("br")) {
            gameRoom.setPlayersCount(8);
        } else {
            gameRoom.setPlayersCount(4);
        }
        if (lawNames.contains("gps")) {
            gameRoom.setGps(true);
        } else {
            gameRoom.setGps(false);
        }
        gameRoom.setCreateTime(System.currentTimeMillis());
        gameRoom.setDeadlineTime(System.currentTimeMillis() + (2L * 60 * 60 * 1000));
        gameRoom.setCreateMemberId(memberId);
        return gameRoom;
    }


    public void onlineServer(GameServer gameServer) {
        gameServer.setOnlineTime(System.currentTimeMillis());
        gameServerDao.save(gameServer);
    }

    public void offlineServer(String[] gameServerIds) {
        gameServerDao.remove(gameServerIds);
    }

    public void createGameRoom(GameRoom gameRoom) {
        gameRoomDao.save(gameRoom);
        String createMemberId = gameRoom.getCreateMemberId();
        MemberGameRoom mgr = new MemberGameRoom();
        mgr.setGameRoom(gameRoom);
        mgr.setMemberId(createMemberId);
        memberGameRoomDao.save(mgr);
    }

    public void joinGameRoom(GameRoom gameRoom, String memberId) {
        MemberGameRoom mgr = new MemberGameRoom();
        mgr.setGameRoom(gameRoom);
        mgr.setMemberId(memberId);
        memberGameRoomDao.save(mgr);
    }

    public void createGameLaw(GameLaw law) {
        gameLawDao.save(law);
    }

    public void removeGameLaw(String lawId) {
        gameLawDao.remove(lawId);
    }

    public void addLawsMutexGroup(LawsMutexGroup lawsMutexGroup) {
        lawsMutexGroupDao.save(lawsMutexGroup);
    }

    public void removeLawsMutexGroup(String groupId) {
        lawsMutexGroupDao.remove(groupId);
    }

    public GameRoom findRoomOpen(String roomNo) {
        return gameRoomDao.findRoomOpen(roomNo);
    }


    public MemberGameRoom findMemberGameRoom(String memberId, String GameRoomId) {
        return memberGameRoomDao.findByMemberIdAndGameRoomId(memberId, GameRoomId);
    }

    public void yangzhouMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.yangzhouMajiang, serverGameId, playerId);
    }

    public void yizhengMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.yizhengMajiang, serverGameId, playerId);
    }

    public void paodekuaiPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.paodekuai, serverGameId, playerId);
    }

    public void huangshibaPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.huangshiba, serverGameId, playerId);
    }

    public void bohuPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.bohu, serverGameId, playerId);
    }

    public void guandanPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.guandan, serverGameId, playerId);
    }

    public void bijiPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.biji, serverGameId, playerId);
    }

    public void doudizhuPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.doudizhu, serverGameId, playerId);
    }

    public void taizhouMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.taizhouMajiang, serverGameId, playerId);
    }

    public void tianchangxiaohuaPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.tianchangxiaohua, serverGameId, playerId);
    }

    public void taixingMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.taixingMajiang, serverGameId, playerId);
    }

    public void gaoyouMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.gaoyouMajiang, serverGameId, playerId);
    }

    public void hongzhongMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.hongzhongMajiang, serverGameId, playerId);
    }

    public void maanshanMajiangPlayerQuitQame(String serverGameId, String playerId) {
        memberGameRoomDao.remove(Game.maanshanMajiang, serverGameId, playerId);
    }

    public void expireMemberGameRoom(Game game, String serverGameId) {
        memberGameRoomDao.removeExpireRoom(game, serverGameId);
    }

    public List<MemberGameRoom> queryMemberGameRoomForMember(String memberId) {
        return memberGameRoomDao.findMemberGameRoomByMemberId(memberId);
    }

    public List<GameRoom> findExpireGameRoom(long deadlineTime) {
        return gameRoomDao.findExpireGameRoom(deadlineTime, false);
    }

    /**
     * 延长游戏房间
     */
    public void delayGameRoom(Game game, String serverGameId, long deadlineTime) {
        gameRoomDao.updateGameRoomDeadlineTime(game, serverGameId, deadlineTime);
    }

    public void expireGameRoom(List<String> ids) {
        gameRoomDao.updateGameRoomFinished(ids, true);
    }

    public void gameRoomFinished(Game game, String serverGameId) {
        gameRoomDao.updateFinishGameRoom(game, serverGameId, true);
        memberGameRoomDao.removeExpireRoom(game, serverGameId);
    }

    public void panFinished(Game game, String serverGameId, int no, List<String> playerIds) {
        gameRoomDao.updateGameRoomCurrentPanNum(game, serverGameId, no);
        memberGameRoomDao.updateMemberGameRoomCurrentPanNum(game, serverGameId, playerIds, no);
    }

    public GameRoom findRoomByGameAndServerGameGameId(Game game, String serverGameId) {
        return gameRoomDao.findRoomByGameAndServerGameGameId(game, serverGameId);
    }

    public void stopGameServer(List<String> ids) {
        try {
            this.gameServerDao.updateGameServerState(ids, GameService.GAME_SERVER_STATE_STOP);
        } catch (Exception e) {
            CommonMO commonMO = new CommonMO();
            commonMO.setMsg(GameServerMsgConstant.STOP_GAME_SERVERS_FAILED);
            commonMO.setData(ids);
            this.gameServerSource.gameServer().send(MessageBuilder.withPayload(commonMO).build());
        }
    }

    public void recoverGameServer(List<String> ids) {
        try {
            this.gameServerDao.updateGameServerState(ids, GAME_SERVER_STATE_RUNNINT);
        } catch (Throwable e) {
            CommonMO commonMO = new CommonMO();
            commonMO.setMsg(GameServerMsgConstant.RECOVER_GAME_SERVERS_FAILED);
            commonMO.setData(ids);
            this.gameServerSource.gameServer().send(MessageBuilder.withPayload(commonMO).build());
        }
    }

    public List<RobotRoomVO> robotTest() {
        List<GameRoom> gameRooms = gameRoomDao.robotTest();
        if (gameRooms == null) {
            return null;
        }
        List<RobotRoomVO> gameRoomVOList = new ArrayList<>();
        for (GameRoom gameRoom : gameRooms) {
            int number = gameRoom.getPlayersRecord().size();
            int count = gameRoom.getPlayersCount();
            if (number < count) {
                RobotRoomVO robotRoomVO = new RobotRoomVO();
                BeanUtils.copyProperties(gameRoom, robotRoomVO);
                gameRoomVOList.add(robotRoomVO);
            }
        }
        return gameRoomVOList;
    }

    public void removeAMonthAgo(long aMonthAgoTime) {
        gameRoomDao.removeAMonthAgo(aMonthAgoTime);
    }

}
