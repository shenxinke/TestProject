package com.yst.onecity.bean.consult;

import java.util.List;

/**
 * 评价实体
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ConsultCommentBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"info":[{"headImg":"upload/memberheadimg/20170717100550711414593.png","name":"我是会员","createdTime":"2017-09-21 20:22:11","content":"这款商品不错,下次还来...","memberId":1},{"headImg":"upload/memberheadimg/20170717100550711414593.png","name":"我是会员","createdTime":"2017-09-23 14:01:51","content":"hunter4","memberId":1}],"count":2}
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
         * info : [{"headImg":"upload/memberheadimg/20170717100550711414593.png","name":"我是会员","createdTime":"2017-09-21 20:22:11","content":"这款商品不错,下次还来...","memberId":1},{"headImg":"upload/memberheadimg/20170717100550711414593.png","name":"我是会员","createdTime":"2017-09-23 14:01:51","content":"hunter4","memberId":1}]
         * count : 2
         */

        private int count;
        private List<InfoBean> info;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * headImg : upload/memberheadimg/20170717100550711414593.png
             * name : 我是会员
             * createdTime : 2017-09-21 20:22:11
             * content : 这款商品不错,下次还来...
             * memberId : 1
             */

            private String headImg;
            private String name;
            private String createdTime;
            private String content;
            private int memberId;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }
    }
}
