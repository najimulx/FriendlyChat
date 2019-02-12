package com.example.dell.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private RecyclerView chatRecyclerView;
    private UserAdapter mUserAdapter;
    ArrayList<FriendlyUser> friendlyUserArrayList;
    public static String CURRENT_USER_ID;

    private FirebaseDatabase mFirebaseDatabase;  //Create a instance of Firebase database
    private DatabaseReference mDatabaseReference;   // It is used to Refers to a particular part of Database
    private FirebaseAuth mFirebaseAuth;  // this object is used for authentication check
    private FirebaseAuth.AuthStateListener authStateListener; // It is used to listen to authentication state change i.e sign in or sign out

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatRecyclerView = findViewById(R.id.mRecyclerView);


        friendlyUserArrayList = new ArrayList<>();
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mUserAdapter = new UserAdapter(friendlyUserArrayList);
        chatRecyclerView.setAdapter(mUserAdapter);


        mFirebaseDatabase = FirebaseDatabase.getInstance(); 
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");  // Here we are refering to the "message" part of the database
        mFirebaseAuth = FirebaseAuth.getInstance();
        this.CURRENT_USER_ID = mFirebaseAuth.getUid();
        FirebaseUser checkLogIn = mFirebaseAuth.getCurrentUser();
        if(checkLogIn == null){
            Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
            startActivity(intent);
            finish();
        }

        // Read from the database
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyUser friendlyUser = dataSnapshot.getValue(FriendlyUser.class);
                if (!friendlyUser.getUid().equals(mFirebaseAuth.getUid())){
                friendlyUserArrayList.add(friendlyUser);
                Log.i("ABRA", friendlyUser.getUid() + "   "+ mFirebaseAuth.getUid());
                mUserAdapter = new UserAdapter(friendlyUserArrayList);
                chatRecyclerView.setAdapter(mUserAdapter);}

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

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // here we do something depending on whether user signed in or signed out
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    //Signed Out
                    Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //Signed In
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.signOutMennuButton:
                mFirebaseAuth.signOut();
                return true;
            default:
                return false;
        }
    }
    @Override
    protected void onResume() {
        //We Connect Auth listener when activity resume
        super.onResume();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        //We disconnect Auth listener when activity pause
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(authStateListener);
    }
}
