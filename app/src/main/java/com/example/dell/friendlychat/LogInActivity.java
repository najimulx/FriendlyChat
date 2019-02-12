package com.example.dell.friendlychat;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText et_email, et_pass;
    String email,password;
    Dialog dialog;
    private FirebaseDatabase mFirebaseDatabase;  //Create a instance of Firebase database
    private DatabaseReference mDatabaseReference;   // It is used to Refers to a particular part of Database
    int UserCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        dialog = new Dialog(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");
        Query query = mDatabaseReference.orderByChild("name");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserCount =(int) dataSnapshot.getChildrenCount();
                Log.d("INFOuser", Integer.toString(UserCount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(eventListener);

    }




    public void signInOnClick(View view){
        email = et_email.getText().toString();
        password = et_pass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this,"Try Again",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void signUpOnClick(View view){
        final EditText name,email,password;
        dialog.setContentView(R.layout.custom_popup);
        Button signUp = dialog.findViewById(R.id.bt_popup_signup);
        name = dialog.findViewById(R.id.et_signup_name);
        email = dialog.findViewById(R.id.et_signup_email);
        password = dialog.findViewById(R.id.et_signup_password);
        Log.i("INFOUser",Integer.toString(UserCount)+ "   second");
        if(UserCount <2){
            dialog.show();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FriendlyUser friendlyUser = new FriendlyUser(mAuth.getUid(),name.getText().toString());
                                    mDatabaseReference.push().setValue(friendlyUser);
                                    dialog.dismiss();
                                } else {
                                    // If sign in fails, display a message to the user.

                                }
                            }
                        });
            }
        });

        }else{
            dialog.dismiss();
            Toast.makeText(LogInActivity.this,"LiMIT TWO USER",Toast.LENGTH_LONG).show();
        }
    }

}
