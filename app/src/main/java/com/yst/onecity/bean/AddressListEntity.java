package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 收货地址列表
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class AddressListEntity extends CodeMsgBean implements Serializable {

    private List<AddressEntity> content;

    public List<AddressEntity> getContent() {
        return content;
    }

    public void setContent(List<AddressEntity> content) {
        this.content = content;
    }
}