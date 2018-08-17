package com.yst.onecity.activity.member;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.UploadImageBean;
import com.yst.onecity.bean.information.AppNewsDetailBean;
import com.yst.onecity.bean.information.InformationContentBean;
import com.yst.onecity.bean.information.NewsTypeBean;
import com.yst.onecity.bean.information.PublishNewsJsonBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractNewsTypeCallback;
import com.yst.onecity.callbacks.AbstractUploadImageCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractCameraDialog;
import com.yst.onecity.dialog.AbstractSaveDialog;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.ImageCompress;
import com.yst.onecity.utils.ImagesUitls;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.pickerview.SinglePicker;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.config.Constants.ids;

/**
 * 发布资讯
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class PublishInformationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final int REQUEST_CLIP_IMAGE = 3;
    public static final int REQUEST_CODE_WRITE_CONTENT = 100;
    private String tempPath;
    @BindView(R.id.tv_no_image)
    RadioButton tvNoImage;
    @BindView(R.id.tv_one_image)
    RadioButton tvOneImage;
    @BindView(R.id.tv_three_image)
    RadioButton tvThreeImage;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.iv_image1)
    ImageView ivImage1;
    @BindView(R.id.iv_image2)
    ImageView ivImage2;
    @BindView(R.id.iv_image3)
    ImageView ivImage3;
    @BindView(R.id.ll_add_image)
    LinearLayout llAddImage;
    @BindView(R.id.rl_choose_news_type)
    RelativeLayout llChooseNewsType;
    @BindView(R.id.tv_new_type)
    TextView tvNewType;
    @BindView(R.id.et_news_title)
    ContainsEmojiEditText etNewsTitle;
    @BindView(R.id.tv_text_count)
    TextView tvTextCount;
    /**
     * 资讯内容
     */
    private List<AppNewsDetailBean> publishList = new ArrayList<>();
    /**
     * 封面内容
     */
    private List<AppNewsDetailBean> coverList = new ArrayList<>();
    /**
     * 封面类型 0：无图  1：单图  2：三图
     */
    private int coverType = 0;
    private AbstractSaveDialog dialog;
    private AbstractCameraDialog cameraDialog;
    /**
     * 1是第一个图。2第二个图，2第三个图
     */
    private int addImageFlag = 0;
    /**
     * 资讯分类名称和id
     */
    private String newsTypeName, newsId;
    /**
     * 查看、编辑
     */
    private String flag = "";
    /**
     * 资讯id
     */
    private String informationId = "";
    /**
     * 图片数量
     */
    private int imgNum = 0;
    /**
     * 单张封面实体类
     */
    private AppNewsDetailBean bean;
    /**
     * 第一张封面实体类
     */
    private AppNewsDetailBean bean1;
    /**
     * 第二张封面实体类
     */
    private AppNewsDetailBean bean2;
    /**
     * 第三张封面实体类
     */
    private AppNewsDetailBean bean3;

    @Override
    public int bindLayout() {
        return R.layout.activity_publish_information;
    }

    @Override
    public void initData() {
        setTitleBarTitle("发布资讯");
        setRightText("发布");
        initDialog();
        if (getIntent().getExtras() != null) {
            InformationContentBean informationBean = (InformationContentBean) getIntent().getExtras().getSerializable("bean");
            flag = getIntent().getStringExtra("flag");
            List<InformationContentBean.NewsInfoBean> info = informationBean.getInfo();
            if (info != null) {
                if (info.size() > 0) {
                    for (int i = 0; i < info.size(); i++) {
                        InformationContentBean.NewsInfoBean newsInfo = info.get(i);
                        if (!TextUtils.isEmpty(newsInfo.getCover()) && "1".equals(newsInfo.getCover())) {
                            AppNewsDetailBean detailBean = new AppNewsDetailBean(newsInfo.getSortNum(), newsInfo.getType(),
                                    newsInfo.getContent(), newsInfo.getImgId(), newsInfo.getCover(), newsInfo.getCoverSortNum(),
                                    newsInfo.getProductId(), Constants.URL_IMAGE_HEAD + newsInfo.getAddress(), null, null);
                            coverList.add(detailBean);
                            //商品
                        } else if (!TextUtils.isEmpty(newsInfo.getPimage())) {
                            AppNewsDetailBean detailBean = new AppNewsDetailBean(newsInfo.getSortNum(), newsInfo.getType(),
                                    newsInfo.getContent(), newsInfo.getImgId(), newsInfo.getCover(), newsInfo.getCoverSortNum(),
                                    newsInfo.getProductId(), newsInfo.getPimage(), newsInfo.getPsId(), newsInfo.getMerchantId());
                            publishList.add(detailBean);
                        } else {
                            AppNewsDetailBean detailBean = new AppNewsDetailBean(newsInfo.getSortNum(), newsInfo.getType(),
                                    newsInfo.getContent(), newsInfo.getImgId(), newsInfo.getCover(), newsInfo.getCoverSortNum(),
                                    newsInfo.getProductId(), Constants.URL_IMAGE_HEAD + newsInfo.getAddress(), null, null);
                            publishList.add(detailBean);
                        }
                    }
                    if (coverList.size() == 1) {
                        tvOneImage.setChecked(true);
                        llAddImage.setVisibility(View.VISIBLE);
                        ivImage1.setVisibility(View.VISIBLE);
                        ivImage2.setVisibility(View.GONE);
                        ivImage3.setVisibility(View.GONE);
                        Glide.with(this).load(coverList.get(0).getUrl()).into(ivImage1);
                        bean = coverList.get(0);
                    } else if (coverList.size() == Const.INTEGER_3) {
                        tvThreeImage.setChecked(true);
                        llAddImage.setVisibility(View.VISIBLE);
                        ivImage1.setVisibility(View.VISIBLE);
                        ivImage2.setVisibility(View.VISIBLE);
                        ivImage3.setVisibility(View.VISIBLE);
                        Glide.with(this).load(coverList.get(0).getUrl()).into(ivImage1);
                        Glide.with(this).load(coverList.get(1).getUrl()).into(ivImage2);
                        Glide.with(this).load(coverList.get(2).getUrl()).into(ivImage3);
                        bean1 = coverList.get(0);
                        bean2 = coverList.get(1);
                        bean3 = coverList.get(2);
                    }
                }
            }
            if (Const.CONS_STR_LOOK.equals(flag)) {
                radioGroup.setEnabled(false);
                llChooseNewsType.setEnabled(false);
                etNewsTitle.setEnabled(false);
                tvRight.setVisibility(View.GONE);
                tvNoImage.setEnabled(false);
                tvOneImage.setEnabled(false);
                tvThreeImage.setEnabled(false);
                ivImage1.setEnabled(false);
                ivImage2.setEnabled(false);
                ivImage3.setEnabled(false);
            } else if (Const.CONS_STR_EDIT.equals(flag)) {
                informationId = informationBean.getId();
                coverType = informationBean.getModelType();
                newsId = informationBean.getConsultationClassifyId();
                newsTypeName = informationBean.getDescriptionName();
                imgNum = coverList.size();
            }
            tvTextCount.setText(String.valueOf(informationBean.getTitle().length()) + "/20");
            etNewsTitle.setText(ConstUtils.getStringNoEmpty(informationBean.getTitle()));
            etNewsTitle.requestFocus();
            etNewsTitle.setSelection(informationBean.getTitle().length());
            tvNewType.setText(ConstUtils.getStringNoEmpty(informationBean.getDescriptionName()));
        }
    }

    /**
     * 初始化弹出框
     */
    private void initDialog() {
        cameraDialog = new AbstractCameraDialog(this) {
            @Override
            public void onCameraClick() {
                picByCamera(); // 相机
            }

            @Override
            public void onPhotoClick() {
                selectImg(); // 拍照
            }
        };
    }

    /**
     * 选择图片
     */
    private void selectImg() {
        if (EasyPermissions.hasPermissions(PublishInformationActivity.this, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MultiImageSelector.create().single().showCamera(false).start(PublishInformationActivity.this, REQUEST_IMAGE);
        } else {
            EasyPermissions.requestPermissions(PublishInformationActivity.this,
                    getString(R.string.hunter_center_open_camera_permission), 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 拍照
     */
    protected void picByCamera() {
        if (EasyPermissions.hasPermissions(PublishInformationActivity.this, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else {
            EasyPermissions.requestPermissions(PublishInformationActivity.this,
                    getString(R.string.hunter_center_open_camera_permission), 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        tempPath = System.currentTimeMillis() + ".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), tempPath)));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void setListener() {
        radioGroup.setOnCheckedChangeListener(this);
        etNewsTitle.addTextChangedListener(this);
    }

    @OnClick({R.id.ll_back, R.id.iv_image1, R.id.iv_image2, R.id.iv_image3, R.id.ll_add_image,
            R.id.rl_choose_news_type, R.id.rl_add_content, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                checkSave();
                break;
            //发布
            case R.id.tv_right:
                List<AppNewsDetailBean> contentList2; //正文
                contentList2 = publishList;
                if (TextUtils.isEmpty(tvNewType.getText().toString())
                        || TextUtils.isEmpty(etNewsTitle.getText().toString().trim())
                        || contentList2.size() == 0) {
                    ToastUtils.show("请完善发布内容");
                    return;
                }
                if (ConstUtils.isClickable()) {
                    requestPublishNewsNetWork("1");
                }
                break;
            case R.id.iv_image1:
                addImageFlag = 1;
                cameraDialog.showDialog();
                break;
            case R.id.iv_image2:
                addImageFlag = 2;
                cameraDialog.showDialog();
                break;
            case R.id.iv_image3:
                addImageFlag = 3;
                cameraDialog.showDialog();
                break;
            case R.id.ll_add_image:
                break;
            //选择资讯类型
            case R.id.rl_choose_news_type:
                if (ConstUtils.isClickable()) {
                    requestNewsTypeNetWork();
                }
                break;
            //添加正文
            case R.id.rl_add_content:
                if (Const.CONS_STR_LOOK.equals(flag)) {
                    AddNewsContentActivity.navPublishNewsActivity(this, publishList, flag);
                } else {
                    AddNewsContentActivity.navPublishNewsActivity(this, publishList, "", REQUEST_CODE_WRITE_CONTENT);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取资讯分类
     */
    private void requestNewsTypeNetWork() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", "",
                "status", "0",
                "timestamp", timestamp);
        //请求参数：当前页数(非必传)、0代表不需要分页
        OkHttpUtils.post().url(Constants.NEWS_TYPE)
                .addParams("page", "")
                .addParams("status", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractNewsTypeCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onResponse(NewsTypeBean response, int id) {
                if (response.getCode() == 1) {
                    final List<NewsTypeBean.NewsTypeInfo> typeInfos = response.getContent();
                    final List<String> typeData = new ArrayList<>();
                    for (NewsTypeBean.NewsTypeInfo bean : typeInfos) {
                        typeData.add(bean.getDescription_name());
                    }
                    if (typeData.size() == 0) {
                        ToastUtils.show("暂无资讯分类");
                        return;
                    }
                    SinglePicker picker = new SinglePicker(PublishInformationActivity.this, typeData, new SinglePicker.SinglePickerSelector() {
                        @Override
                        public void onSelectItem(int itemIndex) {
                            newsTypeName = typeInfos.get(itemIndex).getDescription_name();
                            tvNewType.setText(ConstUtils.getStringNoEmpty(newsTypeName));
                            newsId = typeInfos.get(itemIndex).getId();
                        }
                    });
                    picker.setTitleText("请选择资讯分类");
                    picker.show();
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 发布资讯
     */
    private void requestPublishNewsNetWork(final String action) {
        String title = etNewsTitle.getText().toString().trim();
        if (coverType == 1 && bean != null) {
            imgNum = 1;
            publishList.add(bean);
        } else if (coverType == 1) {
            ToastUtils.show("请选择单图封面");
            return;
        }
        if (coverType == Const.INTEGER_2 && bean1 != null && bean2 != null && bean3 != null) {
            imgNum = 3;
            publishList.add(bean1);
            publishList.add(bean2);
            publishList.add(bean3);
        } else if (coverType == Const.INTEGER_2) {
            ToastUtils.show("请选择三图封面");
            return;
        }
        String requestUrl = Constants.PUBLISH_NEWS;
        String json = "";
        if (Const.CONS_STR_EDIT.equals(flag)) {
            requestUrl = Constants.EDIT_NEWS;
            //如果是编辑参数加个资讯id
            PublishNewsJsonBean bean = new PublishNewsJsonBean(informationId, Constants.ISSERVER ? TianyiApplication.appCommonBean.getHunter_id() : TianyiApplication.appCommonBean.getId(),
                    Constants.ISSERVER ? "1" : "0",
                    action, "0", coverType, title, imgNum, newsId, newsTypeName, publishList);
            json = new Gson().toJson(bean);
        } else {
            PublishNewsJsonBean bean = new PublishNewsJsonBean(Constants.ISSERVER ? TianyiApplication.appCommonBean.getHunter_id() : TianyiApplication.appCommonBean.getId(),
                    Constants.ISSERVER ? "1" : "0",
                    action, "0", coverType, title, imgNum, newsId, newsTypeName, publishList);
            json = new Gson().toJson(bean);
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "json", json,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        //用户id、用户类型(0普通会员、1推广师、2门店)、0保存草稿 1发布、暂时固定0、0无图 1单图 2三图、咨询标题、图片数量、分类id、分类名称
        OkHttpUtils.post().url(requestUrl)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("json", json)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
                if (coverType == 1 && bean != null) {
                    publishList.remove(bean);
                }
                if (coverType == Const.INTEGER_2 && bean1 != null && bean2 != null && bean3 != null) {
                    publishList.remove(bean1);
                    publishList.remove(bean2);
                    publishList.remove(bean3);
                }
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 0);
                JumpIntent.jump(PublishInformationActivity.this, PublishStateActivity.class, bundle);
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    ids.clear();
                    if (Const.STR1.equals(action)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", 1);
                        JumpIntent.jump(PublishInformationActivity.this, PublishStateActivity.class, bundle);
                    } else {
                        ToastUtils.show(response.getMsg());
                    }
                    PublishInformationActivity.this.finish();
                } else {
                    ToastUtils.show(response.getMsg());
                    if (coverType == 1 && bean != null) {
                        publishList.remove(bean);
                    }
                    if (coverType == Const.INTEGER_2 && bean1 != null && bean2 != null && bean3 != null) {
                        publishList.remove(bean1);
                        publishList.remove(bean2);
                        publishList.remove(bean3);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("flag", 0);
                    JumpIntent.jump(PublishInformationActivity.this, PublishStateActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.tv_no_image:
                coverList.clear();
                // 无图
                coverType = 0;
                llAddImage.setVisibility(View.GONE);
                break;
            case R.id.tv_one_image:
                // 单图
                coverType = 1;
                llAddImage.setVisibility(View.VISIBLE);
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.GONE);
                ivImage3.setVisibility(View.GONE);
                bean = null;
                ivImage1.setImageResource(R.mipmap.icon_add_picture);
                break;
            case R.id.tv_three_image:
                // 三图
                coverType = 2;
                llAddImage.setVisibility(View.VISIBLE);
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.VISIBLE);
                ivImage3.setVisibility(View.VISIBLE);
                bean1 = null;
                bean2 = null;
                bean3 = null;
                ivImage1.setImageResource(R.mipmap.icon_add_picture);
                ivImage2.setImageResource(R.mipmap.icon_add_picture);
                ivImage3.setImageResource(R.mipmap.icon_add_picture);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //资讯内容编辑回传
            case REQUEST_CODE_WRITE_CONTENT:
                publishList = (ArrayList) data.getSerializableExtra("publishList");
                for (int i = 0; i < publishList.size(); i++) {
                    publishList.get(i).setSortNum(i + 1);
                }
                break;
            case REQUEST_IMAGE:
                ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String url = urlList.get(0);
                if (coverType == 1) {
                    //一图
                    ClipImageActivity.navClipImageActivity(this, url, 16, 9, REQUEST_CLIP_IMAGE);
                } else if (coverType == Const.INTEGER_2) {
                    //三图
                    //直接压缩图片
                    String filePath = PhotoUtil.compressBitmap(url, ConstUtils.getScreenWidth(this), ConstUtils.getScreenWidth(this) * 3 / 2);
                    processImage(filePath);
                }
                break;
            case REQUEST_CAMERA:
                Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tempPath));
                File fileClip = ImagesUitls.getFileFromMediaUri(getApplicationContext(), photoUri);
                String tempPath = ImageCompress.compressImage(fileClip.getPath());
                if (coverType == 1) {
                    //一图
                    ClipImageActivity.navClipImageActivity(this, tempPath, 16, 9, REQUEST_CLIP_IMAGE);
                } else if (coverType == Const.INTEGER_2) {
                    //三图
                    processImage(tempPath);
                }
                break;
            case REQUEST_CLIP_IMAGE:
                String imagePath = data.getStringExtra("savedPath");
                processImage(imagePath);
                break;
            default:
                break;
        }
    }

    /**
     * 图片上传
     *
     * @param imagePath
     */
    private void processImage(final String imagePath) {
        File scalFile = BitmapUtil.scal(imagePath);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "type", "0",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.UPLOAD_IMAGE)
                .addFile("urlfile", scalFile.getName(), scalFile)
                //0是图片
                .addParams("type", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractUploadImageCallBack() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(UploadImageBean response, int id) {
                if (response.getCode() == 1) {
                    if (coverType == 1 && addImageFlag == 1) {
                        Glide.with(context).load(ConstUtils.matchingImageUrl(response.getAddress())).into(ivImage1);
                        bean = new AppNewsDetailBean(addImageFlag, 1, "", response.getId(), "1", addImageFlag, "", null, null, null);
                    }
                    //三图,第一个图
                    if (coverType == Const.INTEGER_2 && addImageFlag == 1) {
                        Glide.with(context).load(ConstUtils.matchingImageUrl(response.getAddress())).into(ivImage1);
                        bean1 = new AppNewsDetailBean(addImageFlag, 1, "", response.getId(), "1", addImageFlag, "", null, null, null);
                        //三图,第二个图
                    } else if (coverType == Const.INTEGER_2 && addImageFlag == Const.INTEGER_2) {
                        Glide.with(context).load(ConstUtils.matchingImageUrl(response.getAddress())).into(ivImage2);
                        bean2 = new AppNewsDetailBean(addImageFlag, 1, "", response.getId(), "1", addImageFlag, "", null, null, null);
                        //三图,第三个图
                    } else if (coverType == Const.INTEGER_2 && addImageFlag == Const.INTEGER_3) {
                        Glide.with(context).load(ConstUtils.matchingImageUrl(response.getAddress())).into(ivImage3);
                        bean3 = new AppNewsDetailBean(addImageFlag, 1, "", response.getId(), "1", addImageFlag, "", null, null, null);
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            checkSave();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断是保存或放弃
     */
    private void checkSave() {
        if (Const.CONS_STR_LOOK.equals(flag)) {
            PublishInformationActivity.this.finish();
            return;
        }
        List<AppNewsDetailBean> contentList; //正文
        contentList = publishList;
        for (int i = 0; i < contentList.size(); i++) {
            if (!TextUtils.isEmpty(contentList.get(i).getCover())) {
                if ("1".equals(contentList.get(i).getCover())) {
                    contentList.remove(i);
                }
            }
        }

        boolean flag = TextUtils.isEmpty(tvNewType.getText().toString())
                || TextUtils.isEmpty(etNewsTitle.getText().toString().trim())
                || contentList.size() == 0
                || (tvOneImage.isChecked() && bean == null)
                || (tvThreeImage.isChecked() && (bean1 == null || bean2 == null || bean3 == null));

        if (flag) {
            initSaveDialog(0);
            dialog.setText("是否放弃本次编辑?");
        } else {
            initSaveDialog(1);
            dialog.setText("保存到草稿箱？");
        }
        dialog.showDialog();
    }

    /**
     * 返回彈出框
     *
     * @param flag 0提示放弃 1保存
     */
    private void initSaveDialog(final int flag) {
        dialog = new AbstractSaveDialog(this) {
            @Override
            public void onSureClick() {
                if (flag == 0) {
                    PublishInformationActivity.this.finish();
                    ids.clear();
                } else {
                    requestPublishNewsNetWork("0");
                }
            }

            @Override
            public void onCancleClick() {
                if (flag == 1) {
                    PublishInformationActivity.this.finish();
                }
            }
        };

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int max = 20;
        if (s.length() > max) {
            s.delete(max, s.length());
        }
        tvTextCount.setText(String.valueOf(s.length()) + "/20");
    }
}
