package com.anbang.qipai.doudizhu.cqrs.c.domain;

/**
 * 倍数
 */
public class DoudizhuBeishu {
	private int renshu;
    private int fdbs;//封顶倍数
    private int qiangdizhuCount;
	private int dachuZhadanCount;
	private boolean chuntian;
	private boolean fanchuntian;
	private int jiaofen;
	private int value = 1;

	public void calculate() {
		int beishu = 1;
        if (jiaofen!=0) {
            beishu*=jiaofen;
        }
		if (renshu == 2) {
			if (qiangdizhuCount == 2) {
				beishu = beishu << 1;
			} else if (qiangdizhuCount == 3) {
				beishu = beishu << 2;
			} else if (qiangdizhuCount == 4) {
				beishu *= 5;
			} else if (qiangdizhuCount == 5) {
				beishu *= 6;
			}
		} else {
			if (qiangdizhuCount == 1) {
				beishu = beishu << 1;
			} else if (qiangdizhuCount == 2) {
				beishu = beishu << 2;
			} else if (qiangdizhuCount == 3) {
				beishu *= 5;
			} else if (qiangdizhuCount == 4) {
				beishu *= 6;
			}
		}
		if (chuntian) {
			beishu = beishu << 1;
		}
		if (fanchuntian) {
			beishu = beishu << 1;
		}
		beishu = beishu << dachuZhadanCount;
		value = beishu;
		if (fdbs!=0){
		    if (fdbs<=value){
		        value=fdbs;
            }
        }
	}



	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public int getQiangdizhuCount() {
		return qiangdizhuCount;
	}

	public void setQiangdizhuCount(int qiangdizhuCount) {
		this.qiangdizhuCount = qiangdizhuCount;
	}


	public int getDachuZhadanCount() {
		return dachuZhadanCount;
	}

	public void setDachuZhadanCount(int dachuZhadanCount) {
		this.dachuZhadanCount = dachuZhadanCount;
	}

	public boolean isChuntian() {
		return chuntian;
	}

	public void setChuntian(boolean chuntian) {
		this.chuntian = chuntian;
	}

	public boolean isFanchuntian() {
		return fanchuntian;
	}

	public void setFanchuntian(boolean fanchuntian) {
		this.fanchuntian = fanchuntian;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

    public int getFdbs() {
        return fdbs;
    }

    public void setFdbs(int fdbs) {
        this.fdbs = fdbs;
    }

    public int getJiaofen() {
        return jiaofen;
    }

    public void setJiaofen(int jiaofen) {
        this.jiaofen = jiaofen;
    }
}
