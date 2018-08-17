package com.yst.onecity.bean.servermember;

import java.util.List;

/**
 * 服务专员基本信息
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class SearchServerMemberBean {


    /**
     * code : 1
     * msg : 成功
     * content : [{"hunterId":1,"star":0,"level":"大众","hunterLable":[{"sort_num":null,"name":"零食","id":1},{"sort_num":null,"name":"水果","id":2}],"num":50,"cityname":null,"countyname":null,"papersImg":null,"imid":null,"merchantName":"我是商户666","logoImg":"upload/memberheadimg/20170717100859270756474.png","backImg":null,"major":"我们是制造快乐的一群人","provincename":null,"merchantId":1,"service":"服务","nickname":"我是推广师","merchantAdress":"公安局刑侦大队1","serviceDigest":null,"goodordernum":0,"memberId":1}]
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
         * hunterId : 1
         * star : 0
         * level : 大众
         * hunterLable : [{"sort_num":null,"name":"零食","id":1},{"sort_num":null,"name":"水果","id":2}]
         * num : 50
         * cityname : null
         * countyname : null
         * papersImg : null
         * imid : null
         * merchantName : 我是商户666
         * logoImg : upload/memberheadimg/20170717100859270756474.png
         * backImg : null
         * major : 我们是制造快乐的一群人
         * provincename : null
         * merchantId : 1
         * service : 服务
         * nickname : 我是推广师
         * merchantAdress : 公安局刑侦大队1
         * serviceDigest : null
         * goodordernum : 0
         * memberId : 1
         */

        private int hunterId;
        private int star;
        private String level;
        private int num;
        private String cityname;
        private String countyname;
        private String papersImg;
        private String imid;
        private String merchantName;
        private String logoImg;
        private String backImg;
        private String major;
        private String provincename;
        private int merchantId;
        private String service;
        private String nickname;
        private String merchantAdress;
        private String serviceDigest;
        private int goodordernum;
        private int memberId;
        private List<HunterLableBean> hunterLable;

        public int getHunterId() {
            return hunterId;
        }

        public void setHunterId(int hunterId) {
            this.hunterId = hunterId;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCountyname() {
            return countyname;
        }

        public void setCountyname(String countyname) {
            this.countyname = countyname;
        }

        public String getPapersImg() {
            return papersImg;
        }

        public void setPapersImg(String papersImg) {
            this.papersImg = papersImg;
        }

        public String getImid() {
            return imid;
        }

        public void setImid(String imid) {
            this.imid = imid;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
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

        public String getProvincename() {
            return provincename;
        }

        public void setProvincename(String provincename) {
            this.provincename = provincename;
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

        public int getGoodordernum() {
            return goodordernum;
        }

        public void setGoodordernum(int goodordernum) {
            this.goodordernum = goodordernum;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
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
