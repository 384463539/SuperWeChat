package cn.ucai.superwechat.utils;

import android.content.Context;

import java.io.File;

import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.bean.Result;


public class NetDao {
    //注册
    public static void register(Context context, String name, String nick, String password, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER).post()
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.PASSWORD, password)
                .targetClass(Result.class)
                .execute(listtener);
    }

    //取消注册
    public static void unRegister(Context context, String name, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UNREGISTER)
                .addParam(I.User.USER_NAME, name)
                .targetClass(Result.class)
                .execute(listtener);
    }

    //登录
    public static void login1(Context context, String name, String password, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.PASSWORD, password)
                .targetClass(Result.class)
                .execute(listtener);
    }

    //更改昵称
    public static void updatNick(Context context, String name, String nick, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.NICK, nick)
                .targetClass(Result.class)
                .execute(listtener);
    }

    //更改头像
    public static void updatAvatar(Context context, String name, File file, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, name)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(Result.class)
                .post()
                .execute(listtener);
    }
}
