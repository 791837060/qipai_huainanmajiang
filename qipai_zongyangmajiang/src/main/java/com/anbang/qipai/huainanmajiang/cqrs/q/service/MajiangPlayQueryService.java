package com.anbang.qipai.huainanmajiang.cqrs.q.service;

import com.anbang.qipai.huainanmajiang.cqrs.c.domain.*;
import com.anbang.qipai.huainanmajiang.cqrs.q.dao.*;
import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.*;
import com.anbang.qipai.huainanmajiang.plan.bean.PlayerInfo;
import com.anbang.qipai.huainanmajiang.plan.dao.PlayerInfoDao;
import com.dml.majiang.pan.frame.LiangangangPanActionFramePlayerViewFilter;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MajiangPlayQueryService {

    @Autowired
    private MajiangGameDboDao majiangGameDboDao;

    @Autowired
    private PlayerInfoDao playerInfoDao;

    @Autowired
    private JuResultDboDao juResultDboDao;

    @Autowired
    private PanResultDboDao panResultDboDao;

    @Autowired
    private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

    @Autowired
    private PanActionFrameDboDao panActionFrameDboDao;

    private LiangangangPanActionFramePlayerViewFilter pvFilter = new LiangangangPanActionFramePlayerViewFilter();

    public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId, String playerId) throws Exception {
        MajiangGameDbo majiangGameDbo = majiangGameDboDao.findById(gameId);
        if (!(majiangGameDbo.getState().name().equals(Playing.name) || majiangGameDbo.getState().name().equals(VotingWhenPlaying.name) || majiangGameDbo.getState().name().equals(VoteNotPassWhenPlaying.name))) {
            throw new Exception("game not playing");
        }

        PanActionFrameDbo latestPanActionFrame = panActionFrameDboDao.findLatestPanActionFrame(gameId);
        PanActionFrame panActionFrame = latestPanActionFrame.getPanActionFrame();
        pvFilter.filter(panActionFrame, playerId);
        return panActionFrame;
    }

    public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId) {
        GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
        if (frame != null) {
            return frame.getPanActionFrame();
        } else {
            return null;
        }
    }

    public int findCurrentPanLastestActionNo(String gameId) {
        GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
        if (frame == null) {
            return 0;
        }
        PanActionFrame panActionFrame = frame.getPanActionFrame();
        return panActionFrame.getNo();
    }

    public void readyForGame(ReadyForGameResult readyForGameResult) {
        MajiangGameValueObject majiangGame = readyForGameResult.getMajiangGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        majiangGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        MajiangGameDbo majiangGameDbo = new MajiangGameDbo(majiangGame, playerInfoMap);
        majiangGameDboDao.save(majiangGameDbo);

        if (majiangGame.getState().name().equals(Playing.name)) {
            PanActionFrame panActionFrame = readyForGameResult.getFirstActionFrame();
            gameLatestPanActionFrameDboDao.save(majiangGame.getId(), panActionFrame);
            // 记录一条Frame，回放的时候要做
            String gameId = majiangGame.getId();
            int panNo = panActionFrame.getPanAfterAction().getNo();
            int actionNo = panActionFrame.getNo();
            PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
            panActionFrameDbo.setPanActionFrame(panActionFrame);
            panActionFrameDboDao.save(panActionFrameDbo);
        }
    }

    public void readyToNextPan(ReadyToNextPanResult readyToNextPanResult) {

        MajiangGameValueObject majiangGame = readyToNextPanResult.getMajiangGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        majiangGame.allPlayerIds().forEach((pid) -> playerInfoMap.put(pid, playerInfoDao.findById(pid)));
        MajiangGameDbo majiangGameDbo = new MajiangGameDbo(majiangGame, playerInfoMap);
        majiangGameDboDao.save(majiangGameDbo);

        if (readyToNextPanResult.getFirstActionFrame() != null) {
            gameLatestPanActionFrameDboDao.save(majiangGame.getId(),
                    readyToNextPanResult.getFirstActionFrame());
            // 记录一条Frame，回放的时候要做
            String gameId = majiangGame.getId();
            int panNo = readyToNextPanResult.getFirstActionFrame().getPanAfterAction().getNo();
            int actionNo = readyToNextPanResult.getFirstActionFrame().getNo();
            PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
            panActionFrameDbo.setPanActionFrame(readyToNextPanResult.getFirstActionFrame());
            panActionFrameDboDao.save(panActionFrameDbo);
        }
    }

    public JuResultDbo findJuResultDbo(String gameId) {
        return juResultDboDao.findByGameId(gameId);
    }

    public PanResultDbo findPanResultDbo(String gameId, int panNo) {
        return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
    }

    public void action(MajiangActionResult majiangActionResult) {
        MajiangGameValueObject majiangGame = majiangActionResult.getMajiangGame();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        majiangGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        MajiangGameDbo majiangGameDbo = new MajiangGameDbo(majiangGame, playerInfoMap);
        majiangGameDboDao.save(majiangGameDbo);

        String gameId = majiangActionResult.getMajiangGame().getId();
        PanActionFrame panActionFrame = majiangActionResult.getPanActionFrame();
        gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
        // 记录一条Frame，回放的时候要做
        int panNo = panActionFrame.getPanAfterAction().getNo();
        int actionNo = panActionFrame.getNo();
        PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
        panActionFrameDbo.setPanActionFrame(panActionFrame);
        panActionFrameDboDao.save(panActionFrameDbo);

        // 盘出结果的话要记录结果
        ZongyangMajiangPanResult tuidaohuPanResult = majiangActionResult.getPanResult();
        if (tuidaohuPanResult != null) {
            PanResultDbo panResultDbo = new PanResultDbo(gameId, tuidaohuPanResult);
            panResultDbo.setPanActionFrame(panActionFrame);
            panResultDboDao.save(panResultDbo);
            if (majiangActionResult.getJuResult() != null) {// 一切都结束了
                // 要记录局结果
                JuResultDbo juResultDbo = new JuResultDbo(gameId, panResultDbo, majiangActionResult.getJuResult());
                juResultDboDao.save(juResultDbo);
            }
        }
    }

    public void xipai(MajiangGameValueObject majiangGame) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        majiangGame.allPlayerIds().forEach((pid) -> playerInfoMap.put(pid, playerInfoDao.findById(pid)));
        MajiangGameDbo majiangGameDbo = new MajiangGameDbo(majiangGame, playerInfoMap);
        majiangGameDboDao.save(majiangGameDbo);
    }

    public List<PanActionFrameDbo> findPanActionFrameDboForBackPlay(String gameId, int panNo) {
        return panActionFrameDboDao.findByGameIdAndPanNo(gameId, panNo);
    }
}
