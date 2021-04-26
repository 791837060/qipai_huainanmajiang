package com.anbang.qipai.game.web.controller;

import com.anbang.qipai.game.plan.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * gameroom rpc
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/gameRoom")
public class GameRoomController {
    @Autowired
    private GameService gameService;

    /**
     * 30 * 24 * 60 * 60 *1000
     */
    private static final long A_MONTH = 2592000000L;

    /**
     * 删除一个月前的数据
     */
    @RequestMapping("/removeAMonthAgo")
    public String removeAMonthAgo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long aMonthAgoTime = System.currentTimeMillis() - A_MONTH;
                gameService.removeAMonthAgo(aMonthAgoTime);
            }
        }).start();
        return "yes";
    }
}
