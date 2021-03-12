package binh.le.game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import binh.le.game.base.BaseActivity;
import binh.le.game.gameBasic.caroGame.CaroGameActivity;
import binh.le.game.databinding.ActivityMainBinding;
import binh.le.game.gameBasic.memoryGame.MemoryGameActivity;
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

    public void openSetting(){
        startActivityWithAnimation(new Intent(this, SettingActivity.class));
    }

}