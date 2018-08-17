package com.yst.onecity.bean.addorder;

/**
 * 发票信息实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/21
 */

public class InvoiceBean {

    /**
     * 类型
     */
    private int type;
    /**
     * 名称
     */
    private String name;
    /**
     * 抬头名称
     */
    private String head;
    /**
     * 识别码
     */
    private String code;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
