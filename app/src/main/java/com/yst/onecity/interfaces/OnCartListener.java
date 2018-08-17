package com.yst.onecity.interfaces;

/**
 * 购物车接口
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public interface OnCartListener {
    /**
     * 群组选中
     * @param groupPosition
     */
    public void groupCheck(int groupPosition);

    /**
     * 群组点击
     * @param groupPosition
     */
    public void groupClick(int groupPosition);

//    public void groupEdit(int groupPosition);

    /**
     * 子view选中
     * @param groupPosition
     * @param childPosition
     */
    public void childCheck(int groupPosition, int childPosition);

    /**
     * 子view删除
     * @param groupPosition
     * @param childPosition
     */
    public void childDelete(int groupPosition, int childPosition);

    /**
     * 子view增加
     * @param groupPosition
     * @param childPosition
     */
    public void childIncrease(int groupPosition, int childPosition);

    /**
     * 子view减少
     * @param groupPosition
     * @param childPosition
     */
    public void childReduce(int groupPosition, int childPosition);

    /**
     * 子view点击
     * @param groupPosition
     * @param childPosition
     */
    public void childClick(int groupPosition, int childPosition);

    /**
     * 子view编辑
     */
    public void childEdit();
//    public void childEditChange(int groupPosition, int childPosition,long productCount);
}
