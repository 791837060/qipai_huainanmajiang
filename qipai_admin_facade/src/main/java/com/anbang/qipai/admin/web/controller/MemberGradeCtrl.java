package com.anbang.qipai.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.grade.MemberGrade;
import com.anbang.qipai.admin.plan.service.MemberGradeService;
import com.anbang.qipai.admin.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * 会员等级controller
 **/
@CrossOrigin
@RestController
@RequestMapping("/grade")
public class MemberGradeCtrl {

	@Autowired
	private MemberGradeService memberGradeService;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersService;

	@RequestMapping(value = "/insert_grade", method = RequestMethod.POST)
	@ResponseBody
	public CommonVO insert_grade(MemberGrade memberGradeDbo) {
		CommonVO co = new CommonVO();
		qipaiMembersService.grade_insert_grade(memberGradeDbo);
		return co;
	}

	@RequestMapping(value = "/find_grade", method = RequestMethod.POST)
	@ResponseBody
	public CommonVO find_grade() {
		CommonVO co = new CommonVO();
		MemberGrade memberGrade = memberGradeService.find_grade();
		co.setData(memberGrade);
		return co;
	}
}
