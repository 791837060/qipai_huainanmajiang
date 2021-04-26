package com.anbang.qipai.qinyouquan.plan.dao.mongodb;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTableStateConfig;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.dao.GameTableDao;
import com.anbang.qipai.qinyouquan.plan.dao.mongodb.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbGameTableDao implements GameTableDao {

    @Autowired
    private GameTableRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GameTable gameTable) {
        repository.save(gameTable);
    }

    @Override
    public GameTable findTableOpen(String no) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").is(no));
        query.addCriteria(Criteria.where("state").in(GameTableStateConfig.WAITING, GameTableStateConfig.PLAYING));
        return mongoTemplate.findOne(query, GameTable.class);
    }

    @Override
    public GameTable findTableByGameAndServerGameGameId(Game game, String serverGameId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("game").is(game).andOperator(Criteria.where("serverGame.gameId").is(serverGameId)));
        return mongoTemplate.findOne(query, GameTable.class);
    }

    @Override
    public List<GameTable> findExpireGameTable(long deadlineTime, String state) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deadlineTime").lte(deadlineTime));
        query.addCriteria(Criteria.where("state").is(state));
        return mongoTemplate.find(query, GameTable.class);
    }

    @Override
    public List<GameTable> findGameTableByLianmengId(String lianmengId, int page, int size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId)
                .andOperator(Criteria.where("state").in(GameTableStateConfig.PLAYING, GameTableStateConfig.WAITING)));
        query.skip((page - 1) * size);
        query.limit(size);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.find(query, GameTable.class);
    }

    @Override
    public void updateStateGameTable(Game game, String serverGameId, String state) {
        Query query = new Query();
        query.addCriteria(Criteria.where("game").is(game));
        query.addCriteria(Criteria.where("serverGame.gameId").is(serverGameId));
        Update update = new Update();
        update.set("state", state);
        mongoTemplate.updateFirst(query, update, GameTable.class);
    }

    @Override
    public void updateGameTableDeadlineTime(Game game, String serverGameId, long deadlineTime) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("game").is(game).andOperator(Criteria.where("serverGame.gameId").is(serverGameId)));
        Update update = new Update();
        update.set("deadlineTime", deadlineTime);
        mongoTemplate.updateFirst(query, update, GameTable.class);
    }

    @Override
    public List<GameTable> findGameTableByMemberIdAndLianmengIdAndGameAndTime(int page, int size, String memberId,
                                                                              String lianmengId,Game game, long startTime, long endTime,String no,String state) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("players").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(no)){
            query.addCriteria(Criteria.where("no").is(no));
        }
        if (!StringUtils.isEmpty(state)){
            query.addCriteria(Criteria.where("state").is(state));
        }
        if (!StringUtils.isEmpty(game)) {
            query.addCriteria(Criteria.where("game").is(game));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("createTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameTable.class);
    }

    @Override
    public long countGameTableByMemberIdAndLianmengIdAndGameAndTime(String memberId, String lianmengId, long startTime, long endTime,String no,String state) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("players").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(no)){
            query.addCriteria(Criteria.where("no").is(no));
        }
        if (!StringUtils.isEmpty(state)){
            query.addCriteria(Criteria.where("state").is(state));
        }
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("createTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, GameTable.class);
    }


    @Override
    public void updateGameTableCurrentPanNum(Game game, String serverGameId, int no) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("game").is(game).andOperator(Criteria.where("serverGame.gameId").is(serverGameId)));
        Update update = new Update();
        update.set("currentPanNum", no);
        mongoTemplate.updateFirst(query, update, GameTable.class);
    }
    @Override
    public void deleteGameTableByNo(String roomNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("no").is(roomNo));
        mongoTemplate.remove(query,GameTable.class);
    }

    @Override
    public void updateGameMemberTable(String gameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("serverGame.gameId").is(gameId));
        Update update = new Update();
        update.set("juFinish", true);
        mongoTemplate.updateMulti(query, update, GameTable.class);
    }

    @Override
    public List<GameTable> findGameTableByMemberId(int page, int size, String memberId,long startTime,long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("players").is(memberId));
        }
        if (startTime>0 && endTime>0){
            query.addCriteria(Criteria.where("deadlineTime").gt(startTime).lt(endTime));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GameTable.class);
    }

    @Override
    public GameHistoricalJuResult getJuResultByRoomNo(int page,int size,String gameId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(gameId)){
            query.addCriteria(Criteria.where("gameId").is(gameId));
        }
        query.skip((page-1)*size);
        query.limit(size);
        return mongoTemplate.findOne(query,GameHistoricalJuResult.class);
    }

    @Override
    public GameTable findGameTableByRoomNo(int page, int size, String no,String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(no)){
            query.addCriteria(Criteria.where("no").is(no));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("players").is(memberId));
        }
        query.skip((page-1)*size);
        query.limit(size);
        return mongoTemplate.findOne(query,GameTable.class);
    }
}
