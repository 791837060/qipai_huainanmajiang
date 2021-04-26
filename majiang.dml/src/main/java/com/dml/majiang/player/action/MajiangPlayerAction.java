package com.dml.majiang.player.action;

/**
 * 吃碰杠胡摸打过
 *
 * @author Neo
 */
public abstract class MajiangPlayerAction {

    private int id;

    private MajiangPlayerActionType type;

    private String actionPlayerId;

    private boolean disabledByHigherPriorityAction;

    public MajiangPlayerAction() {
    }

    public MajiangPlayerAction(MajiangPlayerActionType type, String actionPlayerId) {
        this.type = type;
        this.actionPlayerId = actionPlayerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MajiangPlayerActionType getType() {
        return type;
    }

    public void setType(MajiangPlayerActionType type) {
        this.type = type;
    }

    public String getActionPlayerId() {
        return actionPlayerId;
    }

    public void setActionPlayerId(String actionPlayerId) {
        this.actionPlayerId = actionPlayerId;
    }

    public boolean isDisabledByHigherPriorityAction() {
        return disabledByHigherPriorityAction;
    }

    public void setDisabledByHigherPriorityAction(boolean disabledByHigherPriorityAction) {
        this.disabledByHigherPriorityAction = disabledByHigherPriorityAction;
    }

}
