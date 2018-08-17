package com.yst.onecity.eventbus;

/**
 * 更新sp
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class UpdateSpEvent {

    private String spid;
    private String num;
    private String stid;
    private String spName;
    private String selectOne;
    private String selectTwo;
    private String price;
    private String sortOneName;
    private String standardOneName;
    private boolean isMoreSp;
    private String choosedContent;


    public String getChoosedContent() {
        return choosedContent;
    }

    public void setChoosedContent(String choosedContent) {
        this.choosedContent = choosedContent;
    }

    public boolean isMoreSp() {
        return isMoreSp;
    }

    public void setMoreSp(boolean moreSp) {
        isMoreSp = moreSp;
    }

    public String getStandardTwoName() {
        return standardTwoName;
    }

    public void setStandardTwoName(String standardTwoName) {
        this.standardTwoName = standardTwoName;
    }

    public String getStandardOneName() {
        return standardOneName;
    }

    public void setStandardOneName(String standardOneName) {
        this.standardOneName = standardOneName;
    }

    private String standardTwoName;

    public String getSortOneName() {
        return sortOneName;
    }

    public void setSortOneName(String sortOneName) {
        this.sortOneName = sortOneName;
    }

    public String getSortTwoName() {
        return sortTwoName;
    }

    public void setSortTwoName(String sortTwoName) {
        this.sortTwoName = sortTwoName;
    }

    private String sortTwoName;

    public UpdateSpEvent(String choosedContent,String spid, String num, String stid,String spName,String selectOne,String selectTwo,String price,String sortOneName,String sortTwoName,String standardOneName,String standardTwoName,boolean isMoreSp) {
        this.choosedContent = choosedContent;
        this.spid = spid;
        this.num = num;
        this.stid = stid;
        this.spName = spName;
        this.selectOne = selectOne;
        this.selectTwo = selectTwo;
        this.price = price;
        this.sortOneName = sortOneName;
        this.sortTwoName = sortTwoName;
        this.standardOneName = standardOneName;
        this.standardTwoName = standardTwoName;
        this.isMoreSp = isMoreSp;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSelectOne() {
        return selectOne;
    }

    public void setSelectOne(String selectOne) {
        this.selectOne = selectOne;
    }

    public String getSelectTwo() {
        return selectTwo;
    }

    public void setSelectTwo(String selectTwo) {
        this.selectTwo = selectTwo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
