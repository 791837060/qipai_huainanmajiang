package com.anbang.qipai.qinyouquan.plan.dao.mongodb;


import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GamePayType;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.dao.GameHistoricalJuResultDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongodbGameHistoricalJuResultDao implements GameHistoricalJuResultDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addGameHistoricalResult(GameHistoricalJuResult result) {
        mongoTemplate.insert(result);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndMemberIdAndTime(int page, int size, Game game, String lianmengId, String memberId, long startTime, long endTime) {
        Query query = new Query();
        if (game != null) {
            query.addCriteria(Criteria.where("game").is(game));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByLianmengIdAndMemberIdAndTime(Game game, String lianmengId, String memberId, long startTime, long endTime) {
        Query query = new Query();
        if (game != null) {
            query.addCriteria(Criteria.where("game").is(game));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(int page, int size, String memberId, long startTime, long endTime) {
        Query query = new Query(Criteria.where("playerResultList.playerId").is(memberId));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));

        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByMemberIdAndTime(String memberId, long startTime, long endTime) {
        Query query = new Query(Criteria.where("playerResultList.playerId").is(memberId));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public GameHistoricalJuResult findGameHistoricalResultById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, GameHistoricalJuResult.class);
    }

    @Override
    public long countGameNumByGameAndTime(Game game, long startTime, long endTime) {
//        List<DBObject> pipeline = new ArrayList<>();
//        BasicDBObject match = new BasicDBObject();
//        BasicDBObject criteria = new BasicDBObject("$gt", startTime);
//        criteria.put("$lt", endTime);
//        match.put("finishTime", criteria);
//        match.put("game", game.name());
//        DBObject queryMatch = new BasicDBObject("$match", match);
//        pipeline.add(queryMatch);
//
//        BasicDBObject group = new BasicDBObject();
//        group.put("_id", null);
//        group.put("num", new BasicDBObject("$sum", "$lastPanNo"));
//        DBObject queryGroup = new BasicDBObject("$group", group);
//        pipeline.add(queryGroup);
//        Cursor cursor = mongoTemplate.getCollection("gameHistoricalJuResult").aggregate(pipeline,
//                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
//        try {
//            return (int) cursor.next().get("num");
//        } catch (Exception e) {
//            return 0;
//        }
        //统计的场次
        Query query = new Query();
        query.addCriteria(Criteria.where("game").is(game));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("finishTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public long countDayingjiaByMemberIdAndTime(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("dayingjiaId").is(memberId));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("finishTime").gt(startTime).lt(endTime));
        }

        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public GameHistoricalJuResult getJuResultByGameId(String gameId) {
        Query query = new Query(Criteria.where("gameId").is(gameId));
        return mongoTemplate.findOne(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(Game game, String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query(Criteria.where("lianmengId").is(lianmengId));
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (!StringUtils.isEmpty(game)) {
            query.addCriteria(Criteria.where("game").is(game));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public void memberJuResultFinish(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("dayingjiaId").is(memberId));
        query.addCriteria(Criteria.where("finish").is(false));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        Update update = new Update();
        update.set("finish", true);
        mongoTemplate.updateMulti(query, update, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndTime(int page, int size, String lianmengId,
                                                                                                       String superiorMemberId, long startTime, long endTime, boolean finish) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        query.addCriteria(Criteria.where("finish").is(finish));
        query.addCriteria(Criteria.where("payType").is(GamePayType.DAYINGJIA));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByLianmengIdAndSuperiorMemberIdAndTime(String lianmengId, String superiorMemberId, long startTime, long endTime, boolean finish) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        query.addCriteria(Criteria.where("finish").is(finish));
        query.addCriteria(Criteria.where("payType").is(GamePayType.DAYINGJIA));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public void juResultFinish(String gameId) {
        Query query = new Query(Criteria.where("gameId").is(gameId));
        Update update = new Update();
        update.set("finish", true);
        mongoTemplate.updateMulti(query, update, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(int page, int size, String lianmengId, String memberId,
                                                                                                                        String superiorMemberId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(new Criteria().orOperator(Criteria.where("playerResultList.playerId").is(superiorMemberId), Criteria.where("playerResultList.superiorMemberId").is(superiorMemberId)));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(String lianmengId, String memberId, String superiorMemberId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(new Criteria().orOperator(Criteria.where("playerResultList.playerId").is(superiorMemberId), Criteria.where("playerResultList.superiorMemberId").is(superiorMemberId)));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByTime(String lianmengId, long startTime, long endTime) {
        Query query = new Query(Criteria.where("lianmengId").is(lianmengId));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndMemberIdAndTime(int page, int size, String lianmengId,
                                                                                               String searchMemberId, long startTime, long endTime, boolean finish) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(searchMemberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(searchMemberId));
        }
        query.addCriteria(Criteria.where("finish").is(finish));
        query.addCriteria(Criteria.where("payType").is(GamePayType.DAYINGJIA));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByLianmengIdAndMemberIdAndTime(String lianmengId, String searchMemberId, long startTime, long endTime, boolean finish) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(searchMemberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(searchMemberId));
        }
        query.addCriteria(Criteria.where("finish").is(finish));
        query.addCriteria(Criteria.where("payType").is(GamePayType.DAYINGJIA));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> queryLianmengGoldCost(String lianmengId, Game game, Long startTime, Long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(game)) {
            query.addCriteria(Criteria.where("game").is(game));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("finishTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> querymemberGoldRecord(int page, int size, String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(memberId));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public List<GameHistoricalJuResult> queryHistoricalJuResult(long startTime, long endTime) {
        Query query = new Query();
        if (startTime > 0 && endTime > 0) {
            query.addCriteria(Criteria.where("finishTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }
}
