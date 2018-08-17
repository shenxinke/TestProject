package com.yst.onecity.bean.cartbean;

import java.io.Serializable;

/**
 * 商品实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductEntity implements Serializable {
    private int id;
    private String productId;
    private String productName;
    private double productPrice;
    private long productCount;
    private long productStock;
    private String productImgUrl;
    private int productSpecificationId;
    private int productTypeId;
    private String productSpecification;
    private String productTypeName;
    private int phtype;
    private boolean isChildCheck;
    private String note;
    private int productStatus;
    private int hunterId;

    public int getPhtype() {
        return phtype;
    }

    public void setPhtype(int phtype) {
        this.phtype = phtype;
    }

    public int getHunterId() {
        return hunterId;
    }

    public void setHunterId(int hunterId) {
        this.hunterId = hunterId;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public long getProductStock() {
        return productStock;
    }

    public void setProductStock(long productStock) {
        this.productStock = productStock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProductSpecificationId() {
        return productSpecificationId;
    }
    public void setProductSpecificationId(int productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }
    public String getProductSpecification() {
        return productSpecification;
    }
    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }
    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public boolean isChildCheck() {
        return isChildCheck;
    }

    public void setChildCheck(boolean isChildCheck) {
        this.isChildCheck = isChildCheck;
    }
}
