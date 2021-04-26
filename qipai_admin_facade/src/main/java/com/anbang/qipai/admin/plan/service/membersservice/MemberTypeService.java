package com.anbang.qipai.admin.plan.service.membersservice;

import com.anbang.qipai.admin.plan.bean.members.MemberType;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberTypeDao;
import com.anbang.qipai.admin.util.CalculateUtils;
import com.anbang.qipai.admin.web.vo.reportvo.CommonRatioVo;
import com.anbang.qipai.admin.web.vo.reportvo.MemberRatioVo;
import com.mongodb.BasicDBObject;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 当前会员卡类型
 */
@Service
public class MemberTypeService {

    @Autowired
    private MemberTypeDao memberTypeDao;

    public void saveMemberType (MemberType memberType) {
        memberTypeDao.save(memberType);
    }

    public void removeMemberType (String id) {
        memberTypeDao.remove(id);
    }

    /**
     * 通过bean及时间点查询ids(查询所有数据：包括isVaild false)
     */
    public List<String> listIdsByBeanAndTime(MemberType memberType, Long time) {
        List<MemberType> memberTypes = memberTypeDao.listByBeanAndTime(memberType,time);
        List<String> ids = memberTypes.stream().map(MemberType::getId).collect(Collectors.toList());
        return ids;
    }

    /**
     * 通过bean及时间点查询MemberTypes
     */
    public List<MemberType> listByBean(MemberType memberType, long time) {
        return null;
    }

    /**
     * 查询各种玩家所占数量
     */
    public List<CommonRatioVo> queryRatio(){
        List<MemberRatioVo> ratioVos =  memberTypeDao.queryRatio(null);
        int payMemberSum = ratioVos.stream().mapToInt(MemberRatioVo::getNum).sum();

        for(MemberRatioVo list : ratioVos) {
            double ratio = CalculateUtils.div(list.getNum(),payMemberSum,4);
            list.setRadio(ratio);
        }

        List<CommonRatioVo> commonRatioVos = pieRatioList(ratioVos);
        return commonRatioVos;
    }

    /**
     * 根据ids查询并按属性进行分类
     */
    public List<CommonRatioVo>  queryRatio(List<String> ids){
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<MemberRatioVo> ratioVos =  memberTypeDao.queryRatio(ids);
        int totalSum = ids.size();

        for(MemberRatioVo list : ratioVos) {
            double ratio = CalculateUtils.div(list.getNum(),totalSum,4);
            list.setRadio(ratio);
        }
        List<CommonRatioVo> commonRatioVos = pieRatioList(ratioVos);

        //玉石玩家
        double yuRadio = 1 - ratioVos.stream().mapToDouble(MemberRatioVo::getRadio).sum();
        int yuNum = ids.size() - ratioVos.stream().mapToInt(MemberRatioVo::getNum).sum();
        CommonRatioVo ratioVo = new CommonRatioVo("玉石", yuNum, yuRadio);
        commonRatioVos.add(ratioVo);
        return commonRatioVos;
    }

    /**
     * 饼图拼装
     */
    private List<CommonRatioVo> pieRatioList(List<MemberRatioVo> memberRatioVos) {
        List<CommonRatioVo> ratioVos = new ArrayList<>();
        ratioVos.add(new CommonRatioVo("日卡"));
        ratioVos.add(new CommonRatioVo("周卡"));
        ratioVos.add(new CommonRatioVo("月卡"));
        ratioVos.add(new CommonRatioVo("季卡"));

        for (MemberRatioVo list : memberRatioVos) {
            if ("日卡".equals(list.getCardType())) {
                CommonRatioVo commonRatioVo = ratioVos.get(0);
                commonRatioVo.setNum(commonRatioVo.getNum() + list.getNum());
                commonRatioVo.setRatio(commonRatioVo.getRatio() + list.getRadio());
                commonRatioVo.getRatioVos().add(list);
            }
            if ("周卡".equals(list.getCardType())) {
                CommonRatioVo commonRatioVo = ratioVos.get(1);
                commonRatioVo.setNum(commonRatioVo.getNum() + list.getNum());
                commonRatioVo.setRatio(commonRatioVo.getRatio() + list.getRadio());
                commonRatioVo.getRatioVos().add(list);
            }
            if ("月卡".equals(list.getCardType())) {
                CommonRatioVo commonRatioVo = ratioVos.get(2);
                commonRatioVo.setNum(commonRatioVo.getNum() + list.getNum());
                commonRatioVo.setRatio(commonRatioVo.getRatio() + list.getRadio());
                commonRatioVo.getRatioVos().add(list);
            }
            if ("季卡".equals(list.getCardType())) {
                CommonRatioVo commonRatioVo = ratioVos.get(3);
                commonRatioVo.setNum(commonRatioVo.getNum() + list.getNum());
                commonRatioVo.setRatio(commonRatioVo.getRatio() + list.getRadio());
                commonRatioVo.getRatioVos().add(list);
            }
        }
        return ratioVos;
    }
}
