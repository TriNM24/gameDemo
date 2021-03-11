package binh.le.game.login;

import android.os.Bundle;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected boolean isHaveRightMenu() {
        return false;
    }

    @Override
    protected boolean isHaveBackMenu() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.action_sign_in);
    }

    @Override
    protected void subscribeUi() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}