package com.lovingrabbit.www.oucfreetalk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * Created by zjk on 2017/11/16.
 */

public class PushTestReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;

        Log. e(TAG, responseString);

        if (errorCode == 0) {
            // 绑定成功
            Log.e(TAG, "绑定成功");
        }
    }

    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.e(TAG, responseString);

    }

    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " sucessTags=" + sucessTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.e(TAG, responseString);
        if (errorCode == 0){
            Log.e(TAG, "设置标签成功");
        }
    }

    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " sucessTags=" + sucessTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.e(TAG, responseString);
        if (errorCode == 0){
            Log.e(TAG, "删除标签成功");
        }
    }

    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.e(TAG, responseString);
    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
