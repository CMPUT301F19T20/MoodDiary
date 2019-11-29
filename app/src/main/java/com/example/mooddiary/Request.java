package com.example.mooddiary;

/**
 * This is a class to store a Request object
 */
public class Request {

    private String sender;
    private String receiver;
    private boolean confirmed;
    private boolean declined;

    public Request() {
        this.sender = "";
        this.receiver = "";
        this.confirmed = false;
        this.declined = false;
    }

    public Request(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.confirmed = false;
        this.declined = false;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public boolean getDeclined() { return declined;}

    public void setDeclined(boolean declined) { this.declined = declined; }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setConfirmed(boolean confirmed) {this.confirmed = confirmed; }

    public void confirmRequest() {
        this.confirmed = true;
    }

    /**
     * This sends a follow request to FireBase
     * @param request
     *      This is the request to send
     */
    public static void send(Request request) {
        Database.getRequest(request.getSender()+request.getReceiver()).set(request);
    }
}
