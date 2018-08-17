package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 登录
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class LoginBean implements Serializable {


    /**
     * code : 1
     * msg : 成功
     * content : [{"hunter_id":6,"role":0,"address":"upload/skysimple/20170926113728855780267.png","nickname":"他告诉","name":null,"ImSign":"签名生成失败","id":27,"IMid":"18101249503","uuid":"7fdce310ce584b899b3ea1e52706c0f9","IMpassword":"7717847976"},{"ident":"1"}]
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
         * hunter_id : 6
         * role : 0
         * address : upload/skysimple/20170926113728855780267.png
         * nickname : 他告诉
         * name : null
         * ImSign : 签名生成失败
         * id : 27
         * IMid : 18101249503
         * uuid : 7fdce310ce584b899b3ea1e52706c0f9
         * IMpassword : 7717847976
         * ident : 1
         */

        private String is_newyetai;
        private String hunter_id;
        /**
         * 0:普通用户 1:店铺 2:推广师
         */
        private int role;
        private String address;
        private String nickname;
        private String name;
        private String ImSign;
        private String id;
        private String IMid;
        private String uuid;
        private String IMpassword;
        private String ident;
        private String card;

        public String getIsCertification() {
            return card;
        }

        public void setIsCertification(String isCertification) {
            this.card = isCertification;
        }

        public String getIs_newyetai() {
            return is_newyetai;
        }

        public void setIs_newyetai(String is_newyetai) {
            this.is_newyetai = is_newyetai;
        }

        public String getHunter_id() {
            return hunter_id;
        }

        public void setHunter_id(String hunter_id) {
            this.hunter_id = hunter_id;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImSign() {
            return ImSign;
        }

        public void setImSign(String ImSign) {
            this.ImSign = ImSign;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIMid() {
            return IMid;
        }

        public void setIMid(String IMid) {
            this.IMid = IMid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getIMpassword() {
            return IMpassword;
        }

        public void setIMpassword(String IMpassword) {
            this.IMpassword = IMpassword;
        }

        public String getIdent() {
            return ident;
        }

        public void setIdent(String ident) {
            this.ident = ident;
        }
    }
}
