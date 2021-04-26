package com.anbang.qipai.admin.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
*@author   created by hanzhuofan  2018.09.26
*/
public interface MemberClubCardsSource {
	String channel = "memberClubCard";
	String addClubCard = "addClubCard";
	String deleteClubCard = "deleteClubCard";
	String updateClubCard = "updateClubCard";
	
	@Output
    MessageChannel memberClubCard();
}
