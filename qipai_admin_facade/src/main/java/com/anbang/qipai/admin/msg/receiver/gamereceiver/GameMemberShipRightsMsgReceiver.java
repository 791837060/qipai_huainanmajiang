package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.anbang.qipai.admin.msg.channel.sink.GameMemberShipRightsSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
import com.anbang.qipai.admin.plan.service.MemberShipRightsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 接收游戏大厅权益返回消息类
 *
 * @author 程佳 2018.6.13 这里的类的注解是使一个或多个接口作为参数
 **/
@EnableBinding(GameMemberShipRightsSink.class)
public class GameMemberShipRightsMsgReceiver {

    @Autowired
    private MemberShipRightsService createMemberService;

    private Gson gson = new Gson();

    /**
     * 设置游戏大厅权益接收信息的方法
     *
     * @param payload 接收的json数据 这里的方法的注解做消息监听
     **/
    @StreamListener(GameMemberShipRightsSink.memberrightsconfiguration)
    public void members(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        MemberShipRights commonuser = gson.fromJson(json, MemberShipRights.class);
        MemberShipRights commonusers = createMemberService.findallCommonUser();
        try {
            if (commonusers == null) {
                createMemberService.saveMemberShipRights(commonuser);
            }
            if ("qipai_game_conf".equals(msg)) {
                createMemberService.updateGameMemberShipRights(commonuser);
            }
            if ("qipai_members_conf".equals(msg)) {
                createMemberService.updateMembersMemberShipRights(commonuser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
