package com.yst.onecity.activity.member;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.UploadImageBean;
import com.yst.onecity.bean.information.AppNewsDetailBean;
import com.yst.onecity.bean.information.ProductBean;
import com.yst.onecity.callbacks.AbstractUploadImageCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractPublishNewsDialog;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.Serializable;
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
 * 发布资讯 添加资讯内容
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AddNewsContentActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.scroll_main_view)
    ScrollView scrollMainView;
    @BindView(R.id.llayout_main_content)
    LinearLayout llayoutMainContent;
    @BindView(R.id.tv_add_content)
    TextView tvAddContent;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    private AbstractPublishNewsDialog dialog;
    private int contentId = 0;
    private int currentIndex;
    /**
     * 点击类型  -1添加  2插入 3商品
     */
    private static int mClickType;
    /**
     * 文字类型
     */
    private static final int CONTENT_TEXT = 1;
    /**
     * 图片类型
     */
    private static final int CONTENT_IMAGE = 2;
    /**
     * 商品类型
     */
    private static final int CONTENT_PRODUCT = 3;
    private static final int REQUEST_IMAGETEXT = 6;
    private static final int REQUEST_CLIP_IMAGE = 7;
    private static final int ADD = -1;
    private static final int DELETE = 0;
    private static final int MOVEUP = 1;
    private static final int INSERT = 2;
    /**
     * 添加资讯内容
     */
    public static final int FLAG_ADD_NEWS = 1;
    /**
     * 添加商品内容
     */
    public static final int FLAG_ADD_PRODUCT = 2;
    private int flag = FLAG_ADD_NEWS;
    /**
     * 待发布内容
     */
    private List<AppNewsDetailBean> publishList = new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE_PRODUCT = 19;
    private String flagS;

    public static void navPublishNewsActivity(Activity activity, List<AppNewsDetailBean> publishList, String flag) {
        Intent intent = new Intent(activity, AddNewsContentActivity.class);
        intent.putExtra("publishList", (Serializable) publishList);
        intent.putExtra("flag", flag);
        activity.startActivity(intent);
    }

    public static void navPublishNewsActivity(Activity activity, List<AppNewsDetailBean> publishList, String flag, int requestCode) {
        Intent intent = new Intent(activity, AddNewsContentActivity.class);
        intent.putExtra("publishList", (Serializable) publishList);
        intent.putExtra("flag", flag);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_news_content;
    }

    @Override
    public void initData() {
        setTitleBarTitle("编辑内容");
        initDialog();
        List<AppNewsDetailBean> list = (List<AppNewsDetailBean>) getIntent().getSerializableExtra("publishList");
        flagS = getIntent().getStringExtra("flag");
        if (!TextUtils.isEmpty(flagS) && getString(R.string.tv_chakan).equals(flagS)) {
            tvAddContent.setVisibility(View.GONE);
        }
        if (list != null) {
            publishList.clear();
            for (int i = 0; i < list.size(); i++) {
                if (TextUtils.isEmpty(list.get(i).getCover())) {
                    publishList.add(list.get(i));
                } else if (!TextUtils.isEmpty(list.get(i).getCover()) && !"1".equals(list.get(i).getCover())) {
                    publishList.add(list.get(i));
                }
            }
            for (int i = 0; i < publishList.size(); i++) {
                AppNewsDetailBean newsDetailBean = publishList.get(i);
                if (newsDetailBean.getType() == Constants.INPUT_NEWS_DETAIL_TYPE_TEXT) {
                    addItemText(i, true, newsDetailBean.getDetail());
                } else if (newsDetailBean.getType() == Constants.INPUT_NEWS_DETAIL_TYPE_IMG) {
                    addItemImage(newsDetailBean.getUrl(), i);
                } else if (newsDetailBean.getType() == Constants.INPUT_NEWS_DETAIL_TYPE_PRODUCT) {
                    addItemProduct(i, newsDetailBean.getUrl());
                }
            }
        }
    }

    @OnClick({R.id.tv_add_content, R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_content:
                mClickType = ADD;
                currentIndex = llayoutMainContent.getChildCount();
                dialog.showDialog();
                break;
            case R.id.ll_back:
                saveContent();
                break;
            default:
                break;
        }
    }

    /**
     * 添加文本内容条目
     * isShowBack 回显
     */
    private void addItemText(int index, boolean isShowBack, String content) {
        contentId++;
        //更新UI
        LinearLayout textLinearLayout = initTextLayout(contentId, isShowBack, content);
        llayoutMainContent.addView(textLinearLayout, index);
        removeFirstItemUp();
        if (mClickType == ADD || index == llayoutMainContent.getChildCount() - 1) {
            mHandler.sendEmptyMessage(MESSAGGE_SCROLL);
        }
    }

    /**
     * i
     * 添加图片内容条目
     */
    private void addItemImage(String path, int index) {
        //更新UI
        llayoutMainContent.addView(initImageLayout(path), index);
        removeFirstItemUp();
        if (mClickType == ADD || index == llayoutMainContent.getChildCount() - 1) {
            mHandler.sendEmptyMessage(MESSAGGE_SCROLL);
        }
    }

    /**
     * 添加商品内容条目
     *
     * @param index      索引
     * @param productImg 图片地址
     */
    private void addItemProduct(int index, String productImg) {
        contentId++;
        //更新UI
        LinearLayout downLinearLayout = initProductLayout(contentId, productImg);
        llayoutMainContent.addView(downLinearLayout, index);
        removeFirstItemUp();
        if (mClickType == ADD || index == llayoutMainContent.getChildCount() - 1) {
            mHandler.sendEmptyMessage(MESSAGGE_SCROLL);
        }
    }

    private void initDialog() {        //发布资讯添加商品时，如果既是推广师也是会员，那么只有在推广师中心可以添加商品
        dialog = new AbstractPublishNewsDialog(this, Constants.ISSERVER) {
            @Override
            public void onTextClick() {
                flag = FLAG_ADD_NEWS;
                addItemText(currentIndex, false, null);
                if (mClickType == ADD) {
                    publishList.add(new AppNewsDetailBean(currentIndex, Constants.INPUT_NEWS_DETAIL_TYPE_TEXT, "", "", "0", currentIndex, "", null, null, null));
                } else if (mClickType == INSERT) {
                    publishList.add(currentIndex, new AppNewsDetailBean(currentIndex, Constants.INPUT_NEWS_DETAIL_TYPE_TEXT, "", "", "0", currentIndex, "", null, null, null));
                }
            }

            @Override
            public void onImageClick() {
                flag = FLAG_ADD_NEWS;
                if (EasyPermissions.hasPermissions(AddNewsContentActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    MultiImageSelector.create().single().showCamera(true).start(AddNewsContentActivity.this, REQUEST_IMAGETEXT);
                } else {
                    EasyPermissions.requestPermissions(AddNewsContentActivity.this, "请打开拍照和读取照片权限", 300, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }

            @Override
            public void onProductClick() {
                flag = FLAG_ADD_PRODUCT;
                startActivityForResult(new Intent(AddNewsContentActivity.this, ChooseNewsProductActivity.class), REQUEST_CODE_CHOOSE_PRODUCT);
            }
        };
    }

    /**
     * 初始化文本布局
     */
    private LinearLayout initTextLayout(int contentId, boolean isShowBack, String content) {
        //根部局
        LinearLayout textLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_publish_note_text, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 24;
        textLinearLayout.setLayoutParams(params);
        textLinearLayout.setId(contentId);
        textLinearLayout.setTag(CONTENT_TEXT);

        TextView delete = (TextView) textLinearLayout.findViewById(R.id.delete);
        TextView moveUp = (TextView) textLinearLayout.findViewById(R.id.move_up);
        TextView insert = (TextView) textLinearLayout.findViewById(R.id.insert);
        EditText contentEdit = (EditText) textLinearLayout.findViewById(R.id.content_edt);

        delete.setId(contentId);
        moveUp.setId(contentId);
        insert.setId(contentId);
        contentEdit.setId(contentId);

        if (isShowBack) {
            contentEdit.setText(content);
            contentEdit.requestFocus();
            contentEdit.setSelection(content.length());
        }

        if (getString(R.string.tv_chakan).equals(flagS)) {
            delete.setEnabled(false);
            moveUp.setEnabled(false);
            insert.setEnabled(false);
            contentEdit.setEnabled(false);
        }
        delete.setOnClickListener(new ItemClikListener(DELETE, contentId));
        moveUp.setOnClickListener(new ItemClikListener(MOVEUP, contentId));
        insert.setOnClickListener(new ItemClikListener(INSERT, contentId));

        return textLinearLayout;
    }

    /**
     * 初始化商品布局
     */
    private LinearLayout initProductLayout(int contentId, String productImg) {
        //根部局
        LinearLayout productFrameLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_publish_news_add_product, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 24;
        productFrameLayout.setLayoutParams(params);
        productFrameLayout.setId(contentId);
        productFrameLayout.setTag(CONTENT_PRODUCT);

        ImageView contentImg = (ImageView) productFrameLayout.findViewById(R.id.content_img);
        TextView typeFlag = (TextView) productFrameLayout.findViewById(R.id.typeFlag);
        TextView delete = (TextView) productFrameLayout.findViewById(R.id.delete);
        TextView moveUp = (TextView) productFrameLayout.findViewById(R.id.move_up);
        TextView insert = (TextView) productFrameLayout.findViewById(R.id.insert);
        typeFlag.setId(contentId);
        delete.setId(contentId);
        moveUp.setId(contentId);
        insert.setId(contentId);
        if (getString(R.string.tv_chakan).equals(flagS)) {
            delete.setEnabled(false);
            moveUp.setEnabled(false);
            insert.setEnabled(false);
        }
        delete.setOnClickListener(new ItemClikListener(DELETE, contentId));
        moveUp.setOnClickListener(new ItemClikListener(MOVEUP, contentId));
        insert.setOnClickListener(new ItemClikListener(INSERT, contentId));
        contentImg.setScaleType(flag == FLAG_ADD_NEWS ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.CENTER_CROP);
        //图片的宽度 = 屏幕宽度 - 左右边距
        contentImg.setLayoutParams(ConstUtils.getLineanrLayoutParams16B9f(ConstUtils.getScreenWidth(this) - ConstUtils.dip2px(this, 10 * 2)));
        contentImg.setId(contentId);
        Glide.with(this).load(productImg).into(contentImg);
        return productFrameLayout;
    }

    /**
     * 初始化图片布局
     */
    private LinearLayout initImageLayout(String path) {
        contentId++;
        //根部局
        LinearLayout imageFrameLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_publish_note_photo, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 24;
        imageFrameLayout.setLayoutParams(params);
        imageFrameLayout.setId(contentId);
        imageFrameLayout.setTag(CONTENT_IMAGE);

        ImageView contentImg = (ImageView) imageFrameLayout.findViewById(R.id.content_img);
        TextView delete = (TextView) imageFrameLayout.findViewById(R.id.delete);
        TextView moveUp = (TextView) imageFrameLayout.findViewById(R.id.move_up);
        TextView insert = (TextView) imageFrameLayout.findViewById(R.id.insert);
        delete.setId(contentId);
        moveUp.setId(contentId);
        insert.setId(contentId);
        if (getString(R.string.tv_chakan).equals(flagS)) {
            delete.setEnabled(false);
            moveUp.setEnabled(false);
            insert.setEnabled(false);
        }
        delete.setOnClickListener(new ItemClikListener(DELETE, contentId));
        moveUp.setOnClickListener(new ItemClikListener(MOVEUP, contentId));
        insert.setOnClickListener(new ItemClikListener(INSERT, contentId));
        contentImg.setScaleType(flag == FLAG_ADD_NEWS ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.CENTER_CROP);
        //图片的宽度 = 屏幕宽度 - 左右边距
        contentImg.setLayoutParams(ConstUtils.get16B9f(ConstUtils.getScreenWidth(this) - ConstUtils.dip2px(this, 10 * 2)));
        contentImg.setId(contentId);
        contentImg.setTag(R.id.consutImgIndex, path);
        Glide.with(this).load(path).into(contentImg);
        return imageFrameLayout;
    }

    /**
     * 条目多个子控件点击事件(图文，文本条目的子控件 ps：删除 上移 插入)
     */
    class ItemClikListener implements View.OnClickListener {
        private int mClickId;

        /**
         * 0 delete 1 moveUp 2 insert
         */
        private int mTag;

        public ItemClikListener(int tag, int clickId) {
            this.mTag = tag;
            this.mClickId = clickId;
        }

        @Override
        public void onClick(View v) {
            if (llayoutMainContent == null || llayoutMainContent.getChildCount() == 0) {
                return;
            }
            for (int i = 0; i < llayoutMainContent.getChildCount(); i++) {
                //该子条目类型
                int tag = (int) llayoutMainContent.getChildAt(i).getTag();
                LinearLayout dellayout = null;
                if (tag == CONTENT_TEXT) {
                    LinearLayout linearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                    dellayout = (LinearLayout) linearLayout.getChildAt(1);
                } else if (tag == CONTENT_IMAGE) {
                    LinearLayout mLinearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                    FrameLayout mFrameLayout = (FrameLayout) mLinearLayout.getChildAt(0);
                    dellayout = (LinearLayout) ((LinearLayout) (mFrameLayout.getChildAt(1))).getChildAt(1);
                } else if (tag == CONTENT_PRODUCT) {
                    LinearLayout linearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                    LinearLayout mLinearLayout = (LinearLayout) linearLayout.getChildAt(0);
                    dellayout = (LinearLayout) (mLinearLayout.getChildAt(1));
                }
                if (dellayout.getChildAt(mTag).getId() != mClickId) {
                    continue;
                }
                if (mTag == DELETE) {
                    deleteItem(i, tag);
                } else if (mTag == MOVEUP && i != 0) {
                    moveUpItem(i);
                } else if (mTag == INSERT) {
                    insertItem(i);
                }

            }
        }
    }

    /**
     * 删除条目操作
     */
    private void deleteItem(int index, int tag) {
        if (tag == CONTENT_PRODUCT) {
            ids.remove(publishList.get(index).getProductId());
            MyLog.e("@@@@@@@@@@@@", new Gson().toJson(ids));
        }
        //更新UI
        llayoutMainContent.removeViewAt(index);
        removeFirstItemUp();
        publishList.remove(index);
    }

    /**
     * 上移条目操作
     *
     * @param index 索引
     */
    private void moveUpItem(int index) {
        //更换UI
        LinearLayout currentItemView = (LinearLayout) llayoutMainContent.getChildAt(index);
        llayoutMainContent.removeViewAt(index);
        llayoutMainContent.addView(currentItemView, index - 1);
        AppNewsDetailBean newsDetailBean = publishList.get(index);
        publishList.remove(index);
        publishList.add(index - 1, newsDetailBean);
        removeFirstItemUp();
    }

    /**
     * 插入条目操作
     */
    private void insertItem(int index) {
        currentIndex = index + 1;
        mClickType = INSERT;
        dialog.showDialog();
    }

    /**
     * 过滤第一条条目数据不可上移操作
     */
    private void removeFirstItemUp() {
        if (llayoutMainContent == null || llayoutMainContent.getChildCount() == 0) {
            return;
        }
        for (int i = 0; i < llayoutMainContent.getChildCount(); i++) {
            LinearLayout delLayout = null;
            int tag = (int) llayoutMainContent.getChildAt(i).getTag();
            if (tag == CONTENT_TEXT) {
                LinearLayout linearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                delLayout = (LinearLayout) linearLayout.getChildAt(1);
            } else if (tag == CONTENT_IMAGE) {
                LinearLayout mLinearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                FrameLayout mFrameLayout = (FrameLayout) mLinearLayout.getChildAt(0);
                delLayout = (LinearLayout) ((LinearLayout) (mFrameLayout.getChildAt(1))).getChildAt(1);
            } else if (tag == CONTENT_PRODUCT) {
                LinearLayout linearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                LinearLayout mLinearLayout = (LinearLayout) linearLayout.getChildAt(0);
                delLayout = (LinearLayout) (mLinearLayout.getChildAt(1));
            }
            if (delLayout == null) {
                return;
            }
            if (i == 0) {
                if (tag == CONTENT_PRODUCT) {
                    //隐藏或显示上移按钮
                    delLayout.getChildAt(1).setVisibility(View.GONE);
                } else {
                    //隐藏或显示上移按钮
                    delLayout.getChildAt(0).setVisibility(View.GONE);
                }
            } else {
                if (tag == CONTENT_PRODUCT) {
                    //隐藏或显示上移按钮
                    delLayout.getChildAt(1).setVisibility(View.VISIBLE);
                } else {
                    //隐藏或显示上移按钮
                    delLayout.getChildAt(0).setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_IMAGETEXT:
                ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String url = urlList.get(0);
                if (flag == FLAG_ADD_NEWS) {
                    //跳转到裁剪图片页面 如需裁剪取消本行注释
                    ClipImageActivity.navClipImageActivity(this, url, 16, 9, REQUEST_CLIP_IMAGE);
                } else {
                    //直接压缩图片
                    String filePath = PhotoUtil.compressBitmap(url, ConstUtils.getScreenWidth(this), ConstUtils.getScreenWidth(this) * 3 / 2);
                    processImage(filePath);
                }
                break;
            //图片裁剪完毕
            case REQUEST_CLIP_IMAGE:
                String imagePath = data.getStringExtra("savedPath");
                processImage(imagePath);
                break;
            //选择商品
            case REQUEST_CODE_CHOOSE_PRODUCT:
                ProductBean productBean = (ProductBean) data.getSerializableExtra("data");
                AppNewsDetailBean appNewsDetailBean = new AppNewsDetailBean(currentIndex,
                        Constants.INPUT_NEWS_DETAIL_TYPE_PRODUCT, "", "", "0", currentIndex,
                        productBean.getId(), productBean.getImage(), productBean.getPsId(), productBean.getMerchantId());
                publishList.add(appNewsDetailBean);
                addItemProduct(currentIndex, productBean.getImage());
            default:
                break;
        }
    }

    private void processImage(final String imagePath) {
        mHandler.sendEmptyMessage(MESSAGGE_IMGSCROLL);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "type", "0",
                "timestamp", timestamp);
        File scalFile = BitmapUtil.scal(imagePath);
        //上传裁剪后的图片
        //0是图片
        OkHttpUtils.post().url(Constants.UPLOAD_IMAGE)
                .addFile("urlfile", scalFile.getName(), scalFile)
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
                MyLog.e("@@@@@@@@@@@", e.getMessage());
            }

            @Override
            public void onResponse(UploadImageBean response, int id) {
                if (response.getCode() == 1) {
                    addItemImage(Constants.URL_IMAGE_HEAD + response.getAddress(), currentIndex);
                    if (mClickType == ADD) {
                        publishList.add(new AppNewsDetailBean(currentIndex, Constants.INPUT_NEWS_DETAIL_TYPE_IMG,
                                "", response.getId(), "0", currentIndex, "", Constants.URL_IMAGE_HEAD + response.getAddress(),
                                null, null));
                    } else if (mClickType == INSERT) {
                        publishList.add(currentIndex, new AppNewsDetailBean(currentIndex, Constants.INPUT_NEWS_DETAIL_TYPE_IMG,
                                "", response.getId(), "0", currentIndex, "", Constants.URL_IMAGE_HEAD + response.getAddress(), null, null));
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    private static final int MESSAGE_CLICK = 4;
    private static final int MESSAGGE_SCROLL = 5;
    private static final int MESSAGGE_IMGSCROLL = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_CLICK:
                    mHandler.removeMessages(MESSAGE_CLICK);
                    break;
                case MESSAGGE_SCROLL:
                    scrollMainView.fullScroll(ScrollView.FOCUS_DOWN);
                    break;
                case MESSAGGE_IMGSCROLL:
                    int y = 0;
                    for (int i = 0; i < llayoutMainContent.getChildCount(); i++) {
                        LinearLayout linearLayout = (LinearLayout) llayoutMainContent.getChildAt(i);
                        y += linearLayout.getMeasuredHeight();
                    }
                    scrollMainView.scrollTo(0, y);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 拍照和选取本地文件权限授权
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        MultiImageSelector.create().single().showCamera(true).start(AddNewsContentActivity.this, REQUEST_IMAGETEXT);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveContent();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void saveContent() {
        for (int i = 0; i < llayoutMainContent.getChildCount(); i++) {
            LinearLayout itemView = (LinearLayout) llayoutMainContent.getChildAt(i);
            if ((int) (itemView.getTag()) == CONTENT_TEXT) {
                EditText editText = (EditText) itemView.getChildAt(0);
                String content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.show("请完善内容");
                    return;
                }
                publishList.remove(i);
                publishList.add(i, new AppNewsDetailBean(0, Constants.INPUT_NEWS_DETAIL_TYPE_TEXT, content, "", "0", currentIndex, null, null, null, null));

            }
        }
        Intent intent = new Intent();
        intent.putExtra("publishList", (Serializable) publishList);
        setResult(RESULT_OK, intent);
        finish();
    }

}
