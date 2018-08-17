package com.yst.onecity.bean.chatmodel;

import com.tencent.TIMGroupPendencyHandledStatus;
import com.tencent.TIMGroupPendencyItem;

/**
 * 群关系链消息的界面绑定数据
 * 可用于本地操作后界面修改
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class GroupFuture {

    private TIMGroupPendencyItem futureItem;

    private TIMGroupPendencyHandledStatus type;

    public GroupFuture(TIMGroupPendencyItem item){
        futureItem = item;
        type = item.getHandledStatus();
    }

    public TIMGroupPendencyHandledStatus getType() {
        return type;
    }

    public void setType(TIMGroupPendencyHandledStatus type) {
        this.type = type;
    }

    public TIMGroupPendencyItem getFutureItem() {
        return futureItem;
    }

    public void setFutureItem(TIMGroupPendencyItem futureItem) {
        this.futureItem = futureItem;
    }
}
