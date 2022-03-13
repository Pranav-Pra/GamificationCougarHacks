package com.example.gamification;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private static String uid = null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Profile profile;
    private ArrayList<Employee> leaderboardObjects = new ArrayList<>();
    private ArrayList<Task> employeeTasks = new ArrayList<>();

    public static Boss currentBoss;
    public static Employee currentEmployee;

    public FirebaseHelper() {
        //get a reference to or the instance of the auth and firestore elemnts
        // these lines of code establish the connections to the auth and database we are linked to
        // based on the json file
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    public FirebaseHelper(String bossUid) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear out the array list so that none of the events are duplicated in the display
                leaderboardObjects.clear();
                Log.i("andrew", "change made to data");
                // this for each loop will get each Document Snapshot from the query, and one at a time,
                // convert them to an object of the Event class and then add them to the array list

                for (QueryDocumentSnapshot doc : value) {
                    Profile profile = doc.toObject(Profile.class);
                    if(profile.getBossUid().equals(bossUid)){
                        Employee currentLeader = new Employee(profile.getName(), profile.getLevel(),
                                profile.getPoints(), profile.getCode(), profile.getBossUid());
                        leaderboardObjects.add(currentLeader);
                    }

                }
            }
        });
    }
    public ArrayList<Employee> getLeaderboardObjects(){
        return leaderboardObjects;
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
        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.update("code", code);
    }
    public void updateBoss(String bossUid){
        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.update("bossUid", bossUid);
    }

    public void addTask(String userId, Task t) {
        db.collection("users").document(userId).collection("tasks").document(t.getInstructions()).set(t)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

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
        ArrayList<String> employees = new ArrayList<>();
        user.put("level", level);
        user.put("code", code);
        if(level.equals("Employee")) {
            user.put("points", 0);
        }
        else{
            user.put("employees", employees);
        }

        // this will create a new document in the collection "users" and assign it a docID that is = to newID
        db.collection("users").document(newUID).set(user)
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

    public void getTasks(TasksCallback tasksCallback) {
        db.collection("users").document(uid).collection("tasks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                // convert the snapshot into a WishListItem object
                                Task w = doc.toObject(Task.class);
                                employeeTasks.add(w);
                            }

                            // I am done getting all the data
                            Log.i("LFRA", "Success reading all data: " + employeeTasks.toString());
                            tasksCallback.onCallback(employeeTasks);
                        } else {
                            Log.d("LFRA", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    public void getData(FirestoreCallback firestoreCallback) {
        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getUid();
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                int points;
                                String name = documentSnapshot.getString("name");
                                String level = documentSnapshot.getString("level");
                                String code = documentSnapshot.getString("code");

                                if(level.equals("Employee")) {
                                    currentEmployee = documentSnapshot.toObject(Employee.class);
                                    points = (int)(Math.floor(documentSnapshot.getDouble("points")));
                                    profile = new Profile(name, points, level, code);
                                    profile = documentSnapshot.toObject(Profile.class);

                                } else {
                                    ArrayList<String> AL = (ArrayList<String>) documentSnapshot.get("employees");
                                    ArrayList<String> AL2 = (ArrayList<String>) documentSnapshot.get("employeeNames");
                                    currentBoss = new Boss(name, code, level, AL, AL2);
                                    profile = documentSnapshot.toObject(Profile.class);
                                }


                                firestoreCallback.onCallback(profile);
                            }
                        }
                    });
        }
    }

    public ArrayList<String> getUIDListBoss(){
        ArrayList<String> res = new ArrayList<>();
        getData(new FirebaseHelper.FirestoreCallback() {
            @Override
            public void onCallback(Profile profile) {
                    for (int i = 0; i < profile.getEmployees().size(); i++) {
                        res.add(profile.getEmployees().get(i));
                    }
                }

        });
        return res;
    }



    public void getCodes(CodesCallback codesCallback) {
        db.collection("codes").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    ArrayList<Code> codes = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                        Code current = new Code(documentSnapshot.getString("codeName"),documentSnapshot.getString("bossUID"));
                        codes.add(current);
                    }
                    codesCallback.onCallback(codes);
                }
            });
    }

    public void addToBossArray(String bossUid, String currentUid){
        DocumentReference profileRef = db.collection("users").document(bossUid);
        profileRef.update("employees", FieldValue.arrayUnion(currentUid));

    }

    public void addToBossNameArray(String bossUid, String currentName){
        DocumentReference profileRef = db.collection("users").document(bossUid);
        profileRef.update("employeeNames", FieldValue.arrayUnion(currentName));
    }

    public interface FirestoreCallback {
        void onCallback(Profile profile);
    }

    public interface CodesCallback {
        void onCallback(ArrayList<Code> codes);
    }

    public interface TasksCallback {
        void onCallback(ArrayList<Task> tasks);
    }
}
