package com.yst.onecity.bean.product;

import java.util.List;

/**
 * 商品评价
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductCommentListBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"badnum":0,"allcommentnum":125,"contentList":[{"isone":0,"pcontent":"好评好评","soncontentList":[],"pcreatedtime":"2017-09-21 16:22:11","preplynum":0,"pnickname":"我是会员","pstart":3,"pfabulous":0,"pcommentid":2,"pmemberid":1,"paddress":"upload/memberheadimg/20170717100550711414593.png"}],"goodnum":123,"commentnum":2}
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
         * badnum : 0
         * allcommentnum : 125
         * contentList : [{"isone":0,"pcontent":"好评好评","soncontentList":[],"pcreatedtime":"2017-09-21 16:22:11","preplynum":0,"pnickname":"我是会员","pstart":3,"pfabulous":0,"pcommentid":2,"pmemberid":1,"paddress":"upload/memberheadimg/20170717100550711414593.png"}]
         * goodnum : 123
         * commentnum : 2
         */

        private int badnum;
        private int allcommentnum;
        private int goodnum;
        private int commentnum;
        private String feedback;
        private List<ContentListBean> contentList;

        public int getBadnum() {
            return badnum;
        }

        public void setBadnum(int badnum) {
            this.badnum = badnum;
        }

        public int getAllcommentnum() {
            return allcommentnum;
        }

        public void setAllcommentnum(int allcommentnum) {
            this.allcommentnum = allcommentnum;
        }

        public int getGoodnum() {
            return goodnum;
        }

        public void setGoodnum(int goodnum) {
            this.goodnum = goodnum;
        }

        public int getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(int commentnum) {
            this.commentnum = commentnum;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public List<ContentListBean> getContentList() {
            return contentList;
        }

        public void setContentList(List<ContentListBean> contentList) {
            this.contentList = contentList;
        }

        public static class ContentListBean {
            /**
             * isone : 0
             * pcontent : 好评好评
             * soncontentList : []
             * pcreatedtime : 2017-09-21 16:22:11
             * preplynum : 0
             * pnickname : 我是会员
             * pstart : 3
             * pfabulous : 0
             * pcommentid : 2
             * pmemberid : 1
             * paddress : upload/memberheadimg/20170717100550711414593.png
             */

            private int isone;
            private String pcontent;
            private String pcreatedtime;
            private int preplynum;
            private String pnickname;
            private int pstart;
            private String product_specification_name;
            private int pfabulous;
            private int pcommentid;
            private int pmemberid;
            private String paddress;
            private String pdz;
            private List<?> soncontentList;

            public int getIsone() {
                return isone;
            }

            public void setIsone(int isone) {
                this.isone = isone;
            }

            public String getPcontent() {
                return pcontent;
            }

            public void setPcontent(String pcontent) {
                this.pcontent = pcontent;
            }

            public String getPcreatedtime() {
                return pcreatedtime;
            }



            public void setPcreatedtime(String pcreatedtime) {
                this.pcreatedtime = pcreatedtime;
            }

            public int getPreplynum() {
                return preplynum;
            }

            public void setPreplynum(int preplynum) {
                this.preplynum = preplynum;
            }

            public String getPnickname() {
                return pnickname;
            }

            public void setPnickname(String pnickname) {
                this.pnickname = pnickname;
            }

            public int getPstart() {
                return pstart;
            }

            public void setPstart(int pstart) {
                this.pstart = pstart;
            }

            public int getPfabulous() {
                return pfabulous;
            }

            public void setPfabulous(int pfabulous) {
                this.pfabulous = pfabulous;
            }

            public int getPcommentid() {
                return pcommentid;
            }

            public void setPcommentid(int pcommentid) {
                this.pcommentid = pcommentid;
            }

            public int getPmemberid() {
                return pmemberid;
            }

            public void setPmemberid(int pmemberid) {
                this.pmemberid = pmemberid;
            }

            public String getPaddress() {
                return paddress;
            }

            public void setPaddress(String paddress) {
                this.paddress = paddress;
            }

            public String getProduct_specification_name() {
                return product_specification_name;
            }

            public void setProduct_specification_name(String product_specification_name) {
                this.product_specification_name = product_specification_name;
            }

            public String getPdz() {
                return pdz;
            }

            public void setPdz(String pdz) {
                this.pdz = pdz;
            }

            public List<?> getSoncontentList() {
                return soncontentList;
            }

            public void setSoncontentList(List<?> soncontentList) {
                this.soncontentList = soncontentList;
            }
        }
    }
}
