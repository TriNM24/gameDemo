package binh.le.game.gameBasic.topPlayer;

import android.os.Bundle;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivityTopPlayerBinding;
import binh.le.game.gameBasic.topPlayer.fragment.SectionsPagerAdapter;

public class TopPlayerActivity extends BaseActivity<ActivityTopPlayerBinding> {

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
        return R.layout.activity_top_player;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.title_activity_top_player);
    }

    @Override
    protected void subscribeUi() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}