package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopOrder;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanShopOrderDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanShopOrderRepository;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanShopOrderVO;

@Component
public class MongodbChaguanShopOrderDao implements ChaguanShopOrderDao {

	@Autowired
	private ChaguanShopOrderRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(ChaguanShopOrder order) {
		repository.save(order);
	}

	@Override
	public long countByConditions(ChaguanShopOrderVO order) {
		Query query = new Query();
		if (order.getId() != null && !"".equals(order.getId())) {
			query.addCriteria(Criteria.where("id").is(order.getId()));
		}
		if (order.getPay_type() != null && !"".equals(order.getPay_type())) {
			query.addCriteria(Criteria.where("pay_type").is(order.getPay_type()));
		}
		if (order.getPayerId() != null && !"".equals(order.getPayerId())) {
			query.addCriteria(Criteria.where("payerId").is(order.getPayerId()));
		}
		if (order.getStatus() != null && !"".equals(order.getStatus())) {
			if ("NOTPAY".equals(order.getStatus())) {
				query.addCriteria(Criteria.where("status").in("WAIT_BUYER_PAY", "USERPAYING"));
			}
			if ("PAYFAIL".equals(order.getStatus())) {
				query.addCriteria(
						Criteria.where("status").in("TRADE_CLOSED", "CLOSED", "REFUND", "REVOKED", "PAYERROR"));
			}
			if ("PAYSUCCESS".equals(order.getStatus())) {
				query.addCriteria(Criteria.where("status").in("TRADE_SUCCESS", "TRADE_FINISHED", "SUCCESS"));
			}
		}
		if (order.getPayerName() != null && !"".equals(order.getPayerName())) {
			query.addCriteria(Criteria.where("payerName").regex(order.getPayerName()));
		}
		if (order.getStartTime() != null || order.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (order.getStartTime() != null) {
				criteria = criteria.gte(order.getStartTime());
			}
			if (order.getEndTime() != null) {
				criteria = criteria.lte(order.getEndTime());
			}
			query.addCriteria(criteria);
		}
		return mongoTemplate.count(query, ChaguanShopOrder.class);
	}

	@Override
	public List<ChaguanShopOrder> findByConditions(int page, int size, ChaguanShopOrderVO order) {
		Query query = new Query();
		if (order.getId() != null && !"".equals(order.getId())) {
			query.addCriteria(Criteria.where("id").is(order.getId()));
		}
		if (order.getPay_type() != null && !"".equals(order.getPay_type())) {
			query.addCriteria(Criteria.where("pay_type").is(order.getPay_type()));
		}
		if (order.getPayerId() != null && !"".equals(order.getPayerId())) {
			query.addCriteria(Criteria.where("payerId").is(order.getPayerId()));
		}
		if (order.getStatus() != null && !"".equals(order.getStatus())) {
			if ("NOTPAY".equals(order.getStatus())) {
				query.addCriteria(Criteria.where("status").in("WAIT_BUYER_PAY", "USERPAYING"));
			}
			if ("PAYFAIL".equals(order.getStatus())) {
				query.addCriteria(
						Criteria.where("status").in("TRADE_CLOSED", "CLOSED", "REFUND", "REVOKED", "PAYERROR"));
			}
			if ("PAYSUCCESS".equals(order.getStatus())) {
				query.addCriteria(Criteria.where("status").in("TRADE_SUCCESS", "TRADE_FINISHED", "SUCCESS"));
			}
		}
		if (order.getPayerName() != null && !"".equals(order.getPayerName())) {
			query.addCriteria(Criteria.where("payerName").regex(order.getPayerName()));
		}
		if (order.getStartTime() != null || order.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (order.getStartTime() != null) {
				criteria = criteria.gte(order.getStartTime());
			}
			if (order.getEndTime() != null) {
				criteria = criteria.lte(order.getEndTime());
			}
			query.addCriteria(criteria);
		}
		// 需要建立索引
		query.with(order.getSort());
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, ChaguanShopOrder.class);
	}

}
