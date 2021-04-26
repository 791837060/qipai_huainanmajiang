package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.finish.JuFinishiDeterminer;
import com.dml.majiang.pan.result.CurrentPanResultBuilder;
import com.dml.majiang.pan.result.PanResult;

import java.util.List;


public class MaanshanMajiangFixedPanNumbersJuFinishiDeterminer implements JuFinishiDeterminer {

    private int fixedPanNumbers;

    private OptionalPlay optionalPlay;

    public MaanshanMajiangFixedPanNumbersJuFinishiDeterminer() {
    }

    public MaanshanMajiangFixedPanNumbersJuFinishiDeterminer(int fixedPanNumbers, OptionalPlay optionalPlay) {
        this.fixedPanNumbers = fixedPanNumbers;
        this.optionalPlay = optionalPlay;
    }

    public boolean determineToFinishJu(Ju ju, MajiangGame majiangGame) {
        if (ju.countFinishedPan() >= fixedPanNumbers) {
            return true;
        }

        boolean hasPlayerOver = false;
        MaanshanMajiangPanResult panResult = (MaanshanMajiangPanResult) ju.findLatestFinishedPanResult();
        if (panResult != null) {
            for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                if (maanshanMajiangPanPlayerResult.getTotalScore() <= 0) {
                    hasPlayerOver = true;
                    break;
                }
            }
        }
        return (majiangGame.getCurrentDao() == (majiangGame.getOptionalPlay().getDaozi() + 1)) && hasPlayerOver;
    }

    @Override
    public boolean determineToFinishJu(Ju ju) {
//        if (ju.countFinishedPan() >= fixedPanNumbers) {
//            return true;
//        }
//
//        boolean hasPlayerOver = false;
//        MaanshanMajiangPanResult panResult = (MaanshanMajiangPanResult) ju.findLatestFinishedPanResult();
//        if (panResult != null) {
//            for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
//                if (maanshanMajiangPanPlayerResult.getTotalScore() <= 0) {
//                    hasPlayerOver = true;
//                    break;
//                }
//            }
//        }
//        return (ju.getCurrentDao() == (optionalPlay.getDaozi() + 1)) && hasPlayerOver;
        return false;
    }

    public int getFixedPanNumbers() {
        return fixedPanNumbers;
    }

    public void setFixedPanNumbers(int fixedPanNumbers) {
        this.fixedPanNumbers = fixedPanNumbers;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
