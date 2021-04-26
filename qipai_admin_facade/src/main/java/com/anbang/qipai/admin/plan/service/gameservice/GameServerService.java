package com.anbang.qipai.admin.plan.service.gameservice;

import com.anbang.qipai.admin.plan.bean.games.GameServer;
import com.anbang.qipai.admin.plan.dao.gamedao.GameServerDao;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GameServerService {

    @Autowired
    private GameServerDao gameServerDao;

    public List<String> saveSnapshot(List<String> ids) throws Exception {
        final List<GameServer> gameServers = this.gameServerDao.findGameServersByIds(ids);
        List<String>failedServers=new ArrayList<>();
        HttpClient httpClient = new HttpClient();
        httpClient.start();
        for (GameServer gameServer : gameServers) {
            final String wsUrl = gameServer.getWsUrl();
            final String url = this.getIpFromWsUrl(wsUrl);
            try {
                this.sendSaveSnapshotRequest(url, httpClient);
            } catch (Throwable e) {
                failedServers.add(gameServer.getId());
            }
        }
        return failedServers;
    }

    private String getIpFromWsUrl(String wsUrl) {
        StringBuilder sb = new StringBuilder(wsUrl);
        sb.delete(0, 5);
        final int i = sb.indexOf("/");
        final String ip = sb.substring(0, i);
        return "http://" +
                ip +
                "/snapshot/save";
    }

    private void sendSaveSnapshotRequest(String url, HttpClient client) throws Exception {
        Request request = client.newRequest(url);
        request.method(HttpMethod.GET);
        request.agent("jetty client");
        request.timeout(5, TimeUnit.SECONDS);
        final ContentResponse response = request.send();
        final int status = response.getStatus();
        if (status != 200) {
            throw new Exception("failed");
        }
    }


}
