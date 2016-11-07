package cn.ucai.superwechat.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.UserBean;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuPerWeChatHelper;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.db.UserDao;
import cn.ucai.superwechat.utils.NetDao;
import cn.ucai.superwechat.utils.OkHttpUtils;

public class UserProfileActivity extends BaseActivity implements OnClickListener {

    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    @InjectView(R.id.title)
    RelativeLayout title;
    @InjectView(R.id.user_head_avatar)
    ImageView headAvatar;
    @InjectView(R.id.user_nickname)
    TextView tvNickName;
    @InjectView(R.id.layout_nickname)
    RelativeLayout rlNickName;
    @InjectView(R.id.user_name)
    TextView tvUsername;
    @InjectView(R.id.layout_username)
    RelativeLayout layoutUsername;
    @InjectView(R.id.layout_ewname)
    RelativeLayout layoutEwname;
    @InjectView(R.id.layout_dz)
    RelativeLayout layoutDz;
    @InjectView(R.id.user_sex)
    TextView userSex;
    @InjectView(R.id.layout_sex)
    RelativeLayout layoutSex;
    @InjectView(R.id.user_dq)
    TextView userDq;
    @InjectView(R.id.layout_dq)
    RelativeLayout layoutDq;
    @InjectView(R.id.user_gxname)
    TextView userGxname;
    @InjectView(R.id.layout_gxname)
    RelativeLayout layoutGxname;
    //    private ImageView headAvatar;
//    private TextView tvNickName;
//    private TextView tvUsername;
//    private RelativeLayout rlNickName;
    private ProgressDialog dialog;
    UserBean user;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_user_profile);
        ButterKnife.inject(this);
        initView();
        initListener();
        user = EaseUserUtils.getUserInfo2(EMClient.getInstance().getCurrentUser());
    }

    private void initView() {
//        headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
//        tvUsername = (TextView) findViewById(R.id.user_username);
//        tvNickName = (TextView) findViewById(R.id.user_nickname);
//        rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
    }

    private void initListener() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        boolean enableUpdate = intent.getBooleanExtra("setting", false);
        if (enableUpdate) {
            rlNickName.setOnClickListener(this);
            headAvatar.setOnClickListener(this);
        } else {
        }
        if (username != null) {
            if (username.equals(EMClient.getInstance().getCurrentUser())) {
                tvUsername.setText(EMClient.getInstance().getCurrentUser());
                EaseUserUtils.setUserNick2(username, tvNickName);
                EaseUserUtils.setUserAvatar2(this, username, headAvatar);
            } else {
                tvUsername.setText(username);
                EaseUserUtils.setUserNick2(username, tvNickName);
                EaseUserUtils.setUserAvatar2(this, username, headAvatar);
                asyncFetchUserInfo(username);
            }
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.user_head_avatar:
//                uploadHeadPhoto();
//                break;
//            case R.id.rl_nickname:
//                final EditText editText = new EditText(this);
//                new Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
//                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String nickString = editText.getText().toString();
//                                if (TextUtils.isEmpty(nickString)) {
//                                    Toast.makeText(UserProfileActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                updateRemoteNick(nickString);
//                            }
//                        }).setNegativeButton(R.string.dl_cancel, null).show();
//                break;
//            default:
//                break;
//        }
//
//    }

    public void asyncFetchUserInfo(String username) {
        SuPerWeChatHelper.getInstance().getUserProfileManager().asyncGetUserInfo(username, new EMValueCallBack<EaseUser>() {

            @Override
            public void onSuccess(EaseUser user) {
                if (user != null) {
                    SuPerWeChatHelper.getInstance().saveContact(user);
                    if (isFinishing()) {
                        return;
                    }
                    tvNickName.setText(user.getNick());
                    if (!TextUtils.isEmpty(user.getAvatar())) {
                        Glide.with(UserProfileActivity.this).load(user.getAvatar()).placeholder(R.drawable.em_default_avatar).into(headAvatar);
                    } else {
                        Glide.with(UserProfileActivity.this).load(R.drawable.em_default_avatar).into(headAvatar);
                    }
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }


    private void uploadHeadPhoto() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.dl_title_upload_photo);
        builder.setItems(new String[]{getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload)},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Toast.makeText(UserProfileActivity.this, getString(R.string.toast_no_support),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }


    private void updateRemoteNick(final String nickName) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean updatenick = SuPerWeChatHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
                if (UserProfileActivity.this.isFinishing()) {
                    return;
                }
                if (!updatenick) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    updateBDNick(nickName);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
//                                    .show();
//                            tvNickName.setText(nickName);
//                        }
//                    });
                }
            }
        }).start();
    }

    public File savaFIle(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            String imagePath = EaseImageUtils.getImagePath(user.getMUserName() + I.AVATAR_SUFFIX_PNG);
            File file = new File(imagePath);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }

    private void updataUserAvatar(final Intent data) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        dialog.show();
        File file = savaFIle(data);
        NetDao.updatAvatar(this, user.getMUserName(), file, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                Log.e("superwechat", result.toString());
                if (result != null && result.isRetMsg()) {
                    String json = result.getRetData().toString();
                    Gson gson = new Gson();
                    UserBean u = gson.fromJson(json, UserBean.class);
                    SuPerWeChatHelper.getInstance().saveAppcontact(u);
                    setPicToView(data);
                } else {
                    Toast.makeText(UserProfileActivity.this, "头像上传失败1", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(UserProfileActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    updataUserAvatar(data);
//                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            headAvatar.setImageDrawable(drawable);
            dialog.dismiss();
            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
                    Toast.LENGTH_SHORT).show();


//            uploadUserAvatar(Bitmap2Bytes(photo));
        }

    }

    private void uploadUserAvatar(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String avatarUrl = SuPerWeChatHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (avatarUrl != null) {
                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_fail),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }).start();
    }


    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public void updataUser(UserBean u) {
        user = u;
        SuPerWeChatHelper.getInstance().saveAppcontact(u);
    }

    public void updateBDNick(final String nick) {
        NetDao.updatNick(this, user.getMUserName(), nick, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result != null) {
                    String json = result.getRetData().toString();
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(json, UserBean.class);
                    updataUser(userBean);
                    dialog.dismiss();
                    Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                            .show();
                    tvNickName.setText(nick);
                } else {
                    Toast.makeText(UserProfileActivity.this, "更新昵称失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(UserProfileActivity.this, "更新昵称失败", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.title, R.id.user_head_avatar, R.id.layout_nickname, R.id.layout_username, R.id.layout_ewname, R.id.layout_dz, R.id.layout_sex, R.id.layout_dq, R.id.layout_gxname})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:
                break;
            case R.id.user_head_avatar:
                uploadHeadPhoto();
                break;
            case R.id.layout_nickname:
                final EditText editText = new EditText(this);
                editText.setText(user.getMUserNick());
                new Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nickString = editText.getText().toString().trim();
                                if (TextUtils.isEmpty(nickString)) {
                                    Toast.makeText(UserProfileActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (nickString.equals(user.getMUserNick())) {
                                    Toast.makeText(UserProfileActivity.this, "用户昵称未修改", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                updateRemoteNick(nickString);
                            }
                        }).setNegativeButton(R.string.dl_cancel, null).show();
                break;
            case R.id.layout_username:
                break;
            case R.id.layout_ewname:
                break;
            case R.id.layout_dz:
                break;
            case R.id.layout_sex:
                break;
            case R.id.layout_dq:
                break;
            case R.id.layout_gxname:
                break;
        }
    }
}
