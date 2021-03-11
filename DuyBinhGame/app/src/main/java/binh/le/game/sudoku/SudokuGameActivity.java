package binh.le.game.sudoku;

import android.os.Bundle;

import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivitySudokuGameBinding;

public class SudokuGameActivity extends BaseActivity<ActivitySudokuGameBinding> {

    @Override
    protected boolean isHaveRightMenu() {
        return false;
    }

    @Override
    protected boolean isHaveBackMenu() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_sudoku_game;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.sudoku_game_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeUi() {
        binding.setAction(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(isHaveBackMenu()) {
            NavDestination currentDestination = Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination();
            String className = ((FragmentNavigator.Destination) currentDestination).getClassName();
            if(className.equals(SudokuMenuFragment.class.getName())) {
                finish();
                overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_right_exit);
            }else {
                Navigation.findNavController(this,R.id.nav_host_fragment).popBackStack();
            }
        }
        return true;
    }
}