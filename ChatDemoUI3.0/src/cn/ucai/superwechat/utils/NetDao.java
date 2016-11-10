package cn.ucai.superwechat.utils;

import android.content.Context;

import com.hyphenate.chat.EMClient;

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
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(Result.class)
                .post()
                .execute(listtener);
    }

    //查询用户信息
    public static void findUser(Context context, String name, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, name)
                .targetClass(Result.class)
                .execute(listener);
    }

    //添加好友
    public static void addFriedn(Context context, String name, String friendName, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CONTACT)
                .addParam(I.Contact.USER_NAME, name)
                .addParam(I.Contact.CU_NAME, friendName)
                .targetClass(Result.class)
                .execute(listener);
    }

    //删除好友
    public static void deletFriend(Context context, String name, String friendName, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CONTACT)
                .addParam(I.Contact.USER_NAME, name)
                .addParam(I.Contact.CU_NAME, friendName)
                .targetClass(Result.class)
                .execute(listener);
    }

    //下载好友
    public static void loadAppContact(Context context, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME, EMClient.getInstance().getCurrentUser())
                .targetClass(Result.class)
                .execute(listener);
    }

    //创建群组
    public static void createGroup(Context context, String hxid, String groupname, String description, String owner, boolean ispublic, boolean isinvites, File file, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID, hxid)
                .addParam(I.Group.NAME, groupname)
                .addParam(I.Group.DESCRIPTION, description)
                .addParam(I.Group.OWNER, owner)
                .addParam(I.Group.IS_PUBLIC, ispublic + "")
                .addParam(I.Group.ALLOW_INVITES, isinvites + "")
                .addFile(file)
                .post()
                .targetClass(Result.class)
                .execute(listener);
    }
}
