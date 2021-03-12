package binh.le.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivitySplashScreenBinding;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.firebase.dao.UserDao;
import binh.le.game.login.LoginActivity;
import binh.le.game.ultis.SharePrefUtils;

public class SplashScreenActivity extends BaseActivity<ActivitySplashScreenBinding> {

    private boolean isLogined = false;

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
        return R.layout.activity_splash_screen;
    }

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected void subscribeUi() {
        UserDao userDao = FirebaseHelper.getInstance().getUserDao();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            isLogined = true;
        }
    }

    private synchronized void checkUser() {
        if (isLogined) {
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getBaseContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle);
        } else {
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getBaseContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(2500);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkUser();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.imgLogo.startAnimation(animation1);
    }
}