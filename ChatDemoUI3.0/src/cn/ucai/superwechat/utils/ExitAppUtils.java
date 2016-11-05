package cn.ucai.superwechat.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class ExitAppUtils {
    private static ExitAppUtils instance = new ExitAppUtils();
    List<Activity> list = new LinkedList<>();

    private ExitAppUtils() {

    }

    public static ExitAppUtils getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void delActivity(Activity activity) {
        list.remove(activity);
    }

    public void exit() {
        for (Activity a : list) {
            a.finish();
        }
    }
}
