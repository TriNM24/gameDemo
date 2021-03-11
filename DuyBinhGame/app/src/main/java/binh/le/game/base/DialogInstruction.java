package binh.le.game.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import binh.le.game.R;

public class DialogInstruction extends DialogFragment {

    public static String TAG_LAYOUT_ID = "layout_id";

    public static DialogInstruction newInstance(int layoutID) {
        Bundle args = new Bundle();
        args.putInt(TAG_LAYOUT_ID, layoutID);
        DialogInstruction fragment = new DialogInstruction();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getArguments().getInt(TAG_LAYOUT_ID, R.layout.dialog_memory_game_instruction);
        View root = inflater.inflate(layoutId, null);
        return root;
    }
}
