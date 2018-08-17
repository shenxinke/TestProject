package com.yst.onecity.adapter.chatadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.ui.CircleImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.chatmodel.AbstractConversation;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.TimeUtil;

import java.util.List;

/**
 * 会话界面adapter
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/3/14.
 */
public class ConversationAdapter extends ArrayAdapter<AbstractConversation> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private Context mContext;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout_item file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ConversationAdapter(Context context, int resource, List<AbstractConversation> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.lastMessage = (TextView) view.findViewById(R.id.last_message);
            viewHolder.time = (TextView) view.findViewById(R.id.message_time);
            viewHolder.unread = (TextView) view.findViewById(R.id.unread_num);
            view.setTag(viewHolder);
        }
        final AbstractConversation data = getItem(position);

        viewHolder.lastMessage.setText(data.getLastMessageSummary());
        viewHolder.time.setText(TimeUtil.getTimeStr(data.getLastMessageTime()));
        long unRead = data.getUnreadNum();

        if(data.getNickName() == null || "".equals(data.getNickName())) {
///            viewHolder.tvName.setText(data.getName().length() > 11 ? data.getName().substring(0, 11) : data.getName());
            viewHolder.tvName.setText("匿名用户");
        } else {
            viewHolder.tvName.setText(data.getNickName());
        }

        if(data.getFaceUrl() == null || "".equals(data.getFaceUrl())) {
            viewHolder.avatar.setImageResource(data.getAvatar());
        } else {
            Glide.with(mContext).load(data.getFaceUrl()).error(R.mipmap.head_2).into(viewHolder.avatar);
        }

        if (unRead <= 0){
            viewHolder.unread.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.unread.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if (unRead < Const.INTEGER_10){
                viewHolder.unread.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.point1));
            }else{
                viewHolder.unread.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.point2));
                if (unRead > Const.INTEGER_99){
                    unReadStr = getContext().getResources().getString(R.string.time_more);
                }
            }
            viewHolder.unread.setText(unReadStr);
        }
        return view;
    }

    public class ViewHolder{
        public TextView tvName;
        public CircleImageView avatar;
        public TextView lastMessage;
        public TextView time;
        public TextView unread;

    }
}
