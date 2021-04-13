package com.example.minigames.gameBasic.helicopterGame;

import android.os.Bundle;
import android.view.Menu;

import com.example.minigames.R;
import com.example.minigames.base.BaseActivity;
import com.example.minigames.databinding.ActivityCaroGameBinding;

public class HelicopterGameActivity extends BaseActivity<ActivityCaroGameBinding> {

    @Override
    protected boolean isHaveBackMenu() {
        return false;
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
    }

    @Override
    protected void subscribeUi() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}