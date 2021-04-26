package com.anbang.qipai.game.plan.dao.mongodb;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.*;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.BijiJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.DoudizhuJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.GuandanJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.puke.PaodekuaiJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.zipai.BohuJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.zipai.HuangshibaJuPlayerResult;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import com.anbang.qipai.game.plan.dao.MemberLatAndLonDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.MemberLatAndLonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MongodbMemberLatAndLonDao implements MemberLatAndLonDao {
    @Autowired
    private MemberLatAndLonRepository memberLatAndLonRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberLatAndLon memberLatAndLon) {
        memberLatAndLonRepository.save(memberLatAndLon);
    }

    @Override
    public List<MemberLatAndLon> find(List<String> memberIds, String roomNo) {
        List<MemberLatAndLon> memberLatAndLons=new ArrayList<>();
        for (String memberId : memberIds) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(memberId));
            query.addCriteria(Criteria.where("roomNo").is(roomNo));
            memberLatAndLons.add(mongoTemplate.findOne(query, MemberLatAndLon.class));
        }
        return memberLatAndLons;
    }

    @Override
    public MemberLatAndLon findBymemberId(String memberId) {
        Query query=new Query(Criteria.where("id").is(memberId));
        return mongoTemplate.findOne(query, MemberLatAndLon.class);
    }

    @Override
    public void del(String playerId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(playerId));
        query.addCriteria(criteria);
        mongoTemplate.remove(query, MemberLatAndLon.class);
    }

    @Override
    public void paodekuaiDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            PaodekuaiJuPlayerResult playerResult= (PaodekuaiJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void huangshibaDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            HuangshibaJuPlayerResult playerResult= (HuangshibaJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void bohuDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            BohuJuPlayerResult playerResult= (BohuJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void guandanDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            GuandanJuPlayerResult playerResult= (GuandanJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void yangzhouMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : juPlayerResultList) {
            YangzhouMajiangJuPlayerResult playerResult= (YangzhouMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void yizhengMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : juPlayerResultList) {
            YizhengMajiangJuPlayerResult playerResult= (YizhengMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void doudizhuDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            DoudizhuJuPlayerResult playerResult= (DoudizhuJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void bijiDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            BijiJuPlayerResult playerResult= (BijiJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void taizhouMajiangDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            TaizhouMajiangJuPlayerResult playerResult= (TaizhouMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void tianchangxiaohuaDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            TianchangxiaohuaJuPlayerResult playerResult= (TianchangxiaohuaJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void taixingMajiangDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            TaixingMajiangJuPlayerResult playerResult= (TaixingMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void gaoyouMajiangDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            GaoyouMajiangJuPlayerResult playerResult= (GaoyouMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void hongzhongMajiangDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            HongzhongMajiangJuPlayerResult playerResult= (HongzhongMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

    @Override
    public void maanshanMajiangDels(List<GameJuPlayerResult> playerResultList) {
        for (GameJuPlayerResult gameJuPlayerResult : playerResultList) {
            MaanshanMajiangJuPlayerResult playerResult= (MaanshanMajiangJuPlayerResult)gameJuPlayerResult;
            String playerId = playerResult.getPlayerId();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("id").is(playerId));
            query.addCriteria(criteria);
            mongoTemplate.remove(query, MemberLatAndLon.class);
        }
    }

}
