package binh.le.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import binh.le.game.base.BaseActivity;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.gameBasic.caroGame.CaroGameActivity;
import binh.le.game.databinding.ActivityMainBinding;
import binh.le.game.gameBasic.memoryGame.MemoryGameActivity;
import binh.le.game.gameBasic.shootingGame.ShootingActivity;
import binh.le.game.gameBasic.topPlayer.TopPlayerActivity;
import binh.le.game.service.BackgroundSoundService;
import binh.le.game.gameBasic.sudoku.SudokuGameActivity;
import binh.le.game.setting.SettingActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    Intent intentMusicBg;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.actionbar_game_name);
    }

    @Override
    protected boolean isHaveBackMenu() {
        return true;
    }

    @Override
    protected boolean isHaveRightMenu() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ActionBar supportActionBar = getSupportActionBar();
        super.onCreate(savedInstanceState);
        intentMusicBg = new Intent(MainActivity.this, BackgroundSoundService.class);

        setSupportActionBar(binding.toolbar);
        setTitle(getActionBarTitle());
        centerTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupNavigationView();
    }

    private void setupNavigationView() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_menu_game1: {
                onClickGame1();
                break;
            }
            case R.id.nav_menu_game2: {
                onClickGame2();
                break;
            }
            case R.id.nav_menu_game3: {
                onClickGame3();
                break;
            }
        }
        //close navigation drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        intentMusicBg = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(intentMusicBg);
    }

    @Override
    protected void subscribeUi() {
        binding.setOnClick(this);
        FirebaseHelper.getInstance().getUserDao().keepSyncedData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(intentMusicBg);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        Uri photoUrl = currentUser.getPhotoUrl();
        if (photoUrl != null) {
            Picasso.get().load(photoUrl).placeholder(R.drawable.img_default_account).fit().into(binding.imgUser);
        }
        String userName = currentUser.getDisplayName();
        userName = TextUtils.isEmpty(userName) ? currentUser.getEmail() : userName;
        binding.txtName.setText(userName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intentMusicBg);
    }

    public void onClickGame1() {
        Intent caroGame = new Intent(this, CaroGameActivity.class);
        startActivityWithAnimation(caroGame);
    }

    public void onClickGame2() {
        Intent memoryGame = new Intent(this, MemoryGameActivity.class);
        startActivityWithAnimation(memoryGame);
    }

    public void onClickGame3() {
        Intent sudokuGame = new Intent(this, SudokuGameActivity.class);
        startActivityWithAnimation(sudokuGame);
    }

    public void onClickGame4() {
        Intent sudokuGame = new Intent(this, ShootingActivity.class);
        startActivityWithAnimation(sudokuGame);
    }

    public void onClickTopPlayer() {
        Intent top = new Intent(this, TopPlayerActivity.class);
        startActivityWithAnimation(top);
    }

    public void openSetting() {
        startActivityWithAnimation(new Intent(this, SettingActivity.class));
    }

}