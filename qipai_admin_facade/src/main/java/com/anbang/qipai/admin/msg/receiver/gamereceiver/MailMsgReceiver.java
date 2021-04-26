package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.anbang.qipai.admin.msg.channel.sink.MailSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.mail.MailState;
import com.anbang.qipai.admin.plan.bean.mail.SystemMail;
import com.anbang.qipai.admin.plan.service.gameservice.MailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 接收邮件返回消息类
 *
 * @author 程佳 2018.6.6 这里的类的注解是使一个或多个接口作为参数
 **/
@EnableBinding(MailSink.class)
public class MailMsgReceiver {

    @Autowired
    private MailService mailService;

    private Gson gson = new Gson();

    /**
     * 接收信息的方法
     *
     * @param payload 接收的json数据 这里的方法的注解做消息监听
     **/
    @StreamListener(MailSink.mail)
    public void mail(CommonMO mo) {
        String json = gson.toJson(mo.getData());
        try {
            // 添加返回邮件信息
            if ("newMail".equals(mo.getMsg())) {
                SystemMail mail = gson.fromJson(json, SystemMail.class);
                mailService.addmail(mail);
            }
            // 添加返回邮件状态
            if ("newMailState".equals(mo.getMsg())) {
                Type type = new TypeToken<ArrayList<MailState>>() {
                }.getType();
                ArrayList<MailState> lists = gson.fromJson(json, type);
                mailService.addMailById(lists);
            }

            // 修改返回邮件状态
            if ("updateMailState".equals(mo.getMsg())) {
                MailState mailState = gson.fromJson(json, MailState.class);
                mailService.updateMailState(mailState);
            }

            // 所有的设为已读
            if ("updateMailStateAll".equals(mo.getMsg())) {
                String memberId = gson.fromJson(json, String.class);
                mailService.findallmembermail(memberId);
            }

            // 删除所有已读
            if ("deleteMailStateAll".equals(mo.getMsg())) {
                String memberId = gson.fromJson(json, String.class);
                mailService.deleteallmail(memberId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
