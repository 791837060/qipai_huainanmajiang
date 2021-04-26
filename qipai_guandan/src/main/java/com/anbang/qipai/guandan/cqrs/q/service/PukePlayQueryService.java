package com.anbang.qipai.guandan.cqrs.q.service;

import com.anbang.qipai.guandan.cqrs.c.domain.GameInfoPlayerViewFilter;
import com.anbang.qipai.guandan.cqrs.c.domain.PanActionFramePlayerViewFilter;
import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.domain.result.*;
import com.anbang.qipai.guandan.cqrs.c.domain.state.StartChaodi;
import com.anbang.qipai.guandan.cqrs.c.domain.state.VoteNotPassWhenChaodi;
import com.anbang.qipai.guandan.cqrs.c.domain.state.VotingWhenChaodi;
import com.anbang.qipai.guandan.cqrs.q.dao.*;
import com.anbang.qipai.guandan.cqrs.q.dbo.*;
import com.anbang.qipai.guandan.plan.bean.PlayerInfo;
import com.anbang.qipai.guandan.plan.dao.PlayerInfoDao;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;
import com.dml.shuangkou.pan.PanActionFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PukePlayQueryService {

    @Autowired
    private PukeGameDboDao pukeGameDboDao;

    @Autowired
    private PlayerInfoDao playerInfoDao;

    @Autowired
    private PanResultDboDao panResultDboDao;

    @Autowired
    private JuResultDboDao juResultDboDao;

    @Autowired
    private PanActionFrameDboDao panActionFrameDboDao;

    @Autowired
    private PukeGameInfoDboDao pukeGameInfoDboDao;

    @Autowired
    private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

    @Autowired
    private GameLatestPukeGameInfoDboDao gameLatestPukeGameInfoDboDao;

    @Autowired
    private PukeGamePlayerChaodiDboDao pukeGamePlayerChaodiDboDao;

    private PanActionFramePlayerViewFilter pvFilter = new PanActionFramePlayerViewFilter();

    private GameInfoPlayerViewFilter gvFilter = new GameInfoPlayerViewFilter();

    public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId, String playerId) throws Exception {
        PukeGameDbo pukeGameDbo = pukeGameDboDao.findById(gameId);
        if (!(pukeGameDbo.getState().name().equals(Playing.name)
                || pukeGameDbo.getState().name().equals(VotingWhenPlaying.name)
                || pukeGameDbo.getState().name().equals(VoteNotPassWhenPlaying.name)
                || pukeGameDbo.getState().name().equals(StartChaodi.name)
                || pukeGameDbo.getState().name().equals(VotingWhenChaodi.name)
                || pukeGameDbo.getState().name().equals(VoteNotPassWhenChaodi.name))) {
            throw new Exception("game not playing");
        }

        GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
        return pvFilter.filter(frame, playerId, false);
    }

    public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId)  {
        GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
        if (frame != null) {
            return frame.getPanActionFrame();
        } else {
            return null;
        }
    }

    public PukeGameInfoDbo findAndFilterCurrentGameInfoForPlayer(String gameId, String playerId) throws Exception {
        PukeGameDbo pukeGameDbo = pukeGameDboDao.findById(gameId);
        if (!(pukeGameDbo.getState().name().equals(Playing.name)
                || pukeGameDbo.getState().name().equals(VotingWhenPlaying.name)
                || pukeGameDbo.getState().name().equals(VoteNotPassWhenPlaying.name)
                || pukeGameDbo.getState().name().equals(StartChaodi.name)
                || pukeGameDbo.getState().name().equals(VotingWhenChaodi.name)
                || pukeGameDbo.getState().name().equals(VoteNotPassWhenChaodi.name))) {
            throw new Exception("game not playing");
        }

        GameLatestPukeGameInfoDbo info = gameLatestPukeGameInfoDboDao.findById(gameId);
        PukeGameInfoDbo pukeGameInfoDbo = gvFilter.filter(playerId, info);
        return pukeGameInfoDbo;
    }

    public void readyForGame(ReadyForGameResult readyForGameResult) {
        PukeGameValueObject pukeGame = readyForGameResult.getPukeGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        if (pukeGame.getState().name().equals(StartChaodi.name)) {
            PukeGamePlayerChaodiDbo dbo = new PukeGamePlayerChaodiDbo();
            dbo.setGameId(pukeGame.getId());
            dbo.setPanNo(pukeGame.getPanNo());
            dbo.setPlayerChaodiStateMap(pukeGame.getPlayerChaodiStateMap());
            dbo.setCreateTime(System.currentTimeMillis());
            pukeGamePlayerChaodiDboDao.addPukeGamePlayerChaodiDbo(dbo);
        }

        if (readyForGameResult.getFirstActionFrame() != null) {
            PanActionFrame panActionFrame = readyForGameResult.getFirstActionFrame();
            gameLatestPanActionFrameDboDao.save(pukeGame.getId(), panActionFrame);
            // 记录一条Frame，回放的时候要做
            String gameId = pukeGame.getId();
            int panNo = panActionFrame.getPanAfterAction().getNo();
            int actionNo = panActionFrame.getNo();
            PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
            panActionFrameDbo.setPanActionFrame(panActionFrame);
            panActionFrameDboDao.save(panActionFrameDbo);

            PukeGameInfoDbo pukeGameInfoDbo = new PukeGameInfoDbo(pukeGame, playerInfoMap, actionNo);
            gameLatestPukeGameInfoDboDao.save(gameId, pukeGameInfoDbo);
            pukeGameInfoDboDao.save(pukeGameInfoDbo);
        }
    }

    public void action(PukeActionResult pukeActionResult) {

        PukeGameValueObject pukeGame = pukeActionResult.getPukeGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        String gameId = pukeGameDbo.getId();
        PanActionFrame panActionFrame = pukeActionResult.getPanActionFrame();
        gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
        // 记录一条Frame，回放的时候要做
        int panNo = panActionFrame.getPanAfterAction().getNo();
        int actionNo = panActionFrame.getNo();
        PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
        panActionFrameDbo.setPanActionFrame(panActionFrame);
        panActionFrameDboDao.save(panActionFrameDbo);

        PukeGameInfoDbo pukeGameInfoDbo = new PukeGameInfoDbo(pukeGame, playerInfoMap, actionNo);
        gameLatestPukeGameInfoDboDao.save(gameId, pukeGameInfoDbo);
        pukeGameInfoDboDao.save(pukeGameInfoDbo);
        // 盘出结果的话要记录结果
        GuandanPanResult GuandanPanResult = pukeActionResult.getPanResult();
        if (GuandanPanResult != null) {
            PanResultDbo panResultDbo = new PanResultDbo(gameId, GuandanPanResult);
            panResultDbo.setPanActionFrame(panActionFrame);
            panResultDbo.setPukeGameInfoDbo(pukeGameInfoDbo);
            panResultDboDao.save(panResultDbo);
            if (pukeActionResult.getJuResult() != null) {// 一切都结束了
                // 要记录局结果
                JuResultDbo juResultDbo = new JuResultDbo(gameId, panResultDbo, pukeActionResult.getJuResult());
                juResultDboDao.save(juResultDbo);
            }
        }
    }

    public void chaodi(ChaodiResult chaodiResult) {

        PukeGameValueObject pukeGame = chaodiResult.getPukeGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        String gameId = pukeGame.getId();
        int panNo = pukeGame.getPanNo();
        pukeGamePlayerChaodiDboDao.updatePukeGamePlayerChaodiDbo(gameId, panNo, pukeGame.getPlayerChaodiStateMap());
        PanActionFrame panActionFrame = chaodiResult.getPanActionFrame();
        if (panActionFrame != null) {
            gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
            // 记录一条Frame，回放的时候要做
            int actionNo = panActionFrame.getNo();
            PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
            panActionFrameDbo.setPanActionFrame(panActionFrame);
            panActionFrameDboDao.save(panActionFrameDbo);

            PukeGameInfoDbo pukeGameInfoDbo = new PukeGameInfoDbo(pukeGame, playerInfoMap, actionNo);
            gameLatestPukeGameInfoDboDao.save(gameId, pukeGameInfoDbo);
            pukeGameInfoDboDao.save(pukeGameInfoDbo);
        }
        // 盘出结果的话要记录结果
        GuandanPanResult GuandanPanResult = chaodiResult.getPanResult();
        if (GuandanPanResult != null) {
            PanResultDbo panResultDbo = new PanResultDbo(gameId, GuandanPanResult);
            panResultDbo.setPanActionFrame(panActionFrame);
            panResultDboDao.save(panResultDbo);
            if (chaodiResult.getJuResult() != null) {// 一切都结束了
                // 要记录局结果
                JuResultDbo juResultDbo = new JuResultDbo(gameId, panResultDbo, chaodiResult.getJuResult());
                juResultDboDao.save(juResultDbo);
            }
        }
    }

    public void readyToNextPan(ReadyToNextPanResult readyToNextPanResult) {
        PukeGameValueObject pukeGame = readyToNextPanResult.getPukeGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((pid) -> playerInfoMap.put(pid, playerInfoDao.findById(pid)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);
        if (pukeGame.getState().name().equals(StartChaodi.name)) {
            PukeGamePlayerChaodiDbo dbo = new PukeGamePlayerChaodiDbo();
            dbo.setGameId(pukeGame.getId());
            dbo.setPanNo(pukeGame.getPanNo());
            dbo.setPlayerChaodiStateMap(pukeGame.getPlayerChaodiStateMap());
            dbo.setCreateTime(System.currentTimeMillis());
            pukeGamePlayerChaodiDboDao.addPukeGamePlayerChaodiDbo(dbo);
        }

        if (readyToNextPanResult.getFirstActionFrame() != null) {
            String gameId = pukeGameDbo.getId();
            PanActionFrame panActionFrame = readyToNextPanResult.getFirstActionFrame();
            gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
            // 记录一条Frame，回放的时候要做
            int panNo = readyToNextPanResult.getFirstActionFrame().getPanAfterAction().getNo();
            int actionNo = readyToNextPanResult.getFirstActionFrame().getNo();
            PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
            panActionFrameDbo.setPanActionFrame(readyToNextPanResult.getFirstActionFrame());
            panActionFrameDboDao.save(panActionFrameDbo);

            PukeGameInfoDbo pukeGameInfoDbo = new PukeGameInfoDbo(pukeGame, playerInfoMap, actionNo);
            gameLatestPukeGameInfoDboDao.save(gameId, pukeGameInfoDbo);
            pukeGameInfoDboDao.save(pukeGameInfoDbo);
        }

    }

    public PanResultDbo findPanResultDbo(String gameId, int panNo) {
        return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
    }

    public JuResultDbo findJuResultDbo(String gameId) {
        return juResultDboDao.findByGameId(gameId);
    }

    public PukeGamePlayerChaodiDbo findLastPlayerChaodiDboByGameId(String gameId) {
        return pukeGamePlayerChaodiDboDao.findLastByGameId(gameId);
    }

    public PukeGamePlayerChaodiDbo findPlayerChaodiDboByGameIdAndPanNo(String gameId, int panNo) {
        return pukeGamePlayerChaodiDboDao.findByGameIdAndPanNo(gameId, panNo);
    }

    public List<PanActionFrameDbo> findPanActionFrameDboForBackPlay(String gameId, int panNo) {
        return panActionFrameDboDao.findByGameIdAndPanNo(gameId, panNo);
    }

    public List<PukeGameInfoDbo> findGameInfoDboForBackPlay(String gameId, int panNo) {
        return pukeGameInfoDboDao.findByGameIdAndPanNo(gameId, panNo);
    }
}
