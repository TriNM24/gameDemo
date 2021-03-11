package binh.le.game.caroGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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