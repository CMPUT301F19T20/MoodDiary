package com.example.mooddiary;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();

    public static DocumentReference getUserMoodList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("MoodList");
    }

    public static DocumentReference getUserPassword(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("Password");
    }

    public static DocumentReference getUserFollowList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("FollowList");
    }

    public static DocumentReference getUserFollowerList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("FollowerList");
    }

    public static Query getRequestListByReceiver(String receiverName) {
        return Database.db.collection("requests").whereEqualTo("receiver", receiverName);
    }

    public static DocumentReference getRequest(String docName) {
        return Database.db.collection("requests").document(docName);
    }


}
