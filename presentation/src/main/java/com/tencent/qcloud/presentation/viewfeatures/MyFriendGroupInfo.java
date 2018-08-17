package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public interface MyFriendGroupInfo extends MvpView {
    /**
     * 我的群列表
     * @param timFriendGroups
     */
    void showMyGroupList(List<TIMFriendGroup> timFriendGroups);

    /**
     * 群成员
     * @param groupname
     * @param timUserProfiles
     */
    void showGroupMember(String groupname,List<TIMUserProfile> timUserProfiles);
}
