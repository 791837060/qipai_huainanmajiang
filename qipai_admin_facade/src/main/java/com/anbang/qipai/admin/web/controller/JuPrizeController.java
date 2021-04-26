package com.anbang.qipai.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.admin.msg.service.JuPrizeSourceService;
import com.anbang.qipai.admin.plan.bean.juprize.DrawTypeEnum;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeTypeEnum;
import com.anbang.qipai.admin.plan.service.juprizeservice.JuPrizeRecordDetailService;
import com.anbang.qipai.admin.plan.service.juprizeservice.JuPrizeReleaseService;
import com.anbang.qipai.admin.plan.service.juprizeservice.JuPrizeService;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeRecordDetailQuery;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeReleaseVo;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeVo;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 对局奖励
 */
@CrossOrigin
@RestController
@RequestMapping("/juPrize")
public class JuPrizeController {
    @Autowired
    private JuPrizeReleaseService juPrizeReleaseService;

    @Autowired
    private JuPrizeService juPrizeService;

    @Autowired
    private JuPrizeSourceService juPrizeSourceService;

    @Autowired
    private JuPrizeRecordDetailService juPrizeRecordDetailService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/getJuPrizeInfo")
    public CommonVO getJuPrizeInfo() {
        Map data;
        try {
            List<JuPrize> juPrizes = juPrizeService.listJuPrize();
            List<JuPrizeVo> juPrizeVos = new ArrayList<>();
            for (JuPrize list : juPrizes) {
                juPrizeVos.add(new JuPrizeVo(list));
            }
            data = juPrizeVos.stream().collect(Collectors.groupingBy(JuPrizeVo::getDrawType));

            List<JuPrizeRelease> juPrizeReleases = juPrizeReleaseService.listJuPrizeRelease();
            List<JuPrizeReleaseVo> juPrizeReleaseVos = new ArrayList<>();
            for (JuPrizeRelease list : juPrizeReleases) {
                juPrizeReleaseVos.add(new JuPrizeReleaseVo(list));
            }
            data.put("juPrizeReleases", juPrizeReleaseVos);

            return CommonVOUtil.success(data, "success");
        } catch (Exception e) {
            logger.error("getJuPrizeInfo/" + JSON.toJSONString(e));
        }
        return CommonVOUtil.systemException();
    }

    @PostMapping("/saveFirstJuPrize")
    public CommonVO saveFirstJuPrize(JuPrize juPrize) {
        if (juPrize.getDrawType() == null && juPrize.getName() == null && juPrize.getIconUrl() == null) {
            return CommonVOUtil.lackParameter();
        }
        juPrize.setDrawType(DrawTypeEnum.first);
        juPrizeService.saveOrUpdateJuPrize(juPrize);
        return CommonVOUtil.success("success");
    }

    @PostMapping("/saveGeneralJuPrize")
    public CommonVO saveGeneralJuPrize(JuPrize juPrize) {
        if (juPrize.getDrawType() != null && juPrize.getName() != null && juPrize.getIconUrl() != null
                && juPrize.getPrizeType() == null) {
            return CommonVOUtil.lackParameter();
        }
        juPrize.setDrawType(DrawTypeEnum.general);
        juPrizeService.saveOrUpdateJuPrize(juPrize);
        return CommonVOUtil.success("success");
    }

    @PostMapping("/deleteJuPrize")
    public CommonVO deleteJuPrize(String id) {
        if (StringUtils.isBlank(id)) {
            return CommonVOUtil.lackParameter();
        }
        juPrizeService.deleteJuPrize(id);
        return CommonVOUtil.success("success");
    }

    @PostMapping("/getJuPrize")
    public CommonVO getJuPrize(String id) {
        if (StringUtils.isBlank(id)) {
            return CommonVOUtil.lackParameter();
        }
        JuPrize juPrize = juPrizeService.getJuPrize(id);
        Map map = new HashMap();
        map.put("juPrize", juPrize);
        return CommonVOUtil.success(map, "success");
    }

    @PostMapping("/getJuPrizeType")
    public CommonVO getJuPrizeType() {
        Map data = new HashMap();
        data.put("JuPrizeType", JuPrizeTypeEnum.getJuPrizeType());
        return CommonVOUtil.success(data, "success");
    }

    @PostMapping("/juPrizeRelease")
    public CommonVO juPrizeRelease(@RequestParam(value = "juPrizeReleaseList") String juPrizeReleaseList) {
        List<JuPrizeRelease> juPrizeReleases = JSON.parseArray(juPrizeReleaseList,JuPrizeRelease.class);
        if (CollectionUtils.isEmpty(juPrizeReleases)) {
            return  CommonVOUtil.lackParameter();
        }

        List<JuPrize> juPrizes = juPrizeService.listJuPrize();
        Map<DrawTypeEnum, List<JuPrize>> map = juPrizes.stream().collect(Collectors.groupingBy(JuPrize::getDrawType));

        if (map.get(DrawTypeEnum.first) == null || map.get(DrawTypeEnum.general) == null) {
            return CommonVOUtil.error("请先设置概率");
        }

        int firstPrizeProbCount = map.get(DrawTypeEnum.first).stream().mapToInt(JuPrize::getPrizeProb).sum();
        int prizeProbCount = map.get(DrawTypeEnum.general).stream().mapToInt(JuPrize::getPrizeProb).sum();

        if (firstPrizeProbCount != 10000 || prizeProbCount != 10000) {
            return CommonVOUtil.error("抽奖概率的和不是100%");
        }

        for (JuPrizeRelease list : juPrizeReleases) {
            list.setFirstJuPrizes(map.get(DrawTypeEnum.first));
            list.setGeneralJuPrizes(map.get(DrawTypeEnum.general));
            list.setCreatTime(System.currentTimeMillis());
            juPrizeReleaseService.save(list);
        }

        juPrizeSourceService.juPrizeRelease(juPrizeReleases);

        return CommonVOUtil.success("success");
    }

    @PostMapping("/listJuPrizeRecord")
    public CommonVO listJuPrizeRecord(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      JuPrizeRecordDetailQuery query) {
        ListPage listPage = juPrizeRecordDetailService.listPrizeRecordDetailByQuery(page, size, query);
        return CommonVOUtil.success(listPage, "success");
    }

    @RequestMapping(value= "/juPrizeRecordExport", method = RequestMethod.GET)
    public CommonVO juPrizeRecordExport(JuPrizeRecordDetailQuery query, HttpServletResponse response) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String fileName = format.format(date) + "juPrizeRecord.xlsx";
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        try {
            OutputStream output = response.getOutputStream();
            juPrizeRecordDetailService.juPrizeRecordExport(query, output);
            output.close();
            return CommonVOUtil.success("success");
        } catch (IOException e) {
            logger.error("juPrizeRecordExport-" + JSON.toJSONString(e));
        }
        return CommonVOUtil.systemException();
    }
}
