package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 服务专员基本信息
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerMemberInfoBean implements Serializable {


    /**
     * code : 1
     * msg : 成功
     * content : {"hunterId":1,"hunterLable":[{"name":"hello","id":4},{"name":"hello","id":5}],"num":0,"papersImg":null,"merchantName":"我是商户","commentNum":0,"logoImg":"upload/skysimple/20170922125555742907321.jpg","backImg":"upload/skysimple/20170922125709048356833.jpg","major":"","phone":"","merchantId":1,"service":"","nickname":"小小龙","name":"","merchantAdress":"","serviceDigest":"专业服务员","card":""}
     */

    private String code;
    private String msg;
    private ContentBean content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * hunterId : 1
         * hunterLable : [{"name":"hello","id":4},{"name":"hello","id":5}]
         * num : 0
         * papersImg : null  证件图
         * merchantName : 我是商户   服务专员所在店铺
         * commentNum : 0
         * logoImg : upload/skysimple/20170922125555742907321.jpg  头像
         * backImg : upload/skysimple/20170922125709048356833.jpg  背景图
         * major : 
         * phone : 
         * merchantId : 1
         * service : 
         * nickname : 小小龙
         * name : 
         * merchantAdress :   店铺地址
         * serviceDigest : 专业服务员  简介
         * card :  身份证
         *merchantLat
         * merchantLng
         */

        private int hunterId;
        private int num;
        private String papersImg;
        private String merchantName;
        private int commentNum;
        private String logoImg;
        private String backImg;
        private String major;
        private String phone;
        private int merchantId;
        private String service;
        private String nickname;
        private String name;
        private String merchantAdress;
        private String serviceDigest;
        private String card;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        private String level;

        public String getMerchantLat() {
            return merchantLat;
        }

        public void setMerchantLat(String merchantLat) {
            this.merchantLat = merchantLat;
        }

        public String getMerchantLng() {
            return merchantLng;
        }

        public void setMerchantLng(String merchantLng) {
            this.merchantLng = merchantLng;
        }

        private String merchantLat;
        private String merchantLng;
        private List<HunterLableBean> hunterLable;

        public int getHunterId() {
            return hunterId;
        }

        public void setHunterId(int hunterId) {
            this.hunterId = hunterId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPapersImg() {
            return papersImg;
        }

        public void setPapersImg(String papersImg) {
            this.papersImg = papersImg;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMerchantAdress() {
            return merchantAdress;
        }

        public void setMerchantAdress(String merchantAdress) {
            this.merchantAdress = merchantAdress;
        }

        public String getServiceDigest() {
            return serviceDigest;
        }

        public void setServiceDigest(String serviceDigest) {
            this.serviceDigest = serviceDigest;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public List<HunterLableBean> getHunterLable() {
            return hunterLable;
        }

        public void setHunterLable(List<HunterLableBean> hunterLable) {
            this.hunterLable = hunterLable;
        }

        public static class HunterLableBean {
            /**
             * name : hello
             * id : 4
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
