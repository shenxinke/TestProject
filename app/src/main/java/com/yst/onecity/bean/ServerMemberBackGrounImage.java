package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 服务专员背景图片
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerMemberBackGrounImage implements Serializable {

    /**
     * code : 1
     * msg : 成功
     * content : [{"address":"upload/memberheadimg/20170717100550711414593.png","name":"20170717100550.png","id":3289},{"address":"upload/memberheadimg/20170717101647374094806.jpeg","name":"timg.jpeg","id":3294},{"address":"upload/toutiao/20170905114803790977163.jpg","name":"timg (7).jpg","id":9038}]
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
         * address : upload/memberheadimg/20170717100550711414593.png
         * name : 20170717100550.png
         * id : 3289
         */

        private String address;
        private String name;
        private int id;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private boolean isCheck;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
