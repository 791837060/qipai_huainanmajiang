package com.anbang.qipai.members.plan.service;


import com.anbang.qipai.members.plan.bean.MemberYushi;
import com.anbang.qipai.members.plan.dao.MemberYushiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberYushiService {


	@Autowired
    private MemberYushiDao memberYushiDao;



	public List<Map<String,Object>> findAllYushi(String payerId){
        List<Map<String,Object>> volist = new ArrayList<>();
        List<MemberYushi> yushiList = memberYushiDao.findAllYushi();
        for (MemberYushi yushi : yushiList){
            Map<String,Object> vo=new HashMap<>();
            vo.put("id",yushi.getId()+"_yushi");
            vo.put("name",yushi.getName());
            vo.put("yushi",yushi.getYushi());
            vo.put("price",yushi.getPrice());
            vo.put("originalPrice",yushi.getPrice());

            volist.add(vo);
        }
	    return volist;
    }

    public void addYushi(MemberYushi yushi) {
        memberYushiDao.addYushi(yushi);
    }

    public void deleteYushiByIds(String[] yushiId) {
        memberYushiDao.deleteYushiByIds(yushiId);
    }

    public void updateYushi(MemberYushi yushi) {
        memberYushiDao.updateYushi(yushi);
    }

    public MemberYushi findYushiById(String id) {
        return memberYushiDao.getYushiById(id);
    }

}
