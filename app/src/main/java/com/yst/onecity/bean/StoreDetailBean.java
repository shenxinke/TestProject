package com.yst.onecity.bean;

/**
 * 店铺详情
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class StoreDetailBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"telPhone":"13888899998","address":"北京市朝阳区麦子店街道嘉里大通中心1","latitude":"39.959983","description":"我们是制造快乐的一群人","attachmentImg":null,"merchantName":"测试1","longitude":"116.46536"}
     */

    private int code;
    private String msg;
    private ContentBean content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * telPhone : 13888899998
         * address : 北京市朝阳区麦子店街道嘉里大通中心1
         * latitude : 39.959983
         * description : 我们是制造快乐的一群人
         * attachmentImg : null
         * merchantName : 测试1
         * longitude : 116.46536
         */

        private String telPhone;
        private String address;
        private String latitude;
        private String description;
        private String attachmentImg;
        private String merchantName;
        private String longitude;

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttachmentImg() {
            return attachmentImg;
        }

        public void setAttachmentImg(String attachmentImg) {
            this.attachmentImg = attachmentImg;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
