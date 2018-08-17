package com.yst.onecity.view.editor;

import android.content.Context;

import java.util.List;

/**
 * SortRichEditor接口
 *
 * @author  jiaofan
 * @date    2018/6/5
 * @version 4.2.0
 */
public interface SEditorDataI {

    /**
     * 插入一段文字
     * @param str 要添加的文字
     */
    void addText(String str);

    /**
     * 根据绝对路径添加一张图片
     *
     * @param imagePath
     */
    void addImage(String imagePath);

    /**
     * 根据网络路径添加一张图片
     * @param imagePath 本地路径
     * @param imageId   图片id
     * @param isGoods   是否为商品
     * @param context   当前
     * @param isSet     是否为回显
     */
    void addNetImage(String imagePath, String imageId, boolean isGoods, Context context, boolean isSet);

    /**
     * 根据网络路径添加一张图片，带url
     * @param imagePath 本地路径
     * @param imageId   图片id
     * @param url       网络地址
     * @param isGoods   是否为商品
     * @param context   当前
     * @param isSet     是否为回显
     */
    void addNetImage(String imagePath, String url, String imageId, boolean isGoods, Context context, boolean isSet);

    /**
     * 根据图片绝对路径数组批量添加一组图片
     *
     * @param imagePaths
     */
    void addImageArray(String[] imagePaths);

    /**
     * 根据图片绝对路径集合批量添加一组图片
     *
     * @param imageList
     */
    void addImageList(List<String> imageList);

    /**
     * 获取标题
     *
     * @return
     */
    String getTitleData();

    /**
     * 生成编辑数据
     * @return 数据集合
     */
    List<SEditorData> buildEditData();

    /**
     * 获取当前编辑器中图片数量
     * @return
     */
    int getImageCount();

    /**
     * 编辑器内容是否为空
     *
     * @return
     */
    boolean isContentEmpty();

    /**
     * 获取所有编辑框文本内容总长度
     *
     * @return
     */
    int getAllTextLength();

}
