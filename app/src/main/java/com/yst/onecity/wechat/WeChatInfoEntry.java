package com.yst.onecity.wechat;

import com.yst.onecity.bean.CodeMsgBean;

/**
 * 微信支付服务端返回订单信息实体
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class WeChatInfoEntry extends CodeMsgBean {

    private WeChatOrderEntry content;

    public WeChatOrderEntry getContent() {
        return content;
    }

    public void setContent(WeChatOrderEntry content) {
        this.content = content;
    }
}
