package com.yst.onecity.bean;

/**
 * 基础实体类
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/6/2.
 */
public class BasicBean {
    public int code;
    public String msg;
    public String address;
    public String content;

    @Override
    public String toString() {
        return "BasicBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
