package cn.ucai.superwechat.utils;


import android.app.Activity;
import android.content.Context;

import cn.ucai.superwechat.R;

public class MFGT {

    public static void finsh(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
