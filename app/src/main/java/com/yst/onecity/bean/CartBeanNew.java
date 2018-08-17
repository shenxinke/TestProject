package com.yst.onecity.bean;

/**
 * @desc:
 * @author:WuXiaofang
 * @date:
 */

public class CartBeanNew {
    private int productSpecificationId;
    private int hunterId;
    private String productSpecification;
    private int buyNum;
    private String address;
    private int productId;
    private double price;
    private int num;
    private String name;
    private int productStatus;
    private int id;
    private int phtype;
    private boolean isEdit;
    private boolean isChecked;
    private double intergal;

    public double getIntergal() {
        return intergal;
    }

    public void setIntergal(double intergal) {
        this.intergal = intergal;
    }

    public CartBeanNew(int productSpecificationId, int hunterId, String productSpecification, int buyNum, String address, int productId, double price, int num, String name, int productStatus, int id, int phtype, boolean isEdit, boolean isChecked) {
        this.productSpecificationId = productSpecificationId;
        this.hunterId = hunterId;
        this.productSpecification = productSpecification;
        this.buyNum = buyNum;
        this.address = address;
        this.productId = productId;
        this.price = price;
        this.num = num;
        this.name = name;
        this.productStatus = productStatus;
        this.id = id;
        this.phtype = phtype;
        this.isEdit = isEdit;
        this.isChecked = isChecked;
    }

    public CartBeanNew() {
    }

    public int getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(int productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public int getHunterId() {
        return hunterId;
    }

    public void setHunterId(int hunterId) {
        this.hunterId = hunterId;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhtype() {
        return phtype;
    }

    public void setPhtype(int phtype) {
        this.phtype = phtype;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "CartBeanNew{" +
                "productSpecificationId=" + productSpecificationId +
                ", hunterId=" + hunterId +
                ", productSpecification='" + productSpecification + '\'' +
                ", buyNum=" + buyNum +
                ", address='" + address + '\'' +
                ", productId=" + productId +
                ", price=" + price +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", productStatus=" + productStatus +
                ", id=" + id +
                ", phtype=" + phtype +
                ", isEdit=" + isEdit +
                ", isChecked=" + isChecked +
                '}';
    }
}
