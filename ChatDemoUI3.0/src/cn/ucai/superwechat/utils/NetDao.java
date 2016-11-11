package cn.ucai.superwechat.utils;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.io.File;
import java.util.List;

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
    public static void createGroup(Context context, EMGroup group, File file, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID, group.getGroupId())
                .addParam(I.Group.NAME, group.getGroupName())
                .addParam(I.Group.DESCRIPTION, group.getDescription())
                .addParam(I.Group.OWNER, group.getOwner())
                .addParam(I.Group.IS_PUBLIC, group.isPublic() + "")
                .addParam(I.Group.ALLOW_INVITES, group.isAllowInvites() + "")
                .addFile2(file)
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    //创建群组
    public static void createGroup(Context context, EMGroup group, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID, group.getGroupId())
                .addParam(I.Group.NAME, group.getGroupName())
                .addParam(I.Group.DESCRIPTION, group.getDescription())
                .addParam(I.Group.OWNER, group.getOwner())
                .addParam(I.Group.IS_PUBLIC, group.isPublic() + "")
                .addParam(I.Group.ALLOW_INVITES, group.isAllowInvites() + "")
                .post()
                .targetClass(Result.class)
                .execute(listener);
    }

    //更改群头像
    public static void updatGroupAvatar(Context context, String name, File file, OkHttpUtils.OnCompleteListener<Result> listtener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, name)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_GROUP_PATH)
                .addFile2(file)
                .targetClass(Result.class)
                .post()
                .execute(listtener);
    }

    public static void addMembers(Context context, EMGroup group, OkHttpUtils.OnCompleteListener<Result> listtener) {
        List<String> members = group.getMembers();
        String str = "";
        for (String s : members) {
            if (!s.equals(EMClient.getInstance().getCurrentUser())) {
                str += s + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_GROUP_MEMBERS)
                .addParam(I.Member.USER_NAME, str)
                .addParam(I.Member.GROUP_HX_ID, group.getGroupId())
                .targetClass(Result.class)
                .execute(listtener);
    }


}
