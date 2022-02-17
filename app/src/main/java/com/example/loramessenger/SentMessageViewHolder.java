package com.example.loramessenger;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loramessenger.R;

public class SentMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewMessage;
    private TextView textViewDelivered;

    public SentMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewMessage = itemView.findViewById(R.id.textViewMessage);
        textViewDelivered = itemView.findViewById(R.id.textViewDelivered);
    }

    public TextView getTextViewMessage() {
        return textViewMessage;
    }

    public TextView getTextViewDelivered() {
        return textViewDelivered;
    }
}
