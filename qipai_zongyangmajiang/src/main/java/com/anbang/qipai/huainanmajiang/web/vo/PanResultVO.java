package com.anbang.qipai.huainanmajiang.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.TuiDaoHuPanPlayerResultDbo;

public class PanResultVO {

    private List<TuiDaoHuPanPlayerResultVO> playerResultList;

    private boolean hu;

    private int panNo;

    private long finishTime;

    private int paiCount;

    private List<NiaoPaiVO> niaoPaiList = new ArrayList<>();// 抓到的鸟牌


    private PanActionFrameVO panActionFrame;

    private Double difen;

    public PanResultVO(PanResultDbo dbo, MajiangGameDbo majiangGameDbo) {
        List<TuiDaoHuPanPlayerResultDbo> list = dbo.getPlayerResultList();
        if (list != null) {
            playerResultList = new ArrayList<>(list.size());
            list.forEach((panPlayerResult) -> playerResultList.add(new TuiDaoHuPanPlayerResultVO(majiangGameDbo.findPlayer(panPlayerResult.getPlayerId()), dbo.getZhuangPlayerId(), dbo.isZimo(), dbo.getDianpaoPlayerId(), panPlayerResult)));
        }
        for (TuiDaoHuPanPlayerResultVO tuiDaoHuPanPlayerResultVO : playerResultList) {
            if (tuiDaoHuPanPlayerResultVO.getNiaoPaiList().size() > 0) {
                setNiaoPaiList(tuiDaoHuPanPlayerResultVO.getNiaoPaiList());
            }
        }
        hu = dbo.isHu();
        panNo = dbo.getPanNo();
        finishTime = dbo.getFinishTime();
        paiCount = dbo.getPanActionFrame().getPanAfterAction().getAvaliablePaiList().getPaiCount();
        panActionFrame = new PanActionFrameVO(dbo.getPanActionFrame());
        difen=majiangGameDbo.getDifen();
    }

    public PanActionFrameVO getPanActionFrame() {
        return panActionFrame;
    }

    public void setPanActionFrame(PanActionFrameVO panActionFrame) {
        this.panActionFrame = panActionFrame;
    }

    public void setPlayerResultList(List<TuiDaoHuPanPlayerResultVO> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public List<TuiDaoHuPanPlayerResultVO> getPlayerResultList() {
        return playerResultList;
    }

    public boolean isHu() {
        return hu;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public int getPanNo() {
        return panNo;
    }

    public int getPaiCount() {
        return paiCount;
    }

    public void setPaiCount(int paiCount) {
        this.paiCount = paiCount;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

    public List<NiaoPaiVO> getNiaoPaiList() {
        return niaoPaiList;
    }

    public void setNiaoPaiList(List<NiaoPaiVO> niaoPaiList) {
        this.niaoPaiList = niaoPaiList;
    }
}
