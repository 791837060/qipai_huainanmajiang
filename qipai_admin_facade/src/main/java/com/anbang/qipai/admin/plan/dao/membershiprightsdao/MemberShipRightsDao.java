package com.anbang.qipai.admin.plan.dao.membershiprightsdao;

import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
/**会员权益service
 * @author 程佳 2018.5.31
 * **/
public interface MemberShipRightsDao {
	
	/**会员权益
	 * @Param commonuser 普通会员权益信息 
	 * **/
	MemberShipRights saveMemberShipRights(MemberShipRights commonuser);
	
	MemberShipRights findallCommonUser();
	
	void updateGameMemberShipRights(MemberShipRights commonuser);
	
	void updateMembersMemberShipRights(MemberShipRights commonuser);

}
