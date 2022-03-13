package com.example.gamification;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private static String uid = null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Profile profile;
    private ArrayList<Profile> leaderboardObjects = new ArrayList<>();

    public FirebaseHelper() {
        //get a reference to or the instance of the auth and firestore elemnts
        // these lines of code establish the connections to the auth and database we are linked to
        // based on the json file
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
    public String getUid(){
        return uid;
    }

    public void updateUid(String uid) {
        FirebaseHelper.uid = uid;
    }

    public void updateCode(String code) {
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("code", code);
    }
    public void updateBoss(String bossUid){
        DocumentReference documentReference = db.collection(uid).document(uid);
        documentReference.update("bossUid", bossUid);
    }

    public void addUserToFirestore(String name, String level, String code, String newUID) {
        // this will add a document with the uid of the current user to the collection called "users"
        // for this we will create a hash map since there are only two fields - a name and the uid value

        // the docID of the document we are adding will be = to the uid of the current user
        // similar to how I said " we are making a new folder for this user"
        Map<String, Object> user = new HashMap<>();

        //put data into my object using a key value pair where I label each item I put in the Map
        // the key "name" is the key that is used to label the data in firestore
        user.put("name", name);
        user.put("level", level);
        user.put("code", code);
        if(level.equals("Employee")) {
            user.put("points", 0);
        }

        // this will create a new document in the collection "users" and assign it a docID that is = to newID
        db.collection(newUID).document(newUID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Pranav", name + "'s user account added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Pranav", "error adding user account", e);
                    }
                });
    }

    public void addCode(String code) {
        Map<String, Object> data = new HashMap<>();
        data.put("codeName", code);
        data.put("bossUID", uid);
        db.collection("codes").document(code).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Pranav", "code added to database: " + code);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Pranav", "error adding code", e);
                    }
                });
    }

    public void getData(FirestoreCallback firestoreCallback) {
        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getUid();
            db.collection(uid).document(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                int points;
                                String name = documentSnapshot.getString("name");
                                String level = documentSnapshot.getString("level");
                                String code = documentSnapshot.getString("code");
                                if(level.equals("Employee")) {
                                    Employee current = documentSnapshot.toObject(Employee.class);
                                    points = (int)(Math.floor(documentSnapshot.getDouble("points")));
                                    profile = new Profile(name, points, level, code);
                                } else {
                                    Boss currentBoss = documentSnapshot.toObject(Boss.class);
                                    profile = new Profile(name, level, code);
                                }


                                firestoreCallback.onCallback(profile);
                            }
                        }
                    });
        }
    }

    public void getCodes(CodesCallback codesCallback) {
        db.collection("codes").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    ArrayList<Code> codes = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                        codes.add(documentSnapshot.toObject(Code.class));
                    }
                    codesCallback.onCallback(codes);
                }
            });
    }

    public void addToBossArray(String bossUid, String currentUid){
        DocumentReference profileRef = db.collection(bossUid).document(bossUid);
        profileRef.update("employees", FieldValue.arrayUnion(currentUid));
    }

    public interface FirestoreCallback {
        void onCallback(Profile profile);
    }

    public interface CodesCallback {
        void onCallback(ArrayList<Code> codes);
    }

}
