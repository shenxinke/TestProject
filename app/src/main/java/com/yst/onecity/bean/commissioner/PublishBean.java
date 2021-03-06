package com.yst.onecity.bean.commissioner;

import java.util.List;

/**
 * 发布的bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/5
 */

public class PublishBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"num":1,"list":[{"address":"upload/consultationimg/20180614142022374085632.png","readNum":3,"id":648,"videoAddress":"","time":"2018-06-14 15:08:12","modelType":1,"title":"哈哈啥","type":0,"isFailure":1}]}
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
         * num : 1
         * list : [{"address":"upload/consultationimg/20180614142022374085632.png","readNum":3,"id":648,"videoAddress":"","time":"2018-06-14 15:08:12","modelType":1,"title":"哈哈啥","type":0,"isFailure":1}]
         */

        private int num;
        private List<ListBean> list;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * address : upload/consultationimg/20180614142022374085632.png
             * readNum : 3
             * id : 648
             * videoAddress :
             * time : 2018-06-14 15:08:12
             * modelType : 1
             * title : 哈哈啥
             * type : 0
             * isFailure : 1
             */

            private String address;
            private int readNum;
            private int id;
            private String videoAddress;
            private String time;
            private int modelType;
            private String title;
            private int type;
            private int isFailure;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVideoAddress() {
                return videoAddress;
            }

            public void setVideoAddress(String videoAddress) {
                this.videoAddress = videoAddress;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getModelType() {
                return modelType;
            }

            public void setModelType(int modelType) {
                this.modelType = modelType;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getIsFailure() {
                return isFailure;
            }

            public void setIsFailure(int isFailure) {
                this.isFailure = isFailure;
            }
        }
    }
}
