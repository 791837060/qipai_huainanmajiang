package com.anbang.qipai.admin.plan.service.juprizeservice;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRecordDetail;
import com.anbang.qipai.admin.plan.bean.members.MemberOrder;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeRecordDetailDao;
import com.anbang.qipai.admin.util.ExcelUtils;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeRecordDetailQuery;
import com.highto.framework.web.page.ListPage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class JuPrizeRecordDetailService {

    @Autowired
    private JuPrizeRecordDetailDao juPrizeRecordDetailDao;

    public ListPage listPrizeRecordDetailByQuery(int page, int size, JuPrizeRecordDetailQuery query) {
        int count = (int) juPrizeRecordDetailDao.countJuPrizeRecordByQuery(query);
        List<JuPrizeRecordDetail> recordDetails = juPrizeRecordDetailDao.listJuPrizeRecordByQuery(page, size, query);
        ListPage listPage = new ListPage(recordDetails, page, size, count);
        return listPage;
    }

    public void juPrizeRecordExport(JuPrizeRecordDetailQuery query, OutputStream output) throws IOException {
        Integer rowid = 0;
        Integer sheetNum = 1;
        XSSFWorkbook workbook = new XSSFWorkbook();
        long amount = juPrizeRecordDetailDao.countJuPrizeRecordByQuery(query);
        int size = 300;
        long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
        for (int page = 1; page <= pageNum; page++) {
            List<JuPrizeRecordDetail> recordDetails = juPrizeRecordDetailDao.listJuPrizeRecordByQuery(page, size, query);
            export(rowid, sheetNum, recordDetails, workbook);
            sheetNum++;
        }
        workbook.write(output);
        workbook.close();
    }

    private static void export(Integer rowid, Integer sheetNum, List<JuPrizeRecordDetail> list,
                                       XSSFWorkbook workbook) {
        XSSFSheet spreadsheet = null;
        if (rowid > 20000) {
            sheetNum++;
            spreadsheet = workbook.createSheet("Record" + sheetNum);
            rowid = 0;
        } else {
            spreadsheet = workbook.createSheet("Record" + sheetNum);
            XSSFRow row = spreadsheet.createRow(rowid);
            row.createCell(0).setCellValue("记录编号");
            row.createCell(1).setCellValue("游戏ID");
            row.createCell(2).setCellValue("游戏昵称");
            row.createCell(3).setCellValue("手机号码");
            row.createCell(4).setCellValue("奖励类型");
            row.createCell(5).setCellValue("奖励数量");
            row.createCell(6).setCellValue("登录IP");
            row.createCell(7).setCellValue("IP归属地");
            row.createCell(8).setCellValue("领取时间");
            rowid++;
        }
        for (JuPrizeRecordDetail record : list) {
            XSSFRow row = spreadsheet.createRow(rowid);
            row.createCell(0).setCellValue(record.getId());
            row.createCell(1).setCellValue(record.getPlayerId());
            row.createCell(2).setCellValue(record.getNickName());
            row.createCell(3).setCellValue(record.getPhone());
            row.createCell(4).setCellValue(record.getPrizeType());
            row.createCell(5).setCellValue(record.getSingleNum());
            row.createCell(6).setCellValue(record.getLoginIp());
            row.createCell(7).setCellValue(record.getIpAddress());
            row.createCell(8)
                    .setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getSendTime())));
            rowid++;
        }
    }
}
