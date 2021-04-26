package com.anbang.qipai.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.remote.service.QipaiXiuxianchangRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * 休闲场管理
 * 
 * @author lsc
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/xiuxianchang")
public class XiuxianchangController {

	@Autowired
	private QipaiXiuxianchangRemoteService qipaiXiuxianchangRemoteService;

	@RequestMapping(value = "/queryplayingroom", method = RequestMethod.POST)
	public CommonVO queryMemberPlayingRoom(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		CommonRemoteVO rvo = qipaiXiuxianchangRemoteService.game_queryMemberPlayingRoom(page, size);
		data.put("roomList", rvo.getData());
		return vo;
	}
}
