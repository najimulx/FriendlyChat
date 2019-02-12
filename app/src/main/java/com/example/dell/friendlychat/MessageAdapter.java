package com.example.dell.friendlychat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<FriendlyMessage> friendlyMessageArrayList;
    final int RMSG = 0,SMSG = 1;
    public MessageAdapter(ArrayList<FriendlyMessage> friendlyMessagesArrayList) {
        this.friendlyMessageArrayList = friendlyMessagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final RecyclerView.ViewHolder holder;
        View view;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (i){
            case RMSG:
                view = inflater.inflate(R.layout.recieve_msg,viewGroup,false);
                holder = new RecieveViewHolder(view);
                break;
            case SMSG:
                view = inflater.inflate(R.layout.my_message,viewGroup,false);
                holder = new MyViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.my_message,viewGroup,false);
                holder = new MyViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder){
            ((MyViewHolder)viewHolder).mText.setText(friendlyMessageArrayList.get(i).getText());
        }
        else{
            ((RecieveViewHolder)viewHolder).rText.setText(friendlyMessageArrayList.get(i).getText());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(friendlyMessageArrayList.get(position).getUid().equals(MainActivity.CURRENT_USER_ID) ){
            return SMSG;
        }
        else{
            return RMSG;
        }
    }

    @Override
    public int getItemCount() {
        return friendlyMessageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.myMsgTextView);
        }
    }
    public class RecieveViewHolder extends RecyclerView.ViewHolder {

        TextView rText;

        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);
            rText = itemView.findViewById(R.id.recieveTextView);
        }
    }
}
