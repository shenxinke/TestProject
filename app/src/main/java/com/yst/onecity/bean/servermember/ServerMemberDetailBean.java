package com.yst.onecity.bean.servermember;

import java.util.List;

/**
 * 服务专员详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ServerMemberDetailBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"hunterId":1,"hunterLable":[{"sort_num":null,"name":"零食","id":1},{"sort_num":null,"name":"水果","id":2}],"num":50,"papersImg":null,"merchantName":"我是商户1","commentNum":64,"logoImg":"upload/memberheadimg/20170717100859270756474.png","backImg":null,"major":"我们是制造快乐的一群人","phone":null,"merchantId":1,"service":"服务","nickname":"我是推广师","name":null,"merchantAdress":null,"serviceDigest":null,"card":null,"memberId":null}
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
         * hunterId : 1
         * hunterLable : [{"sort_num":null,"name":"零食","id":1},{"sort_num":null,"name":"水果","id":2}]
         * num : 50
         * papersImg : null
         * merchantName : 我是商户1
         * commentNum : 64
         * logoImg : upload/memberheadimg/20170717100859270756474.png
         * backImg : null
         * major : 我们是制造快乐的一群人
         * phone : null
         * merchantId : 1
         * service : 服务
         * nickname : 我是推广师
         * name : null
         * merchantAdress : null
         * serviceDigest : null
         * card : null
         * memberId : null
         */

        private int hunterId;
        private int num;
        private String papersImg;
        private String merchantImg;
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
        private Object card;
        private Object memberId;
        private int isCollection;
        private List<HunterLableBean> hunterLable;
        private String imid;

        public String getImid() {
            return imid;
        }

        public void setImid(String imid) {
            this.imid = imid;
        }

        public String getMerchantImg() {
            return merchantImg;
        }

        public void setMerchantImg(String merchantImg) {
            this.merchantImg = merchantImg;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
            this.isCollection = isCollection;
        }

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

        public Object getCard() {
            return card;
        }

        public void setCard(Object card) {
            this.card = card;
        }

        public Object getMemberId() {
            return memberId;
        }

        public void setMemberId(Object memberId) {
            this.memberId = memberId;
        }

        public List<HunterLableBean> getHunterLable() {
            return hunterLable;
        }

        public void setHunterLable(List<HunterLableBean> hunterLable) {
            this.hunterLable = hunterLable;
        }

        public static class HunterLableBean {
            /**
             * sort_num : null
             * name : 零食
             * id : 1
             */

            private Object sort_num;
            private String name;
            private int id;

            public Object getSort_num() {
                return sort_num;
            }

            public void setSort_num(Object sort_num) {
                this.sort_num = sort_num;
            }

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