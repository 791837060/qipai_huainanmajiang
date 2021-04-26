package com.anbang.qipai.admin.plan.service.chaguanservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanDbo;
import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameTable;
import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanMemberDbo;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResultExpand;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanDboDao;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanGameTableDao;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanMemberDboDao;
import com.anbang.qipai.admin.plan.dao.gamedao.GameHistoricalJuResultDao;
import com.anbang.qipai.admin.plan.dao.gamedao.GameHistoricalPanResultDao;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.highto.framework.web.page.ListPage;

@Service
public class ChaguanService {

	@Autowired
	private ChaguanDboDao chaguanDboDao;

	@Autowired
	private ChaguanMemberDboDao chaguanMemberDboDao;

	@Autowired
	private ChaguanGameTableDao chaguanGameTableDao;

	@Autowired
	private GameHistoricalJuResultDao gameHistoricalJuResultDao;

	@Autowired
	private GameHistoricalPanResultDao gameHistoricalPanResultDao;

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	public ListPage findChaguanDbo(int page, int size,String agentId) {
		int amount = (int) chaguanDboDao.count(agentId);
		List<ChaguanDbo> chaguanList = chaguanDboDao.find(page, size,agentId);
		return new ListPage(chaguanList, page, size, amount);
	}

	public ListPage findChaguanMemberDbo(int page, int size, String chaguanId) {
		int amount = (int) chaguanMemberDboDao.countByChaguanId(chaguanId);
		List<ChaguanMemberDbo> chaguanMemberList = chaguanMemberDboDao.findByChaguanId(page, size, chaguanId);
		return new ListPage(chaguanMemberList, page, size, amount);
	}

	public ChaguanDbo findByChaguanId(String chaguanId) {
		return chaguanDboDao.findByChaguanId(chaguanId);
	}

	public void saveChaguanMemberDbo(ChaguanMemberDbo dbo) {
		chaguanMemberDboDao.save(dbo);
	}

	public void removeChaguanMemberDbo(ChaguanMemberDbo dbo) {
		chaguanMemberDboDao.save(dbo);
	}

	public void setChaguanMemberDbo(ChaguanMemberDbo dbo) {
		chaguanMemberDboDao.save(dbo);
	}

	public void saveChaguanDbo(ChaguanDbo dbo) {
		chaguanDboDao.save(dbo);
	}

	public void updateChaguanDbo(ChaguanDbo dbo) {
		chaguanDboDao.save(dbo);
	}

	public void removeChaguanDboById(String chaguanId) {
		chaguanDboDao.removeById(chaguanId);
		chaguanMemberDboDao.removeByChaguanId(chaguanId);
	}

	public void saveChaguanGameTable(ChaguanGameTable table) {
		chaguanGameTableDao.save(table);
	}

	public void updateChaguanGameTable(ChaguanGameTable table) {
		chaguanGameTableDao.updateGameTable(table);
	}

	public ListPage queryRoomList(int page, int size, String playerId, String tableNo) {
		int count = chaguanGameTableDao.countTableByPlayerIdAndTableNo(playerId, tableNo);
		List<ChaguanGameTable> tableList = chaguanGameTableDao.findTableByPlayerIdAndTableNo(page, size, playerId,
				tableNo);
		ListPage listPage = new ListPage(tableList, page, size, count);
		return listPage;
	}

	public Map queryRoomDetail(String gameId) {
		GameHistoricalJuResult juResult = gameHistoricalJuResultDao.findJuResultBygameId(gameId);
		List<GameHistoricalPanResult> panResults = gameHistoricalPanResultDao.findPanResultByGameId(gameId);
		List<GameHistoricalPanResultExpand> result = new ArrayList<>();
		for (GameHistoricalPanResult list : panResults) {
			GameHistoricalPanResultExpand expand = new GameHistoricalPanResultExpand();
			BeanUtils.copyProperties(list, expand);
			// 获取返回码
			CommonRemoteVO commonRemoteVO = qipaiGameRomoteService.queryBackcode(list.getGame(), list.getGameId(),
					list.getNo());
			if (commonRemoteVO.getData() == null) {
				expand.setCodeStatus(0);
			} else {
				expand.setCodeStatus(1);
				expand.setCode(String.valueOf(commonRemoteVO.getData()));
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
}
