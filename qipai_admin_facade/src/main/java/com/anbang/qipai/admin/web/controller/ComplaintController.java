package com.anbang.qipai.admin.web.controller;


import com.anbang.qipai.admin.plan.service.ComplaintService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;


    @RequestMapping("/querymemberComplaint")
    public CommonVO querymemberComplaint(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,String memberId){
        CommonVO vo = new CommonVO();
        ListPage listPage=complaintService.find(page,size,memberId);
        vo.setData(listPage);
        vo.setMsg("查询成功");
        return vo;
    }
}
