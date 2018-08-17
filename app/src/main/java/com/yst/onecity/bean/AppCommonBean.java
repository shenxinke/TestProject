package com.yst.onecity.bean;

/**
 * 用户登陆基本信息
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class AppCommonBean {

    private String phone = "";
    private String uuid = "";
    private String hunter_id = "";
    private String address = "";
    private String im_id = "";
    private String nickname = "";
    private String id = "";
    private String ident = "";
    private String im_password = "";
    private String password = "";
    private String headhunterGoodsService = "";
    private String goodsDetailImgIDs = "";
    private String ImSign = "";
    private String card = "";
    private String name = "";
    private String token = "";
    private String isNewYeTai = "";
    private String provinceName = "";
    private String cityName = "";
    private String countyName = "";
    /**
     * 猎头图片地址
     */
    private String headhunterAddress;
    /**
     * 猎头昵称
     */
    private String headhunterNickName;
    /**
     * 0普通会员  1推广师 2门店
     */
    private int userType;

    public String getIsCertification() {
        return card;
    }

    public void setIsCertification(String isCertification) {
        this.card = isCertification;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIsNewYeTai() {
        return isNewYeTai;
    }

    public void setIsNewYeTai(String isNewYeTai) {
        this.isNewYeTai = isNewYeTai;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getHeadhunterNickName() {
        return headhunterNickName;
    }

    public void setHeadhunterNickName(String headhunterNickName) {
        this.headhunterNickName = headhunterNickName;
    }

    public String getHeadhunterAddress() {
        return headhunterAddress;
    }

    public void setHeadhunterAddress(String headhunterAddress) {
        this.headhunterAddress = headhunterAddress;
    }

    public String getImSign() {
        return ImSign;
    }

    public void setImSign(String imSign) {
        ImSign = imSign;
    }

    public String getGoodsDetailImgIDs() {
        return goodsDetailImgIDs;
    }

    public void setGoodsDetailImgIDs(String goodsDetailImgIDs) {
        this.goodsDetailImgIDs = goodsDetailImgIDs;
    }

    public String getHeadhunterGoodsService() {
        return headhunterGoodsService;
    }

    public void setHeadhunterGoodsService(String headhunterGoodsService) {
        this.headhunterGoodsService = headhunterGoodsService;
    }

    public String getHunter_id() {
        return hunter_id;
    }

    public void setHunter_id(String hunter_id) {
        this.hunter_id = hunter_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getIm_password() {
        return im_password;
    }

    public void setIm_password(String im_password) {
        this.im_password = im_password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public String toString() {
        return "AppCommonBean{" +
                "phone='" + phone + '\'' +
                ", uuid='" + uuid + '\'' +
                ", hunter_id='" + hunter_id + '\'' +
                ", address='" + address + '\'' +
                ", im_id='" + im_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", id='" + id + '\'' +
                ", ident='" + ident + '\'' +
                ", im_password='" + im_password + '\'' +
                ", password='" + password + '\'' +
                ", headhunterGoodsService='" + headhunterGoodsService + '\'' +
                ", goodsDetailImgIDs='" + goodsDetailImgIDs + '\'' +
                ", ImSign='" + ImSign + '\'' +
                ", card='" + card + '\'' +
                ", name='" + name + '\'' +
                ", headhunterAddress='" + headhunterAddress + '\'' +
                ", headhunterNickName='" + headhunterNickName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
