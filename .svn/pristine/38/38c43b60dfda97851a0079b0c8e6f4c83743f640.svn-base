package com.yst.onecity.bean.chatmodel;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMFileElem;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.chatadapter.ChatAdapter;
import com.yst.onecity.utils.FileUtil;
import com.yst.onecity.utils.MyLog;

/**
 * 文件消息
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class FileMessage extends AbstractMessage {

    public FileMessage(TIMMessage message){
        this.message = message;
    }

    public FileMessage(String filePath){
        message = new TIMMessage();
        TIMFileElem elem = new TIMFileElem();
        elem.setPath(filePath);
        elem.setFileName(filePath.substring(filePath.lastIndexOf("/")+1));
        message.addElement(elem);
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {
        clearView(viewHolder);
        TIMFileElem e = (TIMFileElem) message.getElement(0);
        TextView tv = new TextView(TianyiApplication.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv.setTextColor(ContextCompat.getColor(TianyiApplication.getContext(), isSelf() ? R.color.white : R.color.black));
        tv.setText(e.getFileName());
        getBubbleView(viewHolder).addView(tv);
        showStatus(viewHolder);
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return TianyiApplication.getContext().getString(R.string.summary_file);
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {
        if (message == null) {
            return;
        }
        final TIMFileElem e = (TIMFileElem) message.getElement(0);
        e.getFile(new TIMValueCallBack<byte[]>() {
            @Override
            public void onError(int i, String s) {
                MyLog.e(TAG, "getFile failed. code: " + i + " errmsg: " + s);
            }

            @Override
            public void onSuccess(byte[] bytes) {
                String[] str = e.getFileName().split("/");
                String filename = str[str.length-1];
                if (FileUtil.isFileExist(filename, Environment.DIRECTORY_DOWNLOADS)) {
                    Toast.makeText(TianyiApplication.getContext(), TianyiApplication.getContext().getString(R.string.save_exist),Toast.LENGTH_SHORT).show();
                    return;
                }
                java.io.File mFile = FileUtil.createFile(bytes, filename, Environment.DIRECTORY_DOWNLOADS);
                if (mFile != null){
                    Toast.makeText(TianyiApplication.getContext(), TianyiApplication.getContext().getString(R.string.save_succ) +
                            "path : " + mFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TianyiApplication.getContext(), TianyiApplication.getContext().getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
