package com.anbang.qipai.admin.plan.dao.mongodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.mail.MailState;
import com.anbang.qipai.admin.plan.bean.mail.SystemMail;
import com.anbang.qipai.admin.plan.dao.systemmaildao.MailDao;


@Component
public class MongodbMailDao implements MailDao{

	@Autowired
	private MongoTemplate mongotemplate;
	
	@Override
	public Map<String,Object> findall(Query query,Pageable pageable,Integer size) {
		 Map<String,Object> map = new HashMap<String,Object>();
		 Long total = mongotemplate.count(query, SystemMail.class);
		 Long count = total%size==0?total/size:total/size+1;
		 List<SystemMail> list = mongotemplate.find(query.with(pageable),SystemMail.class);
		 map.put("list", list);
		 map.put("count",count);
		return map;
	}

	@Override
	public void addmail(SystemMail mail) {
		mongotemplate.insert(mail);
	}

	@Override
	public void addMailById(MailState mailState) {
		mongotemplate.insert(mailState);
	}

	@Override
	public List<MailState> find_mail_record(Query query, Pageable pageable) {
		return mongotemplate.find(query.with(pageable),MailState.class);
	}

	@Override
	public int count(Query query) {
		int total = (int) mongotemplate.count(query,MailState.class);
		return total;
	}

	@Override
	public SystemMail findMailById(String id) {
		return mongotemplate.findById(id, SystemMail.class);
	}

	@Override
	public void updateMailState(MailState mailState) {
		Query query = new Query(Criteria.where("id").is(mailState.getId()));
		Update update = new Update();
		update.set("statemail",mailState.getStatemail());
		update.set("receive",mailState.getReceive());
		update.set("deletestate",mailState.getDeletestate());
		update.set("rewardTime",mailState.getRewardTime());
		mongotemplate.updateFirst(query, update, MailState.class);
	}

	@Override
	public List<MailState> findallmembermail(String memberid) {
		Query query = new Query(Criteria.where("memberid").is(memberid));
		return mongotemplate.find(query,MailState.class);
	}

	@Override
	public MailState findMailStateById(String id) {
		return mongotemplate.findById(id, MailState.class);
	}

	@Override
	public List<SystemMail> findSystemMail(String mailType,String adminName) {
		Query query = new Query();
		if(mailType != null && !"".equals(mailType)) {
			query.addCriteria(Criteria.where("mailType").is(mailType));
		}
		if(adminName != null && !"".equals(adminName)) {
			query.addCriteria(Criteria.where("adminname").is(adminName));
		}
		return mongotemplate.find(query, SystemMail.class);
	}

	
}
