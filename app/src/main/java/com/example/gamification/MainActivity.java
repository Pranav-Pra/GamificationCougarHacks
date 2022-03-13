package com.example.gamification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IFRA";
    public static FirebaseHelper firebaseHelper;
    private EditText nameET, emailSignUpET, passSignUpET, confPassSignUpET, emailSignInET, passSignInET;
    private Spinner signUpSpinner;
    private Profile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseHelper = new FirebaseHelper();
    }

    public void goToSignUp(View v) {
        setContentView(R.layout.sign_up);

        signUpSpinner = (Spinner) findViewById(R.id.signUpSpinner);

        ArrayAdapter<CharSequence> adaptersignUp = ArrayAdapter.createFromResource(this,
                R.array.bossOrEmployee, android.R.layout.simple_spinner_item);

        //https://developer.android.com/guide/topics/ui/controls/spinner
        adaptersignUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        signUpSpinner.setAdapter(adaptersignUp);
    }

    public void goToSignIn(View v) {
        setContentView(R.layout.sign_in);
    }

    public void signIn(View v) {
        // Note we don't care what they entered for name here
        // it could be blank

        emailSignInET = findViewById(R.id.emailSignInET);
        passSignInET = findViewById(R.id.passSignInET);

        // Get user data
        String email = emailSignInET.getText().toString();
        String password = passSignInET.getText().toString();

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
                                // TODO: firebaseHelper.attachReadDataToUser();

                                //TODO: we can do any other UI updating or change screens based on how our app should respond
                                // grab firebase data, if they are employee display points, if they are boss display code
                                updateIfLoggedIn();

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

        nameET = findViewById(R.id.nameET);
        emailSignUpET = findViewById(R.id.emailSignUpET);
        passSignUpET = findViewById(R.id.passSignUpET);
        confPassSignUpET = findViewById(R.id.confPassSignUpET);
        signUpSpinner = findViewById(R.id.signUpSpinner);

        // Get user data
        String name = nameET.getText().toString();
        String email =  emailSignUpET.getText().toString();
        String password = passSignUpET.getText().toString();
        String confPassword = confPassSignUpET.getText().toString();
        String level = signUpSpinner.getSelectedItem().toString();

        // verify all user data is entered
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || confPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 char long", Toast.LENGTH_SHORT).show();
        }
        // verify that password match
        else if (!(password.equals(confPassword))){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
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
                                if(level.equals("Boss")) {
                                    firebaseHelper.addUserToFirestore(name, level,generateRandomCode(), user.getUid());
                                } else {
                                    firebaseHelper.addUserToFirestore(name, level,"", user.getUid());
                                }

                                // let's further investigate why this method call is needed
                                // TODO: firebaseHelper.attachReadDataToUser();

                                updateIfLoggedIn();

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

    public void updateIfLoggedIn() {
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();

        if(user != null) {
            firebaseHelper.getData(new FirebaseHelper.FirestoreCallback() {
                @Override
                public void onCallback(Profile profile) {
                    if(profile.getLevel().equals("Employee")) {
                        userProfile = new Profile(profile.getName(), profile.getPoints(), profile.getLevel(), profile.getCode());
                    } else {

                        userProfile = new Profile(profile.getName(), profile.getLevel(), profile.getCode());
                    }

                    if(userProfile.getLevel().equals("Employee") && userProfile.getCode().equals("")) {
                        Intent intent = new Intent(getApplicationContext(), EmployeeJoinCode.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                        intent.putExtra("name", userProfile.getName());
                        intent.putExtra("level", userProfile.getLevel());
                        intent.putExtra("code", userProfile.getCode());
                        if (userProfile.getLevel().equals("Employee")) {
                            intent.putExtra("points", userProfile.getPoints());
                        }
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public Profile getUserData() {
        return userProfile;
    }

    private String generateRandomCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        String code = "";
        for(int i = 0; i < 8; i++) {
            int randNum = (int)(Math.random() * 36);
            code += characters.charAt(randNum);
        }
        return code;
    }
}