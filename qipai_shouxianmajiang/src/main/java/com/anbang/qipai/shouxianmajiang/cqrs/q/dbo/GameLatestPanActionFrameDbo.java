package com.anbang.qipai.shouxianmajiang.cqrs.q.dbo;

import com.dml.majiang.pan.frame.PanActionFrame;

public class GameLatestPanActionFrameDbo {
    private String id;// 就是gameid
    private PanActionFrame panActionFrame;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PanActionFrame getPanActionFrame() {
        return panActionFrame;
    }

    public void setPanActionFrame(PanActionFrame panActionFrame) {
        this.panActionFrame = panActionFrame;
    }
}
