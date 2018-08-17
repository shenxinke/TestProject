package com.yst.onecity.bean;

/**
 * 天天奖
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class DistrictModel {

    private String name;

    private String zipcode;

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name, String zipcode) {
        super();
        this.name=name;
        this.zipcode=zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode=zipcode;
    }

    @Override
    public String toString() {
        return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
    }

}
