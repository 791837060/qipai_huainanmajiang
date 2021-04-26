package com.anbang.qipai.dalianmeng.plan.dao.mongodb;


import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalJuResultDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndLianmengIdAndRoomNoAndTime(int page,
                                                                                                        int size, String lianmengId, String memberId, String roomNo, long startTime, long endTime) {
        Query query = new Query();
        if (StringUtil.isNotBlank(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (StringUtil.isNotBlank(roomNo)) {
            query.addCriteria(Criteria.where("roomNo").is(roomNo));
        }
        if (StringUtil.isNotBlank(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.addCriteria(Criteria.where("finishTime").gt(startTime).lte(endTime));
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "finishTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByMemberIdAndLianmengIdAndAndRoomNoTime(String lianmengId, String memberId, String roomNo,
                                                                 long startTime, long endTime) {
        Query query = new Query();
        if (StringUtil.isNotBlank(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        if (StringUtil.isNotBlank(roomNo)) {
            query.addCriteria(Criteria.where("roomNo").is(roomNo));
        }
        if (StringUtil.isNotBlank(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.addCriteria(Criteria.where("finishTime").gt(startTime).lte(endTime));
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
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("finishTime").lt(endTime)), GameHistoricalJuResult.class);
    }

    @Override
    public long countLianmengTotalGames(String lianmengId, String memberId, Identity identity, long startTime, long endTime) {
        Query query = new Query(Criteria.where("lianmengId").is(lianmengId));
        if (!identity.equals(Identity.MENGZHU)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        }
        query.addCriteria(Criteria.where("finishTime").gt(startTime).lte(endTime));
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public long countLianmengTotalGamesByMemberIdAndLianMemberId(List<String> memberIdS, String lianmengId, long startTime, long endTime) {
        long count = 0;
        for (String memberId : memberIdS) {
            Query query = new Query();
            query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
            query.addCriteria(Criteria.where("lianmengId.playerId").is(lianmengId));
            query.addCriteria(Criteria.where("finishTime").gt(startTime).lte(endTime));
            count += mongoTemplate.count(query, GameHistoricalJuResult.class);
        }
        return count;
    }

    @Override
    public long countgameBymember(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("playerResultList.playerId").is(memberId));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("finishTime").gt(startTime).lt(endTime));
        }
        //query.addCriteria(Criteria.where("finishTime").gt(startTime).lt(endTime));
        return mongoTemplate.count(query, GameHistoricalJuResult.class);
    }

    @Override
    public long getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(String lianmengId, List<String> memberId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").in(memberId));
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
    public List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(int page, int size, String lianmengId, List<String> memberId,
                                                                                                                        long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("playerResultList.playerId").in(memberId));
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
}
