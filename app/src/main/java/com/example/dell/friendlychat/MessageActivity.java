package com.example.dell.friendlychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    EditText et_message;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<FriendlyMessage> friendlyMessageArrayList;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    public static String toUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("message");

        recyclerView = findViewById(R.id.message_recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        friendlyMessageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(friendlyMessageArrayList);
        recyclerView.setAdapter(messageAdapter);
        et_message = findViewById(R.id.et_message);
        toUid = UserAdapter.sendinguid;
        String rUsername = UserAdapter.recieveUsername;
        Log.i("abra",toUid);
        setTitle(rUsername);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                Log.i("abra",friendlyMessage.getUid()+"   "+MainActivity.CURRENT_USER_ID+" xxxx  "+ friendlyMessage.getToUid()+"   "+toUid);
                if (((friendlyMessage.getUid().equals(MainActivity.CURRENT_USER_ID))&&(friendlyMessage.getToUid().equals(toUid)))||((friendlyMessage.getUid().equals(toUid))&&(friendlyMessage.getToUid().equals(MainActivity.CURRENT_USER_ID)))){
                    Log.i("abra",friendlyMessage.getText());
                    friendlyMessageArrayList.add(friendlyMessage);
                    messageAdapter = new MessageAdapter(friendlyMessageArrayList);
                    recyclerView.setAdapter(messageAdapter);}
                    linearLayoutManager.scrollToPosition(friendlyMessageArrayList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void sendOnClick(View view){
        FriendlyMessage friendlyMessage = new FriendlyMessage(et_message.getText().toString(),MainActivity.CURRENT_USER_ID, toUid);
        databaseReference.push().setValue(friendlyMessage);
        et_message.setText("");
    }
}
