package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import com.anbang.qipai.admin.plan.bean.tasks.WhiteList;
import com.anbang.qipai.admin.plan.dao.tasksdao.WhiteListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yins
 * @Description: TODO
 */
@Component
public class MongodbWhiteListDao implements WhiteListDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addWhiteList(WhiteList whiteList) {
        mongoTemplate.insert(whiteList);
    }

    @Override
    public void deleteWhiteList(String[] ids) {
        Object[] idsTemp = ids;
        Query query = new Query(Criteria.where("id").in(idsTemp));
        mongoTemplate.remove(query, WhiteList.class);

    }

    @Override
    public List<WhiteList> findWhiteLists(int page, int size, List<String> playerIds,boolean flag) {
        Query query = new Query();
        if (flag) {
            query.addCriteria(Criteria.where("playerId").in(playerIds));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, WhiteList.class);
    }

    @Override
    public long countWhiteList(List<String> playerIds,boolean flag) {
        Query query = new Query();
        if (flag) {
            query.addCriteria(Criteria.where("playerId").in(playerIds));
        }
        return mongoTemplate.count(query, WhiteList.class);
    }
}
