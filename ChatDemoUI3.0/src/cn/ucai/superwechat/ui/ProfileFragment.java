package cn.ucai.superwechat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @InjectView(R.id.profile_iv_userAvatar)
    ImageView profileIvUserAvatar;
    @InjectView(R.id.profile_tv_nick)
    TextView profileTvNick;
    @InjectView(R.id.profile_tv_username)
    TextView profileTvUsername;
    @InjectView(R.id.profile_tv_phone)
    TextView profileTvPhone;
    @InjectView(R.id.profile_tv_collect)
    TextView profileTvCollect;
    @InjectView(R.id.profile_tv_money)
    TextView profileTvMoney;
    @InjectView(R.id.profile_tv_smail)
    TextView profileTvSmail;
    @InjectView(R.id.profile_tv_setting)
    TextView profileTvSetting;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        String name = EMClient.getInstance().getCurrentUser();
        EaseUserUtils.setUserAvatar2(getActivity(), name, profileIvUserAvatar);
        EaseUserUtils.setUserNick2(name, profileTvNick);
        EaseUserUtils.setUserName(name, profileTvUsername);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.profile_tv_phone, R.id.profile_tv_collect, R.id.profile_tv_money, R.id.profile_tv_smail, R.id.profile_tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_tv_phone:
                break;
            case R.id.profile_tv_collect:
                break;
            //进入红包页面
            case R.id.profile_tv_money:
                RedPacketUtil.startChangeActivity(getActivity());
                break;
            case R.id.profile_tv_smail:
                break;
            case R.id.profile_tv_setting:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(((MainActivity)getActivity()).isConflict){
            outState.putBoolean("isConflict", true);
        }else if(((MainActivity)getActivity()).getCurrentAccountRemoved()){
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
}
