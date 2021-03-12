package binh.le.game.gameBasic.caroGame;

import binh.le.game.R;
import binh.le.game.base.BaseFragment;
import binh.le.game.databinding.FragmentCaroGameComVsHumBinding;

public class CaroGameComvsHumanFragment extends BaseFragment<FragmentCaroGameComVsHumBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_caro_game_com_vs_hum;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.caro_game_com_vs_human_fragment_label);
    }

    @Override
    protected void subscribeUi() {

    }
}