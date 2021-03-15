package binh.le.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import binh.le.game.base.BaseActivity;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.gameBasic.caroGame.CaroGameActivity;
import binh.le.game.databinding.ActivityMainBinding;
import binh.le.game.gameBasic.memoryGame.MemoryGameActivity;
import binh.le.game.gameBasic.topPlayer.TopPlayerActivity;
import binh.le.game.service.BackgroundSoundService;
import binh.le.game.gameBasic.sudoku.SudokuGameActivity;
import binh.le.game.setting.SettingActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    Intent intentMusicBg;

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
        return false;
    }

    @Override
    protected boolean isHaveRightMenu() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentMusicBg = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intentMusicBg);
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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = currentUser.getPhotoUrl();
        if(photoUrl != null){
            Picasso.get().load(photoUrl).placeholder(R.drawable.img_default_account).fit().into(binding.imgUser);
        }
        String userName = currentUser.getDisplayName();
        userName = TextUtils.isEmpty(userName) ? currentUser.getEmail() : userName;
        binding.txtName.setText(userName);
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
        Toast.makeText(this, "onClickGame4", Toast.LENGTH_SHORT).show();
    }

    public void onClickTopPlayer(){
        Intent top = new Intent(this, TopPlayerActivity.class);
        startActivityWithAnimation(top);
    }

    public void openSetting(){
        startActivityWithAnimation(new Intent(this, SettingActivity.class));
    }

}