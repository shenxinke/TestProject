package com.yst.onecity.bean.addressmanager;

import java.util.List;

/**
 * 城市列表的bean类
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/27
 */

public class CitylistBean {
    private String firstName;
    private List<String> cityList;

    public CitylistBean(String firstName, List<String> cityList) {
        this.firstName = firstName;
        this.cityList = cityList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }
}
