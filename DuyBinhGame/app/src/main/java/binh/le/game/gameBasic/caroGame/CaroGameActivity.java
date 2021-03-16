package binh.le.game.gameBasic.caroGame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.base.DialogInstruction;
import binh.le.game.databinding.ActivityCaroGameBinding;

public class CaroGameActivity extends BaseActivity<ActivityCaroGameBinding> {

    private MediaPlayer player;
    private boolean play_music;
    private Menu menuList;

    @Override
    protected boolean isHaveBackMenu() {
        return true;
    }

    @Override
    protected boolean isHaveRightMenu() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_caro_game;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.title_activity_caro_game);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeUi() {
        // Create a new MediaPlayer object and initialize it. We will then start playing the
        // background music when the activity resumes, and pause it when the activity pauses.
        player = MediaPlayer.create(this, R.raw.audio_caro_game);
        player.setLooping(true);
        play_music = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuList = menu;
        getMenuInflater().inflate(R.menu.menu_caro_game, menu);
        //default disable menu dual play
        menu.findItem(R.id.menu_play_with_com).setEnabled(false);
        if (play_music) {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_off_white_24dp);
        } else {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_up_white_24dp);
        }
        return isHaveRightMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_play_with_com:
                menuList.findItem(R.id.menu_play_with_com).setEnabled(false);
                menuList.findItem(R.id.menu_dual_play).setEnabled(true);
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_caroGameDualFragment_to_caroGameComvsHumanFragment);
                return true;
            case R.id.menu_dual_play:
                menuList.findItem(R.id.menu_play_with_com).setEnabled(true);
                menuList.findItem(R.id.menu_dual_play).setEnabled(false);
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_caroGameComvsHumanFragment_to_caroGameDualFragment);
                return true;
            case R.id.menu_information:
                DialogInstruction.newInstance(R.layout.dialog_instruction_caro).show(getSupportFragmentManager(),"caro_instruction");
                return true;
            case R.id.action_sound:
                if (play_music) {
                    player.pause();
                    play_music = false;
                    menuList.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_up_white_24dp);
                } else {
                    player.start();
                    play_music = true;
                    menuList.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_off_white_24dp);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if (play_music)
            player.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (play_music)
            player.start();
    }
}