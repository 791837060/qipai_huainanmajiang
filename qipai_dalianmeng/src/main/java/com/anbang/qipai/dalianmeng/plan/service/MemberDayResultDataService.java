package com.anbang.qipai.dalianmeng.plan.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.PowerAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;
import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengDiamondDayCostDao;
import com.anbang.qipai.dalianmeng.plan.dao.MemberDayResultDataDao;
import com.anbang.qipai.dalianmeng.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MemberDayResultDataService {

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private PowerAccountDboDao powerAccountDboDao;

    @Autowired
    private MemberDayResultDataDao memberDayResultDataDao;

    @Autowired
    private LianmengDiamondDayCostDao lianmengDiamondDayCostDao;

    private final ExecutorService executorService = Executors.newCachedThreadPool();


    public void dayInit() {
        List<MemberLianmengDbo> memberLianmengList = memberLianmengDboDao.findAll();
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengList) {
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                LianmengDiamondDayCost lianmengDiamondDayCost = new LianmengDiamondDayCost();
                lianmengDiamondDayCost.setCost(0);
                lianmengDiamondDayCost.setCreateTime(System.currentTimeMillis());
                lianmengDiamondDayCost.setLianmengId(memberLianmengDbo.getLianmengId());
                lianmengDiamondDayCostDao.save(lianmengDiamondDayCost);
            }
            MemberDayResultData memberDayResultData = new MemberDayResultData();
            memberDayResultData.setMemberId(memberLianmengDbo.getMemberId());
            memberDayResultData.setLianmengId(memberLianmengDbo.getLianmengId());
            memberDayResultData.setCreateTime(System.currentTimeMillis());
            memberDayResultData.setJuCount(0);
            memberDayResultData.setMemberPower(powerAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId()).getBalance());
            double power = 0;
            Deque<MemberLianmengDbo> memberLianmengDboDeque = new LinkedList<>();
            MemberLianmengDbo memberLianmengDboNode = memberLianmengDbo;
            memberLianmengDboDeque.push(memberLianmengDboNode);
            while (!memberLianmengDboDeque.isEmpty()) {
                memberLianmengDboNode = memberLianmengDboDeque.pop();
                power= new BigDecimal(Double.toString(powerAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDboNode.getMemberId(), memberLianmengDboNode.getLianmengId()).getBalance())).add(new BigDecimal(power)).doubleValue();
                List<MemberLianmengDbo> children = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior( memberLianmengDboNode.getLianmengId(),
                        memberLianmengDboNode.getMemberId());
                for (int i = children.size() - 1; i >= 0; i--) {
                    if (!children.get(i).getId().equals(memberLianmengDboNode.getId())) {
                        memberLianmengDboDeque.push(children.get(i));
                    }

                }
            }
            memberDayResultData.setMemberPowerCost(0d);
            memberDayResultData.setPower(power);
            memberDayResultData.setDayingjiaCount(0);
            memberDayResultData.setScore(0d);
            memberDayResultData.setPowerCost(0d);
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


    public void increaseJuCount(String memberId,String lianmengId)   {
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
                        memberDayResultDataDao.increaseJuCount(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId,startTime,endTime).getJuCount()+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.increaseJuCount(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getJuCount()+1);
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
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
        }    }

    public void updatePowerCost(String memberId,String lianmengId,double power)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                lianmengId, startTime, endTime)==null) {
            MemberLianmengDbo finalMemberLianmengDbo1 = memberLianmengDbo;
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.updateMemberPowerCost(finalMemberLianmengDbo1.getMemberId(), lianmengId, startTime, endTime,
                            new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo1.getMemberId(),
                                    lianmengId, startTime, endTime).getMemberPowerCost())).add(new BigDecimal(Double.toString(power))).doubleValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.updateMemberPowerCost(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                    new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                            lianmengId, startTime, endTime).getMemberPowerCost())).add(new BigDecimal(Double.toString(power))).doubleValue());
        }
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId, startTime, endTime)==null) {
                MemberLianmengDbo finalMemberLianmengDbo = memberLianmengDbo;
                executorService.submit(()->{
                    try {
                        Thread.sleep(1000L*60*10);
                        memberDayResultDataDao.updatePowerCost(finalMemberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                                new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(finalMemberLianmengDbo.getMemberId(),
                                        lianmengId, startTime, endTime).getPowerCost())).add(new BigDecimal(Double.toString(power))).doubleValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                memberDayResultDataDao.updatePowerCost(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId,startTime,endTime).getPowerCost())).add(new BigDecimal(Double.toString(power))).doubleValue());
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void updatePower(String memberId,String lianmengId,double power)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                    lianmengId, startTime, endTime)!=null) {
                memberDayResultDataDao.updatePower(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                        new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                                lianmengId, startTime, endTime).getPower())).add(new BigDecimal(Double.toString(power))).doubleValue());
            }
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }

    public void updateMemberPowerForMember(String memberId ,String lianmengId ,double power)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                lianmengId, startTime, endTime)!=null) {
            memberDayResultDataDao.updateMemberPower(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                    new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                            lianmengId,startTime,endTime).getMemberPower())).add(new BigDecimal(Double.toString(power))).doubleValue());
        }
    }

    public void updateMemberTotalPower(String memberId,String lianmengId,double power)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                lianmengId, startTime, endTime)!=null) {
            memberDayResultDataDao.updatePower(memberLianmengDbo.getMemberId(), lianmengId, startTime, endTime,
                    new BigDecimal(Double.toString(memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(),
                            lianmengId,startTime,endTime).getPower())).add(new BigDecimal(Double.toString(power))).doubleValue());
        }
    }

    public void updateScore(String memberId, String lianmengId, double score)   {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberId,
                lianmengId, startTime, endTime)==null) {
            executorService.submit(()->{
                try {
                    Thread.sleep(1000L*60*10);
                    memberDayResultDataDao.updateScore(memberId, lianmengId, startTime, endTime, memberDayResultDataDao.
                            findByMemberIdAndLianmengIdAndTime(memberId, lianmengId, startTime, endTime).getScore() + score);
                    memberDayResultDataDao.updateTotalScore(memberLianmengDbo.getSuperiorMemberId(), lianmengId, startTime, endTime, memberDayResultDataDao.
                            findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getSuperiorMemberId(), lianmengId, startTime, endTime).getTotalScore() + score);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }else {
            memberDayResultDataDao.updateScore(memberId, lianmengId, startTime, endTime, memberDayResultDataDao.
                    findByMemberIdAndLianmengIdAndTime(memberId, lianmengId, startTime, endTime).getScore() + score);
            memberDayResultDataDao.updateTotalScore(memberLianmengDbo.getSuperiorMemberId(), lianmengId, startTime, endTime, memberDayResultDataDao.
                    findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getSuperiorMemberId(), lianmengId, startTime, endTime).getTotalScore() + score);
        }
    }

    public void deleteByTime(long currentTime) {
        memberDayResultDataDao.deleteByTime(currentTime - 24L * 60 * 60 * 1000 * 7);
        lianmengDiamondDayCostDao.deleteByTime(currentTime - 24L * 60 * 60 * 1000 * 7);
    }

    public MemberDayResultData findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        long currentTime = System.currentTimeMillis();
        long startTime = TimeUtil.getDayStartTime(currentTime);
        long endTime = TimeUtil.getDayEndTime(currentTime);
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
            memberDayResultData.setJuCount(0);
            memberDayResultData.setPower(0);
            memberDayResultData.setDayingjiaCount(0);
            memberDayResultData.setScore(0);
            memberDayResultData.setPowerCost(0);
            memberDayResultDataDao.save(memberDayResultData);
        }
    }

    public void lianmengInit(String lianmengId) {
        LianmengDiamondDayCost lianmengDiamondDayCost = new LianmengDiamondDayCost();
        lianmengDiamondDayCost.setLianmengId(lianmengId);
        lianmengDiamondDayCost.setCreateTime(System.currentTimeMillis());
        lianmengDiamondDayCost.setCost(0);
        lianmengDiamondDayCostDao.save(lianmengDiamondDayCost);

    }

    public long dayInitForAdmin(long currentTime ) {
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        List<MemberLianmengDbo> memberLianmengList = memberLianmengDboDao.findAll();
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengList) {
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                LianmengDiamondDayCost lianmengDiamondDayCost = new LianmengDiamondDayCost();
                lianmengDiamondDayCost.setCost(0);
                lianmengDiamondDayCost.setCreateTime(System.currentTimeMillis());
                lianmengDiamondDayCost.setLianmengId(memberLianmengDbo.getLianmengId());
                lianmengDiamondDayCostDao.save(lianmengDiamondDayCost);
            }
            MemberDayResultData memberDayResultData = new MemberDayResultData();
            memberDayResultData.setMemberId(memberLianmengDbo.getMemberId());
            memberDayResultData.setLianmengId(memberLianmengDbo.getLianmengId());
            memberDayResultData.setCreateTime(System.currentTimeMillis());
            memberDayResultData.setJuCount(0);
            memberDayResultData.setMemberPower(powerAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId()).getBalance());
            double power = 0;
            Deque<MemberLianmengDbo> memberLianmengDboDeque = new LinkedList<>();
            MemberLianmengDbo memberLianmengDboNode = memberLianmengDbo;
            memberLianmengDboDeque.push(memberLianmengDboNode);
            while (!memberLianmengDboDeque.isEmpty()) {
                memberLianmengDboNode = memberLianmengDboDeque.pop();
                power += powerAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDboNode.getMemberId(), memberLianmengDboNode.getLianmengId()).getBalance();
                List<MemberLianmengDbo> children = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior( memberLianmengDboNode.getLianmengId(),
                        memberLianmengDboNode.getMemberId());
                for (int i = children.size() - 1; i >= 0; i--) {
                    if (!children.get(i).getId().equals(memberLianmengDboNode.getId())) {
                        memberLianmengDboDeque.push(children.get(i));
                    }

                }
            }
            memberDayResultData.setPower(power);
            memberDayResultData.setDayingjiaCount(0);
            memberDayResultData.setScore(0);
            memberDayResultData.setPowerCost(0);
            memberDayResultDataDao.save(memberDayResultData);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        return endTime-startTime;
    }

    public void memberPowerInit(  ) {
//        long startTime = System.currentTimeMillis();
//        System.out.println(startTime);
        List<MemberLianmengDbo> memberLianmengList = memberLianmengDboDao.findAll();
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengList) {
            long currentTime = System.currentTimeMillis();
            long startTime = TimeUtil.getDayStartTime(currentTime);
            long endTime = TimeUtil.getDayEndTime(currentTime);
            memberDayResultDataDao.updateMemberPower(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId(), startTime, endTime,
                    powerAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), memberLianmengDbo.getLianmengId()).getBalance());
        }
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime);
    }

    public LianmengDiamondDayCost findLianmengDiamondDayCostByLianmengIdAndTime(long startTime ,long endTime ,String lianmengId){
        return lianmengDiamondDayCostDao.findByLianmengId(lianmengId,startTime,endTime);
    }


}
