package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.anbang.qipai.admin.msg.channel.sink.GameDataReportSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.report.GameDataReport;
import com.anbang.qipai.admin.plan.service.GameReportService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(GameDataReportSink.class)
public class GameDataReportMsgReceiver {
    @Autowired
    private GameReportService gameReportService;

    private Gson gson = new Gson();

    @StreamListener(GameDataReportSink.GAMEDATAREPORT)
    public void gameDataReport(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if ("record gameDataReport".equals(msg)) {
                GameDataReport report = gson.fromJson(json, GameDataReport.class);
                gameReportService.addGameReport(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
