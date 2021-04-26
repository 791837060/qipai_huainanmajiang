package com.anbang.qipai.admin.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.grade.MemberGrade;
import com.anbang.qipai.admin.plan.dao.gradedao.MemberGradeDao;

@Service
public class MemberGradeService {
	
	@Autowired
	private MemberGradeDao memberGradeDao;
	
	public void insert_grade(MemberGrade memberGrade) {
		memberGradeDao.insert_grade(memberGrade);
	}
	
	public MemberGrade find_grade() {
		return memberGradeDao.find_grade("1");
	}

}
