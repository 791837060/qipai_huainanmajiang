package com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.preparedapai.dizhu.DizhuDeterminer;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;

/**
 * 从座风东的玩家开始抢地主，四次之后，如果每次都抢地主，则东家当地主，否则最后抢地主的玩家当地主
 * 
 * @author lsc
 *
 */
public class QiangdizhuDizhuDeterminer implements DizhuDeterminer {

	private int renshu;
	private Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
	private Node node;
	private int qiangdizhuCount;

	public void init(Ju ju) {
		qiangdizhuCount = 0;
		Pan currentPan = ju.getCurrentPan();
		for (String pid : currentPan.sortedPlayerIdList()) {
			if (currentPan.findPlayerById(pid).getPosition().equals(Position.dong)) {
				playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.startJiaodizhu);
			} else {
				playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.waitForJiaodizhu);
			}
		}
		Node root = new Node();
		Node leftChildren = null;
		Node rightChildren = null;
		if (renshu == 2) {
			// 1-1
			root.createLeftChildren(PlayerQiangdizhuState.jiaodizhu, null);
			leftChildren = root.getLeftChildren();
			// 2-1
			leftChildren.createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 3-1
			leftChildren.getLeftChildren().createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 4-1
			leftChildren.getLeftChildren().getLeftChildren().createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 5-1
			leftChildren.getLeftChildren().getLeftChildren().getLeftChildren().createLeftChildren(
					PlayerQiangdizhuState.afterqiang, currentPan.playerIdForPosition(Position.dong));
			// 5-2
			leftChildren.getLeftChildren().getLeftChildren().getLeftChildren().createRightChildren(
					PlayerQiangdizhuState.afterbuqiang, currentPan.playerIdForPosition(Position.xi));
			// 4-2
			leftChildren.getLeftChildren().getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 3-2
			leftChildren.getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.xi));
			// 2-2
			leftChildren.createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.dong));

			// 1-2
			root.createRightChildren(PlayerQiangdizhuState.bujiao, null);
			rightChildren = root.getRightChildren();
			// 2-3
			rightChildren.createLeftChildren(PlayerQiangdizhuState.afterjiaodizhu,
					currentPan.playerIdForPosition(Position.xi));
			// 2-4
			rightChildren.createRightChildren(PlayerQiangdizhuState.afterbujiao,
					currentPan.playerIdForPosition(Position.dong));
		} else if (renshu == 3) {
			// 1-1
			root.createLeftChildren(PlayerQiangdizhuState.jiaodizhu, null);
			leftChildren = root.getLeftChildren();
			// 2-1
			leftChildren.createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 3-1
			leftChildren.getLeftChildren().createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 4-1
			leftChildren.getLeftChildren().getLeftChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-2
			leftChildren.getLeftChildren().getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.xi));
			// 3-2
			leftChildren.getLeftChildren().createRightChildren(PlayerQiangdizhuState.buqiang, null);
			// 4-3
			leftChildren.getLeftChildren().getRightChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-4
			leftChildren.getLeftChildren().getRightChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.nan));
			// 2-2
			leftChildren.createRightChildren(PlayerQiangdizhuState.buqiang, null);
			// 3-3
			leftChildren.getRightChildren().createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 4-5
			leftChildren.getRightChildren().getLeftChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-6
			leftChildren.getRightChildren().getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.xi));
			// 3-4
			leftChildren.getRightChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.dong));

			// 1-2
			root.createRightChildren(PlayerQiangdizhuState.bujiao, null);
			rightChildren = root.getRightChildren();
			// 2-3
			rightChildren.createLeftChildren(PlayerQiangdizhuState.jiaodizhu, null);
			// 3-5
			rightChildren.getLeftChildren().createLeftChildren(PlayerQiangdizhuState.qiang, null);
			// 4-9
			rightChildren.getLeftChildren().getLeftChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-10
			rightChildren.getLeftChildren().getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.xi));
			// 3-6
			rightChildren.getLeftChildren().createRightChildren(PlayerQiangdizhuState.buqiang, null);
			// 4-11
			rightChildren.getLeftChildren().getRightChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-12
			rightChildren.getLeftChildren().getRightChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.nan));
			// 2-4
			rightChildren.createRightChildren(PlayerQiangdizhuState.bujiao, null);
			// 3-7
			rightChildren.getRightChildren().createLeftChildren(PlayerQiangdizhuState.jiaodizhu, null);
			// 4-13
			rightChildren.getRightChildren().getLeftChildren().createLeftChildren(PlayerQiangdizhuState.afterqiang,
					currentPan.playerIdForPosition(Position.dong));
			// 4-14
			rightChildren.getRightChildren().getLeftChildren().createRightChildren(PlayerQiangdizhuState.afterbuqiang,
					currentPan.playerIdForPosition(Position.xi));
			// 3-8
			rightChildren.getRightChildren().createRightChildren(PlayerQiangdizhuState.afterbujiao,
					currentPan.playerIdForPosition(Position.dong));
		} else {

		}
		node = root;
	}

	@Override
	public String determineToDizhu(Ju ju, String playerId, Integer qiang) throws Exception {
		Node newNode = null;
		if (qiang==1) {
			newNode = node.getLeftChildren();
		} else {
			newNode = node.getRightChildren();
		}
		if (newNode == null) {
			throw new CannotQiangdizhuException();
		}
		node = newNode;
		if (qiang==1) {
			qiangdizhuCount++;
		}
		Pan currentPan = ju.getCurrentPan();
		String nextPlayerId = null;
		Position nextPosition = currentPan.findPlayerPosition(playerId);
		while (nextPlayerId == null) {
			nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
			nextPlayerId = currentPan.playerIdForPosition(nextPosition);
		}
		for (String pid : currentPan.sortedPlayerIdList()) {
			if (pid.equals(playerId)) {
				playerQiangdizhuMap.put(playerId, node.getState());
			} else {
				if (node.getDizhuId() != null) {
					playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.over);
				} else {
					if (node.getState().equals(PlayerQiangdizhuState.bujiao)) {
						if (pid.equals(nextPlayerId)) {
							playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.startJiaodizhu);
						} else {
							playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.waitForJiaodizhu);
						}
					} else {
						if (pid.equals(nextPlayerId)) {
							playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.startQiangdizhu);
						} else {
							playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.waitForQiangdizhu);
						}
					}
				}
			}
		}
		return node.getDizhuId();
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public Map<String, PlayerQiangdizhuState> getPlayerQiangdizhuMap() {
		return playerQiangdizhuMap;
	}

	public void setPlayerQiangdizhuMap(Map<String, PlayerQiangdizhuState> playerQiangdizhuMap) {
		this.playerQiangdizhuMap = playerQiangdizhuMap;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getQiangdizhuCount() {
		return qiangdizhuCount;
	}

	public void setQiangdizhuCount(int qiangdizhuCount) {
		this.qiangdizhuCount = qiangdizhuCount;
	}

}
