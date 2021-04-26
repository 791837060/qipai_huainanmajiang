package com.dml.doudizhu.ju;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.gameprocess.CurrentPanFinishiDeterminer;
import com.dml.doudizhu.gameprocess.JuFinishiDeterminer;
import com.dml.doudizhu.pai.waihao.WaihaoGenerator;
import com.dml.doudizhu.pan.CurrentPanResultBuilder;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.doudizhu.pan.PanResult;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.doudizhu.player.action.ActionStatisticsListenerManager;
import com.dml.doudizhu.player.action.da.AllKedaPaiSolutionsGenerator;
import com.dml.doudizhu.player.action.da.DaAction;
import com.dml.doudizhu.player.action.da.DaActionStatisticsListener;
import com.dml.doudizhu.player.action.da.DaPaiSolutionsTipsFilter;
import com.dml.doudizhu.player.action.da.DianShuZuYaPaiSolutionCalculator;
import com.dml.doudizhu.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.doudizhu.player.action.da.ZaDanYaPaiSolutionCalculator;
import com.dml.doudizhu.player.action.guo.GuoAction;
import com.dml.doudizhu.player.action.guo.GuoActionStatisticsListener;
import com.dml.doudizhu.preparedapai.avaliablepai.AvaliablePaiFiller;
import com.dml.doudizhu.preparedapai.dipai.DipaiDeterminer;
import com.dml.doudizhu.preparedapai.dizhu.DizhuDeterminer;
import com.dml.doudizhu.preparedapai.fapai.FaPaiStrategy;
import com.dml.doudizhu.preparedapai.lipai.ShoupaiSortStrategy;
import com.dml.doudizhu.preparedapai.luanpai.LuanPaiStrategy;
import com.dml.doudizhu.preparedapai.position.MenfengDeterminer;
import com.dml.doudizhu.preparedapai.xianda.XiandaDeterminer;

public class Ju {

	private Pan currentPan;
	private List<PanResult> finishedPanResultList = new ArrayList<>();
	private JuResult juResult;

	private CurrentPanFinishiDeterminer panFinishiDeterminer;
	private JuFinishiDeterminer juFinishiDeterminer;
	private AvaliablePaiFiller avaliablePaiFiller;
	private LuanPaiStrategy luanPaiStrategyForFirstPan;
	private LuanPaiStrategy luanPaiStrategyForNextPan;
	private FaPaiStrategy faPaiStrategy;
	private DizhuDeterminer dizhuDeterminer;
	private DipaiDeterminer dipaiDeterminer;
	private MenfengDeterminer menfengDeterminerForFirstPan;
	private MenfengDeterminer menfengDeterminerForNextPan;
	private XiandaDeterminer xiandaDeterminer;
	private ShoupaiSortStrategy shoupaiSortStrategy;

	private WaihaoGenerator waihaoGenerator;
	private ActionStatisticsListenerManager actionStatisticsListenerManager = new ActionStatisticsListenerManager();

	private CurrentPanResultBuilder currentPanResultBuilder;
	private JuResultBuilder juResultBuilder;

	private AllKedaPaiSolutionsGenerator allKedaPaiSolutionsGenerator;
	private DianShuZuYaPaiSolutionCalculator dianShuZuYaPaiSolutionCalculator;
	private ZaDanYaPaiSolutionCalculator zaDanYaPaiSolutionCalculator;

	private YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter;
	private DaPaiSolutionsTipsFilter daPaiSolutionsTipsFilter;

	public void addDaListener(DaActionStatisticsListener daActionStatisticsListener) {
		actionStatisticsListenerManager.addDaListener(daActionStatisticsListener);
	}

	public void addGuoListener(GuoActionStatisticsListener guoActionStatisticsListener) {
		actionStatisticsListenerManager.addGuoListener(guoActionStatisticsListener);
	}

	public void startFirstPan(List<String> allPlayerIds, long startTime) throws Exception {
		currentPan = new Pan();
		currentPan.setNo(1);
		allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
		menfengDeterminerForFirstPan.determineMenfengForPlayer(this);
		avaliablePaiFiller.fillAvaliablePai(this);

		// 先乱牌，再发牌，再理牌
		luanPaiStrategyForFirstPan.luanpai(this);
		faPaiStrategy.fapai(this);
		currentPan.getDoudizhuPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));

		currentPan.recordPanActionFrame(null, startTime);
	}

	public void startPlaying(long currentTime) throws Exception {
		// 重新理牌
		currentPan.getDoudizhuPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));
		// 谁第一个打牌
		String dapaiPlayerId = xiandaDeterminer.determineToXiandaplayer(this);
		DoudizhuPlayer player = currentPan.findPlayerById(dapaiPlayerId);
		player.putYaPaiSolutionCandidates(
				allKedaPaiSolutionsGenerator.generateAllKedaPaiSolutions(player.getAllShoupai()));

		// 提示
		player.generateDaPaiSolutionsForTips(daPaiSolutionsTipsFilter);

		currentPan.updateActionPositionByActionPlayer(dapaiPlayerId);

		currentPan.recordPanActionFrame(null, currentTime);
	}

	public void startNextPan() throws Exception {
		actionStatisticsListenerManager.updateListenersForNextPan();
		currentPan = new Pan();
		currentPan.setNo(countFinishedPan() + 1);
		PanResult latestFinishedPanResult = findLatestFinishedPanResult();
		List<String> allPlayerIds = latestFinishedPanResult.allPlayerIds();
		allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
		menfengDeterminerForNextPan.determineMenfengForPlayer(this);
		avaliablePaiFiller.fillAvaliablePai(this);

		// 先乱牌，再发牌，再理牌
		luanPaiStrategyForNextPan.luanpai(this);
		faPaiStrategy.fapai(this);
		currentPan.getDoudizhuPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));

		currentPan.recordPanActionFrame(null, System.currentTimeMillis());

	}

	public PanActionFrame da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, long actionTime)
			throws Exception {
		DaAction daAction = currentPan.da(playerId, paiIds, dianshuZuheIdx, waihaoGenerator);
		// 每次要理牌
		currentPan.findPlayerById(playerId).lipai(shoupaiSortStrategy);
		actionStatisticsListenerManager.updateDaActionListener(daAction, this);

		if (panFinishiDeterminer.determineToFinishCurrentPan(this)) {// 是否盘结束
			PanResult panResult = currentPanResultBuilder.buildCurrentPanResult(this, actionTime);
			finishedPanResultList.add(panResult);
			PanActionFrame panActionFrame = currentPan.recordPanActionFrame(daAction, actionTime);
			currentPan = null;
			if (juFinishiDeterminer.determineToFinishJu(this)) {// 是否局结束
				juResult = juResultBuilder.buildJuResult(this);
			}
			return panActionFrame;
		} else {
			// 生成下家的候选方案。
			currentPan.updateNextPlayersDaSolution(dianShuZuYaPaiSolutionCalculator, zaDanYaPaiSolutionCalculator);
			// 可压提示过滤
			currentPan.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);

			currentPan.updateActionPositionToNextPlayer();
			return currentPan.recordPanActionFrame(daAction, actionTime);
		}

	}

	public PanActionFrame guo(String playerId, long actionTime) throws Exception {
		GuoAction guoAction = currentPan.guo(playerId);
		actionStatisticsListenerManager.updateGuoActionListener(guoAction, this);
		// 看下一人是否是最后出牌人
		if (currentPan.ifStartYapai()) {// 下一人是最后出牌人
			DoudizhuPlayer nextPlayer = currentPan.findNextActionPlayer();
			nextPlayer.putYaPaiSolutionCandidates(
					allKedaPaiSolutionsGenerator.generateAllKedaPaiSolutions(nextPlayer.getAllShoupai()));

			// 可压提示过滤
			nextPlayer.generateDaPaiSolutionsForTips(daPaiSolutionsTipsFilter);

			currentPan.updateActionPositionToNextPlayer();
			currentPan.setLatestDapaiPlayerId(null);
		} else {
			// 生成下家的候选方案。
			currentPan.updateNextPlayersDaSolution(dianShuZuYaPaiSolutionCalculator, zaDanYaPaiSolutionCalculator);
			// 可压提示过滤
			currentPan.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);

			currentPan.updateActionPositionToNextPlayer();
		}
		return currentPan.recordPanActionFrame(guoAction, actionTime);
	}

	public void finish() {
		juResult = juResultBuilder.buildJuResult(this);
	}

	public int countFinishedPan() {
		return finishedPanResultList.size();
	}

	public PanResult findLatestFinishedPanResult() {
		if (!finishedPanResultList.isEmpty()) {
			return finishedPanResultList.get(finishedPanResultList.size() - 1);
		} else {
			return null;
		}
	}

	public Pan getCurrentPan() {
		return currentPan;
	}

	public void setCurrentPan(Pan currentPan) {
		this.currentPan = currentPan;
	}

	public List<PanResult> getFinishedPanResultList() {
		return finishedPanResultList;
	}

	public void setFinishedPanResultList(List<PanResult> finishedPanResultList) {
		this.finishedPanResultList = finishedPanResultList;
	}

	public JuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(JuResult juResult) {
		this.juResult = juResult;
	}

	public CurrentPanFinishiDeterminer getPanFinishiDeterminer() {
		return panFinishiDeterminer;
	}

	public void setPanFinishiDeterminer(CurrentPanFinishiDeterminer panFinishiDeterminer) {
		this.panFinishiDeterminer = panFinishiDeterminer;
	}

	public JuFinishiDeterminer getJuFinishiDeterminer() {
		return juFinishiDeterminer;
	}

	public void setJuFinishiDeterminer(JuFinishiDeterminer juFinishiDeterminer) {
		this.juFinishiDeterminer = juFinishiDeterminer;
	}

	public AvaliablePaiFiller getAvaliablePaiFiller() {
		return avaliablePaiFiller;
	}

	public void setAvaliablePaiFiller(AvaliablePaiFiller avaliablePaiFiller) {
		this.avaliablePaiFiller = avaliablePaiFiller;
	}

	public LuanPaiStrategy getLuanPaiStrategyForFirstPan() {
		return luanPaiStrategyForFirstPan;
	}

	public void setLuanPaiStrategyForFirstPan(LuanPaiStrategy luanPaiStrategyForFirstPan) {
		this.luanPaiStrategyForFirstPan = luanPaiStrategyForFirstPan;
	}

	public LuanPaiStrategy getLuanPaiStrategyForNextPan() {
		return luanPaiStrategyForNextPan;
	}

	public void setLuanPaiStrategyForNextPan(LuanPaiStrategy luanPaiStrategyForNextPan) {
		this.luanPaiStrategyForNextPan = luanPaiStrategyForNextPan;
	}

	public FaPaiStrategy getFaPaiStrategy() {
		return faPaiStrategy;
	}

	public void setFaPaiStrategy(FaPaiStrategy faPaiStrategy) {
		this.faPaiStrategy = faPaiStrategy;
	}

	public DizhuDeterminer getDizhuDeterminer() {
		return dizhuDeterminer;
	}

	public void setDizhuDeterminer(DizhuDeterminer dizhuDeterminer) {
		this.dizhuDeterminer = dizhuDeterminer;
	}

	public MenfengDeterminer getMenfengDeterminerForFirstPan() {
		return menfengDeterminerForFirstPan;
	}

	public void setMenfengDeterminerForFirstPan(MenfengDeterminer menfengDeterminerForFirstPan) {
		this.menfengDeterminerForFirstPan = menfengDeterminerForFirstPan;
	}

	public MenfengDeterminer getMenfengDeterminerForNextPan() {
		return menfengDeterminerForNextPan;
	}

	public void setMenfengDeterminerForNextPan(MenfengDeterminer menfengDeterminerForNextPan) {
		this.menfengDeterminerForNextPan = menfengDeterminerForNextPan;
	}

	public XiandaDeterminer getXiandaDeterminer() {
		return xiandaDeterminer;
	}

	public void setXiandaDeterminer(XiandaDeterminer xiandaDeterminer) {
		this.xiandaDeterminer = xiandaDeterminer;
	}

	public YaPaiSolutionsTipsFilter getYaPaiSolutionsTipsFilter() {
		return yaPaiSolutionsTipsFilter;
	}

	public void setYaPaiSolutionsTipsFilter(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
		this.yaPaiSolutionsTipsFilter = yaPaiSolutionsTipsFilter;
	}

	public AllKedaPaiSolutionsGenerator getAllKedaPaiSolutionsGenerator() {
		return allKedaPaiSolutionsGenerator;
	}

	public void setAllKedaPaiSolutionsGenerator(AllKedaPaiSolutionsGenerator allKedaPaiSolutionsGenerator) {
		this.allKedaPaiSolutionsGenerator = allKedaPaiSolutionsGenerator;
	}

	public WaihaoGenerator getWaihaoGenerator() {
		return waihaoGenerator;
	}

	public void setWaihaoGenerator(WaihaoGenerator waihaoGenerator) {
		this.waihaoGenerator = waihaoGenerator;
	}

	public ActionStatisticsListenerManager getActionStatisticsListenerManager() {
		return actionStatisticsListenerManager;
	}

	public void setActionStatisticsListenerManager(ActionStatisticsListenerManager actionStatisticsListenerManager) {
		this.actionStatisticsListenerManager = actionStatisticsListenerManager;
	}

	public CurrentPanResultBuilder getCurrentPanResultBuilder() {
		return currentPanResultBuilder;
	}

	public void setCurrentPanResultBuilder(CurrentPanResultBuilder currentPanResultBuilder) {
		this.currentPanResultBuilder = currentPanResultBuilder;
	}

	public JuResultBuilder getJuResultBuilder() {
		return juResultBuilder;
	}

	public void setJuResultBuilder(JuResultBuilder juResultBuilder) {
		this.juResultBuilder = juResultBuilder;
	}

	public DianShuZuYaPaiSolutionCalculator getDianShuZuYaPaiSolutionCalculator() {
		return dianShuZuYaPaiSolutionCalculator;
	}

	public void setDianShuZuYaPaiSolutionCalculator(DianShuZuYaPaiSolutionCalculator dianShuZuYaPaiSolutionCalculator) {
		this.dianShuZuYaPaiSolutionCalculator = dianShuZuYaPaiSolutionCalculator;
	}

	public ZaDanYaPaiSolutionCalculator getZaDanYaPaiSolutionCalculator() {
		return zaDanYaPaiSolutionCalculator;
	}

	public void setZaDanYaPaiSolutionCalculator(ZaDanYaPaiSolutionCalculator zaDanYaPaiSolutionCalculator) {
		this.zaDanYaPaiSolutionCalculator = zaDanYaPaiSolutionCalculator;
	}

	public DaPaiSolutionsTipsFilter getDaPaiSolutionsTipsFilter() {
		return daPaiSolutionsTipsFilter;
	}

	public void setDaPaiSolutionsTipsFilter(DaPaiSolutionsTipsFilter daPaiSolutionsTipsFilter) {
		this.daPaiSolutionsTipsFilter = daPaiSolutionsTipsFilter;
	}

	public ShoupaiSortStrategy getShoupaiSortStrategy() {
		return shoupaiSortStrategy;
	}

	public void setShoupaiSortStrategy(ShoupaiSortStrategy shoupaiSortStrategy) {
		this.shoupaiSortStrategy = shoupaiSortStrategy;
	}

	public DipaiDeterminer getDipaiDeterminer() {
		return dipaiDeterminer;
	}

	public void setDipaiDeterminer(DipaiDeterminer dipaiDeterminer) {
		this.dipaiDeterminer = dipaiDeterminer;
	}

}
