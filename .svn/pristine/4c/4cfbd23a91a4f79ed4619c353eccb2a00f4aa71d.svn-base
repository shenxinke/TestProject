package com.yst.onecity.adapter.chatadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.ui.CircleImageView;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.bean.chatmodel.AbstractMessage;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;

import java.util.List;

/**
 * 聊天界面adapter
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/07/06
 */
public class ChatAdapter extends ArrayAdapter<AbstractMessage> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout_item file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChatAdapter(Context context, int resource, List<AbstractMessage> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftAvatar = (CircleImageView) view.findViewById(R.id.leftAvatar);
            viewHolder.rightAvatar = (CircleImageView) view.findViewById(R.id.rightAvatar);
            viewHolder.leftMessage = (RelativeLayout) view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = (RelativeLayout) view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = (RelativeLayout) view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = (RelativeLayout) view.findViewById(R.id.rightPanel);
            viewHolder.sending = (ProgressBar) view.findViewById(R.id.sending);
            viewHolder.error = (ImageView) view.findViewById(R.id.sendError);
            viewHolder.sender = (TextView) view.findViewById(R.id.sender);
            viewHolder.rightDesc = (TextView) view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = (TextView) view.findViewById(R.id.systemMessage);
            view.setTag(viewHolder);
        }
        if (position < getCount()){
            final AbstractMessage data = getItem(position);
            data.showMessage(viewHolder, getContext());
        }
        if (TianyiApplication.appCommonBean.getAddress().contains(Const.CONS_STR_HTTP)){
            Glide.with(getContext()).load(TianyiApplication.appCommonBean.getAddress()).into(viewHolder.rightAvatar);
        }else{
            Glide.with(getContext()).load(Constants.URL_IMAGE_HEAD + TianyiApplication.appCommonBean.getAddress()).into(viewHolder.rightAvatar);
        }
        Glide.with(getContext()).load(ChatActivity.hunterImgUrl).into(viewHolder.leftAvatar);
        return view;
    }

    public class ViewHolder{
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
        public CircleImageView rightAvatar;
        public CircleImageView leftAvatar;
    }
}
