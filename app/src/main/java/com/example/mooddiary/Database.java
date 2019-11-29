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

    /**
     * This is to get userMoodList
     * @param userName This is user name
     * @return         Return usermoodlist
     */

    public static DocumentReference getUserMoodList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("MoodList");
    }

    /**
     * This is to get user passoword
     * @param userName  This is user name
     * @return          Return password
     */
    public static DocumentReference getUserPassword(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("Password");
    }

    /**
     * This is to get user follow list
     * @param userName User name
     * @return return userfollowlist
     */

    public static DocumentReference getUserFollowList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("FollowList");
    }

    /**
     * This is to get follower list
     * @param userName this is user name
     * @return return get user follower list
     */

    public static DocumentReference getUserFollowerList(String userName) {
        return Database.db.collection("users")
                .document("users")
                .collection(userName)
                .document("FollowerList");
    }

    /**
     * This is get request list by receiver
     * @param receiverName receiver name
     * @return              return request list by receiver
     */

    public static Query getRequestListByReceiver(String receiverName) {
        return Database.db.collection("requests").whereEqualTo("receiver", receiverName);
    }

    /**
     * This is get request
     * @param docName document name
     * @return return request
     */

    public static DocumentReference getRequest(String docName) {
        return Database.db.collection("requests").document(docName);
    }


}
