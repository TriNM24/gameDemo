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

    LoadingDialog loadingDialog;

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
        binding.setAction(this);
        loadingDialog = LoadingDialog.newInstance(null);
    }

    public void login() {
        String email = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        String error = "";
        if (TextUtils.isEmpty(email) || !Utils.validate(email)) {
            error = getString(R.string.invalid_mail);
        } else if (TextUtils.isEmpty(password) || password.length() <= 5) {
            error = getString(R.string.invalid_password);
        }
        if (TextUtils.isEmpty(error)) {
            getSupportFragmentManager().beginTransaction().add(loadingDialog, "").commitAllowingStateLoss();
            FirebaseHelper.getInstance().getUserDao().signIn(email, password, true)
                    .observe(this, authResultTask -> {
                        processResult(authResultTask);
                        if (loadingDialog != null) {
                            loadingDialog.dismissAllowingStateLoss();
                        }
                    });
        } else {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    private void processResult(Task<AuthResult> authResultTask) {
        if (authResultTask == null) {
            Utils.showAlertDialog(this, getString(R.string.confirm_title), getString(R.string.verify_email));
            return;
        }
        if (authResultTask.isSuccessful()) {
            FirebaseUser firebaseUser1 = authResultTask.getResult().getUser();
            if (firebaseUser1 != null) {
                FirebaseHelper.getInstance().getUserDao().updateUserInfo(firebaseUser1.getUid(),
                        firebaseUser1.getEmail()).observe(LoginActivity.this, aBoolean -> {
                    startActivityWithAnimation(new Intent(this, MainActivity.class));
                });

            }
        } else {
            String error = null;
            try {
                throw authResultTask.getException();
            } catch (FirebaseAuthWeakPasswordException e) {
                error = getApplication().getString(R.string.login_message_password_invalid);
            } catch (FirebaseAuthInvalidCredentialsException e) {
                error = getApplication().getString(R.string.login_message_incorrect_pass_or_mail);
            } catch (FirebaseNetworkException e) {
                error = getApplication().getString(R.string.message_network_error);
            } catch (Exception e) {
                error = e.getMessage();
                //error = getApplication().getString(R.string.message_network_error);
            }

            if (!TextUtils.isEmpty(error)) {
                Utils.showAlertDialog(this, getString(R.string.confirm_title), error);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}