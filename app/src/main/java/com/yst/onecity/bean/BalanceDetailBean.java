package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 余额明细
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class BalanceDetailBean implements Serializable {
    /**
     * code : 200
     * msg : 查询数据成功
     * content : {"total":"0.02","standby":"30.99","enable":"0.02","datas":[{"score":"-0.0155","tradeTime":"2017-09-29 03:00:00","balance":0,"sourceType":"消耗待用积分"},{"score":"0.0155","tradeTime":"2017-09-29 03:00:00","balance":0.0205,"sourceType":"返回可用积分"},{"score":"10.0000","tradeTime":"2017-09-28 14:23:38","balance":0,"sourceType":"购物返待用积分"},{"score":"0.0100","tradeTime":"2017-09-28 10:06:25","balance":0,"sourceType":"购物返待用积分"},{"score":"6.0000","tradeTime":"2017-09-28 09:46:57","balance":0,"sourceType":"购物返待用积分"},{"score":"5.0000","tradeTime":"2017-09-28 09:44:28","balance":0,"sourceType":"购物返待用积分"},{"score":"-0.0050","tradeTime":"2017-09-28 03:00:00","balance":0,"sourceType":"消耗待用积分"},{"score":"0.0050","tradeTime":"2017-09-28 03:00:00","balance":0.005,"sourceType":"返回可用积分"},{"score":"10.0000","tradeTime":"2017-09-27 17:03:58","balance":0,"sourceType":"购物返待用积分"}],"today":"0.02"}
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
         * total : 0.02
         * standby : 30.99
         * enable : 0.02
         * datas : [{"score":"-0.0155","tradeTime":"2017-09-29 03:00:00","balance":0,"sourceType":"消耗待用积分"},{"score":"0.0155","tradeTime":"2017-09-29 03:00:00","balance":0.0205,"sourceType":"返回可用积分"},{"score":"10.0000","tradeTime":"2017-09-28 14:23:38","balance":0,"sourceType":"购物返待用积分"},{"score":"0.0100","tradeTime":"2017-09-28 10:06:25","balance":0,"sourceType":"购物返待用积分"},{"score":"6.0000","tradeTime":"2017-09-28 09:46:57","balance":0,"sourceType":"购物返待用积分"},{"score":"5.0000","tradeTime":"2017-09-28 09:44:28","balance":0,"sourceType":"购物返待用积分"},{"score":"-0.0050","tradeTime":"2017-09-28 03:00:00","balance":0,"sourceType":"消耗待用积分"},{"score":"0.0050","tradeTime":"2017-09-28 03:00:00","balance":0.005,"sourceType":"返回可用积分"},{"score":"10.0000","tradeTime":"2017-09-27 17:03:58","balance":0,"sourceType":"购物返待用积分"}]
         * today : 0.02
         */

        private String total;
        private String standby;
        private String enable;
        private String today;
        private List<DatasBean> datas;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getStandby() {
            return standby;
        }

        public void setStandby(String standby) {
            this.standby = standby;
        }

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * score : -0.0155
             * tradeTime : 2017-09-29 03:00:00
             * balance : 0
             * sourceType : 消耗待用积分
             */

            private double score;
            private String tradeTime;
            private String balance;
            private String sourceType;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getTradeTime() {
                return tradeTime;
            }

            public void setTradeTime(String tradeTime) {
                this.tradeTime = tradeTime;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getSourceType() {
                return sourceType;
            }

            public void setSourceType(String sourceType) {
                this.sourceType = sourceType;
            }
        }
    }
}
