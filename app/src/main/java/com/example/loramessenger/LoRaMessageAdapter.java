package com.example.loramessenger;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loramessenger.messages.LoRaMessageType;
import com.example.loramessenger.messages.LoRaTextMessage;

import java.util.List;

public class LoRaMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LoRaTextMessage> loRaTextMessageList;

    public LoRaMessageAdapter(List<LoRaTextMessage> loRaTextMessageList) {
        this.loRaTextMessageList = loRaTextMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (loRaTextMessageList.get(position).getMessageType() == LoRaMessageType.RECEIVED) {
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
            ((ReceivedMessageViewHolder) holder).getTextViewSender().setText(loRaTextMessageList.get(position).getSender());
            ((ReceivedMessageViewHolder) holder).getTextViewMessage().setText(loRaTextMessageList.get(position).getMessage());
        } else {
            ((SentMessageViewHolder) holder).getTextViewMessage().setText(loRaTextMessageList.get(position).getMessage());
            if (loRaTextMessageList.get(position).isDelivered()){
                ((SentMessageViewHolder) holder).getTextViewDelivered().setText("Delivered");
            }
            else {
                ((SentMessageViewHolder) holder).getTextViewDelivered().setText("Sent");
            }
        }
    }

    @Override
    public int getItemCount() {
        return loRaTextMessageList.size();
    }
}
