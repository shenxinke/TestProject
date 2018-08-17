package com.yst.onecity.bean.commissioner;

/**
 * 专员信息的bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/8
 */

public class CommissionerBean {


    /**
     * code : 1
     * msg : 获取专员信息成功
     * content : {"logoAttachmentId":3648,"address":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180612/20180612204448065628029.png","ph_city_id":279,"description":"监控","merchant_id":5,"ph_province_id":223,"ph_county_id":283,"profit_money":8365635,"major":null,"nickname":"青饼","showType":"1","service_area":"山西-阳泉-平定县","id":4,"service_digest":"日快乐","bindStatus":1}
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
         * logoAttachmentId : 3648
         * address : https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180612/20180612204448065628029.png
         * ph_city_id : 279
         * description : 监控
         * merchant_id : 5
         * ph_province_id : 223
         * ph_county_id : 283
         * profit_money : 8365635
         * major : null
         * nickname : 青饼
         * showType : 1
         * service_area : 山西-阳泉-平定县
         * id : 4
         * service_digest : 日快乐
         * bindStatus : 1
         */

        private int logoAttachmentId;
        private String address;
        private int ph_city_id;
        private String description;
        private int merchant_id;
        private int ph_province_id;
        private int ph_county_id;
        private double profit_money;
        private Object major;
        private String nickname;
        private String showType;
        private String service_area;
        private int id;
        private String service_digest;
        private int bindStatus;
        private int member_id;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public int getLogoAttachmentId() {
            return logoAttachmentId;
        }

        public void setLogoAttachmentId(int logoAttachmentId) {
            this.logoAttachmentId = logoAttachmentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPh_city_id() {
            return ph_city_id;
        }

        public void setPh_city_id(int ph_city_id) {
            this.ph_city_id = ph_city_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getPh_province_id() {
            return ph_province_id;
        }

        public void setPh_province_id(int ph_province_id) {
            this.ph_province_id = ph_province_id;
        }

        public int getPh_county_id() {
            return ph_county_id;
        }

        public void setPh_county_id(int ph_county_id) {
            this.ph_county_id = ph_county_id;
        }

        public double getProfit_money() {
            return profit_money;
        }

        public void setProfit_money(double profit_money) {
            this.profit_money = profit_money;
        }

        public Object getMajor() {
            return major;
        }

        public void setMajor(Object major) {
            this.major = major;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getService_area() {
            return service_area;
        }

        public void setService_area(String service_area) {
            this.service_area = service_area;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getService_digest() {
            return service_digest;
        }

        public void setService_digest(String service_digest) {
            this.service_digest = service_digest;
        }

        public int getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(int bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
