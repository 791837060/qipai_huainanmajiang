package com.anbang.qipai.doudizhu.web.vo;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.doudizhu.player.action.DoudizhuPlayerAction;

public class PanActionFrameVO {
	private int no;
	private DoudizhuPlayerAction action;
	private PanValueObjectVO panAfterAction;
	private long actionTime;

	public PanActionFrameVO() {

	}

	public PanActionFrameVO(PanActionFrame panActionFrame) {
		no = panActionFrame.getNo();
		action = panActionFrame.getAction();
		panAfterAction = new PanValueObjectVO(panActionFrame.getPanAfterAction());
		actionTime = panActionFrame.getActionTime();
	}

	public PanActionFrameVO(PanActionFrame panActionFrame, GameLatestInfoDbo gameLatestInfoDbo) {
		no = panActionFrame.getNo();
		action = panActionFrame.getAction();
		panAfterAction = new PanValueObjectVO(panActionFrame.getPanAfterAction(), gameLatestInfoDbo);
		actionTime = panActionFrame.getActionTime();
	}

	public PanActionFrameVO(PanActionFrame panActionFrame, GameInfoDbo gameInfoDbo) {
		no = panActionFrame.getNo();
		action = panActionFrame.getAction();
		panAfterAction = new PanValueObjectVO(panActionFrame.getPanAfterAction(), gameInfoDbo);
		actionTime = panActionFrame.getActionTime();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public DoudizhuPlayerAction getAction() {
		return action;
	}

	public void setAction(DoudizhuPlayerAction action) {
		this.action = action;
	}

	public PanValueObjectVO getPanAfterAction() {
		return panAfterAction;
	}

	public void setPanAfterAction(PanValueObjectVO panAfterAction) {
		this.panAfterAction = panAfterAction;
	}

	public long getActionTime() {
		return actionTime;
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
	}

}
