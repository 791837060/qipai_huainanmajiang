package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberYushi;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberYushiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/12/9 10:48
 */
@Component
public class MongodbMemberYushiDao implements MemberYushiDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MemberYushi> findAllYushi() {
        return mongoTemplate.findAll(MemberYushi.class);
    }

    @Override
    public MemberYushi getYushiById(String yushiId) {
        Query query = new Query(Criteria.where("id").is(yushiId));
        return mongoTemplate.findOne(query, MemberYushi.class);
    }

    @Override
    public void addYushi(MemberYushi yushi) {
        mongoTemplate.insert(yushi);
    }

    @Override
    public void deleteYushiByIds(String[] yushiId) {
        Object[] ids = yushiId;
        Query query = new Query(Criteria.where("id").in(ids));
        mongoTemplate.remove(query, MemberYushi.class);
    }

    @Override
    public void updateYushi(MemberYushi yushi) {
        Query query = new Query(Criteria.where("id").is(yushi.getId()));
        Update update = new Update();
        update.set("name", yushi.getName());
        update.set("price", yushi.getPrice());
        update.set("firstDiscount", yushi.getFirstDiscount());
        update.set("firstDiscountPrice", yushi.getFirstDiscountPrice());
        update.set("yushi", yushi.getYushi());
        mongoTemplate.updateFirst(query, update, MemberYushi.class);
    }
}
