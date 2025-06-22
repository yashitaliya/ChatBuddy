package com.example.chatbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class signup extends AppCompatActivity {

    EditText  username,email,password,repassword;
    TextView login;

    FirebaseAuth auth;
    Button signup;
    //ImageView profile;
    //Uri imageUri;
    //String imageuri;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing the account");
        progressDialog.setCancelable(false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        //profile = findViewById(R.id.profile);
        username = findViewById(R.id.username);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        login = findViewById(R.id.login_link);
        signup = findViewById(R.id.signupbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee = username.getText().toString();
                String emaill = email.getText().toString();
                String passwordd = password.getText().toString();
                String repasswordd = repassword.getText().toString();
                String status = "Hey I'm Using this Application";

                if(TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) || TextUtils.isEmpty(passwordd) || TextUtils.isEmpty(repasswordd))
                {
                    progressDialog.dismiss();
                    Toast.makeText(signup.this, "Please Enter Valid Information",
                            Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailpattern)) {
                             progressDialog.dismiss();
                             email.setError("Enter Valid Email ");
                } else if (password.length()<6) {
                             progressDialog.dismiss();
                            password.setError("Enter upto 6 Character");
                } else if (!passwordd.equals(repasswordd)) {
                            progressDialog.dismiss();
                            password.setError("Password doesn't Match");
                }
                else {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(emaill, passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()) {
                                String userid = task.getResult().getUser().getUid();
                                DatabaseReference userref= database.getReference().child("users").child(userid);

                                Users user = new Users(userid, namee, emaill,passwordd, status);

                                userref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.show();
                                            Intent intent = new Intent(signup.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(signup.this, "Error in Creating user !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                               /* String id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                StorageReference storageReference = storage.getReference().child(
                                        "Upload").child(id); */

                                /*if (imageUri != null) {
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                            if (task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(id, namee, emaill,
                                                                passwordd, imageuri,
                                                                status);

                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressDialog.show();
                                                                    Intent intent = new Intent(signup.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(signup.this, "Error in Creating user !", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    String status = "Hey I'm Using This Application";
                                    Users users = new Users(id, namee, emaill, passwordd, imageuri,
                                            status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.show();
                                                Intent intent = new Intent(signup.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(signup.this, "Error in creating " +
                                                        "the user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }*/
                            }else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(signup.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    });
                }
            }
        });

    /*    profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select picture"), 10);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null)
            {
                    imageUri=data.getData();
                    profile.setImageURI(imageUri);
            }
        }

     */

    }
}