package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopProduct;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanShopProductDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanShopProductRepository;

@Component
public class MongodbChaguanShopProductDao implements ChaguanShopProductDao {

	@Autowired
	private ChaguanShopProductRepository repository;

	@Autowired
	private MongoTemplate mognoTemplate;

	@Override
	public void save(ChaguanShopProduct product) {
		repository.save(product);
	}

	@Override
	public void removeByIds(String[] productIds) {
		Object[] ids = productIds;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").in(ids));
		mognoTemplate.remove(query, ChaguanShopProduct.class);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public List<ChaguanShopProduct> findByConditions(int page, int size) {
		Query query = new Query();
		query.skip((page - 1) * size);
		query.limit(size);
		query.with(new Sort(new Order(Direction.ASC, "weight")));
		return mognoTemplate.find(query, ChaguanShopProduct.class);
	}

}
