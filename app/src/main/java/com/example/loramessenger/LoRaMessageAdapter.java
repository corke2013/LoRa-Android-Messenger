package com.example.loramessenger;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LoRaMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LoRaMessage> loRaMessageList;

    public LoRaMessageAdapter(List<LoRaMessage> loRaMessageList) {
        this.loRaMessageList = loRaMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (loRaMessageList.get(position).getMessageType() == LoRaMessageType.RECEIVED) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ReceivedMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message_line, parent, false));
        }
        return new SentMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            ((ReceivedMessageViewHolder) holder).getTextViewSender().setText(loRaMessageList.get(position).getSender());
            ((ReceivedMessageViewHolder) holder).getTextViewMessage().setText(loRaMessageList.get(position).getMessage());
        } else {
            ((SentMessageViewHolder) holder).getTextViewMessage().setText(loRaMessageList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return loRaMessageList.size();
    }
}
