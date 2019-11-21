package com.example.mooddiary;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();

    public static DocumentReference getUserMoodList() {
        return Database.db.collection("users")
                .document("users")
                .collection(LoginActivity.userName)
                .document("MoodList");
    }

    public static DocumentReference getUserFollowList() {
        return Database.db.collection("users")
                .document("users")
                .collection(LoginActivity.userName)
                .document("FollowList");
    }

    public static DocumentReference getUserFollowerList(){
        return Database.db.collection("users")
                .document("users")
                .collection(LoginActivity.userName)
                .document("FollowerList");
    }

    public static DocumentReference getFriendMoodList(String friendUsername) {
        return Database.db.collection("users")
                .document("users")
                .collection(friendUsername)
                .document("MoodList");
    }

    public static DocumentReference getRequestList(String docName) {
        return Database.db.collection("requests").document(docName);
    }
}
