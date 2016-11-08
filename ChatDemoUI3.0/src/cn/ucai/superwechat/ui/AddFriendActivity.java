package cn.ucai.superwechat.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ucai.superwechat.R;

public class AddFriendActivity extends AppCompatActivity {

    @InjectView(R.id.add_iv_back)
    ImageView addIvBack;
    @InjectView(R.id.add_tv_add)
    TextView addTvAdd;
    @InjectView(R.id.add_et_note)
    EditText addEtNote;
    ProgressDialog progressDialog;
    String name;
    String meg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.inject(this);
        name = getIntent().getStringExtra("name");
        initData();
    }

    private void initData() {
        meg = "我是" + EaseUserUtils.getUserInfo2(EMClient.getInstance().getCurrentUser()).getMUserNick();
        addEtNote.setText(meg);
    }

    @OnClick({R.id.add_iv_back, R.id.add_tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_iv_back:
                finish();
                break;
            case R.id.add_tv_add:
                progressDialog = new ProgressDialog(this);
                String stri = getResources().getString(R.string.Is_sending_a_request);
                progressDialog.setMessage(stri);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            //demo use a hardcode reason here, you need let user to input if you like
                            String s = getResources().getString(R.string.Add_a_friend);
                            EMClient.getInstance().contactManager().addContact(name, meg);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    String s1 = getResources().getString(R.string.send_successful);
                                    Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (final Exception e) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                    Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
        }
    }
}
