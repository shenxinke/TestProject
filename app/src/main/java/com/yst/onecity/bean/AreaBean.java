package com.yst.onecity.bean;

/**
 * 省市区的bean
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/4/2
 */

public class AreaBean {


    /**
     * code : 1
     * msg : 获取成功
     * content : {"districtId":143,"cityName":"邢台","proId":39,"provinceName":"河北","cityId":130,"countyName":"广宗县"}
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
         * districtId : 143
         * cityName : 邢台
         * proId : 39
         * provinceName : 河北
         * cityId : 130
         * countyName : 广宗县
         */

        private int districtId;
        private String cityName;
        private int proId;
        private String provinceName;
        private int cityId;
        private String countyName;

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }
    }
}
