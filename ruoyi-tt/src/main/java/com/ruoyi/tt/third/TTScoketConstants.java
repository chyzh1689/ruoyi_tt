package com.ruoyi.tt.third;

import com.ruoyi.tt.enums.ChannelPackage;

import java.util.HashMap;
import java.util.Map;

public class TTScoketConstants {
    /***设备登录***/
    public static final String PACKAGE_NAME_ADMIN = "admin";
    /***推特账号 登录***/
    public static final String PACKAGE_NAME_TT ="com.twitter.android";
    public static final String PACKAGE_NAME_FB ="com.facebook.android";
    public static final String PACKAGE_NAME_DY ="com.ss.android.ugc.aweme";

    public static Map<String, String> getChannelMap() {
        return channelMap;
    }

    private static final Map<String,String> channelMap = new HashMap<>(8);
    static{
        channelMap.put(PACKAGE_NAME_ADMIN, PACKAGE_NAME_ADMIN);
        channelMap.put(PACKAGE_NAME_TT, ChannelPackage.TT.name());
        channelMap.put(PACKAGE_NAME_FB, ChannelPackage.FB.name());
        channelMap.put(PACKAGE_NAME_DY,ChannelPackage.DY.name());

        channelMap.put(ChannelPackage.TT.name(),PACKAGE_NAME_TT);
        channelMap.put(ChannelPackage.FB.name(),PACKAGE_NAME_FB);
        channelMap.put(ChannelPackage.DY.name(),PACKAGE_NAME_DY);
    }

    public static final String ACTION_DEVICE_LOGIN = "device-login";
    public static final String ACTION_ACCOUNT_LOGIN = "account-login";
    public static final String ACTION_ACCOUNT_DISCONNECT = "account-disconnect";
    public static final String ACTION_APP_CONFIG = "app-config";

    public static final String ACTION_FOLLOW = "follow";
    public static final String ACTION_follow_list = "follow-list";
    public static final String ACTION_CHAT_MSG= "chat-msg";
    public static final String ACTION_CHAT_CMD= "chat-cmd";
    public static final String ACTION_SYNC="sync";
    public static final String ACTION_ERROR="error";
    public static final String ACTION_CMD="cmd";
    public static final String ACTION_USER_LIST="user-list";
    public static final String ACTION_ENTER_ROOM="enter-room";
    public static final String ACTION_QUERY_ROOM="query-room";

    public static final String param_user_id = "userId";
    public static final String param_sec_uid = "secUid";
    public static final String param_user_name = "userName";
    public static final String param_location = "location";
    public static final String param_image_url = "imageUrl";
    public static final String param_status = "status";

    public static final Integer CODE_UNATIVE = 400;

    public static final Integer CODE_ACTIVE = 200;

}
