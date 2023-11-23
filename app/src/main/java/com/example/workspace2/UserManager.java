package com.example.workspace2;

public class UserManager {
    private static long userId;

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long id) {
        userId = id;
    }
}
