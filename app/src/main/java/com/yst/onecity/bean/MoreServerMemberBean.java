package com.yst.onecity.bean;

import java.util.List;

/**
 * 搜索服务专员
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/1
 */

public class MoreServerMemberBean {

    /**
     * code : 1
     * msg : 成功
     * content : [{"logoImg":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180612/20180612163239704523400.png","hid":264,"address":"测试","rate":0,"counts":0,"isBind":0,"nickname":"只缘身在此山","ctime":null,"mid":1573,"serviceDigest":null,"cons":[{"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180612200207233829274.jpeg","num":1,"id":598,"title":"测试分享"}]},{"logoImg":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180525/20180525151836884838635.png","hid":809,"address":"随便的","rate":0,"counts":1,"isBind":0,"nickname":"七七八八","ctime":null,"mid":1539,"serviceDigest":null,"cons":[{"address":"upload/consultationimg/20180613174636783284630.jpg","num":3,"id":617,"title":"去这耍"},{"address":"upload/consultationimg/20180613180143864874552.jpg","num":3,"id":619,"title":"猫猫狗狗"},{"address":"upload/consultationimg/20180613182221791737593.png","num":3,"id":620,"title":"大大泡泡糖"}]}]
     */

    private int code;
    private String msg;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * logoImg : https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180612/20180612163239704523400.png
         * hid : 264
         * address : 测试
         * rate : 0
         * counts : 0
         * isBind : 0
         * nickname : 只缘身在此山
         * ctime : null
         * mid : 1573
         * serviceDigest : null
         * cons : [{"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180612200207233829274.jpeg","num":1,"id":598,"title":"测试分享"}]
         */

        private String logoImg;
        private int hid;
        private String address;
        private String rate;
        private int counts;
        private int isBind;
        private String nickname;
        private String ctime;
        private int mid;
        private String serviceDigest;
        private List<ConsBean> cons;

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }

        public int getHid() {
            return hid;
        }

        public void setHid(int hid) {
            this.hid = hid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public int getIsBind() {
            return isBind;
        }

        public void setIsBind(int isBind) {
            this.isBind = isBind;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getServiceDigest() {
            return serviceDigest;
        }

        public void setServiceDigest(String serviceDigest) {
            this.serviceDigest = serviceDigest;
        }

        public List<ConsBean> getCons() {
            return cons;
        }

        public void setCons(List<ConsBean> cons) {
            this.cons = cons;
        }

        public static class ConsBean {
            /**
             * address : http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180612200207233829274.jpeg
             * num : 1
             * id : 598
             * title : 测试分享
             */

            private String address;
            private int num;
            private int id;
            private String title;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
