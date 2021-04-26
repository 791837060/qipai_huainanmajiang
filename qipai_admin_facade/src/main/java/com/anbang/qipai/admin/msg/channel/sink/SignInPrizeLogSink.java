package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
*@author   created by hanzhuofan  2018.09.18
*/
public interface SignInPrizeLogSink {

	String channel = "signInPrizeLog";
	String addSignInPrizeLog = "addSignInPrizeLog";
	String addSignInPrizeExchangeLog = "addSignInPrizeExchangeLog";

	String RAFFLE_RECORD = "RAFFLE_HISTORY";
	String OBTAIN_SIGN_PRIZE = "OBTAIN_SIGN_PRIZE_LOG";
	String PRIZE_EXCHANGE = "EXCHANGE_HONGBAO_PHONEFEE";
	String RAFFLE_HISTORY = "RAFFLE_HISTORY";
	String RAFFLE_HISTORY_ADDRESS = "RAFFLE_HISTORY_ADDRESS";

	@Input
	SubscribableChannel signInPrizeLog();
}
