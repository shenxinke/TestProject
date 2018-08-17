package com.yst.onecity.activity.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.GridViewImageAdapter;
import com.yst.onecity.adapter.common.AbstractCommonRcyAdapter;
import com.yst.onecity.adapter.common.CommonRcyHolder;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EditBean;
import com.yst.onecity.bean.ImageBean;
import com.yst.onecity.bean.MemberInfoBean;
import com.yst.onecity.bean.ShoppingMallBean;
import com.yst.onecity.bean.UploadImageBean;
import com.yst.onecity.bean.consult.ConsultDetailBean;
import com.yst.onecity.callbacks.AbstractConsultDetailCallback;
import com.yst.onecity.callbacks.AbstractUploadImageCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractIsSaveDialog;
import com.yst.onecity.dialog.AbstractPublishSuccessDialog;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.ImagesUitls;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.GlideRoundTransform;
import com.yst.onecity.view.NoTextEtWatcher;
import com.yst.onecity.view.editor.CommoditySortRichEditor;
import com.yst.onecity.view.editor.SEditorData;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 编辑分享页面
 *
 * @author jiaofan
 * @version 4.2.0
 * @date 2018/6/1
 */
public class EditShareActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener ,CommoditySortRichEditor.IEditTextChange{

    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_top1)
    ImageView mIvTop1;
    @BindView(R.id.txt_top1)
    TextView mTxtTop1;
    @BindView(R.id.iv_top2)
    ImageView mIvTop2;
    @BindView(R.id.txt_top2)
    TextView mTxtTop2;
    @BindView(R.id.iv_line_left)
    ImageView mIvLineLeft;
    @BindView(R.id.iv_line_right)
    ImageView mIvLineRight;
    @BindView(R.id.et_title)
    ContainsEmojiEditText mEtTitle;
    @BindView(R.id.rb_one)
    RadioButton mRbOne;
    @BindView(R.id.rb_three)
    RadioButton mRbThree;
    @BindView(R.id.iv_background)
    RoundedImageView mIvBackground;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.iv_add_pic)
    ImageView mIvAddPic;
    @BindView(R.id.rel_cover_close)
    RelativeLayout mRelCoverClose;
    @BindView(R.id.rel_one)
    RelativeLayout mRelOne;
    @BindView(R.id.gridView_publish)
    GridView mGridViewPublish;
    @BindView(R.id.txt_next)
    TextView mTxtNext;
    @BindView(R.id.txt_back)
    TextView mTxtBack;
    @BindView(R.id.et_content)
    CommoditySortRichEditor mEtContent;
    @BindView(R.id.txt_count)
    TextView mTxtCount;
    @BindView(R.id.txt_addProduct)
    TextView mTxtAddProduct;
    @BindView(R.id.recyclerView_product)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.txt_continue)
    TextView mTxtContinue;
    @BindView(R.id.ll_left)
    LinearLayout mLlLeft;
    @BindView(R.id.ll_addProduct)
    LinearLayout mLlAddProduct;
    @BindView(R.id.rel_right)
    RelativeLayout mRelRight;
    @BindView(R.id.rel_continue)
    RelativeLayout mRelContinue;
    @BindView(R.id.rg_edit)
    RadioGroup mRgEdit;

    private static final int ONE_IMAGE = 2;
    private static final int THREE_IMAGE = 4;
    private static final int TEXT_IMAGE = 5;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int ADD_PRODUCT = 6;
    private String cipPath;
    private int imageType = 1;
    private int imgType = 1;
    private List<ImageBean> imgList = new ArrayList<>();
    private ArrayList<String> noList = new ArrayList<>();
    private GridViewImageAdapter gridViewAdapter;
    private AbstractCommonRcyAdapter<ShoppingMallBean.ContentBean> mAdapter;
    private String shareId;
    private String fromFlag = "1";
    public static List<ShoppingMallBean.ContentBean> mAllData = new ArrayList<>();
    private String imgId = "-1";
    private String isRealName = "";
    private String mId = "-1";
    private boolean isClick = true;

    @Override
    public int bindLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_edit_share;
    }

    @Override
    public void initData() {
        setTitleBarTitle("编辑分享");
        setRightText("发布");
        mEtContent.setIEditTextChange(this);
        mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_636363));
        setGridView();
        mEtTitle.addTextChangedListener(new NoTextEtWatcher(mEtTitle, 20, this));
        mRgEdit.setOnCheckedChangeListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shareId = bundle.getString("id");
            fromFlag = bundle.getString("from");
            MyLog.e("bundle", "shareId==="+shareId);
            MyLog.e("bundle", "fromFlag==="+fromFlag);
            getDetail(shareId);
        }
        requestIsRealName();
    }

    /**
     * 是否已经实名认证过
     */
    private void requestIsRealName() {
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
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.app_on_request_error_msg));
                MyLog.e("response", "onError___" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("response", "onResponse___" + response);
                MemberInfoBean memberInfoBean = new Gson().fromJson(response, MemberInfoBean.class);
                if (memberInfoBean != null && memberInfoBean.getCode() == 1 && memberInfoBean.getContent() != null) {
                    isRealName = memberInfoBean.getContent().getCheckMemCard();
                    if (TextUtils.isEmpty(isRealName) || Const.STR0.equals(isRealName)) {
                        ToastUtils.show("请先实名认证再发布分享");
                    }
                } else {
                    ToastUtils.show(memberInfoBean.getMsg());
                }
            }
        });
    }

    /**
     * 草稿箱资讯回显
     * @param id 草稿箱资讯id
     */
    private void getDetail(String id) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "consultationId", id,
                "merberId", TianyiApplication.appCommonBean.getId() == null ? "" : TianyiApplication.appCommonBean.getId(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.CONSULT_DETAIL)
                .addParams("consultationId", id)
                .addParams("merberId", TianyiApplication.appCommonBean.getId() == null ? "" : TianyiApplication.appCommonBean.getId())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractConsultDetailCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ConsultDetailBean response, int id) {
                if (response.getCode() == Const.INTEGER_1) {
                    if (response.getContent() != null) {
                        ConsultDetailBean.ContentBean.ConsultationBean consultationBean = response.getContent().getConsultation();
                        setEditData(consultationBean);
                        dismissInfoProgressDialog();
                    }
                } else {
                    dismissInfoProgressDialog();
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 设置编辑回显的数据
     */
    private void setEditData(ConsultDetailBean.ContentBean.ConsultationBean consultationBean) {
        mEtTitle.setText(consultationBean.getTitle());
        List<ConsultDetailBean.ContentBean.ConsultationBean.InfoBean> infoBean = consultationBean.getInfo();
        //封面一图
        if (consultationBean.getModel_type() == 1) {
            imageType = 1;
            imgType = 1;
            mRbOne.setChecked(true);
            mRelOne.setVisibility(View.VISIBLE);
            mGridViewPublish.setVisibility(View.GONE);
            if (infoBean.size() > 0) {
                for (int i = 0; i < infoBean.size(); i++) {
                    if (infoBean.get(i).getCover() == 1) {
                        imgId = String.valueOf(infoBean.get(i).getImgId());
                        mIvAdd.setVisibility(View.GONE);
                        mRelCoverClose.setVisibility(View.VISIBLE);
                        Glide.with(this).load(ConstUtils.matchingImageUrl(infoBean.get(i).getAddress())).transform(new GlideRoundTransform(EditShareActivity.this, 4)).placeholder(R.mipmap.changfang).into(mIvBackground);
                    }
                }
            }
            //封面三图
        } else if (consultationBean.getModel_type() == Const.INTEGER_2) {
            imageType = 2;
            imgType = 2;
            mRbThree.setChecked(true);
            mRelOne.setVisibility(View.GONE);
            mGridViewPublish.setVisibility(View.VISIBLE);
            if (infoBean.size() > 0) {
                for (int i = 0; i < infoBean.size(); i++) {
                    if (infoBean.get(i).getType() == 1 && infoBean.get(i).getCover() == 1) {
                        imgList.add(new ImageBean(String.valueOf(infoBean.get(i).getImgId()), ConstUtils.matchingImageUrl(infoBean.get(i).getAddress())));
                    }
                }
            }
            gridViewAdapter.notifyDataSetChanged();
        }
        //图文编辑框
        if (infoBean.size() > 0) {
            for (int i = 0; i < infoBean.size(); i++) {
                if (infoBean.get(i).getType() == 1 && infoBean.get(i).getCover() == 0) {
                    mEtContent.addNetImage(ConstUtils.matchingImageUrl(infoBean.get(i).getAddress()), String.valueOf(infoBean.get(i).getImgId()), false, EditShareActivity.this, false);
                } else if (infoBean.get(i).getType() == 0) {
                    mEtContent.addText(infoBean.get(i).getContent());
                }
            }
        }
        //商品列表
        if (infoBean.size() > 0) {
            for (int i = 0; i < infoBean.size(); i++) {
                if (infoBean.get(i).getType() == 2) {
                    ShoppingMallBean.ContentBean bean = new ShoppingMallBean.ContentBean();
                    bean.setImageurl(infoBean.get(i).getPimage());
                    bean.setId(infoBean.get(i).getProductId());
                    bean.setName(infoBean.get(i).getPname());
                    mAllData.add(bean);
                    mRecyclerViewProduct.setVisibility(View.VISIBLE);
                    mRelContinue.setVisibility(View.VISIBLE);
                    mLlAddProduct.setVisibility(View.GONE);
                    initAdapter();
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_one:
                imageType = 1;
                imgType = 1;
                mRelOne.setVisibility(View.VISIBLE);
                mGridViewPublish.setVisibility(View.GONE);
                break;
            case R.id.rb_three:
                imageType = 2;
                imgType = 2;
                mRelOne.setVisibility(View.GONE);
                mGridViewPublish.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 三图模式初始化
     */
    private void setGridView() {
        gridViewAdapter = new GridViewImageAdapter(imgList, this, 3);
        mGridViewPublish.setAdapter(gridViewAdapter);
        mGridViewPublish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isClick) {
                    return;
                }
                if (position == imgList.size() && imgList.size() < Const.INTEGER_3) {
                    ConstUtils.selectThreeImg(EditShareActivity.this, 3, imgList.size(), THREE_IMAGE);
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.tv_right, R.id.iv_add_pic, R.id.iv_add, R.id.rel_cover_close, R.id.txt_next, R.id.txt_back, R.id.txt_addProduct, R.id.txt_continue})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //返回
            case R.id.ll_back:
                onBackPressed();
                break;
            //发布
            case R.id.tv_right:
                ConstUtils.setFilter(mTvRight, Const.INTEGER_2000);
                requestPublish(fromFlag, "1");
                break;
            //输入框内插入图片
            case R.id.iv_add_pic:
                imageType = 4;
                ConstUtils.selectOneImg(EditShareActivity.this, true, TEXT_IMAGE);
                break;
            //一图状态下添加图片
            case R.id.iv_add:
                ConstUtils.selectOneImg(EditShareActivity.this, true, ONE_IMAGE);
                break;
            //一图状态下删除封面
            case R.id.rel_cover_close:
                imgId = "-1";
                mIvBackground.setImageResource(R.mipmap.changfang);
                mRelCoverClose.setVisibility(View.GONE);
                mIvAdd.setVisibility(View.VISIBLE);
                break;
            //下一步
            case R.id.txt_next:
                checkViewStatus(1);
                break;
            //上一步
            case R.id.txt_back:
                checkViewStatus(2);
                break;
            //添加商品
            case R.id.txt_addProduct:
            //继续添加商品
            case R.id.txt_continue:
                JumpIntent.jump(this, AddProductActivity.class, ADD_PRODUCT);
                break;
            default:
                break;
        }
    }

    /**
     * 是否保存草稿箱弹框
     */
    private void showSaveDialog() {
        AbstractIsSaveDialog dialog = new AbstractIsSaveDialog(this) {
            @Override
            public void saveClick() {
                requestPublish(fromFlag, "0");
            }

            @Override
            public void noSaveClick() {
                mAllData.clear();
                finish();
            }
        };
        dialog.showDialog();
    }

    /**
     * 发布
     *
     * @param from 1代表从首页列表进入发布页面，0代表从草稿箱进入
     * @param action 1代表发布，0代表保存到草稿箱
     */
    private void requestPublish(String from, String action) {
        if (Const.STR1.equals(action)) {
            if (TextUtils.isEmpty(isRealName) || Const.STR0.equals(isRealName)) {
                ToastUtils.show("请先实名认证再发布分享");
                return;
            }
            if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
                ToastUtils.show("请填写标题");
                return;
            }
            if (imageType == Const.INTEGER_1) {
                if (TextUtils.isEmpty(imgId) || mId.equals(imgId)) {
                    ToastUtils.show("请添加封面图");
                    return;
                }
            }
            if (imageType == Const.INTEGER_2) {
                if (imgList.size() < Const.INTEGER_3) {
                    ToastUtils.show("请添加3张封面图");
                    return;
                }
            }
            if (mEtContent.isContentEmpty()) {
                ToastUtils.show("请填写正文");
                return;
            }
            if (mAllData.size() == Const.INTEGER_0) {
                ToastUtils.show("请添加商品");
                return;
            }

            if (mEtContent.getAllTextLength() > Const.INTEGER_2000) {
                ToastUtils.show("请不要超过2000字");
                return;
            }
        }

        String json = getJson(from, action);

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "json", json,
                "timestamp", timestamp);

        //1代表首次发布，0代表从草稿箱进入
        String postUrl;
        if (Const.STR1.equals(from)) {
            postUrl = Constants.ADD_PUBLISH;
        } else {
            postUrl = Constants.UPDATE_PUBLISH;
        }
        PostFormBuilder url = OkHttpUtils.post().url(postUrl);
        url.addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("json", json)
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
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("response", "publish_response" + s);
                try {
                    JSONObject obj=new JSONObject(s);
                    if (obj.getInt(Const.CONS_STR_CODE) == Const.INTEGER_1) {
                        if (Const.STR1.equals(action)) {
                            showSuccessDialog();
                        } else {
                            ToastUtils.show("保存成功");
                            finish();
                        }
                        mAllData.clear();
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发布内容转json
     * @param action 1代表发布，0代表保存到草稿箱
     * @return json串
     */
    private String getJson(String from, String action) {
        String json;
        EditBean editBean = new EditBean();
        List<EditBean.ContentBean> list = new ArrayList<>();
        EditBean.ContentBean contentBean;
        if (imgType == 1 && !mId.equals(imgId)) {
            contentBean = new EditBean.ContentBean();
            contentBean.setAttachmentId(imgId);
            contentBean.setCover("1");
            contentBean.setType("1");
            contentBean.setCoverSortNum("0");
            contentBean.setSortNum("0");
            list.add(contentBean);
        } else if (imgType == Const.INTEGER_2) {
            for (int i = 0; i < imgList.size(); i++) {
                contentBean = new EditBean.ContentBean();
                contentBean.setAttachmentId(imgList.get(i).getImageId());
                contentBean.setCover("1");
                contentBean.setType("1");
                contentBean.setCoverSortNum(String.valueOf(i));
                contentBean.setSortNum(String.valueOf(i));
                list.add(contentBean);
            }
        }

        List<SEditorData> setData = mEtContent.buildEditData();

        for (int j = 0; j < setData.size(); j++) {
            int type;
            contentBean = new EditBean.ContentBean();
            contentBean.setSortNum(String.valueOf(j));
            contentBean.setCoverSortNum(String.valueOf(j));
            if (!TextUtils.isEmpty(setData.get(j).getInputStr())){
                type = 0;
                contentBean.setDetail(setData.get(j).getInputStr());
            }else{
                type = 1;
                contentBean.setAttachmentId(setData.get(j).getImageId());
            }
            contentBean.setCover(String.valueOf(0));
            contentBean.setType(String.valueOf(type));
            list.add(contentBean);
        }

        for (int i = 0; i < mAllData.size(); i++) {
            contentBean = new EditBean.ContentBean();
            contentBean.setSortNum(String.valueOf(i));
            contentBean.setCoverSortNum(String.valueOf(i));
            contentBean.setType(String.valueOf(2));
            contentBean.setProductId(String.valueOf(mAllData.get(i).getId()));
            list.add(contentBean);
        }

        editBean.setContent(list);
        editBean.setAction(action);
        editBean.setModelType("-1".equals(imgId) && imgList.size() == 0 ? "0" : String.valueOf(imgType));
        editBean.setTitle(mEtTitle.getText().toString().trim());
        editBean.setImgNum("1".equals(String.valueOf(imgType)) ? "1" : "3");
        if (Const.STR0.equals(from)) {
            editBean.setId(shareId);
        }
        json = new Gson().toJson(editBean);
        MyLog.e("zzz", "json==="+json);
        return json;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //一图
            case ONE_IMAGE:
                ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (urlList.size() > 0) {
                    startPhotoZoom(Uri.fromFile(new File(urlList.get(0))));
                }
                break;
            //三图
            case THREE_IMAGE:
                ArrayList<String> threeList = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                isClick = false;
                for (int i = 0; i < threeList.size(); i++) {
                    File file = BitmapUtil.scal(threeList.get(i));
                    uploadImage(file, i, threeList);
                }
                break;
            //裁剪
            case PHOTO_REQUEST_CUT:
                Uri clipPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPath));
                File fileClip = ImagesUitls.getFileFromMediaUri(this, clipPhotoUri);
                uploadImage(fileClip, 0, noList);
                break;
            //输入框插入的图片
            case TEXT_IMAGE:
                ArrayList<String> imgList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (imgList.size() > 0) {
                    File file = BitmapUtil.scal(imgList.get(0));
                    uploadImage(file, 0, imgList);
                }
                break;
            //添加商品回显
            case ADD_PRODUCT:
                mAllData = (List<ShoppingMallBean.ContentBean>) data.getSerializableExtra("mAllData");
                if (mAllData.size() != Const.INTEGER_0) {
                    mRecyclerViewProduct.setVisibility(View.VISIBLE);
                    mRelContinue.setVisibility(View.VISIBLE);
                    mLlAddProduct.setVisibility(View.GONE);
                    initAdapter();
                } else {
                    mRecyclerViewProduct.setVisibility(View.GONE);
                    mRelContinue.setVisibility(View.GONE);
                    mLlAddProduct.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 底部横向商品预览列表
     */
    private void initAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewProduct.setLayoutManager(llm);
        mAdapter = new AbstractCommonRcyAdapter<ShoppingMallBean.ContentBean>(this, mAllData, R.layout.item_product_preview) {
            @Override
            public void convert(CommonRcyHolder holder, int position, ShoppingMallBean.ContentBean item) {
                holder.setImageView(EditShareActivity.this, R.id.item_iv_cover, item.getImageurl(), R.mipmap.img_default_p);
                RelativeLayout delete = holder.getView(R.id.item_rel_close);
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAllData.remove(position);
                        notifyDataSetChanged();
                        if (mAllData.size() == Const.INTEGER_0) {
                            mRecyclerViewProduct.setVisibility(View.GONE);
                            mRelContinue.setVisibility(View.GONE);
                            mLlAddProduct.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };
        mRecyclerViewProduct.setAdapter(mAdapter);
    }

    /**
     * 系统裁剪
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 100);
        cipPath = System.currentTimeMillis() + ".png";
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPath)));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 图片上传
     */
    private void uploadImage(File filePath, int i, ArrayList<String> list) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "type", "0",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.UPLOAD_IMAGE)
                .addFile("urlfile", filePath.getName(), filePath)
                //0是图片
                .addParams("type", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build()
                .execute(new AbstractUploadImageCallBack() {
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
                        if (response.getCode() == Const.INTEGER_1) {
                            if (imageType == Const.INTEGER_1) {
                                imgId = response.getId();
                                Glide.with(context).load(ConstUtils.matchingImageUrl(response.getAddress())).transform(new GlideRoundTransform(EditShareActivity.this, 4)).placeholder(R.mipmap.changfang).into(mIvBackground);
                                mIvAdd.setVisibility(View.GONE);
                                mRelCoverClose.setVisibility(View.VISIBLE);
                            } else if (imageType == Const.INTEGER_2) {
                                imgList.add(new ImageBean(response.getId(), ConstUtils.matchingImageUrl(response.getAddress())));
                                gridViewAdapter.notifyDataSetChanged();
                                if (i == list.size() - 1) {
                                    isClick = true;
                                }
                            } else if (imageType == Const.INTEGER_4) {
                                mEtContent.addNetImage(ConstUtils.matchingImageUrl(response.getAddress()), response.getId(), false, EditShareActivity.this, false);
                            }
                        } else {
                            ToastUtils.show("上传失败");
                        }
                    }
                });
    }

    /**
     * 发布成功弹框
     */
    private void showSuccessDialog() {
        AbstractPublishSuccessDialog dialog = new AbstractPublishSuccessDialog(this) {
            @Override
            public void sureClick() {
                finish();
            }
        };
        dialog.showDialog();
    }

    /**
     * 根据标识改变控件属性
     *
     * @param flag 标识
     */
    private void checkViewStatus(int flag) {
        if (flag == Const.INTEGER_1) {
            mLlLeft.setVisibility(View.GONE);
            mRelRight.setVisibility(View.VISIBLE);
            mIvTop1.setImageResource(R.mipmap.pic_normal);
            mTxtTop1.setTextColor(ContextCompat.getColor(this, R.color.color_989CA8));
            mIvTop2.setImageResource(R.mipmap.content_selected);
            mTxtTop2.setTextColor(ContextCompat.getColor(this, R.color.color_FCAD23));
            mIvLineLeft.setImageResource(R.mipmap.line_normal);
            mIvLineRight.setImageResource(R.mipmap.line_selected);
        } else if (flag == Const.INTEGER_2) {
            mLlLeft.setVisibility(View.VISIBLE);
            mRelRight.setVisibility(View.GONE);
            mIvTop1.setImageResource(R.mipmap.pic_selected);
            mTxtTop1.setTextColor(ContextCompat.getColor(this, R.color.color_FCAD23));
            mIvTop2.setImageResource(R.mipmap.content_normal);
            mTxtTop2.setTextColor(ContextCompat.getColor(this, R.color.color_989CA8));
            mIvLineLeft.setImageResource(R.mipmap.line_selected);
            mIvLineRight.setImageResource(R.mipmap.line_normal);
        }
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())
                && mEtContent.isContentEmpty()
                && mAllData.size() == 0
                && "-1".equals(imgId)
                && imgList.size() == 0
                ) {
            finish();
        } else {
            showSaveDialog();
        }
    }

    @Override
    public void changeNum(int allTextLength) {
        mTxtCount.setText(String.valueOf(allTextLength));
        if (allTextLength > Const.INTEGER_2000) {
            mTxtCount.setTextColor(ContextCompat.getColor(this, R.color.color_ed5452));
        } else {
            mTxtCount.setTextColor(ContextCompat.getColor(this, R.color.color_989CA8));
        }
    }
}
