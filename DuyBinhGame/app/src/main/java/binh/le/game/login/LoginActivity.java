package binh.le.game.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import binh.le.game.MainActivity;
import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivityLoginBinding;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.ultis.LoadingDialog;
import binh.le.game.ultis.Utils;

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