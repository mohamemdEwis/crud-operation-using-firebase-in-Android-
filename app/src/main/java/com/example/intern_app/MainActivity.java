package com.example.intern_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText ed_user, ed_pass, ed_phone, ed_Email;
    Button btn_SeeAll, btn_SeeAll_user, btn_Add, btn_Edit, btn_Delete;
    RecyclerView rv_User;

    DatabaseReference databaseReference;

    List<User> listUsers = new ArrayList<>();

    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);
        ed_phone = findViewById(R.id.ed_phone);
        ed_Email = findViewById(R.id.ed_Email);
        btn_SeeAll = findViewById(R.id.btn_SeeAll);
        btn_SeeAll_user = findViewById(R.id.btn_SeeAll_user);
        btn_Add = findViewById(R.id.btn_Add);
        btn_Edit = findViewById(R.id.btn_Edit);
        btn_Delete = findViewById(R.id.btn_Delete);
        rv_User = findViewById(R.id.rv_User);
        rv_User.setLayoutManager(new GridLayoutManager(this, 1));


        databaseReference = FirebaseDatabase.getInstance().getReference();


        getUsers();

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_User();
            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_User();
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_user();
            }
        });

        btn_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsers();
            }
        });

        btn_SeeAll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
            }
        });
    }

    public void getUser() {
        listUsers.clear();
        String user = ed_user.getText().toString();

        Query query = databaseReference.child("User").orderByChild("user").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot object : dataSnapshot.getChildren()) {
                    listUsers.add(object.getValue(User.class));
                }

                adapter = new UserAdapter(MainActivity.this, listUsers);
                rv_User.setAdapter(adapter);

                clean_fields();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUsers() {
        listUsers.clear();
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot object : dataSnapshot.getChildren()) {
                    listUsers.add(object.getValue(User.class));
                }

                adapter = new UserAdapter(MainActivity.this, listUsers);
                rv_User.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clean_fields();
    }

    public void add_User() {
        listUsers.clear();
        User user = new User(
                ed_user.getText().toString(),
                ed_pass.getText().toString(),
                ed_phone.getText().toString(),
                ed_Email.getText().toString()
        );

        databaseReference.child("User").push().setValue(user,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(MainActivity.this, "Added User.", Toast.LENGTH_SHORT).show();
                    }
                });

        clean_fields();
    }

    public void Edit_User() {
        listUsers.clear();

        final User user = new User(
                ed_user.getText().toString(),
                ed_pass.getText().toString(),
                ed_phone.getText().toString(),
                ed_Email.getText().toString()
        );

        Query query = databaseReference.child("user").orderByChild("user").equalTo(user.getuser());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();
                }

                databaseReference.child("user").child(key).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clean_fields();
    }

    public void delete_user() {
        listUsers.clear();
        String user = ed_user.getText().toString();

        Query query = databaseReference.child("user").orderByChild("user").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot object : dataSnapshot.getChildren()) {
                    object.getRef().removeValue();
                }
                Toast.makeText(MainActivity.this, "User was deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clean_fields();
    }

    public void clean_fields() {
        ed_user.setText("");
        ed_pass.setText("");
        ed_phone.setText("");
        ed_Email.setText("");
    }
}