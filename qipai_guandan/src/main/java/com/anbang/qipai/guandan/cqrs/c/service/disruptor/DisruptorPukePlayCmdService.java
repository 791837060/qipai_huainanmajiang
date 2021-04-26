package com.anbang.qipai.guandan.cqrs.c.service.disruptor;

import com.anbang.qipai.guandan.cqrs.c.domain.result.ChaodiResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.guandan.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.guandan.cqrs.c.service.impl.PukePlayCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component(value = "pukePlayCmdService")
public class DisruptorPukePlayCmdService extends DisruptorCmdServiceBase implements PukePlayCmdService {

    @Autowired
    private PukePlayCmdServiceImpl pukePlayCmdServiceImpl;

    @Override
    public PukeActionResult da(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime)
            throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "da", playerId, paiIds,
                dianshuZuheIdx, actionTime);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.da(cmd.getParameter(), cmd.getParameter(),
                    cmd.getParameter(), cmd.getParameter());
            return pukeActionResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PukeActionResult autoDa(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime, String gameId)
            throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "autoDa", playerId, paiIds,
                dianshuZuheIdx, actionTime, gameId);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.autoDa(cmd.getParameter(), cmd.getParameter(),
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
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
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
    public ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId, playerIds);
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
            ReadyToNextPanResult readyToNextPanResult = pukePlayCmdServiceImpl.readyToNextPan(cmd.getParameter(), cmd.getParameter());
            return readyToNextPanResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ChaodiResult chaodi(String playerId, Boolean chaodi, Long actionTime) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "chaodi", playerId, chaodi,
                actionTime);
        DeferredResult<ChaodiResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
            ChaodiResult chaodiResult = pukePlayCmdServiceImpl.chaodi(cmd.getParameter(), cmd.getParameter(),
                    cmd.getParameter());
            return chaodiResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public PukeActionResult guo(String playerId, Long actionTime) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "guo", playerId, actionTime);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.guo(cmd.getParameter(), cmd.getParameter());
            return pukeActionResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PukeActionResult autoGuo(String playerId, Long actionTime, String gameId) throws Exception {
        CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "autoGuo", playerId, actionTime, gameId);
        DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, ()->{
            PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.autoGuo(cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return pukeActionResult;
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
