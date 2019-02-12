package com.example.dell.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    ArrayList<FriendlyUser> friendlyUserArrayList;
    public static String sendinguid;
    public static String recieveUsername;
    public UserAdapter(ArrayList<FriendlyUser> friendlyUserArrayList) {
        this.friendlyUserArrayList = friendlyUserArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.mText.setText(friendlyUserArrayList.get(i).getName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = i;
                String intentSendToUid = friendlyUserArrayList.get(i).getUid();
                Log.i("abra",intentSendToUid);

                Intent intent = new Intent(v.getContext(),MessageActivity.class);
                v.getContext().startActivity(intent);
                sendinguid = intentSendToUid;
                recieveUsername = friendlyUserArrayList.get(i).getName();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendlyUserArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.messageTextView);
        }
    }


}
