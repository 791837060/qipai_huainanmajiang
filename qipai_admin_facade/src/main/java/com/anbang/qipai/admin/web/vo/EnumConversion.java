package com.anbang.qipai.admin.web.vo;

import com.anbang.qipai.admin.plan.bean.games.Game;

/**
 * @Description:
 */
public class EnumConversion {

    // 游戏名
    public static String getGameName(Game game) {
        switch (game) {
            case yangzhouMajiang:
                return "扬州麻将";
            case paodekuai:
                return "跑得快";
            case biji:
                return "比鸡";
            case doudizhu:
                return "斗地主";
            case taizhouMajiang:
                return "泰州麻将";
            case yizhengMajiang:
                return "仪征麻将";
            case tianchangxiaohua:
                return "天长小花";
            case gaoyouMajiang:
                return "高邮麻将";
            case guandan:
                return "掼蛋";
        }
        return "";
    }
}
