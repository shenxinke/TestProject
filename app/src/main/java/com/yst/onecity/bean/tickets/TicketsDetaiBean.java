package com.yst.onecity.bean.tickets;

import java.util.List;

/**
 * 预览奖券详情实体
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/29.
 */
public class TicketsDetaiBean {


    /**
     * code : 1
     * msg : 成功
     * content : [{"phone":"13886244152","nickname":"小杨","lottery_price":50},{"phone":"13886244152","nickname":"小杨","lottery_price":50},{"phone":"13886244152","nickname":"小杨","lottery_price":50}]
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
         * phone : 13886244152
         * nickname : 小杨
         * lottery_price : 50
         */

        private String phone;
        private String nickname;
        private String lottery_price;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLottery_price() {
            return lottery_price;
        }

        public void setLottery_price(String lottery_price) {
            this.lottery_price = lottery_price;
        }
    }
}
