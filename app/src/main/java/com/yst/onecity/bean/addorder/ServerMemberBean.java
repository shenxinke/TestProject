package com.yst.onecity.bean.addorder;

import java.util.List;

/**
 * 商品实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ServerMemberBean {

    /**推广师名称*/
    public int hunterId;
    public String name;
    public String image;
    public String note="";

    /**商品列表*/
    public List<SureOrderBean.ContentBean.MerchantListBean.HunterBean.ProductDetailBean> productList;

}
