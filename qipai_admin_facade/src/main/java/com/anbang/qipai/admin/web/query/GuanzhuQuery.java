package com.anbang.qipai.admin.web.query;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 馆主query
 *
 * @author ethan
 */
public class GuanzhuQuery {
    private String id;
    private String nickname;
    private String phone;// 推广员手机
    private String userName;// 姓名

    private String chaguanyushiSort;
    private String dayingjiakaSort;
    private String chaguanNumSort;

    public Sort getSort() {
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(chaguanyushiSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "chaguanyushi"));
        } else if ("DESC".equals(chaguanyushiSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "chaguanyushi"));
        }
        if ("ASC".equals(dayingjiakaSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "dayingjiaka"));
        } else if ("DESC".equals(dayingjiakaSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "dayingjiaka"));
        }
        if ("ASC".equals(chaguanNumSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "chaguanNum"));
        } else if ("DESC".equals(chaguanNumSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "chaguanNum"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChaguanyushiSort() {
        return chaguanyushiSort;
    }

    public void setChaguanyushiSort(String chaguanyushiSort) {
        this.chaguanyushiSort = chaguanyushiSort;
    }

    public String getDayingjiakaSort() {
        return dayingjiakaSort;
    }

    public void setDayingjiakaSort(String dayingjiakaSort) {
        this.dayingjiakaSort = dayingjiakaSort;
    }

    public String getChaguanNumSort() {
        return chaguanNumSort;
    }

    public void setChaguanNumSort(String chaguanNumSort) {
        this.chaguanNumSort = chaguanNumSort;
    }
}
