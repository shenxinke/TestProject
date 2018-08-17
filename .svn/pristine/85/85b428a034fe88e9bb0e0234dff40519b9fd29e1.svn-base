package com.yst.onecity.activity.video;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.taobao.av.logic.media.TaoMediaRecorder;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.BasicBean;
import com.yst.onecity.bean.UploadImageBean;
import com.yst.onecity.callbacks.AbstractUploadImageCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 编辑发布视频页面
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/6/2.
 */
public class EditPublishVideoActivity extends BaseActivity {
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.et_content)
    ContainsEmojiEditText etContent;
    @BindView(R.id.iv_video)
    ImageView ivVideo;

    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * 视频封面路径
     */
    private String framePicPath;

    /**
     * 上传视频后服务器返回的address
     */
    private String videoAddress;
    private String cover;
    private String content;
    private TaoMediaRecorder taoMediaRecorder;
    private File file;

    @Override
    public int bindLayout() {
        return R.layout.activity_video_editpublish;
    }

    @Override
    public void initData() {
        init();
        upLoadImage();
    }

    private void init() {
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Const.INTEGER_30)});
        //视频路径
        videoPath = getIntent().getStringExtra("videoPath");
        //视频封面路径
        framePicPath = getIntent().getStringExtra("framePicPath");
        //设置视频封面图
        Glide.with(EditPublishVideoActivity.this).load(framePicPath).error(R.mipmap.img_default_p).into(ivVideo);

        file = new File(videoPath);
        if(!file.exists()){
            taoMediaRecorder = new TaoMediaRecorder(this);
            String path = taoMediaRecorder.getFileDir() + File.separator + "temp_output.mp4";
            file = new File(path);
        }
    }

    /**
     * 上传封面图
     */
    private void upLoadImage() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "type", "0",
                "timestamp", timestamp);
        File scalFile = BitmapUtil.scal(framePicPath);
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
            }

            @Override
            public void onResponse(UploadImageBean response, int id) {
                if (response.getCode() == 1) {
                    cover = response.getAddress();
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    @OnClick(R.id.tv_publish)
    public void publish(){
        if(!Utils.isClickable()){
            return;
        }
        content = etContent.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            ToastUtils.show("说点什么吧");
            return;
        }
        upLoadVideo();
    }

    @OnClick(R.id.tv_cancle)
    public void toCancel(){
        finish();
    }

    /**
     * 上传视频接口
     */
    private void upLoadVideo() {

        String url = Constants.VIDEO_PX_UPLOAD;
        HashMap<String, String> params = new HashMap<>(16);
        params.put("module","video");
        try {
            OkHttpUtils.post().url(url)
                    .addFile("urlfile",file.getName(),file)
                    .params(params)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(60000)
                    .writeTimeOut(60000)
                    .execute(new StringCallback() {

                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    showProgressDialog();
                }

                @Override
                public void onAfter(int id) {
                    super.onAfter(id);
                    dismissProgressDialog();
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(getString(R.string.str_net_error_message));
                }

                @Override
                public void onResponse(String response, int id) {
                    Gson gson = new Gson();
                    BasicBean basicBean = gson.fromJson(response, BasicBean.class);
                    if(Const.INTEGER_1 == basicBean.code){
                        videoAddress = basicBean.address;
                        MyLog.e("xxx",basicBean.toString());
                        publishVideo();
                    }else{
                        ToastUtils.show(basicBean.msg);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发布视频及资讯接口
     */
    private void publishVideo() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "status", "1",
                "source","0",
                "cover",cover,
                "videoAddress",videoAddress,
                "content",content,
                "productId", ProductDetailActivity.productId,
                "timestamp", timestamp);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post()
                .url(Constants.VIDEO_PX_ADD_PUBLISH)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("status", "1")
                .addParams("source", "0")
                .addParams("productId", ProductDetailActivity.productId)
                .addParams("cover",cover)
                .addParams("videoAddress",videoAddress)
                .addParams("content",content)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        showProgressDialog();
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(TianyiApplication.getInstance().getString(R.string.str_net_error_message));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (Const.INTEGER_1 == jsonObject.getInt(Const.CONS_STR_CODE)) {
                                RxBus.get().send(RxCode.CODE_PUBLISH_VIDEO_SUCCRSSS);
                                ToastUtils.show("发布成功");
                                finish();
                            } else {
                                ToastUtils.show(jsonObject.getString(Const.CONS_STR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(taoMediaRecorder != null){
            taoMediaRecorder.stop();
            taoMediaRecorder = null;
        }
    }
}
