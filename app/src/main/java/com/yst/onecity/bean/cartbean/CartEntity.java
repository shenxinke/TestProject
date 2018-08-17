package com.yst.onecity.bean.cartbean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 购物车实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class CartEntity implements Serializable{
    private ArrayList<ProductEntity> productEntityList;
    private String shopId;
    private String shopName;
    private String address;
    private String leveType;
    private String major;
    private boolean isGroupCheck;
    private boolean isGroupEdit;
    private String shop;


    public ArrayList<ProductEntity> getProductEntityList() {
        return productEntityList;
    }
    public void setProductEntityList(ArrayList<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLeveType() {
        return leveType;
    }

    public void setLeveType(String leveType) {
        this.leveType = leveType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isGroupCheck() {
        return isGroupCheck;
    }

    public void setGroupCheck(boolean isGroupCheck) {
        this.isGroupCheck = isGroupCheck;
    }

    public boolean isGroupEdit() {
        return isGroupEdit;
    }

    public void setGroupEdit(boolean isGroupEdit) {
        this.isGroupEdit = isGroupEdit;
    }

}
