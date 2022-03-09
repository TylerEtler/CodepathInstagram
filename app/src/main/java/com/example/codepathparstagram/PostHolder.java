package com.example.codepathparstagram;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class PostHolder extends RecyclerView.ViewHolder {

    TextView tvBody;
    TextView tvUsername;
    ImageView ivImage;
    RelativeLayout container;
    TextView tvTimestamp;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        ivImage = itemView.findViewById(R.id.ivImage);
        container = itemView.findViewById(R.id.item_post);
        tvBody = itemView.findViewById(R.id.tvBody);
        tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
    }
}
