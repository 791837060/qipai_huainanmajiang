package com.anbang.qipai.qinyouquan.plan.service;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDiamondAccountDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.qinyouquan.plan.bean.MemberDayResultData;
import com.anbang.qipai.qinyouquan.plan.dao.MemberDayResultDataDao;
import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MemberDayResultDataService {

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private MemberDayResultDataDao memberDayResultDataDao;

    @Autowired
    private MemberDiamondAccountDboDao memberDiamondAccountDboDao;


    private final ExecutorService executorService = Executors.newCachedThreadPool();


    public void dayInit() {
        List<MemberLianmengDbo> memberLianmengList = memberLianmengDboDao.findAll();
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengList) {
            MemberDayResultData memberDayResultData = new MemberDayResultData();
            if (!memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                memberDayResultData.setMemberDiamond(memberDiamondAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId()).getBalance());
                int power = 0;
                Deque<MemberLianmengDbo> memberLianmengDboDeque = new LinkedList<>();
                MemberLianmengDbo memberLianmengDboNode = memberLianmengDbo;
                memberLianmengDboDeque.push(memberLianmengDboNode);
                while (!memberLianmengDboDeque.isEmpty()) {
                    memberLianmengDboNode = memberLianmengDboDeque.pop();
                    power= new BigDecimal(Double.toString(memberDiamondAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDboNode.getMemberId(), memberLianmengDboNode.getLianmengId()).getBalance())).add(new BigDecimal(power)).intValue();
                    List<MemberLianmengDbo> children = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior( memberLianmengDboNode.getLianmengId(),
                            memberLianmengDboNode.getMemberId());
                    for (int i = children.size() - 1; i >= 0; i--) {
                        if (!children.get(i).getId().equals(memberLianmengDboNode.getId())) {
                            memberLianmengDboDeque.push(children.get(i));
                        }

                    }
                }
                memberDayResultData.setDiamond(power);
            }

            memberDayResultData.setMemberId(memberLianmengDbo.getMemberId());
            memberDayResultData.setLianmengId(memberLianmengDbo.getLianmengId());
            memberDayResultData.setCreateTime(System.currentTimeMillis());
            memberDayResultDataDao.save(memberDayResultData);
        }

    }

    public void increaseErrenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseErrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getErrenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseErrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getErrenDayingjiaCount()+1);
        }

    }

    public void increaseMemberErrenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberErrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberErrenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberErrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberErrenDayingjiaCount()+1);
        }

    }

    public void increaseSanrenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseSanrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getSanrenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseSanrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getSanrenDayingjiaCount()+1);
        }

    }

    public void increaseMemberSanrenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberSanrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberSanrenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberSanrenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberSanrenDayingjiaCount()+1);
        }

    }

    public void increaseSirenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseSirenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getSirenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseSirenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getSirenDayingjiaCount()+1);
        }

    }

    public void increaseMemberSirenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberSirenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberSirenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberSirenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberSirenDayingjiaCount()+1);
        }

    }

    public void increaseDuorenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseDuorenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getDuorenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseDuorenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getDuorenDayingjiaCount()+1);
        }

    }

    public void increaseMemberDuorenDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberDuorenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberDuorenDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberDuorenDayingjiaCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberDuorenDayingjiaCount()+1);
        }

    }
    public void increaseErrenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseErrenJuCount(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getErrenJuCount()+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseErrenJuCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getErrenJuCount()+1);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberErrenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberErrenJuCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberErrenJuCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberErrenJuCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberErrenJuCount()+1);
        }

    }

    public void increaseSanrenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseSanrenJuCount(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getSanrenJuCount()+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseSanrenJuCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getSanrenJuCount()+1);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberSanrenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberSanrenJuCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberSanrenJuCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberSanrenJuCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberSanrenJuCount()+1);
        }

    }

    public void increaseSirenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseSirenJuCount(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getSirenJuCount()+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseSirenJuCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getSirenJuCount()+1);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberSirenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberSirenJuCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberSirenJuCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberSirenJuCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberSirenJuCount()+1);
        }

    }
    public void increaseDuorenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseDuorenJuCount(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getDuorenJuCount()+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseDuorenJuCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getDuorenJuCount()+1);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberDuorenJuCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberDuorenJuCount(memberId,lianmengId,startTime,endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId,startTime,endTime).getMemberDuorenJuCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseMemberDuorenJuCount(memberId,lianmengId,startTime,endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId,startTime,endTime).getMemberDuorenJuCount()+1);
        }

    }

    public void increaseDiamondCost(String memberId,String lianmengId,int amount)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseDiamondCost(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getDiamondCost()+amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseDiamondCost(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getDiamondCost()+amount);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberDiamondCost(String memberId, String lianmengId, int amount)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberDiamondCost(memberId, lianmengId, startTime, endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId, startTime, endTime).getMemberDiamondCost() + amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            memberDayResultDataDao.increaseMemberDiamondCost(memberId, lianmengId, startTime, endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId, startTime, endTime).getMemberDiamondCost() + amount);
        }

    }

    public void increaseTotalScore(String memberId,String lianmengId,double amount)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseTotalScore(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getTotalScore()+amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseTotalScore(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getTotalScore()+amount);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberTotalScore(String memberId, String lianmengId, double amount) {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId, startTime, endTime) == null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L * 60 * 10);
                    memberDayResultDataDao.increaseMemberTotalScore(memberId, lianmengId, startTime, endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId, startTime, endTime).getMemberTotalScore() + amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            memberDayResultDataDao.increaseMemberTotalScore(memberId, lianmengId, startTime, endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId, startTime, endTime).getMemberTotalScore() + amount);
        }

    }

    public void increaseFinishCount(String memberId, String lianmengId, int amount) {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId, startTime, endTime) == null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L * 60 * 10);
                    memberDayResultDataDao.increaseFinishJuCount(memberId, lianmengId, startTime, endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId, startTime, endTime).getFinishJuCount() + amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            memberDayResultDataDao.increaseFinishJuCount(memberId, lianmengId, startTime, endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId, startTime, endTime).getFinishJuCount() + amount);
        }

    }

    public void increaseDiamond(String memberId,String lianmengId,int amount)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId,startTime,endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.increaseDiamond(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getDiamond()+amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseDiamond(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getDiamond()+amount);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void increaseMemberDiamond(String memberId, String lianmengId, int amount)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseMemberDiamond(memberId, lianmengId, startTime, endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                                    lianmengId, startTime, endTime).getMemberDiamond() + amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            memberDayResultDataDao.increaseMemberDiamond(memberId, lianmengId, startTime, endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                            lianmengId, startTime, endTime).getMemberDiamond() + amount);
        }

    }

    public void deleteByTime(long currentTime) {
        memberDayResultDataDao.deleteByTime(currentTime - 24L * 60 * 60 * 1000 * 7);
    }

    public void deleteByLianmengIdAndMemberId(String memberId, String lianmengId) {
        memberDayResultDataDao.deleteByMemberIdAndLianmengId(memberId, lianmengId);
    }

    public MemberDayResultData findByMemberIdAndLianmengId(String memberId, String lianmengId,long startTime ,long endTime) {

        return memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId, startTime, endTime);
    }

    public void memberInit(String memberId, String lianmengId) {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId, lianmengId, startTime, endTime)==null) {
            MemberDayResultData memberDayResultData = new MemberDayResultData();
            memberDayResultData.setMemberId(memberId);
            memberDayResultData.setLianmengId(lianmengId);
            memberDayResultData.setCreateTime(System.currentTimeMillis());
            memberDayResultDataDao.save(memberDayResultData);
        }


    }


    public ListPage findByLianmengIdAndSuperiorId(int page, int size, String memberId, String lianmengId, long startTime, long endTime,boolean queryXiaji,String searchMemberId) {
        MemberLianmengDbo queryMemberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (queryMemberLianmengDbo.getIdentity().equals(Identity.MENGZHU)){
            if (!queryXiaji){
                memberId=null;
            }

        }
        List<MemberLianmengDbo> memberLianmengDboList = new ArrayList<>();
        long amount;
        if (searchMemberId!=null){
            memberLianmengDboList.add(memberLianmengDboDao.findByMemberIdAndLianmengId( searchMemberId, lianmengId));
            amount = 1;
        }else {
            memberLianmengDboList = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior(page, size, lianmengId, memberId);
            amount = memberLianmengDboDao.getAmountByMemberIdAndLianmengIdAndSuperior(lianmengId, memberId);
        }

        List list=new ArrayList();
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            MemberDayResultData memberDayResultData = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId(), startTime, endTime);
            Map data = new HashMap();
            if (memberDayResultData==null){
                data.put("memberId",memberLianmengDbo.getMemberId());
                data.put("nickname",memberLianmengDbo.getNickname());
                data.put("headimgurl",memberLianmengDbo.getHeadimgurl());
                data.put("totalScore",0);
                data.put("juCount",0);
                data.put("dayingjiaCount",0);
                data.put("finishCount",0);
                data.put("canFinish","true");
            }else {
                data.put("memberId",memberLianmengDbo.getMemberId());
                data.put("nickname",memberLianmengDbo.getNickname());
                data.put("headimgurl",memberLianmengDbo.getHeadimgurl());
                data.put("totalScore",memberDayResultData.getMemberTotalScore());
                data.put("juCount",memberDayResultData.getMemberErrenJuCount()+memberDayResultData.getMemberSanrenJuCount()+memberDayResultData.getMemberSirenJuCount());
                data.put("dayingjiaCount",memberDayResultData.getMemberErrenDayingjiaCount()+memberDayResultData.getMemberSanrenDayingjiaCount()
                        +memberDayResultData.getMemberSirenDayingjiaCount());
                data.put("finishCount",memberDayResultData.getFinishJuCount());
                if (memberLianmengDbo.getSuperiorMemberId().equals(queryMemberLianmengDbo.getMemberId())){
                    data.put("canFinish","true");
                }else {
                    data.put("canFinish","false");
                }
            }


            list.add(data);

        }
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }

    public void updateFinishCount(String memberId, String lianmengId) {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberDayResultData byMemberIdAndLianmengIdAndTime = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId, startTime, endTime);
        memberDayResultDataDao.increaseFinishJuCount(memberId, lianmengId, startTime, endTime,
                byMemberIdAndLianmengIdAndTime.getMemberErrenDayingjiaCount()+byMemberIdAndLianmengIdAndTime.getMemberSanrenDayingjiaCount()+
                        byMemberIdAndLianmengIdAndTime.getMemberSirenDayingjiaCount());

    }

    public void increaseDayingjiaCount(String memberId,String lianmengId)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                lianmengId,startTime,endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.increaseDayingjiaCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                            memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                    lianmengId,startTime,endTime).getDayingjiaCount()+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.increaseDayingjiaCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                    memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                            lianmengId,startTime,endTime).getDayingjiaCount()+1);
        }
    }



}
