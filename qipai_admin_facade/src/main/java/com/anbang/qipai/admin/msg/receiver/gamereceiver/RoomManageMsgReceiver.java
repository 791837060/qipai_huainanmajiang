package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.anbang.qipai.admin.msg.channel.sink.RoomManageSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.games.GameRoom;
import com.anbang.qipai.admin.plan.service.gameservice.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 接收房间创建更新消息
 */
@EnableBinding(RoomManageSink.class)
public class RoomManageMsgReceiver {

    @Autowired
    private GameService gameService;

    private Gson gson = new Gson();

    @StreamListener(RoomManageSink.CHANNEL)
    public void roomManage(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if (msg.equals(RoomManageSink.CREATROOM) || msg.equals(RoomManageSink.UPDATEPLAYER)) {
                GameRoom gameRoom = gson.fromJson(json, GameRoom.class);
                //创建或更新游戏房间
                gameService.saveGameRoom(gameRoom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
