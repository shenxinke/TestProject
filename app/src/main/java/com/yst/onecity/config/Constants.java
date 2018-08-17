package com.yst.onecity.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态常量管理类
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class Constants {

    /**
     * 录入资讯详情类型 文本：0；图片：1；商品：2
     */
    public static final int INPUT_NEWS_DETAIL_TYPE_TEXT = 0;
    public static final int INPUT_NEWS_DETAIL_TYPE_IMG = 1;
    public static final int INPUT_NEWS_DETAIL_TYPE_PRODUCT = 2;
    /**
     * 发布资讯添加商品时，如果既是推广师也是会员，那么只有在推广师中心可以添加商品
     */
    public static boolean ISSERVER = false;
    /**
     * 订单跳转，0会员1推广师
     */
    public static int ORDER_ISMEMBER = 0;
    public static final String WECHAT_APP_ID = "wxa99c21544d43650a";
    public static final String WECHAT_APP_SECRET = "abd9d416c08eb98da91510a8d128eccc";
    public static List<String> ids = new ArrayList<>();
    /**
     * apk路径
     */
    public static String lujing;
    public static String APP_NAME = "ty.apk";
    /**
     * 服务器地址（测试环境）
     */
    public static final String URL_ROOT = "http://123.207.157.207:28080/";

    /**
     * 域名 正式环境
     */
//    public static final String URL_ROOT = "http://mobile.pujiyc.com/";

    /**
     * 图片服务器路径
     */
    public static final String URL_IMAGE_HEAD = "http://yst-images.img-cn-hangzhou.aliyuncs.com/";

    /**
     * 获取未读消息数量
     */
    public static final String GET_UNREADMESSAGE = URL_ROOT + "mobile/view/membermsg/num";

    /**
     * 修改消息为已读
     */
    public static final String CHANGE_MESSAGE_STATUS = URL_ROOT + "mobile/view/membermsg/update-read-type";

    /**
     * 身份认证
     */
    public static final String URL_CHECKHUNTER = URL_ROOT + "mobile/after/hunter/checkhunter";

    /**
     * 银行卡列表
     */
    public static final String URL_GETBANKCARDLIST = URL_ROOT + "mobile/view/bankcard/getbankcardlist";

    /**
     * 修改用户的昵称
     */
    public static final String URL_UPDATE_NICKNAME = URL_ROOT + "mobile/after/member/modifyMemberNickname";

    /**
     * 获取商品分类和规格
     */
    public static final String GET_PRODUCT_SORT_STANDARD = URL_ROOT + "mobile/view/detail/saleproducttype";
    /**
     * 加入购物车
     */
    public static final String ADD_SHOP_CART = URL_ROOT + "mobile/after/shopcart/add";
    /**
     * 立即购买
     */
    public static final String NOW_BUY = URL_ROOT + "mobile/after/order/ptopay";
    /**
     * 获取购物车列表
     */
    public static final String GET_CART_LIST = URL_ROOT + "mobile/after/shopcart/cart-list";

    /**
     * 更新购物车列表
     */
    public static final String UPDATE_CART_LIST = URL_ROOT + "mobile/after/shopcart/update";

    /**
     * 购物车删除商品
     */
    public static final String DELETE_CART_PRODUCT = URL_ROOT + "mobile/after/shopcart/delete";

    /**
     * 下单
     */
    public static final String URL_TOCREATEORDER = URL_ROOT + "mobile/after/order/tocreateorder";

    /**
     * 商品结算
     */
    public static final String URL_SETTLEMENT_TOPAY = URL_ROOT + "mobile/after/order/topay";
    /**
     * 手机号和验证码登录
     */
    public static final String LOGIN_WITH_CODE = URL_ROOT + "mobile/view/login/message";
    /**
     * 手机号和密码登录
     */
    public static final String LOGIN_WITH_PASSWORD = URL_ROOT + "mobile/view/login/password";
    /**
     * 登录获取验证码
     * public static final String LOGIN_GET_CODE = URL_ROOT + "mobile/view/login/code";
     */
    public static final String LOGIN_GET_CODE = URL_ROOT + "mobile/view/login/getSMSInfoCode";
    /**
     * 注册
     */
    public static final String REGISTER = URL_ROOT + "mobile/view/register/member";
    /**
     * 实名认证
     */
    public static final String REALNAMEAUTHCATION = URL_ROOT + "mobile/after/member/RealNameCretification";
    /**
     * 用户个人认证信息
     */
    public static final String AUTHCATIONINFO = URL_ROOT + "mobile/after/member/getMemberCertifiInfo";
    /**
     * 修改用户登录密码
     */
    public static final String CHANGE_LOGIN_PASSWORD = URL_ROOT + "mobile/after/member/modifyMemberPassword";
    /**
     * 账户安全收取验证码
     */
    public static final String ACCOUNT_SAFE_GET_CAODE = URL_ROOT + "mobile/after/bankcard/SendMobileMsg";
    /**
     * 验证验证码
     */
    public static final String MOBILEVERIFICATION = URL_ROOT + "mobile/after/bankcard/MobileVerification";
    /**
     * 收货地址列表
     */
    public static final String ADDRESS_LIST = URL_ROOT + "mobile/after/memberaddress/address-list";
    /**
     * 添加收货地址
     */
    public static final String ADD_RECEIVE_ADDRESS = URL_ROOT + "mobile/after/memberaddress/saveaddress";
    /**
     * 修改收货地址
     */
    public static final String UPDATE_RECEIVE_ADDRESS = URL_ROOT + "mobile/after/memberaddress/updateaddress";
    /**
     * 服务专员头像上传
     */
    public static final String SERVER_MEMBER_UPLOADHEAD = URL_ROOT + "mobile/view/attachment/mobileUpload-salecircleattachment";
    /**
     * 推广是标签填写
     */
    public static final String ADD_BIAOQIAN = URL_ROOT + "mobile/after/memberLabel/addLabels";
    /**
     * 推广是背景图
     */
    public static final String TUIGUANG_BG_IMAGE = URL_ROOT + "mobile/view/hunter/getHunterBackGroundList";
    /**
     * 推广是修改基本信息
     */
    public static final String UPDATE_TUIGUANGSHI_INFO = URL_ROOT + "mobile/after/hunter/updateHunterInfo";
    /**
     * 会员个人信息
     */
    public static final String MEMBER_INFO = URL_ROOT + "mobile/after/member/membercenter";

    /**
     * 商品收藏
     */
    public static final String COLLECTION_PRODUCT = URL_ROOT + "mobile/after/collectionproduct/collect";
    /**
     * 商品收藏列表
     */
    public static final String URL_COLLECTION_PRODUCT = URL_ROOT + "mobile/after/collectionproduct/list";

    /**
     * 修改头像
     */
    public static final String URL_SAVE_MEMBER_HEAD_SCULPTURE = URL_ROOT + "mobile/after/member/saveMemberHeadSculpture";

    /**
     * 用户是否绑卡
     */
    public static final String URL_TO_BIND_BANKCARD = URL_ROOT + "mobile/after/bankcard/ToBindBankCard";

    /**
     * 用户绑卡发送验证码
     */
    public static final String URL_SEND_MOBILEMSG = URL_ROOT + "mobile/after/bankcard/SendMobileMsg";

    /**
     * 用户绑卡验证验证码
     */
    public static final String URL_MOBILE_VERIFICATION = URL_ROOT + "mobile/after/bankcard/MobileVerification";

    /**
     * 用户绑卡
     */
    public static final String URL_BIND_BANKCARD = URL_ROOT + "mobile/after/bankcard/BindBankCard";

    /**
     * 用户更新银行卡
     */
    public static final String URL_UPDATE_BANKCARD = URL_ROOT + "mobile/after/bankcard/UpdateBankCard";

    /**
     * 用户删除银行卡
     */
    public static final String URL_DELETE_BANKCARD = URL_ROOT + "mobile/after/bankcard/DeleteBankCard";

    /**
     * 收藏资讯列表
     */
    public static final String URL_MYCOLLECTION_LIST = URL_ROOT + "mobile/after/collectioncontent/mycollection-list";

    /**
     * 咨询点赞
     */
    public static final String CONSULT_LIKE = URL_ROOT + "mobile/after/consultationfabulous/fabulous";
    /**
     * 咨询收藏
     */
    public static final String CONSULT_COLLECTION = URL_ROOT + "mobile/after/collectioncontent/collection-content";
    /**
     * 首页 资讯分类
     */
    public static final String GET_HOME_SORT = URL_ROOT + "mobile/view/consultationClassify/findConsultationClassify";
    /**
     * 推广师详情页咨询分类
     */
    public static final String SERVER_MEMBER_SORT = URL_ROOT + "mobile/view/consultationClassify/findConsultationClassifyUser";
    /**
     * 首页咨询列表
     */
    public static final String GET_HOME_CONSULT_LIST = URL_ROOT + "mobile/view/consultation/findconsultationinfobylable";
    /**
     * 咨询回复列表
     */
    public static final String CONSULT_REPLY_LIST = URL_ROOT + "mobile/after/consultationcomment/consultationcomment-list";
    /**
     * 咨询回复
     */
    public static final String CONSULT_REPLY = URL_ROOT + "mobile/after/consultationcomment/replyconsultationcomment";
    /**
     * 咨询详情
     */
    public static final String CONSULT_DETAIL = URL_ROOT + "mobile/view/consultation/getConsultationDetailInfo";
    /**
     * 服务专员咨询列表
     */
    public static final String SERVER_MEMBER_CONSULT_LIST = URL_ROOT + "mobile/view/consultation/findConsultationInfoByHunterId";
    /**
     * 服务专员 商品列表
     */
    public static final String SERVER_MEMBER_PRODUCT_LIST = URL_ROOT + "mobile/view/hunter/list";
    /**
     * 商品详情
     */
    public static final String PRODUCT_DETAIL = URL_ROOT + "mobile/view/detail/text";
    /**
     * 增加商品浏览量
     */
    public static final String PRODUCT_DETAIL_ADDVIEWS = URL_ROOT + "mobile/view/productshow/addProductViews";
    /**
     * 商品图片详情
     */
    public static final String PRODUCT_DETAIL_PIC = URL_ROOT + "mobile/view/detail/image";
    /**
     * 商品评价列表 (未登录)
     */
    public static final String PRODUCT_COMMENT_UNLOGIN = URL_ROOT + "mobile/view/ordercomment/productcommentlist";
    /**
     * 商品评价列表(登录)
     */
    public static final String PRODUCT_COMMENT_LOGIN = URL_ROOT + "mobile/after/ordercomment/productcommentlist";
    /**
     * 发布评论
     */
    public static final String PUBLISH_COMMENT = URL_ROOT + "mobile/after/consultationcomment/replyconsultationcomment";
    /**
     * 咨询评论列表
     */
    public static final String CONSULT_COMMENT_INFO = URL_ROOT + "mobile/view/consultation/getCconsultationcomment ";
    /**
     * 对资讯进行评论
     */
    public static final String CONSULT_COMMENT = URL_ROOT + "mobile/after/consultationcomment/directconsultationcomment";
    /**
     * 首页搜索
     */
    public static final String HOMEPAGE_SEARCH = URL_ROOT + "mobile/view/consultation/findconsultationinfo";
    /**
     * /搜索推广师
     */
    public static final String SEARCH_SERVER_MEMBER = URL_ROOT + "mobile/view/hunter/getHunterSearchList";
    /**
     * 推广师列表
     */
    public static final String GET_SERVER_MEMBER_LIST = URL_ROOT + "mobile/view/hunter/getHunterSearchList";
    /**
     * 推广师详情
     */
    public static final String SERVER_MEMBER_DETAIL = URL_ROOT + "mobile/view/hunter/getHunterInfo";
    /**
     * 推广师商品分类
     */
    public static final String SERVER_MEMBER_PRODUCT_SORT = URL_ROOT + "mobile/view/productyypeview/firstproducttype";
    /**
     * 管理地址获取区
     */
    public static final String GET_AREA = URL_ROOT + "mobile/view/area/findDistrictListByPId";
    /**
     * 获取店铺详情
     */
    public static final String GET_MERCHANT_DETAIL = URL_ROOT + "mobile/view/hunter/getmerchantdetail";
    /**
     * 关注推广师
     */
    public static final String ATTENTION_HUNTER = URL_ROOT + "mobile/after/collectionhunter/collect";
    /**
     * 我的关注列表
     */
    public static final String URL_COLLECTION_HUNTER = URL_ROOT + "mobile/after/collectionhunter/list";

    /**
     * 取消关注
     */
    public static final String URL_COLLECTION_CANCEL = URL_ROOT + "mobile/after/collectionhunter/collect";

    /**
     * 资讯分类
     */
    public static final String NEWS_TYPE = URL_ROOT + "mobile/view/consultationClassify/findConsultationClassify";
    /**
     * 发布资讯
     */
    public static final String PUBLISH_NEWS = URL_ROOT + "mobile/after/consultation/addConsultation";
    /**
     * 资讯、草稿箱列表
     */
    public static final String MY_NEWS_LIST = URL_ROOT + "mobile/after/consultation/findconsultationall";
    /**
     * 发布资讯添加图片
     */
    public static final String UPLOAD_IMAGE = URL_ROOT + "mobile/view/consultation/mobileUpload-uploadconsultationattachment";
    /**
     * 显示或不显示
     */
    public static final String SHOW_NEWS = URL_ROOT + "mobile/after/consultation/hideConsultationById";
    /**
     * 删除资讯
     */
    public static final String DELETE_NEWS = URL_ROOT + "mobile/after/consultation/deleteconsultation";
    /**
     * 发布商品列表
     */
    public static final String PRODUCT_LIST = URL_ROOT + "mobile/after/productmanager/findProductByHunterId";
    /**
     * 服务专员查询线下订单列表
     */
    public static final String SERVER_UNLINE_ORDER_LIST = URL_ROOT + "mobile/after/order/queryUnlineOrdersForServer";
    /**
     * 服务专员查询线上订单列表
     */
    public static final String SERVER_ONLINE_ORDER_LIST = URL_ROOT + "mobile/after/order/queryOnlineOrdersOfServer";
    /**
     * 会员线上订单
     */
    public static final String MEMBER_ONLINE_ORDER = URL_ROOT + "mobile/after/order/queryOnlineOrders";
    /**
     * 会员线下订单
     */
    public static final String MEMBER_UNLINE_ORDER = URL_ROOT + "mobile/after/order/queryUnlineOrders";
    /**
     * 服务专员我的账户余额
     */
    public static final String SERVERMEMBER_MYCOUNT_YUE = URL_ROOT + "mobile/after/hunter/GetHunterResidueMoney";
    /**
     * 管理地址获取省
     */
    public static final String GET_PROVINCE = URL_ROOT + "mobile/view/area/findprolist";
    /**
     * 管理地址获取市
     */
    public static final String GET_CITY = URL_ROOT + "mobile/view/area/findcitylistbypid";

    /**
     * 服务专员基本信息
     */
    public static final String SERVERMEMBER_BASE_INFO = URL_ROOT + "mobile/view/detail/basemessage";
    /**
     * 服务专员信息
     */
    public static final String SERVERMEMBER_INFO = URL_ROOT + "mobile/after/hunter/getHunterInfo";

    /**
     * 删除多个收藏的商品
     */
    public static final String URL_DEL_COLLECTION_GOOD = URL_ROOT + "mobile/after/collectionproduct/del-collection-product";

    /**
     * 删除多个收藏的资讯
     */
    public static final String URL_DEL_COLLECTION_CONTENT = URL_ROOT + "mobile/after/collectioncontent/del-collection-content";
    /**
     * 推广师标签填写
     */
    public static final String ADD_LABLES = URL_ROOT + "mobile/after/memberLabel/addLabels";
    /**
     * 服务专员及会员获取余额明细列表
     */
    public static final String BALANCE_DETAIL_LIST = URL_ROOT + "mobile/after/usermoney/getUserMoneysXytById";
    /**
     * 会员去提现
     */
    public static final String MEMBER_CASH = URL_ROOT + "mobile/after/cashmoney/withDrawals";
    /**
     * 会员线下订单详情
     */
    public static final String MEMBER_UNORDER_DETAIL = URL_ROOT + "mobile/after/order/queryUnlineOrderDetail";
    /**
     * 会员线上订单详情
     */
    public static final String MEMBER_ONLINE_ORDER_DETAIL = URL_ROOT + "mobile/after/order/queryOnlineOrderDeail";
    /**
     * 服务专员线上订单详情
     */
    public static final String SERVER_ONLINE_ORDER_DETAIL = URL_ROOT + "mobile/after/order/queryOnlineOrderDeailForServer";
    /**
     * 服务专员线下订单详情
     */
    public static final String MEMBER_UNLINE_ORDER_DETAIL = URL_ROOT + "mobile/after/order/queryUnlineOrderDetailForServer";
    /**
     * 资讯修改
     */
    public static final String EDIT_NEWS = URL_ROOT + "mobile/after/consultation/updateConsultation";
    /**
     * 会员天天奖
     */
    public static final String MEMBER_DAYDAY = URL_ROOT + "mobile/after/score/memberscore";
    /**
     * 会员天天奖余额明细
     */
    public static final String MEMBER_DAYDAYLIST = URL_ROOT + "mobile/after/score/memberdetailscore";
    /**
     * 确认收货
     */
    public static final String CONFIRM_RECEIVE = URL_ROOT + "mobile/after/order/confirmReceive";
    /**
     * 订单评价
     */
    public static final String ORDER_COMMEND = URL_ROOT + "mobile/after/ordercomment/commonComment";
    /**
     * 查询线上订单各个状态的数量
     */
    public static final String QUERY_ORDER_NUM = URL_ROOT + "mobile/after/order/queryOrderStatus";
    /**
     * 订单评论列表
     */
    public static final String COMMENT_LIST = URL_ROOT + "mobile/after/order/toComment";
    /**
     * 服务员专员提现
     */
    public static final String SERVER_MEMBER_CASH = URL_ROOT + "mobile/after/cashreceivestation/withDrawals";
    //    public static final String SERVER_MEMBER_CASH = URL_ROOT + "mobile/after/cashmoney/CashHunterMoney";
    /**
     * 会员列表
     */
    public static final String SERVER_MEMBER_LIST = URL_ROOT + "mobile/after/hunter/getHunterMemberList";
    /**
     * 删除地址
     */
    public static final String DELETE_ADDRESS = URL_ROOT + "mobile/after/memberaddress/deladdressbyid";
    /**
     * 微信、支付宝支付
     */
    public static final String WECHAT_PAY = URL_ROOT + "mobile/after/payorder/sendunifiedorder";
    /**
     * 获取银行卡名称
     */
    public static final String BANKCARD_NAME = URL_ROOT + "mobile/after/bankcard/getBankCardBank";

    /**
     * 分享接口 测试环境
     */
//    public static final String SHARE_CONSULT = "http://123.207.157.207:10000/tymobile/html/";

    /**
     * 分享接口 正式环境
     */
    public static final String SHARE_CONSULT = "http://mobile.pujiyc.com/html/";

    /**
     * 分享服务专员
     */
    public static final String SHARE_SERVER_MEMBER = URL_ROOT + "mobile/view/shareweb/sharehunter";
    /**
     * 评论详情
     */
    public static final String COMMENT_DETAIL = URL_ROOT + "mobile/after/ordercomment/queryCommonCommentDetail";

    /**
     * 修改服务时间
     */
    public static final String SET_SERVER_TIME = URL_ROOT + "mobile/after/member/updateMemeberServerTime";

    /**
     * 分享注册
     */
    public static final String SHARE_REGISTER = URL_ROOT + "mobile/view/webview/to-share-register?";
    /**
     * 版本检测
     */
    public static final String CHECK_VERSION = URL_ROOT + "mobile/view/webview/checkVersion";

    /**
     * 天天奖和开红包
     */
    public static final String ORDER_REWARD = URL_ROOT + "mobile/after/order/reward";
    /**
     * 注册获取图形验证码
     */
    public static final String GET_IMGVERIFYCODE = URL_ROOT + "mobile/view/checkimagecount/securitycodeimg/11?uniquenessCode=1234568&client_type=A";
    /**
     * 验证图形验证码
     */
    public static final String VERIFY_IMGVERIFYCODE = URL_ROOT + "mobile/view/checkimagecount/checkcode/11";

    /**
     * 商城分类
     */
    public static final String GET_MALL_CLASSFITY = URL_ROOT + "mobile/view/productyypeview/getProductType";

    /**
     * 商城品牌
     */
    public static final String GET_MALL_BRAND = URL_ROOT + "mobile/view/brand/getBrandList";

    /**
     * 根据分类获取商品列表
     */
    public static final String GET_GOODSLIST_BYCLASSFITY = URL_ROOT + "mobile/view/productshow/getProductByType";

    /**
     * 验证是否设置支付密码
     */
    public static final String IS_SET_PASSWORD = URL_ROOT + "mobile/after/member/checkPayPasswrod";
    /**
     * 设置支付密码
     */
    public static final String PAYMENT_CODE = URL_ROOT + "mobile/after/member/settingPayPasswrod ";
    /**
     * 修改支付密码短信
     */
    public static final String CHANGE_SHORT_MESSAGE = URL_ROOT + "mobile/after/member/payPasswordSendCode";
    /**
     * 付款码
     */
    public static final String PAY_CODE = URL_ROOT + "/mobile/after/pay/code";
    /**
     * 未开奖订单列表
     */
    public static final String TICKETS_NO_LOTTERY_LIST = URL_ROOT + "mobile/after/member/noLotteryOrderPage";
    /**
     * 未开奖红包列表
     */
    public static final String TICKETS_NO_LOTTERY_RP_LIST = URL_ROOT + "mobile/after/member/noLotteryRedPacketPage";
    /**
     * 已开奖红包列表
     */
    public static final String TICKETS_HAS_LOTTERY_RP_LIST = URL_ROOT + "mobile/after/member/yetLotteryRedPacketPage";
    /**
     * 获奖通知接口
     */
    public static final String TICKETS_NOTIFICATION = URL_ROOT + "mobile/after/member/winAPrizeInform";
    /**
     * 我的奖券模块奖项预览
     */
    public static final String TICKETS_PREVIEWS = URL_ROOT + "mobile/after/member/lotteryPreview";
    /**
     * 我的奖券模块预览详情列表
     */
    public static final String TICKETS_PREVIEWS_DETAILLIST = URL_ROOT + "mobile/after/member/previewDetails";

    /**
     * 我的账户余额
     */
    public static final String MYACCOUNT_MAIN = URL_ROOT + "mobile/after/usermoney/getShopCartList";
    /**
     * 我的账户剩余可用积分
     */
    public static final String MYACCOUNT_MAIN_INTEGRAL = URL_ROOT + "mobile/after/exclusivescore/residueScore";
    /**
     * 我的账户积分明细
     */
    public static final String MYACCOUNT_MAIN_INTEGRAL_LIST = URL_ROOT + "mobile/after/exclusivescore/getScore";
    /**
     * 专员佣金明细
     */
    public static final String MYACCOUNT_MAIN_ZYYJ_LIST = URL_ROOT + "mobile/after/usermoney/getUserMoneysById";
    /**
     * 判断是否设置过支付密码
     */
    public static final String ISSETPASSWORD = URL_ROOT + "mobile/after/member/checkPayPasswrod";
    /**
     * 获取短信验证码
     */
    public static final String GETSMSCODE = URL_ROOT + "mobile/after/member/payPasswordSendCode";
    /**
     * 设置支付密码接口
     */
    public static final String SETPASSWORD = URL_ROOT + "mobile/after/member/settingPayPasswrod";
    /**
     * 普济余额支付
     */
    public static final String BALANCE_PAY = URL_ROOT + "mobile/after/payorder/payOrdersByBalance";
    /**
     * 购物车去结算
     */
    public static final String TO_PAY = URL_ROOT + "mobile/after/order/topay";
    /**
     * 下单
     */
    public static final String CREATE_ORDER = URL_ROOT + "mobile/after/order/tocreateorder";
    /**
     * 积分支付
     */
    public static final String SCORE_PAY = URL_ROOT + "mobile/after/payorder/payOrdersByScore";
    /**
     * 物流信息
     */
    public static final String LOGISTICSINFORMATION = URL_ROOT + "mobile/after/logisticsInfo/list";
    /**
     * 获取城市列表mobile/after/memberaddress/getProvinceIdByCountyName
     */
    public static final String GET_CITY_LIST = URL_ROOT + "mobile/view/area/findAreaOrderByAZ";
    /**
     * 物流信息
     */
    public static final String GET_AREA_BY_LOCATION = URL_ROOT + "mobile/after/memberaddress/getProvinceIdByCountyName";
    /**
     * 分享成功之后的条数更新
     */
    public static final String SHARE_SUCCESS_UPDATE = URL_ROOT + "mobile/view/shareweb/updatesharenum";

    /**
     * 首页直播视频列表接口
     */
    public static final String HOME_LIVEVIDEO_LIST = URL_ROOT + "mobile/view/video/getVideoList";

    /**
     * 获取视频信息接口
     */
    public static final String VIDEO_GET_INFO = URL_ROOT + "mobile/view/video/getVideoById";

    /**
     * 视频点赞接口
     */
    public static final String VIDEO_GOODLIKE = URL_ROOT + "mobile/after/video/videoFabulous";

    /**
     * 视频详情页面评论列表评论接口
     */
    public static final String VIDEO_SEND_COMMENT = URL_ROOT + "mobile/after/videocomment/addVideoComment";

    /**
     * 视频评论列表
     */
    public static final String VIDEO_COMMENT_LIST = URL_ROOT + "mobile/view/video/getVideoCommentByVId";

    /**
     * 视频详情评论列表评论点赞接口
     */
    public static final String VIDEO_COMMENT_GOODLIKE = URL_ROOT + "mobile/after/videocommentfabulous/videoCommentFabulous";

    /**
     * 直播在线人数接口
     */
    public static final String LIVE_ONLINE_PEOPLE = URL_ROOT + "mobile/view/LivesController/selectLiveOnLine";

    /**
     * 加入直播间
     */
    public static final String LIVE_JOIN_ROOM = URL_ROOT + "mobile/view/LivesController/IntoTheStudio";

    /**
     * 获取直播地址
     */
    public static final String LIVE_ADDRESS = URL_ROOT + "mobile/after/LiveController/getLookUrlFlv";

    /**
     * 退出直播间
     */
    public static final String LIVE_OUT_ROOM = URL_ROOT + "mobile/view/LivesController/outTheStudio";

    /**
     * 直播在线人数列表接口
     */
    public static final String LIVE_ONLINENUM_LIST = URL_ROOT + "mobile/view/LivesController/getMemberList";
    /**
     * 品秀视频播放列表接口
     */
    public static final String VIDEO_PX_LIST = URL_ROOT + "mobile/view/show/getShowList";
    /**
     * 品秀视频浏览量统计
     */
    public static final String VIDEO_PX_ADDSHOW = URL_ROOT + "mobile/view/show/addShowViews";

    /**
     * 品秀评论列表接口
     */
    public static final String VIDEO_PX_COMMMENT_LIST = URL_ROOT + "mobile/view/show/getShowCommentList";
    /**
     * 品秀去评论接口
     */
    public static final String VIDEO_PX_COMMENT_LIST_TOCOMMENT = URL_ROOT + "mobile/after/show/addCommentShow";
    /**
     * 品秀视频点赞接口
     */
    public static final String VIDEO_PX_GOODLIEK = URL_ROOT + "mobile/after/show/showFabulous";
    /**
     * 上传视频接口
     */
    public static final String VIDEO_PX_UPLOAD = URL_ROOT + "mobile/view/attachment/uploadVideo";
    /**
     * 品秀视频信息添加发布接口
     */
    public static final String VIDEO_PX_ADD_PUBLISH = URL_ROOT + "mobile/after/show/addShow";
    /**
     * 增加品秀分享数接口
     */
    public static final String VIDEO_PX_SHARE_UPDATE_NUM = URL_ROOT + "mobile/after/show/addShowShare";

    /**
     * 商品详情页分享列表接口
     */
    public static final String PRODUCT_DETAIL_SHARE_LIST = URL_ROOT + "mobile/view/detail/getConsultationByProduct";

    /**
     * 商品详情购物车数量刷新接口
     */
    public static final String PRODUCT_DETAIL_CART_NUM = URL_ROOT + "mobile/after/shopcart/count";

    /**
     * 专员推荐
     */
    public static final String COMMISSIONER_RECOMMENDED = URL_ROOT + "mobile/view/hunter/getHomeHunter";

    /**
     * 搜索服务专员
     */
    public static final String SEARCH_COMMISSIONER_RECOMMENDED = URL_ROOT + "mobile/view/hunter/search";

    /**
     * 根据分类请求商品列表
     */
    public static final String COMMISSIONER_PRODUCT_LIST = URL_ROOT + "mobile/view/productshow/getProductList";

    /**
     * 专员商品列表
     */
    public static final String GET_COMMISSONER_GOODS_LIST = URL_ROOT + "mobile/view/hunter/findHunterCollectionProduct";

    /**
     * 专员分享列表
     */
    public static final String GET_COMMISSONER_SHARE_LIST = URL_ROOT + "mobile/view/hunter/findHunterConsultation";


    /**
     * 专员客户列表
     */
    public static final String GET_COMMISSONER_CUSTOMER_LIST = URL_ROOT + "mobile/view/hunter/findHunterMember";

    /**
     * 专员评价列表
     */
    public static final String GET_COMMISSONER_COMMENT_LIST = URL_ROOT + "mobile/view/comment/findHunterComment";

    /**
     * 专员主页列表条数
     */
    public static final String GET_COMMISSOR_LIST_NUM = URL_ROOT + "mobile/view/hunter/getHunterMainItemsCount";

    /**
     * 验证用户是否发布信息以及获取专员信息
     */
    public static final String GET_COMMISSOR_INFO = URL_ROOT + "mobile/view/memberm/getHunterInfo";

    /**
     * 绑定服务专员
     */
    public static final String BIND_ZHUAN_YUAN = URL_ROOT + "mobile/after/member/bindHunter";


    /**
     * 获取分享的列表
     */
    public static final String GET_SHARE_LIST = URL_ROOT + "mobile/after/consultation/getMyShareList";

    /**
     * 首次发布
     */
    public static final String ADD_PUBLISH = URL_ROOT + "mobile/after/consultation/addConsultation";

    /**
     * 非首次发布
     */
    public static final String UPDATE_PUBLISH = URL_ROOT + "mobile/after/consultation/updateConsultation";
    /**
     * 获取分享页面的数量
     */
    public static final String GET_SHARE_NUM = URL_ROOT + "mobile/after/consultation/getMyShareNum";

    /**
     * 商品品牌主页
     */
    public static final String GET_BRAND_LIST = URL_ROOT + "mobile/view/brand/getListByBrand";

    /**
     * 获取邀请注册的好友列表
     */
    public static final String GET_FRIEND_LIST = URL_ROOT + "mobile/after/member/inviteMemberItems";

}
