package com.yst.onecity.activity.amap;

import com.amap.api.services.core.PoiItem;

/**
 * 本地结果实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class LocalResultEntity {
    private PoiItem item;
    private boolean checked;
    public LocalResultEntity(PoiItem item){
        this.item = item;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public PoiItem getItem() {
        return item;
    }

    public boolean isChecked() {
        return checked;
    }
}