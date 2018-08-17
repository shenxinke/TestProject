package com.yst.onecity.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.InvitateRegisterActivity;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.MyCollectionActivity;
import com.yst.onecity.activity.MyFocusActivity;
import com.yst.onecity.activity.bankcard.UserBankCardActivity;
import com.yst.onecity.activity.commissioner.CommissionerErCodeActivity;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.activity.commissioner.MyShareActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.member.AboutUsActivity;
import com.yst.onecity.activity.member.AddressManageActivity;
import com.yst.onecity.activity.member.AuthAccountSafeActivity;
import com.yst.onecity.activity.member.ContactCustomerServiceActivity;
import com.yst.onecity.activity.member.MyMemberAccountActivity;
import com.yst.onecity.activity.member.MyMessageActivity;
import com.yst.onecity.activity.member.MyOrderActivity;
import com.yst.onecity.activity.member.MyPublishActivity;
import com.yst.onecity.activity.member.PaymentCodeActivity;
import com.yst.onecity.activity.member.RealNameAuthenticationActivity;
import com.yst.onecity.activity.member.UserEditNickNameActivity;
import com.yst.onecity.activity.servermember.NameAuthenticationInfoActivity;
import com.yst.onecity.activity.servermember.ServerMemberActivity;
import com.yst.onecity.activity.tickets.MyTicketsActivity;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MemberInfoBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractCameraDialog;
import com.yst.onecity.dialog.AbstractLogoutDialog;
import com.yst.onecity.dialog.AbstractVersionCheckDialog;
import com.yst.onecity.dialog.TipsDialog;
import com.yst.onecity.service.DownloadService;
import com.yst.onecity.utils.AppCommonManager;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.FileUtil;
import com.yst.onecity.utils.ImagesUitls;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PermissionUtil;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.UiUtil;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * 我的
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class MyFragment extends BaseFragment {

    public static final int REQUEST_CAMERA = 10001;
    public static final int REQUEST_IMAGE = 10002;
    public static final int PHOTO_REQUEST_CUT = 10003;
    @BindView(R.id.ll_QR)
    LinearLayout llQR;
    @BindView(R.id.tv_my_orders)
    TextView tvMyOrders;
    @BindView(R.id.tv_my_account)
    TextView tvMyAccount;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.rl_my_payment)
    RelativeLayout rlMyPayment;
    @BindView(R.id.iv_register)
    ImageView ivRegister;
    @BindView(R.id.rl_invitate_register)
    RelativeLayout rlInvitateRegister;
    @BindView(R.id.rl_my_publish)
    RelativeLayout rlMyPublish;
    @BindView(R.id.rl_my_attention)
    RelativeLayout rlMyAttention;
    @BindView(R.id.rl_my_tickets)
    RelativeLayout rlMyTickets;
    @BindView(R.id.rl_my_collection)
    RelativeLayout rlMyCollection;
    @BindView(R.id.rl_my_bank_card)
    RelativeLayout rlMyBankCard;
    @BindView(R.id.rl_publish)
    RelativeLayout rlPublish;
    @BindView(R.id.rl_attache)
    RelativeLayout rlAttache;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.rl_account_safe)
    RelativeLayout rlAccountSafe;
    @BindView(R.id.rl_manager_address)
    RelativeLayout rlManagerAddress;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.rl_version_update)
    RelativeLayout rlVersionUpdate;
    @BindView(R.id.rl_contact_customer_service)
    RelativeLayout rlContactCustomerService;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tv_logout)
    LinearLayout tvLogout;
    Unbinder unbinder;
    private Map<String, String> apkInfo = new HashMap<>();
    @BindView(R.id.tv_go_promotion_center)
    TextView tvGoPromotionCenter;
    @BindView(R.id.iv_headView)
    RoundedImageView ivHeadView;
    @BindView(R.id.tv_identification_status)
    TextView tvIdentificationStatus;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.my_news)
    RelativeLayout relUserMessages;
    @BindView(R.id.messageTagTV)
    TextView messageTagTV;
    /**
     * 选择图片的对话框
     */
    private AbstractCameraDialog cameraDialog;
    private String tempPath;
    private String cipPath;
    /**
     * 0，未认证；非0为已认证
     */
    private String checkHunter;
    /**
     * 0是未实名，非0会返回身份证，即已认证
     */
    private String checkMemCard;
    private String nickname;
    /**
     * 进度数
     */
    public static int loadingProgress;
    private File file;
    /**
     * 版本更新显示界面
     */
    private TipsDialog tipsDialog;
    /**
     * 更新时用到的进度条等
     */
    private ProgressBar progressBar;
    /**
     * 显示进度的view
     */
    private TextView progressText;
    /**
     * 退出 提示框
     */
    private AbstractLogoutDialog logoutDialog;
    private String hid = "";
    private String servicearea;

    @Override
    public int bindLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {
        //在当前界面注册一个订阅者
        EventBus.getDefault().register(this);
        cameraDialog = new AbstractCameraDialog(getActivity()) {
            @Override
            public void onCameraClick() {
                picByCamera();
            }

            @Override
            public void onPhotoClick() {
                selectImg();
            }
        };
        getUnReadMessageNum();
        logoutDialog = new AbstractLogoutDialog(getActivity()) {
            @Override
            public void onSureClick() {
                TianyiApplication.mAppCommonManager.quitLogin();
                TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.PASSWORDLOGINTYPE);
                TIMManager.getInstance().logout();
                Intent intent = new Intent(MainActivity.ChangePage);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

            @Override
            public void onCancelClick() {
            }
        };
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (Const.CONS_STR_MYFRAGMENT.equals(event.getMsg())) {
            if (TianyiApplication.isLogin) {
                getMemberCenterInfo();
            } else {
                setUnLoginMemberInfo();
            }
        }
    }

    @Override
    public void onResume() {
        PermissionUtil.getInstance().setmPermissionGrantListener(new PermissionUtil.OnPermissionGrantListener() {
            @Override
            public void grantPermission(boolean isGrant, String permission, int requestCode) {
                if (isGrant == false) {
                    return; //23以下直接回调
                }
                UiUtil.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        uploadApkFile();
                    }
                });
            }
        });
        super.onResume();
        if (TianyiApplication.isLogin) {
            getMemberCenterInfo();
        } else {
            setUnLoginMemberInfo();
        }
    }

    /**
     * 订阅事件FirstEvent
     */
    @Subscribe
    public void onEventMainThread(Uri uri) {
        startPhotoZoom(uri);
    }


    /**
     * 获取未读消息数量
     */
    private void getUnReadMessageNum() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "type", "0"
        );

        OkHttpUtils.post().url(Constants.GET_UNREADMESSAGE)
                .addParams("type", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        if (jsonObject.getInt(Const.CONS_STR_CONTENT) > 0) {
                            messageTagTV.setVisibility(View.VISIBLE);
                            messageTagTV.setText(String.valueOf(jsonObject.getInt("content")));
                        } else {
                            messageTagTV.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.rl_invitate_register, R.id.rl_my_payment, R.id.tv_go_promotion_center, R.id.tv_my_orders, R.id.tv_my_account,
            R.id.rl_my_publish, R.id.rl_my_attention, R.id.rl_my_collection, R.id.rl_my_tickets,
            R.id.rl_my_bank_card, R.id.rl_account_safe, R.id.rl_manager_address,
            R.id.my_news, R.id.rl_about_us, R.id.rl_version_update,
            R.id.rl_contact_customer_service, R.id.tv_logout,
            R.id.tv_identification_status, R.id.iv_headView, R.id.tv_name, R.id.ll_QR, R.id.rl_publish, R.id.rl_attache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //实名认证
            case R.id.tv_identification_status:
                getAuthencitationInfo();
                break;
            //邀请注册
            case R.id.rl_invitate_register:
                JumpIntent.jump(getActivity(), InvitateRegisterActivity.class);
                break;
            case R.id.iv_headView:
                if (!EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    EasyPermissions.requestPermissions(getActivity(), getString(R.string.hunter_center_open_camera_permission), 300,
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                selectImg();
                break;
            case R.id.tv_name:
                Bundle bundle1 = new Bundle();
                if (nickname == null) {
                    bundle1.putString("nickName", "第一次登陆昵称为空");
                } else {
                    bundle1.putString("nickName", nickname);
                }
                JumpIntent.jump(getActivity(), UserEditNickNameActivity.class, bundle1);
                break;
            case R.id.tv_my_orders:
                Constants.ORDER_ISMEMBER = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 0);
                JumpIntent.jump(getActivity(), MyOrderActivity.class, bundle);
                break;
            case R.id.rl_my_attention:
                JumpIntent.jump(getActivity(), MyFocusActivity.class);
                break;
            case R.id.rl_my_collection:
                JumpIntent.jump(getActivity(), MyCollectionActivity.class);
                break;
            case R.id.rl_my_tickets:
                JumpIntent.jump(getActivity(), MyTicketsActivity.class);
                break;
            case R.id.rl_my_bank_card:
                Bundle bankCardBundle = new Bundle();
                bankCardBundle.putInt("type", 0);
                JumpIntent.jump(getActivity(), UserBankCardActivity.class, bankCardBundle);
                break;
            case R.id.my_news:
                JumpIntent.jump(getActivity(), MyMessageActivity.class);
                break;
            case R.id.rl_about_us:
                JumpIntent.jump(getActivity(), AboutUsActivity.class);
                break;
            case R.id.rl_version_update:
                inspectversion();//版本检测
                break;
            case R.id.rl_contact_customer_service:
                JumpIntent.jump(getActivity(), ContactCustomerServiceActivity.class);
                break;
            //退出登录
            case R.id.tv_logout:
                logoutDialog.setText("提示", "您确定要退出登录么?");
                logoutDialog.showDialog();
                break;
            //账号安全
            case R.id.rl_account_safe:
                JumpIntent.jump(getActivity(), AuthAccountSafeActivity.class);
                break;
            //管理收获地址
            case R.id.rl_manager_address:
                JumpIntent.jump(getActivity(), AddressManageActivity.class);
                break;
            //付款码
            case R.id.rl_my_payment:
                JumpIntent.jump(getActivity(), PaymentCodeActivity.class);
                break;
            case R.id.rl_my_publish:
                Constants.ISSERVER = false;
                JumpIntent.jump(getActivity(), MyPublishActivity.class);
                break;
            //我的账户
            case R.id.tv_my_account:
                JumpIntent.jump(getActivity(), MyMemberAccountActivity.class);
                break;
            //进入推广中心
            case R.id.tv_go_promotion_center:
                int userType = TianyiApplication.appCommonBean.getUserType();
                if (!TextUtils.isEmpty(checkHunter) && Const.STR0.equals(checkHunter)) {
                    ToastUtils.show("您暂时还不是服务专员");
                } else {
                    JumpIntent.jump(getActivity(), ServerMemberActivity.class);
                }
                break;
            //二维码
            case R.id.ll_QR:
                Bundle codeBundle = new Bundle();
                codeBundle.putString("hid", hid);
                codeBundle.putString("serviceArea", servicearea);
                JumpIntent.jump(getActivity(), CommissionerErCodeActivity.class, codeBundle);
                break;
            //发布
            case R.id.rl_publish:
                JumpIntent.jump(getActivity(), MyShareActivity.class);
                break;
            //专员主页
            case R.id.rl_attache:
                if (TianyiApplication.isLogin) {
                    Bundle loginBundle = new Bundle();
                    loginBundle.putString(Const.CONS_STR_FROM, Const.STR_FROM_PERSON);
                    loginBundle.putString(Const.CONS_HUNTERID, hid);
                    JumpIntent.jump(getActivity(), CommissionerHomePageActivity.class, loginBundle);
                }
                break;
            default:
        }
    }

    /**
     * 版本检测
     */
    private void inspectversion() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "versionNo", ConstUtils.getVerCode(getActivity()),
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.CHECK_VERSION)
                .addParams("versionNo", ConstUtils.getVerCode(getActivity()))
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null && jsonObject.has(Const.CONS_STR_CODE)) {
                                int code = jsonObject.getInt(Const.CONS_STR_CODE);
                                switch (code) {
                                    /**
                                     * 不需要更新
                                     */
                                    case Const.INTEGER_1:
                                        ToastUtils.show(jsonObject.getString("msg"));
                                        break;
                                    /**
                                     * 需要更新
                                     */
                                    case Const.INTEGER_2:
                                        JSONObject content = jsonObject.getJSONObject(Const.CONS_STR_CONTENT);
                                        if (content != null && content.has(Const.APK_FORCED_UPDATE) && content.has(Const.APK_LOAD_ADDRESS)) {
                                            int forcedUpdate = content.getInt(Const.APK_FORCED_UPDATE);
                                            Constants.lujing = content.getString(Const.APK_LOAD_ADDRESS);
                                            if (Const.INTEGER_1 == forcedUpdate) {
                                                file = FileUtil.updatDelteApkFile();
                                                remindVersionUpdateDialog(true);
                                            } else {
                                                remindVersionUpdateDialog(false);
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 版本更新进度框
     *
     * @param grivate
     * @param width
     * @param layout
     * @param txtId
     * @param anim
     */
    private void setDialog(int grivate, int width, int layout, int txtId, int anim) {
        tipsDialog = TipsDialog.creatTipsDialog(getActivity(), width, layout, grivate, anim);
        progressBar = (ProgressBar) tipsDialog.findViewById(R.id.down_pb);
        progressText = (TextView) tipsDialog.findViewById(txtId);
        tipsDialog.setCancelable(false);
        Button btnCancle = (Button) tipsDialog.findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DownloadService.class);
                // 由intent启动service，后台运行下载进程，在服务中调用notifycation状态栏显示进度条
                getActivity().startService(intent);
                tipsDialog.dismiss();
            }
        });
        tipsDialog.show();
        tipsDialog.setCanceledOnTouchOutside(false);
        PermissionUtil.requestPermissionForActivity(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
    }

    /**
     * 下载APK文件
     */
    private void uploadApkFile() {
        OkHttpUtils.get()
                .url(Constants.lujing)
                .tag(getActivity())
                .build()
                .execute(new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), Constants.APP_NAME) {
                    @Override
                    public void inProgress(float v, long l, int id) {
                        loadingProgress = (int) (v * 100);
                        progressBar.setProgress(loadingProgress);
                        progressText.setText("已为您加载了:" + loadingProgress + "%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("User", "e =" + e.getMessage());
                        ToastUtils.show("下载失败");
                        tipsDialog.dismiss();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        tipsDialog.dismiss();
                        installNewApk();
                    }
                });
    }

    /**
     * 版本更新弹出框
     *
     * @param flag
     */
    private void remindVersionUpdateDialog(boolean flag) {
        AbstractVersionCheckDialog dialog = new AbstractVersionCheckDialog(getActivity(), flag) {
            @Override
            public void onSureClick() {
                setDialog(Gravity.CENTER, (WindowUtils.getScreenWidth(getActivity()) / 6) * 5, R.layout.dialog_tips_mid, R.id.tvId, 0);
            }
        };
        dialog.setCancleStyle(0, R.color.black, "取消");
        dialog.setSureStyle(0, R.color.black, "确认");
        dialog.showDialog();
    }

    /**
     * 跳转安装
     */
    private void installNewApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Const.INTEGER_24) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.yst.onecity.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }

    /**
     * 获取用户的信息接口
     */
    private void getMemberCenterInfo() {
        MyLog.e("MyFragment", "获取用户的信息接口___" + TianyiApplication.appCommonBean.getPhone() + TianyiApplication.appCommonBean.getUuid());
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.MEMBER_INFO)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getActivity().getResources().getString(R.string.app_on_request_error_msg));
                MyLog.e("MyFragment", "onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("MyFragment", "onResponse___" + response);
                Gson gson = new Gson();
                MemberInfoBean memberInfoBean = gson.fromJson(response, MemberInfoBean.class);
                if (memberInfoBean != null && memberInfoBean.getCode() == 1 && memberInfoBean.getContent() != null) {
                    MemberInfoBean.ContentBean content = memberInfoBean.getContent();
                    hid = content.getHid();
                    servicearea = content.getServicearea();
                    setMemberInfo(content);

                } else {
                    ToastUtils.show(memberInfoBean.getMsg());
                }
            }
        });
    }

    /**
     * 设置个人信息
     *
     * @param memberInfoBean
     */
    private void setMemberInfo(MemberInfoBean.ContentBean memberInfoBean) {

        if (memberInfoBean.getAddress() != null) {
            Glide.with(getActivity()).load(ConstUtils.matchingImageUrl(memberInfoBean.getAddress())).into(ivHeadView);
            TianyiApplication.appCommonBean.setAddress(memberInfoBean.getAddress());
        } else {
            Glide.with(getActivity()).load(R.mipmap.head_2).into(ivHeadView);
        }

        if (memberInfoBean.getNickname() != null) {
            tvName.setText(memberInfoBean.getNickname());
            TianyiApplication.appCommonBean.setNickname(memberInfoBean.getNickname());
        } else {
            tvName.setText("昵称");
        }
        ConstUtils.setTextString(TianyiApplication.appCommonBean.getPhone(), tvPhone);
        checkHunter = memberInfoBean.getCheckhunter();
        checkMemCard = memberInfoBean.getCheckMemCard();
        //未认证
        if (Const.STR0.equals(checkMemCard)) {
            tvIdentificationStatus.setText("未认证");
            tvIdentificationStatus.setBackgroundResource(R.mipmap.weirenzheng);
        } else {//已经认证
            tvIdentificationStatus.setText("已认证");
            tvIdentificationStatus.setBackgroundResource(R.mipmap.yirenzheng);

        }
        this.checkMemCard = memberInfoBean.getCheckMemCard();
        nickname = memberInfoBean.getNickname();
    }

    /**
     * (未登录)设置个人信息
     */
    private void setUnLoginMemberInfo() {
        Glide.with(getActivity()).load(R.mipmap.head_2).into(ivHeadView);
        ConstUtils.setTextString("昵称", tvName);
        ConstUtils.setTextString("", tvPhone);
        tvIdentificationStatus.setText("未认证");
        tvIdentificationStatus.setBackgroundResource(R.mipmap.weirenzheng);
    }

    /**
     * 拍照
     */
    protected void picByCamera() {
        tempPath = System.currentTimeMillis() + ".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), tempPath)));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * 选择图片
     */
    private void selectImg() {
        MultiImageSelector.create().single().showCamera(true).start(getActivity(), REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_IMAGE:
                List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (list.size() > 0) {
                    startPhotoZoom(Uri.fromFile(new File(list.get(0))));
                }
                break;
            case REQUEST_CAMERA:
                Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tempPath));
                startPhotoZoom(photoUri);
                break;
            case PHOTO_REQUEST_CUT:
                Uri clipPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPath));
                File fileClip = ImagesUitls.getFileFromMediaUri(getActivity(), clipPhotoUri);
                upLoadImg(fileClip);
                break;
            default:
                break;
        }
    }

    /**
     * 对选择的图片进行裁剪后展示
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        cipPath = System.currentTimeMillis() + ".png";
        intent.putExtra("output", Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), cipPath)));
        //返回格式
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 会员上传头像
     *
     * @param mImageFile
     */
    private void upLoadImg(File mImageFile) {
        OkHttpUtils.post()
                .url(Constants.SERVER_MEMBER_UPLOADHEAD)
                .addFile("urlfile", mImageFile.getName(), mImageFile)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(getActivity().getResources().getString(R.string.app_on_request_error_msg));
                    }

                    @Override
                    public void onResponse(String s, int id) {
                        MyLog.e("sss", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            setUserHead(jsonObject.getString("address"), jsonObject.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 设置用户的头像
     *
     * @param address
     * @param id
     */
    private void setUserHead(final String address, String id) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "attachmentId", id,
                "timestamp", timestamp,
                "memberId", TianyiApplication.appCommonBean.getId());
        OkHttpUtils
                .post()
                .url(Constants.URL_SAVE_MEMBER_HEAD_SCULPTURE)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("attachmentId", id)
                .addParams("memberId", TianyiApplication.appCommonBean.getId())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getActivity().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response != null) {
                    if (response.getCode() == 1) {
                        Glide.with(TianyiApplication.getContext())
                                .load(ConstUtils.matchingImageUrl(address))
                                .dontAnimate()
                                .into(ivHeadView);

                        TIMFriendshipManager.getInstance().setFaceUrl(
                                ConstUtils.matchingImageUrl(address),
                                new TIMCallBack() {
                                    @Override
                                    public void onError(int code, String desc) {
                                        //错误码code和错误描述desc，可用于定位请求失败原因
                                        //错误码code列表请参见错误码表
                                        MyLog.e("TIM", "setFaceUrl failed: " + code + " desc" + desc);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        MyLog.e("TIM", "setFaceUrl succ");
                                    }
                                });

                    }

                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取个人认证信息
     */
    private void getAuthencitationInfo() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone()
        );
        OkHttpUtils.post().url(Constants.AUTHCATIONINFO)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissInfoProgressDialog();
            }
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String s, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JumpIntent.jump(getActivity(), NameAuthenticationInfoActivity.class);
                    } else {
                        JumpIntent.jump(getActivity(), RealNameAuthenticationActivity.class);
                    }
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
