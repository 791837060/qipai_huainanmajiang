package com.anbang.qipai.biji.cqrs.c.service.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.biji.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.biji.cqrs.c.service.impl.PukePlayCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

import java.util.Map;
import java.util.Set;

@Component(value = "pukePlayCmdService")
public class DisruptorPukePlayCmdService extends DisruptorCmdServiceBase implements PukePlayCmdService {

    @Autowired
    private PukePlayCmdServiceImpl pukePlayCmdServiceImpl;

    @Override
    public PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
                                   Long actionTime) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "chupai", playerId, toudaoIndex,
                zhongdaoIndex, weidaoIndex, actionTime);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.chupai(cmd.getParameter(), cmd.getParameter(),
                    cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return pukeActionResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId);
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            ReadyToNextPanResult readyToNextPanResult = pukePlayCmdServiceImpl.readyToNextPan(cmd.getParameter());
            return readyToNextPanResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Boolean> qipai(String playerId, String gameId) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "qipai", playerId,gameId);
        DeferredResult<Map<String, Boolean>> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> pukePlayCmdServiceImpl.qipai(cmd.getParameter(),cmd.getParameter()));
        return result.getResult();
    }

    @Override
    public PukeActionResult autoChupai(String playerId, Long actionTime, String gameId) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "chupai", playerId,actionTime,gameId);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.autoChupai(cmd.getParameter(),cmd.getParameter(),cmd.getParameter());
            return pukeActionResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId,playerIds);
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            ReadyToNextPanResult readyToNextPanResult = pukePlayCmdServiceImpl.readyToNextPan(cmd.getParameter(),cmd.getParameter());
            return readyToNextPanResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds,String gameId) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "autoReadyToNextPan", playerId,playerIds,gameId);
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () ->
                pukePlayCmdServiceImpl.autoReadyToNextPan(cmd.getParameter(),cmd.getParameter(),cmd.getParameter()));
        return result.getResult();
    }

}
