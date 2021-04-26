package com.anbang.qipai.admin.plan.dao.signindao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrize;

public interface SignInPrizeDao {

    void addSignInPrize(SignInPrize signInPrize);

    void updateSignInPrize(SignInPrize signInPrize);

    List<SignInPrize> querySignInPrize();

    SignInPrize querySignInPrizeById(String id);

    void deleteSignInPrizeById(String id);

    long countSignInPrize();

    void releaseSignInPrize();

    void decreaseStoreById(String id);

    int findIndex();

    void save(SignInPrize signInPrize);
}
