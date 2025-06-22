package com.example.chatbuddy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class chatwin extends AppCompatActivity {

    String reciveruid,recivername,senderuid;
    TextView receivername;
    CardView sentbtn;
    EditText textmsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String senderroom,reciverroom;
    RecyclerView mmessangesAdapter;
    ArrayList<msgModelClass> messagesArrayList;
    messagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwin);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        recivername = getIntent().getStringExtra("nameee");
        reciveruid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sentbtn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);

        receivername = findViewById(R.id.recivername);

        mmessangesAdapter = findViewById(R.id.msgadpter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessangesAdapter.setLayoutManager(linearLayoutManager);
        messagesAdapter = new messagesAdapter(chatwin.this, messagesArrayList);
        mmessangesAdapter.setAdapter(messagesAdapter);

        receivername.setText(" "+recivername);

        senderuid = firebaseAuth.getUid();
        senderroom = senderuid+reciveruid;
        reciverroom = reciveruid+senderuid;


        //DatabaseReference reference =database.getReference().child("users").child(firebaseAuth.getUid());
        DatabaseReference chatreferance =
                database.getReference().child("chats").child(senderroom).child("message");


//        chatreferance.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messagesArrayList.clear();
//                for(DataSnapshot dataSnapshot:snapshot.getChildren())
//                {
//                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
//                    messagesArrayList.add(messages);
//                }
//                messagesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("FirebaseError", error.getMessage());
//            }
//        });
        chatreferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                Log.d("FirebaseSnapshot", "Data: " + snapshot.getValue());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
                    if (messages != null) {
                        Log.d("Message", "Retrieved message: " + messages.getMessage());
                        messagesArrayList.add(messages);
                    } else {
                        Log.e("MessageError", "Null message object retrieved");
                    }
                }
                Log.d("MessageList", "ArrayList size: " + messagesArrayList.size());
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        sentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textmsg.getText().toString();
                if(message.isEmpty())
                {
                    Toast.makeText(chatwin.this, "Enter the Message First", Toast.LENGTH_SHORT).show();
                    return;
                }
                textmsg.setText("");
                Date date = new Date();
                msgModelClass messagess = new msgModelClass(message,senderuid,date.getTime());

                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child(senderroom).child("message").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child(reciverroom).child(
                                "message").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });
    }
}