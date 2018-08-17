package com.yst.onecity.utils;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 日志打印工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class OkHttpLog implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public OkHttpLog(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = "OkHttpUtils";
        }

        this.showResponse = showResponse;
        this.tag = tag;
    }

    public OkHttpLog(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        this.logForRequest(request);
        boolean isDebug = android.os.Debug.isDebuggerConnected();
        boolean isOwnApp = ConstUtils.isOwnApp();
///        MyLog.e("debugggg", "isdebug_________" + isDebug);
//        MyLog.e("debugggg", "flags_________" + TianyiApplication.getContext().getApplicationInfo().flags);
//        MyLog.e("debugggg", "isOwnApp_________" + isOwnApp);
//        if (!isDebug || 0 != (TianyiApplication.getContext().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE) || !isOwnApp) {
//            TianyiApplication.exit();
//            System.exit(0);
//        }
        Response response = chain.proceed(request);
        return this.logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            MyLog.e(this.tag, " code : " + clone.code() + " protocol : " + clone.protocol());
            if (this.showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        MyLog.e(this.tag, "response : " + mediaType.toString());
                        if (this.isText(mediaType)) {
                            String resp = body.string();
                            MyLog.i(this.tag, "tag:" + response.request().tag() + " response : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        }

                        MyLog.e(this.tag, "responseBody\'s content :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception var7) {
            ;
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            MyLog.e(this.tag, "method : " + request.method());
            MyLog.i(this.tag, "tag:" + request.tag() + " request : " + url + "?" + this.bodyToString(request));
            if (headers != null && headers.size() > 0) {
                MyLog.e(this.tag, "headers : " + headers.toString());
            }
        } catch (Exception var4) {
            ;
        }

    }

    private boolean isText(MediaType mediaType) {
        return mediaType.type() != null && "text".equals(mediaType.type()) ? true : mediaType.subtype() != null && ("json".equals(mediaType.subtype()) || "xml".equals(mediaType.subtype()) || "html".equals(mediaType.subtype()) || "webviewhtml".equals(mediaType.subtype()));
    }

    private String bodyToString(Request request) {
        try {
            Request e = request.newBuilder().build();
            Buffer buffer = new Buffer();
            e.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException var4) {
            return "something error when show requestBody.";
        }
    }
}
