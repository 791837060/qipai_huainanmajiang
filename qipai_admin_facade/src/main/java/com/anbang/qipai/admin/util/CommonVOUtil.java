package com.anbang.qipai.admin.util;

import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * CommonVO的工具类
 * @author YaphetS
 * @date 2018/11/19
 */
public class CommonVOUtil {
    public static CommonVO success(Object data,String msg) {
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        commonVO.setData(data);
        commonVO.setMsg(msg);
        return commonVO;
    }

    public static CommonVO success(String msg) {
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        commonVO.setMsg(msg);
        return commonVO;
    }

    public static CommonVO success(Boolean success,String msg) {
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(success);
        commonVO.setMsg(msg);
        return commonVO;
    }

    public static CommonVO error(String msg){
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(false);
        commonVO.setMsg(msg);
        return commonVO;
    }

    public static CommonVO systemException(){
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(false);
        commonVO.setMsg("SYSTEMEXCEPTION");
        return commonVO;
    }

    public static CommonVO lackParameter(){
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(false);
        commonVO.setMsg("Lack of parameter");
        return commonVO;
    }
}
