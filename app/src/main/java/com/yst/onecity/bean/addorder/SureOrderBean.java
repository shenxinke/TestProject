package com.yst.onecity.bean.addorder;

import java.util.List;

/**
 * 确认订单实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class SureOrderBean {

    /**
     * code : 1
     * msg : 成功
     * content : [{"address":{"address":{"cityName":"1","detail_address":"01哈哈哈哈哈","user_name":"急急急12哈哈哈","mobile":"18600725430","id":11,"proName":"0"},"defAdd":1},"merchantList":[{"address":"upload/memberheadimg/20170717100550711414593.png","merchant_name":"我是商户666","id":1,"hunter":[{"address":"upload/memberheadimg/20170717100859270756474.png","productDetail":[{"hunter_id":1,"pspcification":"说个法规","buy_num":1,"pname":"好东西","price":12,"product_id":1,"pimg":"upload/memberheadimg/20170717100550711414593.png","num":113,"phtype":0,"status":1},{"hunter_id":1,"pspcification":"啊顺丰到付好","buy_num":1,"pname":"坏东西","price":1,"product_id":2,"pimg":"upload/memberheadimg/20170717100859270756474.png","num":12,"phtype":0,"status":1}],"nickname":"我是推广师","id":1,"merchant_id":1}]}]}]
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
         * address : {"address":{"cityName":"1","detail_address":"01哈哈哈哈哈","user_name":"急急急12哈哈哈","mobile":"18600725430","id":11,"proName":"0"},"defAdd":1}
         * merchantList : [{"address":"upload/memberheadimg/20170717100550711414593.png","merchant_name":"我是商户666","id":1,"hunter":[{"address":"upload/memberheadimg/20170717100859270756474.png","productDetail":[{"hunter_id":1,"pspcification":"说个法规","buy_num":1,"pname":"好东西","price":12,"product_id":1,"pimg":"upload/memberheadimg/20170717100550711414593.png","num":113,"phtype":0,"status":1},{"hunter_id":1,"pspcification":"啊顺丰到付好","buy_num":1,"pname":"坏东西","price":1,"product_id":2,"pimg":"upload/memberheadimg/20170717100859270756474.png","num":12,"phtype":0,"status":1}],"nickname":"我是推广师","id":1,"merchant_id":1}]}]
         */

        private AddressBeanX address;
        private List<MerchantListBean> merchantList;

        public AddressBeanX getAddress() {
            return address;
        }

        public void setAddress(AddressBeanX address) {
            this.address = address;
        }

        public List<MerchantListBean> getMerchantList() {
            return merchantList;
        }

        public void setMerchantList(List<MerchantListBean> merchantList) {
            this.merchantList = merchantList;
        }

        public static class AddressBeanX {
            /**
             * address : {"cityName":"1","detail_address":"01哈哈哈哈哈","user_name":"急急急12哈哈哈","mobile":"18600725430","id":11,"proName":"0"}
             * defAdd : 1
             */

            private AddressBean address;
            private int defAdd;

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public int getDefAdd() {
                return defAdd;
            }

            public void setDefAdd(int defAdd) {
                this.defAdd = defAdd;
            }

            public static class AddressBean {
                /**
                 * cityName : 1
                 * detail_address : 01哈哈哈哈哈
                 * user_name : 急急急12哈哈哈
                 * mobile : 18600725430
                 * id : 11
                 * proName : 0
                 */

                private String cityName;
                private String detail_address;
                private String user_name;
                private String mobile;
                private int id;
                private String proName;

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public String getDetail_address() {
                    return detail_address;
                }

                public void setDetail_address(String detail_address) {
                    this.detail_address = detail_address;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getProName() {
                    return proName;
                }

                public void setProName(String proName) {
                    this.proName = proName;
                }
            }
        }

        public static class MerchantListBean {
            /**
             * address : upload/memberheadimg/20170717100550711414593.png
             * merchant_name : 我是商户666
             * id : 1
             * hunter : [{"address":"upload/memberheadimg/20170717100859270756474.png","productDetail":[{"hunter_id":1,"pspcification":"说个法规","buy_num":1,"pname":"好东西","price":12,"product_id":1,"pimg":"upload/memberheadimg/20170717100550711414593.png","num":113,"phtype":0,"status":1},{"hunter_id":1,"pspcification":"啊顺丰到付好","buy_num":1,"pname":"坏东西","price":1,"product_id":2,"pimg":"upload/memberheadimg/20170717100859270756474.png","num":12,"phtype":0,"status":1}],"nickname":"我是推广师","id":1,"merchant_id":1}]
             */

            private String address;
            private String merchant_name;
            private int id;
            private List<HunterBean> hunter;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<HunterBean> getHunter() {
                return hunter;
            }

            public void setHunter(List<HunterBean> hunter) {
                this.hunter = hunter;
            }

            public static class HunterBean {
                /**
                 * address : upload/memberheadimg/20170717100859270756474.png
                 * productDetail : [{"hunter_id":1,"pspcification":"说个法规","buy_num":1,"pname":"好东西","price":12,"product_id":1,"pimg":"upload/memberheadimg/20170717100550711414593.png","num":113,"phtype":0,"status":1},{"hunter_id":1,"pspcification":"啊顺丰到付好","buy_num":1,"pname":"坏东西","price":1,"product_id":2,"pimg":"upload/memberheadimg/20170717100859270756474.png","num":12,"phtype":0,"status":1}]
                 * nickname : 我是推广师
                 * id : 1
                 * merchant_id : 1
                 */

                private String address;
                private String nickname;
                private int id;
                private int merchant_id;
                private String note;

                public String getNote() {
                    return note;
                }

                public void setNote(String note) {
                    this.note = note;
                }

                private List<ProductDetailBean> productDetail;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMerchant_id() {
                    return merchant_id;
                }

                public void setMerchant_id(int merchant_id) {
                    this.merchant_id = merchant_id;
                }

                public List<ProductDetailBean> getProductDetail() {
                    return productDetail;
                }

                public void setProductDetail(List<ProductDetailBean> productDetail) {
                    this.productDetail = productDetail;
                }

                public static class ProductDetailBean {
                    /**
                     * hunter_id : 1
                     * pspcification : 说个法规
                     * buy_num : 1
                     * pname : 好东西
                     * price : 12
                     * product_id : 1
                     * pimg : upload/memberheadimg/20170717100550711414593.png
                     * num : 113
                     * phtype : 0
                     * status : 1
                     */
                    private int merchantId;
                    private int hunter_id;
                    private String pspcification;
                    private int spid;
                    private int buy_num;
                    private String pname;
                    private double price;
                    private int product_id;
                    private String pimg;
                    private int num;
                    private int phtype;
                    private int status;
                    private String topcart;

                    public int getMerchantId() {
                        return merchantId;
                    }

                    public void setMerchantId(int merchantId) {
                        this.merchantId = merchantId;
                    }

                    public String getTopcart() {
                        return topcart;
                    }

                    public void setTopcart(String topcart) {
                        this.topcart = topcart;
                    }

                    public int getSpid() {
                        return spid;
                    }

                    public void setSpid(int spid) {
                        this.spid = spid;
                    }

                    public int getHunter_id() {
                        return hunter_id;
                    }

                    public void setHunter_id(int hunter_id) {
                        this.hunter_id = hunter_id;
                    }

                    public String getPspcification() {
                        return pspcification;
                    }

                    public void setPspcification(String pspcification) {
                        this.pspcification = pspcification;
                    }

                    public int getBuy_num() {
                        return buy_num;
                    }

                    public void setBuy_num(int buy_num) {
                        this.buy_num = buy_num;
                    }

                    public String getPname() {
                        return pname;
                    }

                    public void setPname(String pname) {
                        this.pname = pname;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public int getProduct_id() {
                        return product_id;
                    }

                    public void setProduct_id(int product_id) {
                        this.product_id = product_id;
                    }

                    public String getPimg() {
                        return pimg;
                    }

                    public void setPimg(String pimg) {
                        this.pimg = pimg;
                    }

                    public int getNum() {
                        return num;
                    }

                    public void setNum(int num) {
                        this.num = num;
                    }

                    public int getPhtype() {
                        return phtype;
                    }

                    public void setPhtype(int phtype) {
                        this.phtype = phtype;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }
                }
            }
        }
    }
}
