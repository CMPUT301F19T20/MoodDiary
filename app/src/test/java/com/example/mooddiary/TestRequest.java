package com.example.mooddiary;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRequest {
    @Test
   public void testgetSender(){
        Request request = new Request("1","2");
        assertEquals("1",request.getSender());
    }
    @Test
    public void testgetReceiver(){
        Request request = new Request("1","2");
        assertEquals("2",request.getReceiver());
    }
    @Test
    public void testsetConfirmed(){
        Request request = new Request("1","2");

        request.setConfirmed(true);
        assertEquals(true,request.getConfirmed());
    }
    @Test
    public void testComfirmedRequest(){
        Request request = new Request("1","2");
        request.confirmRequest();
        assertEquals(true,request.getConfirmed());
    }

}
