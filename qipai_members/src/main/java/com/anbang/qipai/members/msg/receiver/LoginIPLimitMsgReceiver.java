package com.anbang.qipai.members.msg.receiver;


import com.anbang.qipai.members.msg.channel.sink.LoginIPLimitSink;
import com.anbang.qipai.members.msg.msjobj.CommonMO;
import com.anbang.qipai.members.plan.bean.MemberLoginIPLimit;
import com.anbang.qipai.members.plan.dao.MemberLoginIPLimitDao;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(LoginIPLimitSink.class)
public class LoginIPLimitMsgReceiver {

    @Autowired
    private MemberLoginIPLimitDao memberLoginIPLimitDao;

    private Gson gson = new Gson();

    @StreamListener(LoginIPLimitSink.LOGINIPLIMIT)
    public void memberClubCaprd(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        if ("add_ip_limit".equals(msg)) {
            MemberLoginIPLimit limit = gson.fromJson(json, MemberLoginIPLimit.class);
            memberLoginIPLimitDao.save(limit);
        }
        if ("delete_ip_limits".equals(msg)) {
            String[] ids = gson.fromJson(json, String[].class);
            memberLoginIPLimitDao.updateMemberLoginLimitRecordEfficientById(ids, false);
        }
    }
}
