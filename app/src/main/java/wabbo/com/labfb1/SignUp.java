package wabbo.com.labfb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    EditText email, password;
    Button signup;
    TextView login;
    private final String TAG = "FB_signup";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            finish();
        });
        signup.setOnClickListener(v -> {

            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            if (emailText.isEmpty()) {
                Log.i(TAG, "onCreate: no email");
                email.setError("enter email");
            }
            if (passwordText.isEmpty()) {
                Log.i(TAG, "onCreate: no password");
                password.setError("enter password");
            }
            if (!(emailText.isEmpty() || passwordText.isEmpty())) {
                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(SignUp.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    //FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i("TAG", "no one login");
        }
    }

}