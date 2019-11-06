package com.example.mooddiary;

public class Request {

    private String sender;
    private String receiver;
    private boolean confirmed;
    private boolean agreed;

    public Request(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.confirmed = false;
        this.agreed = false;
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

    public boolean isAgreed() {
        return agreed;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setIsConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setIsAgreed(boolean agreed) {
        this.agreed = agreed;
    }
}
