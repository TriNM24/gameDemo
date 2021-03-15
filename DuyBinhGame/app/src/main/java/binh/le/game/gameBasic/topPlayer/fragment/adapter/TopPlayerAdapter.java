package binh.le.game.gameBasic.topPlayer.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binh.le.game.R;

public class TopPlayerAdapter extends RecyclerView.Adapter<TopPlayerAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    public TopPlayerAdapter(Context context, List<String> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_top_player_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_user_mail.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_mail, txt_score;

        ViewHolder(View itemView) {
            super(itemView);
            txt_user_mail = itemView.findViewById(R.id.txt_user_mail);
            txt_score = itemView.findViewById(R.id.txt_score);
        }
    }
}
