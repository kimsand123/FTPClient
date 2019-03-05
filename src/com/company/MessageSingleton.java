package com.company;

public class MessageSingleton {
    private static MessageSingleton ourInstance = new MessageSingleton();

    public static MessageSingleton getInstance() {
        return ourInstance;
    }
    String message="";
    boolean messageyesno=false;

    private MessageSingleton() {
    }

}
