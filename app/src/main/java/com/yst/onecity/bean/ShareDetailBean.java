package com.yst.onecity.bean;

import java.util.List;

/**
 * 分享详情页面
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/6/12
 */

public class ShareDetailBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"consultation":{"comment_num":0,"consultation_classify_id":null,"source":null,"memberHeadImg":"[图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png","img_num":1,"title":"测试专员添加分享","type":0,"update_time":1528772322000,"read_num":6,"user_type":0,"description_name":null,"rejection_reason":null,"id":565,"top_time":null,"author_info":null,"share_num":0,"info":[],"created_time":1528514785000,"publishTime":"2018-06-09","headImg":"[图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png","model_type":1,"verfy_date":null,"is_show":0,"is_delete":0,"fabType":2,"order_date":1528514785000,"conType":1,"phone":"13120238966","user_id":1375,"detail_summary":"测试专员添加分享","fabulous_num":0,"name":"匿名会员","create_user":"","userType":"0","created_ip":"","status":2}}
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
         * consultation : {"comment_num":0,"consultation_classify_id":null,"source":null,"memberHeadImg":"[图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png","img_num":1,"title":"测试专员添加分享","type":0,"update_time":1528772322000,"read_num":6,"user_type":0,"description_name":null,"rejection_reason":null,"id":565,"top_time":null,"author_info":null,"share_num":0,"info":[],"created_time":1528514785000,"publishTime":"2018-06-09","headImg":"[图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png","model_type":1,"verfy_date":null,"is_show":0,"is_delete":0,"fabType":2,"order_date":1528514785000,"conType":1,"phone":"13120238966","user_id":1375,"detail_summary":"测试专员添加分享","fabulous_num":0,"name":"匿名会员","create_user":"","userType":"0","created_ip":"","status":2}
         */

        private ConsultationBean consultation;

        public ConsultationBean getConsultation() {
            return consultation;
        }

        public void setConsultation(ConsultationBean consultation) {
            this.consultation = consultation;
        }

        public static class ConsultationBean {
            /**
             * comment_num : 0
             * consultation_classify_id : null
             * source : null
             * memberHeadImg : [图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png
             * img_num : 1
             * title : 测试专员添加分享
             * type : 0
             * update_time : 1528772322000
             * read_num : 6
             * user_type : 0
             * description_name : null
             * rejection_reason : null
             * id : 565
             * top_time : null
             * author_info : null
             * share_num : 0
             * info : []
             * created_time : 1528514785000
             * publishTime : 2018-06-09
             * headImg : [图片]https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180611/20180611201642094401192.png
             * model_type : 1
             * verfy_date : null
             * is_show : 0
             * is_delete : 0
             * fabType : 2
             * order_date : 1528514785000
             * conType : 1
             * phone : 13120238966
             * user_id : 1375
             * detail_summary : 测试专员添加分享
             * fabulous_num : 0
             * name : 匿名会员
             * create_user :
             * userType : 0
             * created_ip :
             * status : 2
             */

            private int comment_num;
            private Object consultation_classify_id;
            private Object source;
            private String memberHeadImg;
            private int img_num;
            private String title;
            private int type;
            private long update_time;
            private int read_num;
            private int user_type;
            private Object description_name;
            private Object rejection_reason;
            private int id;
            private Object top_time;
            private Object author_info;
            private int share_num;
            private long created_time;
            private String publishTime;
            private String headImg;
            private int model_type;
            private Object verfy_date;
            private int is_show;
            private int is_delete;
            private int fabType;
            private long order_date;
            private int conType;
            private String phone;
            private int user_id;
            private String detail_summary;
            private int fabulous_num;
            private String name;
            private String create_user;
            private String userType;
            private String created_ip;
            private int status;
            private List<?> info;

            public int getComment_num() {
                return comment_num;
            }

            public void setComment_num(int comment_num) {
                this.comment_num = comment_num;
            }

            public Object getConsultation_classify_id() {
                return consultation_classify_id;
            }

            public void setConsultation_classify_id(Object consultation_classify_id) {
                this.consultation_classify_id = consultation_classify_id;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public String getMemberHeadImg() {
                return memberHeadImg;
            }

            public void setMemberHeadImg(String memberHeadImg) {
                this.memberHeadImg = memberHeadImg;
            }

            public int getImg_num() {
                return img_num;
            }

            public void setImg_num(int img_num) {
                this.img_num = img_num;
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

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }

            public int getRead_num() {
                return read_num;
            }

            public void setRead_num(int read_num) {
                this.read_num = read_num;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public Object getDescription_name() {
                return description_name;
            }

            public void setDescription_name(Object description_name) {
                this.description_name = description_name;
            }

            public Object getRejection_reason() {
                return rejection_reason;
            }

            public void setRejection_reason(Object rejection_reason) {
                this.rejection_reason = rejection_reason;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getTop_time() {
                return top_time;
            }

            public void setTop_time(Object top_time) {
                this.top_time = top_time;
            }

            public Object getAuthor_info() {
                return author_info;
            }

            public void setAuthor_info(Object author_info) {
                this.author_info = author_info;
            }

            public int getShare_num() {
                return share_num;
            }

            public void setShare_num(int share_num) {
                this.share_num = share_num;
            }

            public long getCreated_time() {
                return created_time;
            }

            public void setCreated_time(long created_time) {
                this.created_time = created_time;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getModel_type() {
                return model_type;
            }

            public void setModel_type(int model_type) {
                this.model_type = model_type;
            }

            public Object getVerfy_date() {
                return verfy_date;
            }

            public void setVerfy_date(Object verfy_date) {
                this.verfy_date = verfy_date;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public int getFabType() {
                return fabType;
            }

            public void setFabType(int fabType) {
                this.fabType = fabType;
            }

            public long getOrder_date() {
                return order_date;
            }

            public void setOrder_date(long order_date) {
                this.order_date = order_date;
            }

            public int getConType() {
                return conType;
            }

            public void setConType(int conType) {
                this.conType = conType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getDetail_summary() {
                return detail_summary;
            }

            public void setDetail_summary(String detail_summary) {
                this.detail_summary = detail_summary;
            }

            public int getFabulous_num() {
                return fabulous_num;
            }

            public void setFabulous_num(int fabulous_num) {
                this.fabulous_num = fabulous_num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreate_user() {
                return create_user;
            }

            public void setCreate_user(String create_user) {
                this.create_user = create_user;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getCreated_ip() {
                return created_ip;
            }

            public void setCreated_ip(String created_ip) {
                this.created_ip = created_ip;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<?> getInfo() {
                return info;
            }

            public void setInfo(List<?> info) {
                this.info = info;
            }
        }
    }
}