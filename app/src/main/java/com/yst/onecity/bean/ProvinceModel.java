package com.yst.onecity.bean;

import java.util.List;

/**
 * 省市区
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProvinceModel {

    private String name;

    private List<CityModel> cityList;

    public ProvinceModel() {
        super();
    }

    public ProvinceModel(String name, List<CityModel> cityList) {
        super();
        this.name=name;
        this.cityList=cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList=cityList;
    }

    @Override
    public String toString() {
        return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
    }

}