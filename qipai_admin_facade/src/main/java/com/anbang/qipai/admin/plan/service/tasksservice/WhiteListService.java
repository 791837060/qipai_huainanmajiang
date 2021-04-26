package com.anbang.qipai.admin.plan.service.tasksservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.hongbao.BlackList;
import com.anbang.qipai.admin.plan.bean.members.MemberLoginRecord;
import com.anbang.qipai.admin.plan.bean.tasks.WhiteList;
import com.anbang.qipai.admin.plan.dao.hongbaodao.BlackListDao;
import com.anbang.qipai.admin.plan.dao.tasksdao.WhiteListDao;
import com.anbang.qipai.admin.plan.service.membersservice.MemberLoginRecordService;
import com.highto.framework.web.page.ListPage;

/**
 * @author yins
 * @Description: 白名单service
 */
@Service
public class WhiteListService {

	@Autowired
	private WhiteListDao whiteListDao;

	@Autowired
	private BlackListDao blackListDao;

	@Autowired
	private MemberLoginRecordService memberLoginRecordService;

	public ListPage findBlackList(int page, int size) {
		int amount = (int) blackListDao.count();
		List<BlackList> list = blackListDao.find(page, size);
		return new ListPage(list, page, size, amount);
	}

	public void saveBlackList(BlackList blackList) {
		blackListDao.save(blackList);
	}

	public void removeBlackList(String[] ids) {
		blackListDao.remove(ids);
	}

	public void addWhiteList(WhiteList whiteList) {
		whiteList.setAddTime(System.currentTimeMillis());
		whiteListDao.addWhiteList(whiteList);
	}

	public void deleteWhiteList(String[] ids) {
		whiteListDao.deleteWhiteList(ids);
	}

	public ListPage findWhiteLists(int page, int size, String playerId, String loginIP) {
		// 查询条件转换
		List<String> playerIds = new ArrayList<>();
		boolean flag = false;
		if (StringUtils.isNotBlank(loginIP)) {
			flag = true;
			List<MemberLoginRecord> memberLoginRecords = memberLoginRecordService.findMemberLoginRecords(loginIP,
					playerId);
			if (CollectionUtils.isNotEmpty(memberLoginRecords)) {
				playerIds = memberLoginRecords.stream().map(MemberLoginRecord::getMemberId)
						.collect(Collectors.toList());
			}
		} else if (StringUtils.isNotBlank(playerId)) {
			flag = true;
			playerIds.add(playerId);
		}

		// 返回结果转换 temp
		long count = whiteListDao.countWhiteList(playerIds, false);
		List<WhiteList> whiteLists = whiteListDao.findWhiteLists(page, size, playerIds, flag);
		List<Map> data = new ArrayList<>();
		for (WhiteList list : whiteLists) {
			Map map = new HashMap();
			MemberLoginRecord memberLoginRecord = memberLoginRecordService
					.findRecentRecordByMemberId(list.getPlayerId());
			if (memberLoginRecord != null) {
				map.put("nickName", memberLoginRecord.getNickname());
				map.put("loginIp", memberLoginRecord.getLoginIp());
			}
			map.put("playerId", list.getPlayerId());
			map.put("addTime", list.getAddTime());
			map.put("remark", list.getRemark());
			map.put("operator", list.getOperator());
			map.put("id", list.getId());
			data.add(map);
		}
		ListPage listPage = new ListPage(data, page, size, (int) count);
		return listPage;
	}

}
