package binh.le.game.gameBasic.sudoku;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.base.DialogInstruction;
import binh.le.game.databinding.ActivitySudokuGameBinding;

public class SudokuGameActivity extends BaseActivity<ActivitySudokuGameBinding> {

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_information:
                DialogInstruction dialogInstruction = DialogInstruction.newInstance(R.layout.dialog_instruction_sudoku);
                dialogInstruction.show(getSupportFragmentManager(),"instruction");
                return true;
        }
        return super.onOptionsItemSelected(item);
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