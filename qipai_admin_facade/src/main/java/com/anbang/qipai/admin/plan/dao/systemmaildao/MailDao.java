package com.anbang.qipai.admin.plan.dao.systemmaildao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.anbang.qipai.admin.plan.bean.mail.MailState;
import com.anbang.qipai.admin.plan.bean.mail.SystemMail;


/**设置邮件service
 * @author 程佳 2018.6.6
 * **/
public interface MailDao {
	
	/**查询所有邮件
	 * @return 所有邮件信息
	 * **/
	Map<String,Object> findall(Query query,Pageable pageable,Integer size);
	
	/**查询所有会员的所有邮件状态
	 * **/
	List<MailState> find_mail_record(Query query,Pageable pageable);
	
	/**查询总会员条数
	 * @param query 查询条件
	 * @param return 总条数
	 * **/
	int count(Query query);
	
	/**添加邮件
	 * @param mail 要添加的邮件对象
	 * **/
	void addmail(SystemMail mail);
	
	/**修改邮件状态*/
	void updateMailState(MailState mailState);
	
	/**添加邮件状态
	 * @param mailState 要添加的邮件状态对象
	 * **/
	void addMailById(MailState mailState);
	
	/**根据邮件id查询邮件*/
	SystemMail findMailById(String id);
	
	/**根据条件查询多个邮件信息**/
	List<SystemMail> findSystemMail(String mailType,String adminName);
	
	/**查询一个会员的所有邮件
	 * @param memberid 会员编号
	 * **/
	List<MailState> findallmembermail(String memberid);
	
	/**根据邮件状态id查询邮件状态表
	 * **/
	MailState findMailStateById(String id);
	
	
	
	

}
