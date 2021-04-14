package com.example.minigames.gameBasic.helicopterGame;

import android.os.Bundle;
import android.view.Menu;

import com.example.minigames.R;
import com.example.minigames.base.BaseActivity;
import com.example.minigames.databinding.ActivityCaroGameBinding;
import com.example.minigames.firebase.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HelicopterGameActivity extends BaseActivity<ActivityCaroGameBinding> {

    GamePanel gamePanel;

    @Override
    protected boolean isHaveBackMenu() {
        return true;
    }

    @Override
    protected boolean isHaveRightMenu() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_helicopter_game;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.title_activity_helicopter_game);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }

    @Override
    protected void subscribeUi() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseHelper.getInstance().getUserDao().getUser(currentUser.getUid()).observe(this, user -> {
            gamePanel.best = (int) user.getScoreGame5();
            gamePanel.invalidate();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}