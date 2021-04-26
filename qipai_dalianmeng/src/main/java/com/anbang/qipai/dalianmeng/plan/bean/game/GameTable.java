package com.anbang.qipai.dalianmeng.plan.bean.game;

import com.anbang.qipai.dalianmeng.plan.bean.LianmengWanfa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 游戏桌子
 */
public class GameTable {
    private String id;
    private String no;// 房间6位编号,可循环使用
    private String lianmengId;//联盟id
    private Game game;
    private int playersCount;
    private List<String> players;
    private int currentPanNum;
    private int panCountPerJu;
    private List<GameLaw> laws;
    private ServerGame serverGame;
    private long createTime;
    private long deadlineTime;
    private int yushi;//房费
    private LianmengWanfa wanfa;//玩法
    private String state;// 状态
    private boolean gps;
    private boolean juFinish=false;

    public boolean validateLaws() {
        if (laws != null) {
            Set<String> groupIdSet = new HashSet<>();
            for (GameLaw law : laws) {
                String groupId = law.getMutexGroupId();
                if (groupId != null) {
                    // contain this element,return false
                    if (!groupIdSet.add(groupId)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getCurrentPanNum() {
        return currentPanNum;
    }

    public void setCurrentPanNum(int currentPanNum) {
        this.currentPanNum = currentPanNum;
    }

    public int getPanCountPerJu() {
        return panCountPerJu;
    }

    public void setPanCountPerJu(int panCountPerJu) {
        this.panCountPerJu = panCountPerJu;
    }

    public List<GameLaw> getLaws() {
        return laws;
    }

    public void setLaws(List<GameLaw> laws) {
        this.laws = laws;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }


	public LianmengWanfa getWanfa() {
		return wanfa;
	}

	public void setWanfa(LianmengWanfa wanfa) {
		this.wanfa = wanfa;
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getYushi() {
        return yushi;
    }

    public void setYushi(int yushi) {
        this.yushi = yushi;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isJuFinish() {
        return juFinish;
    }

    public void setJuFinish(boolean juFinish) {
        this.juFinish = juFinish;
    }
}
