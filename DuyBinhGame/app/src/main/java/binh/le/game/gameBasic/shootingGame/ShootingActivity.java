package binh.le.game.gameBasic.shootingGame;

import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.base.DialogInstruction;
import binh.le.game.databinding.ActivityShootTheGuyBinding;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.gameBasic.shootingGame.view.SoundEffects;
import binh.le.game.ultis.Utils;

public class ShootingActivity extends BaseActivity<ActivityShootTheGuyBinding> {


    private MediaPlayer player;
    private boolean play_music;
    Menu menu;

    @Override
    protected boolean isHaveRightMenu() {
        return true;
    }

    @Override
    protected boolean isHaveBackMenu() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shoot_the_guy;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.shooting_game_title);
    }

    @Override
    protected void subscribeUi() {
        binding.setAction(this);
        // Create a new MediaPlayer object and initialize it. We will then start playing the
        // background music when the activity resumes, and pause it when the activity pauses.
        player = MediaPlayer.create(this, R.raw.braincandy);
        player.setLooping(true);
        play_music = true;

        // Set the context fo the SoundEffects singleton class
        SoundEffects.INSTANCE.setContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shooting_game, menu);

        this.menu = menu;
        if (play_music) {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_off_white_24dp);
        } else {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_up_white_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_sound:
                if (play_music) {
                    player.pause();
                    play_music = false;
                    menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_up_white_24dp);
                } else {
                    player.start();
                    play_music = true;
                    menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_off_white_24dp);
                }
                break;
            case R.id.menu_information:
                DialogInstruction.newInstance(R.layout.dialog_instruction_shooting).show(getSupportFragmentManager(),"caro_instruction");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(isHaveBackMenu()) {
            if(!binding.drawView.isGameOver) {
                binding.drawView.stopGame();
                Utils.showConfirmDialog(ShootingActivity.this, getString(R.string.game4_name),
                        getString(R.string.alert_game_4_out, binding.drawView.score.getScore()), (dialog, which) -> {
                            if (which == AlertDialog.BUTTON_POSITIVE) {
                                dialog.dismiss();
                                binding.drawView.resumeGame();
                            } else {
                                FirebaseHelper.getInstance().getUserDao().updateGamePoint(4, binding.drawView.score.getScore());
                                finish();
                                overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_right_exit);
                            }
                        });
            }
        }
        return true;
    }

    public void gameOver(){
        FirebaseHelper.getInstance().getUserDao().updateGamePoint(4, binding.drawView.score.getScore());
        Utils.showAlertDialog(ShootingActivity.this, getString(R.string.game4_name),
                getString(R.string.alert_game_4_game_over, binding.drawView.score.getScore()));
    }

    @Override
    protected void onPause() {
        if (play_music)
            player.pause();
        binding.drawView.stopGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (play_music)
            player.start();
    }

    @Override
    protected void onDestroy() {
        player.stop();
        player.reset();
        player.release();
        player = null;
        play_music = false;
        super.onDestroy();
    }

    public void onClick(View v) {
        // Using the View's ID to distinguish which button was clicked
        switch (v.getId()) {
            case R.id.moveLeftButton:
                binding.drawView.moveCannonLeft();
                break;
            case R.id.moveRightButton:
                binding.drawView.moveCannonRight();
                break;
            case R.id.shootButton:
                binding.drawView.shootCannon();
                break;
            default:
                break;
        }

    }

}