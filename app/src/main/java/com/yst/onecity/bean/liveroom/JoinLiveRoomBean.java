package com.yst.onecity.bean.liveroom;

/**
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/22
 */

public class JoinLiveRoomBean {

    /**
     * code : 1
     * msg : 进入直播间成功
     * content : {"address":"upload/skysimple/20180115143359437855868.png","name":"是我自己呀"}
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
         * address : upload/skysimple/20180115143359437855868.png
         * name : 是我自己呀
         */

        private String image;
        private String nick;
        private String im_id;

        public String getAddress() {
            return image;
        }

        public void setAddress(String address) {
            this.image = address;
        }

        public String getName() {
            return nick;
        }

        public void setName(String name) {
            this.nick = name;
        }

        public String getIm_id() {
            return im_id;
        }

        public void setIm_id(String im_id) {
            this.im_id = im_id;
        }
    }
}
