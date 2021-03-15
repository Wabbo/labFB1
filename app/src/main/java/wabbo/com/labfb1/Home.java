package wabbo.com.labfb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseUser currentUser;
    List<MessageModel> list;
    MessageModelAdapter adapter;
    Button logout, send;
    EditText message;
    String email;
    FirebaseAuth mAuth;
    private static final String TAG = "token = ";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        String msg = token;
                        Log.i(TAG, msg);
                        Toast.makeText(Home.this, msg, Toast.LENGTH_SHORT).show();
                        // cqzrXAenTlGXzQbiaYfhb0:APA91bF8VfaNKksN3fcbzEKZjMpa9iUWOw9XFpoUNtRhBCYcEGZUlKM5061rhL6yBm3isLbdghkhxZVJz1SJfpLVj5NwIkilQUxsC_JiIpy2RmP9fYgiEzsK7_pZJpaZuDVoKY9QiS8g
                    }
                });

        mAuth = FirebaseAuth.getInstance();
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        logout = findViewById(R.id.logout);


        send.setOnClickListener(v -> {
            MessageModel model = new MessageModel(message.getText().toString(), email);
            myRef.child(currentUser.getUid()).child(myRef.push().getKey()).setValue(model);
            //myRef.child("vkSPQNAsQnUJJo77ksonIMScn7J3").child(myRef.push().getKey()).setValue(model);
            message.setText("");
        });

        logout.setOnClickListener(v -> {
            signOut();
            finish();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i("TAG", " one is login");
            email = currentUser.getEmail();

            myRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                // myRef.child("vkSPQNAsQnUJJo77ksonIMScn7J3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MessageModel model = dataSnapshot.getValue(MessageModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Log.i("size", "onStart: " + list.size());
            adapter = new MessageModelAdapter(Home.this, list);
            recyclerView.setAdapter(adapter);
        }
    }

    public void signOut() {
        // [START auth_fui_signout]
        mAuth.getInstance().signOut();
        // [END auth_fui_signout]
    }
}