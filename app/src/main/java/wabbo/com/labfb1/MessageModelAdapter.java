package wabbo.com.labfb1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MessageModelAdapter extends RecyclerView.Adapter<MessageModelAdapter.ViewHolder> {
    private final Context context;
    public List<MessageModel> models;
    private static final String TAG = "RecyclerView";

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView body;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            body = itemView.findViewById(R.id.body);
        }
    }

    MessageModelAdapter(Context context, List<MessageModel> models) {
        this.context = context;
        this.models = models;
    }


    @NonNull
    @Override
    public MessageModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.message_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.body.setText(models.get(position).getBody());

        Log.i(TAG, "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}

