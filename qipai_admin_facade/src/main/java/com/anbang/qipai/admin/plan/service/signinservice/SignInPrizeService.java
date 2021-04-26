package com.anbang.qipai.admin.plan.service.signinservice;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.members.SendOutGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.msg.service.SignInPrizeSourceService;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrize;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeDao;


@Service
public class SignInPrizeService {

    @Autowired
    private SignInPrizeDao signInPrizeDao;

    @Autowired
    private SignInPrizeSourceService signInPrizeSourceService;

    /**
     * 添加签到奖品
     *
     * @param signInPrize
     */
    public void addSignInPrize(SignInPrize signInPrize) {
        signInPrizeDao.addSignInPrize(signInPrize);
    }

    /**
     * 修改签到奖品
     *
     * @param signInPrize
     */
    public void updateSignInPrize(SignInPrize signInPrize) {
        signInPrizeDao.updateSignInPrize(signInPrize);
    }

    /**
     * 查询所有签到奖品
     *
     * @return
     */
    public List<SignInPrize> querySignInPrize() {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "id")));
        return signInPrizeDao.querySignInPrize();
    }

    /**
     * 根据id查询签到奖品
     *
     * @param id
     * @return
     */
    public SignInPrize querySignInPrizeById(String id) {
        return signInPrizeDao.querySignInPrizeById(id);
    }

    /**
     * 根据id删除签到奖品
     *
     * @param id
     */
    public void deleteSignInPrizeById(String id) {
        signInPrizeDao.deleteSignInPrizeById(id);
    }


    /**
     * 根据id删除签到奖品
     *
     * @param id
     */
    public void deleteSignInPrizeByIdAdvice(String id) {
        SignInPrize signInPrize = signInPrizeDao.querySignInPrizeById(id);
        SignInPrize signInPrizeNew = new SignInPrize();
        signInPrizeNew.setId(id);
        signInPrizeNew.setIndex(signInPrize.getIndex());

        signInPrizeDao.save(signInPrizeNew);

    }


    //查看没有超出奖池的奖品
    public int countSignInPrize() {
        return (int) signInPrizeDao.countSignInPrize();
    }

    public int findIndex() {
        return signInPrizeDao.findIndex();
    }

    public void releaseSignInPrize() {
        signInPrizeSourceService.releaseSignInPrize();
        signInPrizeDao.releaseSignInPrize();
    }

    public void decreaseStoreById(String id) {
        signInPrizeDao.decreaseStoreById(id);
    }


}
