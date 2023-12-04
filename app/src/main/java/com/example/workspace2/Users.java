package com.example.workspace2;

public class Users {
    private static long userId;
    private String nombre;
    private String email;
    private String password;


    public Users() {

    }

    public Users(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        Users.userId = userId;
    }
}