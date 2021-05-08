package com.anbang.qipai.shouxianmajiang.cqrs.c.service.disruptor;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangActionResult;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ReadyToNextPanResult;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao.XiapiaoResult;
import com.anbang.qipai.shouxianmajiang.cqrs.c.service.MajiangPlayCmdService;
import com.anbang.qipai.shouxianmajiang.cqrs.c.service.impl.MajiangPlayCmdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangActionResult;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ReadyToNextPanResult;
import com.anbang.qipai.shouxianmajiang.cqrs.c.service.MajiangPlayCmdService;
import com.anbang.qipai.shouxianmajiang.cqrs.c.service.impl.MajiangPlayCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

import java.util.Set;

@Component(value = "majiangPlayCmdService")
public class DisruptorMajiangPlayCmdService extends DisruptorCmdServiceBase implements MajiangPlayCmdService {

	@Autowired
	private MajiangPlayCmdServiceImpl majiangPlayCmdServiceImpl;

	@Override
	public MajiangActionResult action(String playerId, Integer actionId, Integer actionNo, Long actionTime)
			throws Exception {
		CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "action", playerId, actionId,
				actionNo, actionTime);
		DeferredResult<MajiangActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			MajiangActionResult majiangActionResult = majiangPlayCmdServiceImpl.action(cmd.getParameter(),
					cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
			return majiangActionResult;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
		CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId);
		DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			ReadyToNextPanResult readyToNextPanResult = majiangPlayCmdServiceImpl.readyToNextPan(cmd.getParameter());
			return readyToNextPanResult;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public MajiangGameValueObject xipai(String playerId) throws Exception {
		CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "xipai", playerId);
		DeferredResult<MajiangGameValueObject> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd,
				() -> {
					MajiangGameValueObject majiangGameValueObject = majiangPlayCmdServiceImpl.xipai(cmd.getParameter());
					return majiangGameValueObject;
				});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw e;
		}
	}


    @Override
    public MajiangActionResult automaticAction(String playerId, Integer actionId, Long actionTime, String gameId) throws Exception {
        CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "automaticAction", playerId, actionId, actionTime, gameId);
        DeferredResult<MajiangActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () ->
                majiangPlayCmdServiceImpl.automaticAction(cmd.getParameter(), cmd.getParameter(), cmd.getParameter(), cmd.getParameter()));
        return result.getResult();
    }


    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception {
        CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId, playerIds);
        DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            ReadyToNextPanResult readyToNextPanResult = majiangPlayCmdServiceImpl.readyToNextPan(cmd.getParameter(), cmd.getParameter());
            return readyToNextPanResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

	@Override
	public ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds, String gameId) throws Exception {
		CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "autoReadyToNextPan", playerId,playerIds,gameId);
		DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () ->
				majiangPlayCmdServiceImpl.autoReadyToNextPan(cmd.getParameter(),cmd.getParameter(),cmd.getParameter()));
		return result.getResult();
	}

    @Override
    public XiapiaoResult xiapiao(String playerId, Integer piaofen) throws Exception {
        CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "xiapiao", playerId, piaofen);
        DeferredResult<XiapiaoResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () ->
                majiangPlayCmdServiceImpl.xiapiao(cmd.getParameter(), cmd.getParameter()));
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public XiapiaoResult xiapiao(String playerId, Integer piaofen,String gameID) throws Exception {
        CommonCommand cmd = new CommonCommand(MajiangPlayCmdServiceImpl.class.getName(), "xiapiao", playerId, piaofen,gameID);
        DeferredResult<XiapiaoResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () ->
                majiangPlayCmdServiceImpl.xiapiao(cmd.getParameter(), cmd.getParameter(),cmd.getParameter()));
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

}
