package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;
import com.anbang.qipai.admin.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;

/**
 * @Description: 前端
 */

@RestController
public class FileController {

    @Autowired
    private AdminAuthService adminAuthService;

    @RequestMapping(value = "/uploadWebFolder", method = RequestMethod.POST)
    public String uploadFolder(MultipartFile[] folder, String token) {
        String adminId = adminAuthService.getAdminIdBySessionId(token);

        FileWriter fw = null;
        String data= adminId + "_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\r";
        try {
            File f = new File("/data/tomcat/apache-tomcat-9.0.10/webapps/uploadrecord.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(data);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUtil.saveMultiFile("/data/tomcat/apache-tomcat-9.0.10/webapps", folder);
        return "ok";
    }

}
