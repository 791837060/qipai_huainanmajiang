package com.anbang.qipai.admin.web.vo.juprize;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;
import com.anbang.qipai.admin.web.vo.EnumConversion;

import java.lang.invoke.SwitchPoint;

/**
 * @Description:
 */
public class JuPrizeReleaseVo {
    private Game game;
    private boolean release;
    private String gameName;

    public JuPrizeReleaseVo(JuPrizeRelease juPrizeRelease) {
        this.game = juPrizeRelease.getGame();
        this.release = juPrizeRelease.isRelease();
        this.gameName = EnumConversion.getGameName(game);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isRelease() {
        return release;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
