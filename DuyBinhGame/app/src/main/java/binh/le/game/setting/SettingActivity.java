package binh.le.game.setting;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import binh.le.game.R;
import binh.le.game.base.BaseActivity;
import binh.le.game.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    private Menu menuList;

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
        return R.layout.activity_setting;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.setting_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuList = menu;
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        //default disable menu dual play
        menu.findItem(R.id.menu_setting_save).setEnabled(false);
        return isHaveRightMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting_edit:
                menuList.findItem(R.id.menu_setting_edit).setEnabled(false);
                menuList.findItem(R.id.menu_setting_save).setEnabled(true);
                enableEdit(true);
                return true;
            case R.id.menu_setting_save:
                menuList.findItem(R.id.menu_setting_edit).setEnabled(true);
                menuList.findItem(R.id.menu_setting_save).setEnabled(false);
                enableEdit(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enableEdit(boolean isEnableEdit){
        binding.imgEdit1.setVisibility(isEnableEdit?View.VISIBLE:View.INVISIBLE);
        binding.imgEdit2.setVisibility(isEnableEdit?View.VISIBLE:View.INVISIBLE);
        binding.txtEditPass.setVisibility(isEnableEdit?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    protected void subscribeUi() {

    }

    public void editImageProfile(){

    }
}