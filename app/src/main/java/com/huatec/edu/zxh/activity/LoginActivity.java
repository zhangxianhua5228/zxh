package com.huatec.edu.zxh.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.huatec.edu.zxh.R;
import com.huatec.edu.zxh.common.BaseActivity;
import com.huatec.edu.zxh.http.ProgressDialogSubscriber;
import com.huatec.edu.zxh.http.entity.MemberEntity;
import com.huatec.edu.zxh.http.persenter.MemberPresenter;
import com.huatec.edu.zxh.utils.SystemConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    public int getContentViewId(){
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    void close(){
        finish();
    }
    @OnClick(R.id.bt_login)
    void login(){
        String userName = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            toastShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            toastShort("请输入密码");
            return;
        }
        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this){
            public void onNext(MemberEntity memberEntity){
                SystemConfig.setLogin(true);
                toastShort("登录成功");
                SystemConfig.setLoginUserName(memberEntity.uname);
                SystemConfig.setLoginUserEmail(memberEntity.email);
                SystemConfig.setLoginUserHead(memberEntity.image);
                setResult(RESULT_OK);
                finish();
            }
        }, userName, pwd);
    }
}
