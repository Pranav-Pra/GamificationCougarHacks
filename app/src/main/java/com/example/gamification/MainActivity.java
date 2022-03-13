package com.example.gamification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IFRA";
    public static FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseHelper = new FirebaseHelper();

        Spinner spinnerSignIn = (Spinner) findViewById(R.id.signInSpinner);
        ArrayAdapter<CharSequence> adapterSignIn = ArrayAdapter.createFromResource(this,
                R.array.bossOrEmployee, android.R.layout.simple_spinner_item);
        //https://developer.android.com/guide/topics/ui/controls/spinner
        adapterSignIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSignIn.setAdapter(adapterSignIn);

        Spinner spinnerSignUp = (Spinner) findViewById(R.id.signUpSpinner);
        ArrayAdapter<CharSequence> adaptersignUp = ArrayAdapter.createFromResource(this,
                R.array.bossOrEmployee, android.R.layout.simple_spinner_item);
        //https://developer.android.com/guide/topics/ui/controls/spinner
        adaptersignUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSignIn.setAdapter(adaptersignUp);
    }

    public void signIn(View v) {
        // Note we don't care what they entered for name here
        // it could be blank

        EditText emailSignIn = findViewById(R.id.emailSignInET);
        EditText passSignIn = findViewById(R.id.passSignInET);

        // Get user data
        String email = emailSignIn.getText().toString();
        String password = passSignIn.getText().toString();

        // verify all user data is entered
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        } else {
            // code to sign in user
            firebaseHelper.getmAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                // updating MY var for uid of current user
                                firebaseHelper.updateUid(firebaseHelper.getmAuth().getCurrentUser().getUid());

                                Log.i(TAG, email + " is signed in");

                                // this will help us with the asynch method calls
                                firebaseHelper.attachReadDataToUser();

                                // we can do any other UI updating or change screens based on how our app should respond
                                updateIfLoggedIn();

                                emailET.setText("");
                                passwordET.setText("");

                                // this is another way to create the intent from inside the OnCompleteListener
                                // Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                                // startActivity(intent);
                            } else {
                                // sign in failed
                                Log.d(TAG, email + " failed to log in");
                            }
                        }
                    });
        }
    }

    public void signUp(View v) {
        // Make references to EditText in xml

        EditText nameSignUp = findViewById(R.id.nameET);
        EditText emailSignUp = findViewById(R.id.emailSignUpET);
        EditText passSignUp = findViewById(R.id.passSignUpET);
        EditText confPassSignUp = findViewById(R.id.confPassSignUpET);

        // Get user data
        String name = nameSignUp.getText().toString();
        String email =  emailSignUp.getText().toString();
        String password = passSignUp.getText().toString();
        String confPassword = confPassSignUp.getText().toString();

        // verify all user data is entered
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || confPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        } else {
            // code to sign up user
            firebaseHelper.getmAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // user account was created in firebase auth
                                Log.i(TAG, email + " account created");

                                FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();

                                // update the FirebaseHelper var uid to equal the uid of currently signed in user
                                firebaseHelper.updateUid(user.getUid());

                                // add a document to our database to represent this user
                                //TODO: update the level string
                                firebaseHelper.addUserToFirestore(name, "",user.getUid());

                                // let's further investigate why this method call is needed
                                firebaseHelper.attachReadDataToUser();

                                updateIfLoggedIn();

                                nameET.setText("");
                                emailET.setText("");
                                passwordET.setText("");

                                // choose whatever actions you want - update UI, switch to a new screen, etc.
                                // take the user to the screen where they can enter wish list items
                                // getApplicationContext() will get the Activity we are currently in, that is sending the intent
                                // Similar to how we have said "this" in the past
                                // Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                                // startActivity(intent);
                            } else {
                                // user WASN'T created
                                Log.d(TAG, email + " sign up failed");
                            }
                        }
                    });
        }
    }
}