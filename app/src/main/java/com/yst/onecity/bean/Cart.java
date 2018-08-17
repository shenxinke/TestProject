package com.yst.onecity.bean;

import java.util.List;

/**
 * 购物车的bean
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/30
 */

public class Cart {
    private boolean isGroupEdit;
    private boolean isGroupCheck;

    private List<BigCartBean.ContentBean> list;

    public Cart(boolean isGroupEdit, boolean isGroupCheck, List<BigCartBean.ContentBean> list) {
        this.isGroupEdit = isGroupEdit;
        this.isGroupCheck = isGroupCheck;
        this.list = list;
    }

    public Cart() {
    }

    public boolean isGroupEdit() {
        return isGroupEdit;
    }

    public void setGroupEdit(boolean groupEdit) {
        isGroupEdit = groupEdit;
    }

    public boolean isGroupCheck() {
        return isGroupCheck;
    }

    public void setGroupCheck(boolean groupCheck) {
        isGroupCheck = groupCheck;
    }

    public List<BigCartBean.ContentBean> getList() {
        return list;
    }

    public void setList(List<BigCartBean.ContentBean> list) {
        this.list = list;
    }
}
