package binh.le.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends BaseActivity<ActivitySplashScreenBinding> {
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}