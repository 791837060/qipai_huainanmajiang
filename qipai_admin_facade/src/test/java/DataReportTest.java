//import com.anbang.qipai.admin.QipaiAdminApplication;
//import com.anbang.qipai.admin.plan.bean.members.CardSouceEnum;
//import com.anbang.qipai.admin.plan.bean.members.MemberType;
//import com.anbang.qipai.admin.plan.bean.tasks.HongbaodianProduct;
//import com.anbang.qipai.admin.plan.bean.tasks.ExchangeRecord;
//import com.anbang.qipai.admin.plan.bean.tasks.TaskReceiveRecord;
//import com.anbang.qipai.admin.plan.service.membersservice.MemberTypeService;
//import com.anbang.qipai.admin.plan.service.tasksservice.ExchangeManageService;
//import com.anbang.qipai.admin.plan.service.tasksservice.ExchangeRecordService;
//import com.anbang.qipai.admin.plan.service.tasksservice.TaskReceiveRecordService;
//import com.anbang.qipai.admin.web.controller.TaskController;
//import com.anbang.qipai.admin.web.vo.CommonVO;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author yins
// * @Description: 任务测试
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = QipaiAdminApplication.class)
//public class DataReportTest {
//
//    @Autowired
//    private MemberTypeService memberTypeService;
//
//    @Test
//    public void insert (){
//        MemberType memberType = new MemberType();
//        memberType.setId("982807");
//        memberType.setCardSource(CardSouceEnum.AGENT);
//        memberType.setPay(true);
//        memberType.setCardType("周卡");
//        memberType.setVipEndTime(1544954075500L);
//        memberType.setValid(true);
//        memberTypeService.saveMemberType(memberType);
//    }
//
//
//
//}
