package com.yst.onecity.bean;

/**
 * 图片实体类
 *
 * @author jiaofan
 * @version 4.2.0
 * @date 2018/6/4
 */
public class ImageBean {
    private String imageId;
    private String address;

    public ImageBean(String imageId, String address) {
        this.imageId = imageId;
        this.address = address;
    }

    public ImageBean(String address) {
        this.address = address;
    }

    public String getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }
}
