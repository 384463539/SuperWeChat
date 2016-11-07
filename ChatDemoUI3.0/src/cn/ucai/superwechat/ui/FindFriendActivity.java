package cn.ucai.superwechat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.UserBean;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuPerWeChatHelper;

public class FindFriendActivity extends BaseActivity {

    @InjectView(R.id.findfrind_iv_back)
    ImageView findfrindIvBack;
    @InjectView(R.id.findfrind_iv_add)
    ImageView findfrindIvAdd;
    @InjectView(R.id.findfriend_iv_userAvatar)
    ImageView findfriendIvUserAvatar;
    @InjectView(R.id.findfriend_tv_usernick)
    TextView findfriendTvUserNick;
    @InjectView(R.id.findfriend_tv_sz)
    TextView findfriendTvSz;
    @InjectView(R.id.findfriend_tv_dq)
    TextView findfriendTvDq;
    @InjectView(R.id.findfriend_tv_qianming)
    TextView findfriendTvQianming;
    @InjectView(R.id.findfriend_btn_add)
    Button findfriendBtnAdd;
    @InjectView(R.id.findfriend_btn_sendmessage)
    Button findfriendBtnSendmessage;
    @InjectView(R.id.findfriend_btn_videocat)
    Button findfriendBtnVideocat;
    Context context;
    UserBean user = null;
    @InjectView(R.id.findfriend_tv_username)
    TextView findfriendTvUsername;

    String name;
    String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        context = this;
        ButterKnife.inject(this);
        Intent intent = getIntent();
        user = (UserBean) intent.getSerializableExtra("userbean");
        updataVIew();
    }

    public void setVisib() {
        if (SuPerWeChatHelper.getInstance().getAppcontactList().containsKey(name)) {
            findfriendBtnSendmessage.setVisibility(View.VISIBLE);
            findfriendBtnVideocat.setVisibility(View.VISIBLE);
            findfriendBtnAdd.setVisibility(View.GONE);
        } else {
            findfriendBtnSendmessage.setVisibility(View.GONE);
            findfriendBtnVideocat.setVisibility(View.GONE);
            findfriendBtnAdd.setVisibility(View.VISIBLE);
        }
    }

    private void updataVIew() {
        name = user.getMUserName();
        nick = user.getMUserNick();
        EaseUserUtils.setUserAvatar2(context, name, findfriendIvUserAvatar);
        findfriendTvUserNick.setText(nick);
        findfriendTvUsername.setText("微信号:" + name);
        setVisib();
    }

    @OnClick({R.id.findfrind_iv_back, R.id.findfriend_tv_sz, R.id.findfriend_btn_add, R.id.findfriend_btn_sendmessage, R.id.findfriend_btn_videocat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findfrind_iv_back:
                finish();
                break;
            case R.id.findfriend_tv_sz:
                break;
            case R.id.findfriend_btn_add:
                Intent intent = new Intent(FindFriendActivity.this, AddFriendActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
                finish();
                break;
            case R.id.findfriend_btn_sendmessage:
                break;
            case R.id.findfriend_btn_videocat:
                break;
        }
    }
}
