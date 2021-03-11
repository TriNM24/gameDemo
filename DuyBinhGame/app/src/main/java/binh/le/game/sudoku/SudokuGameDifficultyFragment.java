package binh.le.game.sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import binh.le.game.R;
import binh.le.game.base.BaseFragment;
import binh.le.game.databinding.FragmentSudokuDifficultyBinding;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class SudokuGameDifficultyFragment extends BaseFragment<FragmentSudokuDifficultyBinding> {

    private boolean newBoard = false;
    private int selectedDifficulty = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_sudoku_difficulty;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.sudoku_game_title);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeUi() {
        binding.setAction(this);
    }

    public void onDifficultyRadioButtonsClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonEasy:
                if (checked) {
                    selectedDifficulty = 0;
                }
                break;
            case R.id.radioButtonNormal:
                if (checked) {
                    selectedDifficulty = 1;
                }
                break;
            case R.id.radioButtonHard:
                if (checked) {
                    selectedDifficulty = 2;
                }
                break;
        }
    }

    public void onStartGameButtonClicked(View view) {
        if (newBoard) {
            /*Intent intent = new Intent();
            intent.putExtra("boardSaved", true);
            intent.putExtra("difficulty", selectedDifficulty);
            setResult(RESULT_OK, intent);
            finish();*/
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("difficulty", selectedDifficulty);
            findNavController(this).navigate(R.id.action_sudokuGameDifficultyFragment_to_sudokuGamePlayFragment,bundle);
        }
    }

}