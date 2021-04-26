package com.anbang.qipai.dalianmeng.cqrs.c.service;

public interface GameTableCmdService {

    String createTable(Long createTime);

    String removeTable(String no);
}
