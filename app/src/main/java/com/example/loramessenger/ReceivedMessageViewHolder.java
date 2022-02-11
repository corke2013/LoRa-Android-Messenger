package com.example.loramessenger;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loramessenger.R;

public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewSender;
    private TextView textViewMessage;

    public ReceivedMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewSender = itemView.findViewById(R.id.textViewSender);
        textViewMessage = itemView.findViewById(R.id.textViewMessage);
    }

    public TextView getTextViewSender() {
        return textViewSender;
    }

    public TextView getTextViewMessage() {
        return textViewMessage;
    }
}
