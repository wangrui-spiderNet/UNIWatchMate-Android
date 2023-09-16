package com.base.api;

import android.content.Context;

import com.base.sdk.AbUniWatch;

import java.util.ArrayList;
import java.util.List;

public class UNIWatchMate {
    private volatile static UNIWatchMate uniqueInstance;
    private static Context mContext;

    private static List<AbUniWatch> mBaseUNIWatches = new ArrayList<>();

    public static AbUniWatch mUNIWatchSdk;
    private static int mMsgTimeOut = 10000;

    private UNIWatchMate() {
    }

    public static UNIWatchMate getInstance() {
        if (uniqueInstance == null) {
            synchronized (UNIWatchMate.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new UNIWatchMate();
                }
            }
        }
        return uniqueInstance;
    }

    public static void init(Context context, int msgTimeOut, AbUniWatch[] supportSdks) {
        mBaseUNIWatches.clear();
        mContext = context;
        if (mMsgTimeOut < 5) {
            mMsgTimeOut = 5;
        } else {
            mMsgTimeOut = msgTimeOut;
        }

        for (int i = 0; i < supportSdks.length; i++) {
            supportSdks[i].init(mContext, mMsgTimeOut);
            mBaseUNIWatches.add(supportSdks[i]);
        }

        if (mUNIWatchSdk == null && mBaseUNIWatches.isEmpty()) {
            throw new RuntimeException("No Sdk Register Exception!");
        }
    }

}
