package com.anbang.qipai.members.web.vo;

/**
 * 拉取会员信息的view obj
 * 
 * @author neo
 *
 */
public class MemberVO {

	private boolean success;

	private String memberId;

	private String nickname;

	private String headimgurl;

	private String gold;

	private String score;

	private boolean verifyUser;// 实名认证，true:通过认证,false:未通过认证


	private Boolean verifyPhone;//是否绑定手机

    private Boolean verifyWeChat;//是否绑定手机


    private String phone;
    private boolean dalianmeng;
    private boolean qinyouquan;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	@Override
	public String toString() {
		return "MemberVO [success=" + success + ", memberId=" + memberId + ", nickname=" + nickname + ", headimgurl="
				+ headimgurl + ", gold=" + gold + "]";
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public boolean isVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(boolean verifyUser) {
		this.verifyUser = verifyUser;
	}

    public Boolean getVerifyPhone() {
        return verifyPhone;
    }

    public void setVerifyPhone(Boolean verifyPhone) {
        this.verifyPhone = verifyPhone;
    }

    public Boolean getVerifyWeChat() {
        return verifyWeChat;
    }

    public void setVerifyWeChat(Boolean verifyWeChat) {
        this.verifyWeChat = verifyWeChat;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public boolean isDalianmeng() {
        return dalianmeng;
    }

    public void setDalianmeng(boolean dalianmeng) {
        this.dalianmeng = dalianmeng;
    }

    public boolean isQinyouquan() {
        return qinyouquan;
    }

    public void setQinyouquan(boolean qinyouquan) {
        this.qinyouquan = qinyouquan;
    }
}
