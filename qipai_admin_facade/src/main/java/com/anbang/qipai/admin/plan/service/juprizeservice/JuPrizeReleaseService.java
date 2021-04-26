package com.anbang.qipai.admin.plan.service.juprizeservice;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeReleaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 */
@Service
public class JuPrizeReleaseService {
    @Autowired
    private JuPrizeReleaseDao juPrizeReleaseDao;

    public void save(JuPrizeRelease juPrizeRelease) {
        juPrizeReleaseDao.save(juPrizeRelease);
    }

    public List<JuPrizeRelease> listJuPrizeRelease(){
        return juPrizeReleaseDao.listJuPrize();
    }

}
