package com.yst.onecity.bean.information;

import com.yst.onecity.bean.CodeMsgBean;

import java.util.List;

/**
 * 咨询实体
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class InformationBean extends CodeMsgBean {

    private List<InformationContentBean> content;

    public List<InformationContentBean> getContent() {
        return content;
    }

    public void setContent(List<InformationContentBean> content) {
        this.content = content;
    }
}
