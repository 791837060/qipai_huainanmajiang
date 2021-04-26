//import com.anbang.qipai.admin.QipaiAdminApplication;
//import com.anbang.qipai.admin.plan.bean.tasks.HongbaodianProduct;
//import com.anbang.qipai.admin.plan.bean.tasks.ExchangeRecord;
//import com.anbang.qipai.admin.plan.bean.tasks.TaskReceiveRecord;
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
//public class TaskTest {
//
//    @Autowired
//    private TaskController taskController;
//
//    @Autowired
//    private TaskReceiveRecordService taskReceiveRecordService;
//
//    @Autowired
//    private ExchangeManageService exchangeManageService;
//
//    @Autowired
//    private ExchangeRecordService exchangeRecordService;
//
//    @Test
//    public void addTaskRecord (){
//        TaskReceiveRecord taskReceiveRecord = new TaskReceiveRecord();
//        taskReceiveRecord.setId("1001");
//        taskReceiveRecord.setLoginIp("192.168.");
//        taskReceiveRecord.setNickName("anme");
//        taskReceiveRecord.setPlayerId("333");
//        taskReceiveRecord.setReceiveTime(System.currentTimeMillis());
//        taskReceiveRecord.setRewardType("type");
//        taskReceiveRecord.setRewardCount(100);
//        taskReceiveRecord.setTaskName("hongbao");
//        taskReceiveRecordService.addTaskRceiveRecord(taskReceiveRecord);
//        CommonVO vo = taskController.queryTaskReceiveRecords(1,10, null);
//    }
//
//    @Test
//    public void addExchangeRecord (){
//        ExchangeRecord exchangeRecord = new ExchangeRecord();
//        exchangeRecord.setId("11001");
//        exchangeRecord.setPlayerId("1111");
//        exchangeRecord.setNickName("player");
//        exchangeRecord.setLoginIp("IP");
//        exchangeRecord.setExchangeTime(System.currentTimeMillis());
//        exchangeRecord.setExchangeType("type");
//        exchangeRecord.setExchangeAmount(33);
//        exchangeRecord.setItemName("6元");
//        exchangeRecordService.addExchangeRecord(exchangeRecord);
//        CommonVO vo = taskController.queryExchangeRecords(1,10,null);
//    }
//
//}
