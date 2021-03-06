package com.example.minigames.gameBasic.sudoku;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import com.example.minigames.R;
import com.example.minigames.base.BaseActivity;
import com.example.minigames.base.DialogInstruction;
import com.example.minigames.databinding.ActivitySudokuGameBinding;

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