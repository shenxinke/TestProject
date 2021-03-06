package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的积分实体类
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/26.
 */
public class AccountIntegralBean {

    /**
     * code : 1
     * msg : null
     * content : {"data":[{"Money":10,"CreatedTime":"2017-06-23 11:53:14","strTime":"2017年06月23日 11:53:14","cateid":25,"ProjactName":"订单退货","OrderNo":"20160726230010885_232252"},{"Money":10,"CreatedTime":"2017-06-21 11:50:33","strTime":"2017年06月21日 11:50:33","cateid":25,"ProjactName":"订单退货","OrderNo":"20161118094210413807_355341"},{"Money":10,"CreatedTime":"2017-05-15 15:03:51","strTime":"2017年05月15日 15:03:51","cateid":25,"ProjactName":"订单退货","OrderNo":"20160726230010885_232253"},{"Money":10,"CreatedTime":"2017-05-12 11:29:14","strTime":"2017年05月12日 11:29:14","cateid":4,"ProjactName":"订单撤销","OrderNo":"20160809154851752224_2_251794"},{"Money":5,"CreatedTime":"2017-04-28 10:07:03","strTime":"2017年04月28日 10:07:03","cateid":25,"ProjactName":"订单退货","OrderNo":"20161209194757016749_369259"},{"Money":10,"CreatedTime":"2017-04-26 21:20:04","strTime":"2017年04月26日 21:20:04","cateid":25,"ProjactName":"订单退货","OrderNo":"20161103171552915795_344838"},{"Money":10,"CreatedTime":"2017-04-26 21:16:32","strTime":"2017年04月26日 21:16:32","cateid":25,"ProjactName":"订单退货","OrderNo":"20160813122742279854_257473"},{"Money":10,"CreatedTime":"2017-04-26 18:00:58","strTime":"2017年04月26日 18:00:58","cateid":25,"ProjactName":"订单退货","OrderNo":"20160808194635525781_251129"},{"Money":10,"CreatedTime":"2017-04-26 17:58:38","strTime":"2017年04月26日 17:58:38","cateid":25,"ProjactName":"订单退货","OrderNo":"20160908165348641544_295798"},{"Money":5,"CreatedTime":"2017-04-25 20:26:35","strTime":"2017年04月25日 20:26:35","cateid":25,"ProjactName":"订单退货","OrderNo":"20161209195050908649_369260"}],"TotalCounts":2599,"PageIndex":1,"state":0}
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
         * integralValue : 5000
         * scoreType : 1
         * integralType : 报单所得
         * integralTime : 2015-08-21 15:44:34
         */

        private String integralValue;
        private int sourceType;
        private String integralType;
        private String integralTime;

        public String getIntegralValue() {
            return integralValue;
        }

        public void setIntegralValue(String integralValue) {
            this.integralValue = integralValue;
        }

        public int getScoreType() {
            return sourceType;
        }

        public void setScoreType(int scoreType) {
            this.sourceType = scoreType;
        }

        public String getIntegralType() {
            return integralType;
        }

        public void setIntegralType(String integralType) {
            this.integralType = integralType;
        }

        public String getIntegralTime() {
            return integralTime;
        }

        public void setIntegralTime(String integralTime) {
            this.integralTime = integralTime;
        }
    }
}
