package binh.le.game.caroGame;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivityCaroGameBinding;

public class CaroGameActivity extends BaseActivity<ActivityCaroGameBinding> {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuList = menu;
        getMenuInflater().inflate(R.menu.menu_caro_game, menu);
        //default disable menu dual play
        menu.findItem(R.id.menu_play_with_com).setEnabled(false);
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
        }
        return super.onOptionsItemSelected(item);
    }
}