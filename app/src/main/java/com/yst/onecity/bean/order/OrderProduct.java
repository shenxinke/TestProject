package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 会员线上订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OrderProduct {
    private String hId;
    private String merchantName;
    private String merchantImg;
    private String mechantId;
    private String hImId;
    private List<ProductDetailBean> pList;

    public String getMechantId() {
        return mechantId;
    }

    public void setMechantId(String mechantId) {
        this.mechantId = mechantId;
    }

    public String gethId() {
        return hId;
    }

    public void sethId(String hId) {
        this.hId = hId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<ProductDetailBean> getpList() {
        return pList;
    }

    public void setpList(List<ProductDetailBean> pList) {
        this.pList = pList;
    }

    public String getMerchantImg() {
        return merchantImg;
    }

    public void setMerchantImg(String merchantImg) {
        this.merchantImg = merchantImg;
    }

    public String gethImId() {
        return hImId;
    }

    public void sethImId(String hImId) {
        this.hImId = hImId;
    }



    public class ProductDetailBean {
        /**
         * phType : 0
         * salePrice : 5
         * totalPrice : 0.5
         * pImg : upload/memberheadimg/20170717100550711414593.png
         * num : 1
         * pContent : 说个法规
         * pame : 好东西
         * pScore : 0.5
         */
        private String phType;
        private double salePrice;
        private double totalPrice;
        private String pImg;
        private int num;
        private String pContent;
        private String pame;
        private double pScore;
        private double totalScore;
        private String oId;
        private String pId;
        private String hId;
        private int psId;
        private String weight;
        private String scorePrice;

        public String getScorePrice() {
            return scorePrice;
        }

        public void setScorePrice(String scorePrice) {
            this.scorePrice = scorePrice;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getPsId() {
            return psId;
        }

        public void setPsId(int psId) {
            this.psId = psId;
        }

        public String getPhType() {
            return phType;
        }

        public void setPhType(String phType) {
            this.phType = phType;
        }

        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getpImg() {
            return pImg;
        }

        public void setpImg(String pImg) {
            this.pImg = pImg;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getpContent() {
            return pContent;
        }

        public void setpContent(String pContent) {
            this.pContent = pContent;
        }

        public String getPame() {
            return pame;
        }

        public void setPame(String pame) {
            this.pame = pame;
        }

        public double getpScore() {
            return pScore;
        }

        public void setpScore(double pScore) {
            this.pScore = pScore;
        }

        public double getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(double totalScore) {
            this.totalScore = totalScore;
        }

        public String getoId() {
            return oId;
        }

        public void setoId(String oId) {
            this.oId = oId;
        }

        public String getpId() {
            return pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }

        public String gethId() {
            return hId;
        }

        public void sethId(String hId) {
            this.hId = hId;
        }
    }
}
