package com.anbang.qipai.dalianmeng.plan.service;


import com.anbang.qipai.dalianmeng.plan.bean.MemberLatAndLon;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.dao.MemberLatAndLonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLatAndLonService {

    @Autowired
    private MemberLatAndLonDao memberLatAndLonDao;

    public void save(MemberLatAndLon memberLatAndLon) {
        memberLatAndLonDao.save(memberLatAndLon);
    }

    public List<MemberLatAndLon> find(List<String> memberIds,String roomNo){
        return memberLatAndLonDao.find(memberIds,roomNo);
    }
	public void deleteMemberLatAndLon(String playerId) {
		memberLatAndLonDao.deleteMemberLatAndLon(playerId);
	}

    public boolean verifyIp(MemberLatAndLon memberLatAndLon , GameTable gameTable){
        for (String player : gameTable.getPlayers()) {
            MemberLatAndLon bymemberId = memberLatAndLonDao.findBymemberId(player,gameTable.getNo());
            if (memberLatAndLon.getRepIP().equals(bymemberId.getRepIP())){
                return false;
            }

        }
        return true;
    }
    public boolean verifyGps(MemberLatAndLon memberLatAndLon , GameTable gameTable){
        for (String player : gameTable.getPlayers()) {
            MemberLatAndLon bymemberId = memberLatAndLonDao.findBymemberId(player,gameTable.getNo());
            if (getDistance(memberLatAndLon.getLon(),memberLatAndLon.getLat(),bymemberId.getLon(),bymemberId.getLat())<=50){
                return false;
            }
        }
        return true;
    }

    private  double getDistance(String lon1,String lat1,String lon2,String lat2){
        double temp_lon1=Double.parseDouble(lon1);
        double temp_lat1=Double.parseDouble(lat1);
        double temp_lon2=Double.parseDouble(lon2);
        double temp_lat2=Double.parseDouble(lat2);
        double radLat1=rad(temp_lat1);
        double radLat2=rad(temp_lat2);
        double a=radLat1-radLat2;
        double b=rad(temp_lon1)-rad(temp_lon2);
        double s = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+ Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        double EARTH_RADIUS = 6378137d;
        s*= EARTH_RADIUS;
        return Math.ceil(s);

    }
    private double rad(double d){
        return d*Math.PI/180.0d;
    }

}
