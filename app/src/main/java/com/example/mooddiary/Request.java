package com.example.mooddiary;

import com.google.firebase.firestore.DocumentReference;

/**
 * This is a class to store a Request object
 */
public class Request {

    private String sender;
    private String receiver;
    private boolean confirmed;

    public Request(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.confirmed = false;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void confirmRequest() {
        this.confirmed = true;
    }

    /**
     * This sends a follow request to FireBase
     * @param request
     *      This is the request to send
     * @param docRef
     *      This is the document reference in the FireBase for request list
     */
    public static void send(Request request, DocumentReference docRef) {
        docRef.set(request);
    }
}
