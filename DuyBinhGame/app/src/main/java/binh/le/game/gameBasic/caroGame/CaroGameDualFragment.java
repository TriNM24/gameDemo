package binh.le.game.gameBasic.caroGame;

import binh.le.game.R;
import binh.le.game.base.BaseFragment;
import binh.le.game.databinding.FragmentCaroGameDualBinding;

public class CaroGameDualFragment extends BaseFragment<FragmentCaroGameDualBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_caro_game_dual;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.caro_game_dual_fragment_label);
    }

    @Override
    protected void subscribeUi() {

    }
}