package com.anbang.qipai.qinyouquan.cqrs.c.service;

public interface GameTableCmdService {

    String createTable(Long createTime);

    String removeTable(String no);
}
