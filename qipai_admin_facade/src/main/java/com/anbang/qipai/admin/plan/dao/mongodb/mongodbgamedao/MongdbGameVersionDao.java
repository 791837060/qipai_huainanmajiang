package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import com.anbang.qipai.admin.plan.bean.games.GameVersion;
import com.anbang.qipai.admin.plan.dao.gamedao.GameVersionDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongdbGameVersionDao implements GameVersionDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GameVersion gameVersion) {
        mongoTemplate.insert(gameVersion);
    }

    @Override
    public void remove(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, GameVersion.class);
    }

    @Override
    public long countByBean(GameVersion gameVersion) {
        Query query = new Query();
        if (StringUtils.isNotBlank(gameVersion.getVersionNo())) {
            query.addCriteria(Criteria.where("versionNo").is(gameVersion.getVersionNo()));
        }
        if (StringUtils.isNotBlank(gameVersion.getRemark())) {
            query.addCriteria(Criteria.where("remark").regex(gameVersion.getRemark()));
        }
        return mongoTemplate.count(query, GameVersion.class);
    }

    @Override
    public List<GameVersion> findByBean(int page, int size, GameVersion gameVersion) {
        Query query = new Query();
        if (StringUtils.isNotBlank(gameVersion.getVersionNo())) {
            query.addCriteria(Criteria.where("versionNo").is(gameVersion.getVersionNo()));
        }
        if (StringUtils.isNotBlank(gameVersion.getRemark())) {
            query.addCriteria(Criteria.where("remark").regex(gameVersion.getRemark()));
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameVersion.class);
    }

    @Override
    public GameVersion findLastRecord(String gameType) {
        Query query = new Query(Criteria.where("gameType").is(gameType));
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        query.with(sort);
        return mongoTemplate.findOne(query, GameVersion.class);
    }
}
