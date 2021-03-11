package binh.le.game.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import binh.le.game.R;

public abstract class BaseFragment <BD extends ViewDataBinding> extends Fragment {

    protected BD binding;

    protected abstract int getLayoutID();

    protected abstract String getActionBarTitle();

    protected abstract void subscribeUi();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getActionBarTitle());
        subscribeUi();
    }
}
