package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pan.Pan;

public class MaanshanMajiangHushu {
    private boolean hu;             //胡
    private boolean zimoHu;         //自摸胡

    private boolean xiaohu;         //小胡
    private int bazhi;              //八支
    private boolean shuangbazhi;    //双八支
    private boolean fengyise;       //风一色
    private boolean qingyise;       //清一色
    private boolean hunyise;        //混一色
    private boolean duiduihu;       //对对胡
    private boolean dadiaoche;      //大吊车
    private int minggang;           //明杠
    private int angang;             //暗杠
    private boolean tongtian;       //通天（一条龙）
    private boolean liulian;        //六连
    private boolean shuangliulian;  //双六连
    private int wutong;             //五同
    private int shuangwutong;       //双五同
    private boolean sihe;           //四核
    private boolean shuangsihe;     //双四核
    private boolean kuyazhi;        //枯支压
    private int sanzhangzaishou;    //三张在手
    private int sanzhangpengchu;    //三张碰出
    private boolean yadang;         //压档
    private boolean shixiao;        //十小
    private boolean quanxiao;       //全小
    private boolean shilao;         //十老
    private boolean quanlao;        //全老
    private boolean budongshou;     //不动手
    private int shuangpuzi;         //双铺子
    private boolean wamo;           //挖摸
    private boolean qingshuidana;   //清水大拿
    private boolean hunshuidana;    //浑水大拿

    private double value;              //总分
    private double tiwaixunhuan;       //体外循环分数

    public boolean calculate(Pan currentPan, boolean shidianqihu) {
        if (shuangbazhi) {
            value += 10;
            bazhi = 0;
        } else {
            if (bazhi >= 5) {
                value += bazhi;
            } else {
                value += 4;
                xiaohu = true;
            }
        }
        if (fengyise) {
            value += 50;
            if (zimoHu) {
                calculaTiwaixunhuan(200);
            } else {
                calculaTiwaixunhuan(100);
            }
        }
        if (qingyise) {
            value += 20;
            if (zimoHu) {
                calculaTiwaixunhuan(60);
            } else {
                calculaTiwaixunhuan(20);
            }
        }
        if (hunyise) {
            value += 5;
        }
        if (duiduihu) {
            value += 5;
        }
        if (dadiaoche) {
            value += 10;
        }
        if (tongtian) {
            value += 15;
            liulian = false;
        } else {
            if (liulian) {
                value += 5;
            }
        }
        if (shuangliulian) {
            value += 10;
        }
        if (sihe) {
            value += 5;
        }
        if (shuangsihe) {
            value += 10;
        }

        if (quanxiao) {
            value += 10;
            shixiao = false;
        } else {
            if (shixiao) {
                value += 5;
            }
        }
        if (quanlao) {
            value += 10;
            shilao = false;
        } else {
            if (shilao) {
                value += 5;
            }
        }
        if (budongshou) {
            value += 5;
        }

        //五同 双五同
        if (shuangwutong != 0) {
            value += shuangwutong;
            wutong = 0;
        } else {
            value += wutong;
        }

        if (shuangpuzi > 0) {
            shuangpuzi = shuangpuzi * 5;
            value += shuangpuzi;
        }

        if (kuyazhi) {
            value += 10;
            yadang = false;
            if (zimoHu) {
                calculaTiwaixunhuan(60);
            } else {
                calculaTiwaixunhuan(20);
            }
        } else {
            if (zimoHu && yadang) {
                wamo = true;
                tiwaixunhuan += 20;
            }
        }

        if (yadang) {
            value += 1;
        }

        value += (minggang * 2);
        value += (angang * 2);
        value += sanzhangzaishou;
        value += sanzhangpengchu;

        if (zimoHu) {
            value *= 2;
            calculaTiwaixunhuan(10);
        }

        if (shidianqihu && value < 10) {
            return false;//10点起胡
        }

        if (value >= 50 && currentPan.getMajiangPlayerIdMajiangPlayerMap().size() ==4) {
            if (currentPan.isDaoNewPan()) {
                qingshuidana = true;
                tiwaixunhuan += 50;
            }
//            else {
//                hunshuidana = true;
//                tiwaixunhuan += 20;
//            }
        }

        return true;
    }

    private void calculaTiwaixunhuan(double tiwaixunhuanScore) {
        if (tiwaixunhuanScore > tiwaixunhuan) {
            tiwaixunhuan = tiwaixunhuanScore;
        }
    }

    public boolean isHu() {
        return hu;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }

    public boolean isZimoHu() {
        return zimoHu;
    }

    public void setZimoHu(boolean zimoHu) {
        this.zimoHu = zimoHu;
    }

    public int getBazhi() {
        return bazhi;
    }

    public void setBazhi(int bazhi) {
        this.bazhi = bazhi;
    }

    public boolean isShuangbazhi() {
        return shuangbazhi;
    }

    public void setShuangbazhi(boolean shuangbazhi) {
        this.shuangbazhi = shuangbazhi;
    }

    public boolean isFengyise() {
        return fengyise;
    }

    public void setFengyise(boolean fengyise) {
        this.fengyise = fengyise;
    }

    public boolean isQingyise() {
        return qingyise;
    }

    public void setQingyise(boolean qingyise) {
        this.qingyise = qingyise;
    }

    public boolean isHunyise() {
        return hunyise;
    }

    public void setHunyise(boolean hunyise) {
        this.hunyise = hunyise;
    }

    public boolean isDuiduihu() {
        return duiduihu;
    }

    public void setDuiduihu(boolean duiduihu) {
        this.duiduihu = duiduihu;
    }

    public boolean isDadiaoche() {
        return dadiaoche;
    }

    public void setDadiaoche(boolean dadiaoche) {
        this.dadiaoche = dadiaoche;
    }

    public int getMinggang() {
        return minggang;
    }

    public void setMinggang(int minggang) {
        this.minggang = minggang;
    }

    public int getAngang() {
        return angang;
    }

    public void setAngang(int angang) {
        this.angang = angang;
    }

    public boolean isTongtian() {
        return tongtian;
    }

    public void setTongtian(boolean tongtian) {
        this.tongtian = tongtian;
    }

    public boolean isLiulian() {
        return liulian;
    }

    public void setLiulian(boolean liulian) {
        this.liulian = liulian;
    }

    public boolean isShuangliulian() {
        return shuangliulian;
    }

    public void setShuangliulian(boolean shuangliulian) {
        this.shuangliulian = shuangliulian;
    }

    public int getWutong() {
        return wutong;
    }

    public void setWutong(int wutong) {
        this.wutong = wutong;
    }

    public int getShuangwutong() {
        return shuangwutong;
    }

    public void setShuangwutong(int shuangwutong) {
        this.shuangwutong = shuangwutong;
    }

    public boolean isSihe() {
        return sihe;
    }

    public void setSihe(boolean sihe) {
        this.sihe = sihe;
    }

    public boolean isShuangsihe() {
        return shuangsihe;
    }

    public void setShuangsihe(boolean shuangsihe) {
        this.shuangsihe = shuangsihe;
    }

    public boolean isKuyazhi() {
        return kuyazhi;
    }

    public void setKuyazhi(boolean kuyazhi) {
        this.kuyazhi = kuyazhi;
    }

    public int getSanzhangzaishou() {
        return sanzhangzaishou;
    }

    public void setSanzhangzaishou(int sanzhangzaishou) {
        this.sanzhangzaishou = sanzhangzaishou;
    }

    public int getSanzhangpengchu() {
        return sanzhangpengchu;
    }

    public void setSanzhangpengchu(int sanzhangpengchu) {
        this.sanzhangpengchu = sanzhangpengchu;
    }

    public boolean isYadang() {
        return yadang;
    }

    public void setYadang(boolean yadang) {
        this.yadang = yadang;
    }

    public boolean isShixiao() {
        return shixiao;
    }

    public void setShixiao(boolean shixiao) {
        this.shixiao = shixiao;
    }

    public boolean isQuanxiao() {
        return quanxiao;
    }

    public void setQuanxiao(boolean quanxiao) {
        this.quanxiao = quanxiao;
    }

    public boolean isShilao() {
        return shilao;
    }

    public void setShilao(boolean shilao) {
        this.shilao = shilao;
    }

    public boolean isQuanlao() {
        return quanlao;
    }

    public void setQuanlao(boolean quanlao) {
        this.quanlao = quanlao;
    }

    public boolean isBudongshou() {
        return budongshou;
    }

    public void setBudongshou(boolean budongshou) {
        this.budongshou = budongshou;
    }

    public boolean isWamo() {
        return wamo;
    }

    public void setWamo(boolean wamo) {
        this.wamo = wamo;
    }

    public boolean isQingshuidana() {
        return qingshuidana;
    }

    public void setQingshuidana(boolean qingshuidana) {
        this.qingshuidana = qingshuidana;
    }

    public boolean isHunshuidana() {
        return hunshuidana;
    }

    public void setHunshuidana(boolean hunshuidana) {
        this.hunshuidana = hunshuidana;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getTiwaixunhuan() {
        return tiwaixunhuan;
    }

    public void setTiwaixunhuan(double tiwaixunhuan) {
        this.tiwaixunhuan = tiwaixunhuan;
    }

    public boolean isXiaohu() {
        return xiaohu;
    }

    public void setXiaohu(boolean xiaohu) {
        this.xiaohu = xiaohu;
    }

    public int getShuangpuzi() {
        return shuangpuzi;
    }

    public void setShuangpuzi(int shuangpuzi) {
        this.shuangpuzi = shuangpuzi;
    }
}
