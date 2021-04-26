package com.dml.shuangkou.pai.dianshuzu;

import java.util.ArrayList;
import java.util.List;

import com.dml.puke.wanfa.dianshu.dianshuzu.*;

/**
 * 所有的点数组不包括单张点数组和王炸
 *
 * @author lsc
 */
public class PaiXing {
    /**
     * 对子点数集合
     */
    private List<DuiziDianShuZu> duiziDianShuZuList = new ArrayList<>();
    /**
     * 连对点数集合
     */
    private List<LianduiDianShuZu> lianduiDianShuZuList = new ArrayList<>();
    /**
     * 三张点数集合
     */
    private List<SanzhangDianShuZu> sanzhangDianShuZuList = new ArrayList<>();
    /**
     * 连三张点数集合
     */
    private List<LiansanzhangDianShuZu> liansanzhangDianShuZuList = new ArrayList<>();
    /**
     * 顺子点数集合
     */
    private List<ShunziDianShuZu> shunziDianShuZuList = new ArrayList<>();
    /**
     * 单个炸弹点数集合
     */
    private List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = new ArrayList<>();
    /**
     * 连续炸弹点数集合
     */
    private List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList = new ArrayList<>();
    /**
     * 三代二点数集合
     */
    private List<SandaierDianShuZu> sandaierDianShuZuArrayList = new ArrayList<>();

    public PaiXing() {
    }

    public boolean hasZhadan() {
        if (!lianXuZhadanDianShuZuList.isEmpty() || !danGeZhadanDianShuZuList.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<DuiziDianShuZu> getDuiziDianShuZuList() {
        return duiziDianShuZuList;
    }

    public void setDuiziDianShuZuList(List<DuiziDianShuZu> duiziDianShuZuList) {
        this.duiziDianShuZuList = duiziDianShuZuList;
    }

    public List<LianduiDianShuZu> getLianduiDianShuZuList() {
        return lianduiDianShuZuList;
    }

    public void setLianduiDianShuZuList(List<LianduiDianShuZu> lianduiDianShuZuList) {
        this.lianduiDianShuZuList = lianduiDianShuZuList;
    }

    public List<LiansanzhangDianShuZu> getLiansanzhangDianShuZuList() {
        return liansanzhangDianShuZuList;
    }

    public void setLiansanzhangDianShuZuList(List<LiansanzhangDianShuZu> liansanzhangDianShuZuList) {
        this.liansanzhangDianShuZuList = liansanzhangDianShuZuList;
    }

    public List<SanzhangDianShuZu> getSanzhangDianShuZuList() {
        return sanzhangDianShuZuList;
    }

    public void setSanzhangDianShuZuList(List<SanzhangDianShuZu> sanzhangDianShuZuList) {
        this.sanzhangDianShuZuList = sanzhangDianShuZuList;
    }

    public List<ShunziDianShuZu> getShunziDianShuZuList() {
        return shunziDianShuZuList;
    }

    public void setShunziDianShuZuList(List<ShunziDianShuZu> shunziDianShuZuList) {
        this.shunziDianShuZuList = shunziDianShuZuList;
    }

    public List<DanGeZhadanDianShuZu> getDanGeZhadanDianShuZuList() {
        return danGeZhadanDianShuZuList;
    }

    public void setDanGeZhadanDianShuZuList(List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList) {
        this.danGeZhadanDianShuZuList = danGeZhadanDianShuZuList;
    }

    public List<LianXuZhadanDianShuZu> getLianXuZhadanDianShuZuList() {
        return lianXuZhadanDianShuZuList;
    }

    public void setLianXuZhadanDianShuZuList(List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList) {
        this.lianXuZhadanDianShuZuList = lianXuZhadanDianShuZuList;
    }

    public List<SandaierDianShuZu> getSandaierDianShuZuArrayList() {
        return sandaierDianShuZuArrayList;
    }

    public void setSandaierDianShuZuArrayList(List<SandaierDianShuZu> sandaierDianShuZuArrayList) {
        this.sandaierDianShuZuArrayList = sandaierDianShuZuArrayList;
    }

}
