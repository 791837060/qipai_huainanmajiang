package com.anbang.qipai.admin.plan.service.juprizeservice;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 */
@Service
public class JuPrizeService {
    @Autowired
    private JuPrizeDao juPrizeDao;

    public void saveOrUpdateJuPrize(JuPrize juPrize) {
        juPrizeDao.save(juPrize);
    }

    public void deleteJuPrize(String id) {
        juPrizeDao.delete(id);
    }

    public JuPrize getJuPrize(String id) {
        return juPrizeDao.getJuPrize(id);
    }

    public List<JuPrize> listJuPrize(){
        return juPrizeDao.listJuPrize();
    }
}
