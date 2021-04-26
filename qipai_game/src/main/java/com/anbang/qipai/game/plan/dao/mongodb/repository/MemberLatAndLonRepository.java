package com.anbang.qipai.game.plan.dao.mongodb.repository;

import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberLatAndLonRepository extends MongoRepository<MemberLatAndLon,String> {
}
