package com.example.chatbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainuserRecycleview;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
         auth=FirebaseAuth.getInstance() ;

         DatabaseReference reference = database.getReference().child("users");

         usersArrayList = new ArrayList<>();

         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot dataSnapshot: snapshot.getChildren())
                 {
                     Users users = dataSnapshot.getValue(Users.class);
                     usersArrayList.add(users);
                 }
                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

         logout = findViewById(R.id.logout);

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Dialog dialog = new Dialog(MainActivity.this,R.style.dialoge);
                 dialog.setContentView(R.layout.dialoge_layout);
                 Button yes,no;
                 yes = dialog.findViewById(R.id.yesbnt);
                 no = dialog.findViewById(R.id.nobnt);

                 yes.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         FirebaseAuth.getInstance().signOut();
                         Intent intent = new Intent(MainActivity.this, login.class);
                         startActivity(intent);
                         finish();
                     }
                 });

                 no.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();
                     }
                 });
                 dialog.show();
             }
         });

         mainuserRecycleview = findViewById(R.id.recyclerView3);
         mainuserRecycleview.setLayoutManager(new LinearLayoutManager(this));
         adapter = new UserAdapter(MainActivity.this,usersArrayList);
         mainuserRecycleview.setAdapter(adapter);

                        if(auth.getCurrentUser() == null)
                        {
                            Intent intent=new Intent(MainActivity.this, login.class);
                            startActivity(intent);

                        }
    }
}