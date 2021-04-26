package com.anbang.qipai.admin.web.vo.membersvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.members.MemberDbo;

public class MemberVO extends MemberDbo {
	private String isVip;
	private String goldSort;
	private String scoreSort;
	private String createTimeSort;
	private String vipSort;
	private String vipEndTimeSort;
	private String vipLevelSort;
	private String vipScoreSort;
	private String onlineStateSort;
	private String verifyUserSort;

	private long lastLoginTime;  //最后一次登录时间

	private int totalInvitationNum; //玩家邀请人数
	private String invitedBy;  //该玩家的邀请人（玩家）

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(goldSort)) {
			orderList.add(new Order(Direction.ASC, "gold"));
		} else if ("DESC".equals(goldSort)) {
			orderList.add(new Order(Direction.DESC, "gold"));
		}
		if ("ASC".equals(scoreSort)) {
			orderList.add(new Order(Direction.ASC, "score"));
		} else if ("DESC".equals(scoreSort)) {
			orderList.add(new Order(Direction.DESC, "score"));
		}
		if ("ASC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.ASC, "createTime"));
		} else if ("DESC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.DESC, "createTime"));
		}
		if ("ASC".equals(vipSort)) {
			orderList.add(new Order(Direction.ASC, "vip"));
		} else if ("DESC".equals(vipSort)) {
			orderList.add(new Order(Direction.DESC, "vip"));
		}
		if ("ASC".equals(vipEndTimeSort)) {
			orderList.add(new Order(Direction.ASC, "vipEndTime"));
		} else if ("DESC".equals(vipEndTimeSort)) {
			orderList.add(new Order(Direction.DESC, "vipEndTime"));
		}
		if ("ASC".equals(vipLevelSort)) {
			orderList.add(new Order(Direction.ASC, "vipLevel"));
		} else if ("DESC".equals(vipLevelSort)) {
			orderList.add(new Order(Direction.DESC, "vipLevel"));
		}
		if ("ASC".equals(vipScoreSort)) {
			orderList.add(new Order(Direction.ASC, "vipScore"));
		} else if ("DESC".equals(vipScoreSort)) {
			orderList.add(new Order(Direction.DESC, "vipScore"));
		}
		if ("ASC".equals(onlineStateSort)) {
			orderList.add(new Order(Direction.ASC, "onlineState"));
		} else if ("DESC".equals(onlineStateSort)) {
			orderList.add(new Order(Direction.DESC, "onlineState"));
		}
		if ("ASC".equals(verifyUserSort)) {
			orderList.add(new Order(Direction.ASC, "verifyUser"));
		} else if ("DESC".equals(verifyUserSort)) {
			orderList.add(new Order(Direction.DESC, "verifyUser"));
		}
		if (!orderList.isEmpty()) {
			Sort sort = new Sort(orderList);
			return sort;
		}
		Sort sort = new Sort(new Order(Direction.DESC, "createTime"));
		return sort;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getGoldSort() {
		return goldSort;
	}

	public void setGoldSort(String goldSort) {
		this.goldSort = goldSort;
	}

	public String getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(String scoreSort) {
		this.scoreSort = scoreSort;
	}

	public String getCreateTimeSort() {
		return createTimeSort;
	}

	public void setCreateTimeSort(String createTimeSort) {
		this.createTimeSort = createTimeSort;
	}

	public String getVipSort() {
		return vipSort;
	}

	public void setVipSort(String vipSort) {
		this.vipSort = vipSort;
	}

	public String getVipEndTimeSort() {
		return vipEndTimeSort;
	}

	public void setVipEndTimeSort(String vipEndTimeSort) {
		this.vipEndTimeSort = vipEndTimeSort;
	}

	public String getVipLevelSort() {
		return vipLevelSort;
	}

	public void setVipLevelSort(String vipLevelSort) {
		this.vipLevelSort = vipLevelSort;
	}

	public String getVipScoreSort() {
		return vipScoreSort;
	}

	public void setVipScoreSort(String vipScoreSort) {
		this.vipScoreSort = vipScoreSort;
	}

	public String getOnlineStateSort() {
		return onlineStateSort;
	}

	public void setOnlineStateSort(String onlineStateSort) {
		this.onlineStateSort = onlineStateSort;
	}

	public String getVerifyUserSort() {
		return verifyUserSort;
	}

	public void setVerifyUserSort(String verifyUserSort) {
		this.verifyUserSort = verifyUserSort;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getTotalInvitationNum() {
		return totalInvitationNum;
	}

	public void setTotalInvitationNum(int totalInvitationNum) {
		this.totalInvitationNum = totalInvitationNum;
	}

	public String getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}
}
