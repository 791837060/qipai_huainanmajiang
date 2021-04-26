package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberGradeSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.grade.MemberGrade;
import com.anbang.qipai.admin.plan.service.MemberGradeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 接收会员等级
 *
 * @author 程佳 2018.6.22
 **/
@EnableBinding(MemberGradeSink.class)
public class MemberGradeMsgReceiver {

    @Autowired
    private MemberGradeService memberGradeService;

    private Gson gson = new Gson();

    @StreamListener(MemberGradeSink.grade)
    public void insert_grade(CommonMO mo) {
        String json = gson.toJson(mo.getData());
        MemberGrade memberGrade = gson.fromJson(json, MemberGrade.class);
        try {
            memberGradeService.insert_grade(memberGrade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
