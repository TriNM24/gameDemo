package binh.le.game.gameBasic.topPlayer.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import binh.le.game.R;
import binh.le.game.base.BaseFragment;
import binh.le.game.databinding.FragmentTopPlayerBinding;
import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.gameBasic.topPlayer.fragment.adapter.TopPlayerAdapter;


public class PlaceholderFragment extends BaseFragment<FragmentTopPlayerBinding> {

    private static final String ARG_SECTION_NUMBER = "section_number";

    TopPlayerAdapter mAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_top_player;
    }

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected void subscribeUi() {

        int game = getArguments().getInt(ARG_SECTION_NUMBER,1);
        binding.listItem.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listItem.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new TopPlayerAdapter(getContext(),game);
        binding.listItem.setAdapter(mAdapter);

        FirebaseHelper.getInstance().getUserDao().getTopUsers(game)
        .observe(getViewLifecycleOwner(), users -> {
            mAdapter.updateData(users);
            mAdapter.notifyDataSetChanged();
        });



    }
}