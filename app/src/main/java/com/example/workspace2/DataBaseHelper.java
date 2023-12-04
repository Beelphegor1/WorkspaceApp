package com.example.workspace2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_USERS = "TABLE_USERS";
    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_USER_NAME = "USER_NAME";
    private static final String COLUMN_USER_EMAIL = "Email";
    private static final String COLUMN_USER_PASSWORD = "PASSWORD";
    private static final String TABLE_TASKS = "TABLE_TASKS";
    private static final String COLUMN_TASK_ID = "TASK_ID";
    private static final String COLUMN_TASK_NAME = "TASK_NAME";
    private static final String COLUMN_TASK_DESCRIPTION = "TASK_DESCRIPTION";
    private static final String COLUMN_TASK_STATE = "TASK_STATE";
    private static final String COLUMN_TASK_START_DATE = "TASK_START_DATE";
    private static final String COLUMN_TASK_END_DATE = "TASK_END_DATE";
    private static final String COLUMN_USER_ID_FK = "USER_ID_FK";


    private static DataBaseHelper instance;
    //Singleton
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DataBaseHelper(Context context) {
        super(context, "worspace.db", null, 1);
    }
    final String TBLUser = "CREATE TABLE  TABLE_USERS ( USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT, Email TEXT, PASSWORD  TEXT)";
    final String TBLTask = "CREATE TABLE TABLE_TASKS  ( TASK_ID  INTEGER PRIMARY KEY AUTOINCREMENT, TASK_NAME  TEXT, TASK_DESCRIPTION  TEXT,TASK_STATE BOOLEAN, TASK_START_DATE TEXT, TASK_END_DATE  TEXT, USER_ID_FK INTEGER, FOREIGN KEY( USER_ID_FK ) REFERENCES  TABLE_USERS ( USER_ID ))";
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TBLUser);

        db.execSQL(TBLTask);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Métodos para gestionar usuarios

    public long agregarUsuario(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getNombre());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public List<Users> obtenerTodosLosUsuarios() {
        List<Users> usuarios = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Users usuario = new Users();
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
                usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)));
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return usuarios;
    }

    // Métodos para gestionar tareas

    public long agregarTarea(Tarea tarea, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, tarea.getNombre());
        values.put(COLUMN_TASK_DESCRIPTION, tarea.getDescripcion());
        values.put(COLUMN_USER_ID_FK, userId);

        // Convierte las fechas a formato de texto antes de almacenarlas en la base de datos
        if (tarea.getFechaInicio() != null && tarea.getFechaFin() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaInicioStr = dateFormat.format(tarea.getFechaInicio());
            String fechaFinStr = dateFormat.format(tarea.getFechaFin());

            values.put(COLUMN_TASK_START_DATE, fechaInicioStr);
            values.put(COLUMN_TASK_END_DATE, fechaFinStr);
        }


        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    public List<Tarea> obtenerTodasLasTareas() {
        List<Tarea> tareas = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tarea tarea = new Tarea();
                tarea.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NAME)));
                tarea.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DESCRIPTION)));

                // Parsea las fechas desde el formato de texto a Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date fechaInicio = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_START_DATE)));
                    Date fechaFin = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_END_DATE)));
                    tarea.setFechaInicio(fechaInicio);
                    tarea.setFechaFin(fechaFin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tareas.add(tarea);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return tareas;
    }


    public boolean usuarioExiste(String nombre, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USER_NAME + " = ? OR " + COLUMN_USER_EMAIL + " = ?", new String[]{nombre, email});

        boolean existe = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existe;
    }

    public boolean verificarCredenciales(String Email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?", new String[]{Email, password});
        boolean credencialesValidas = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return credencialesValidas;
    }

    public String getColumnUserId() {
        return COLUMN_USER_ID;
    }
    public String getColumnUserName() {
        return COLUMN_USER_NAME;
    }
    public String getTableUsers() {
        return TABLE_USERS;
    }


    public long obtenerUserId(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1; // Valor predeterminado si no se encuentra el usuario

        // Consulta para obtener el ID del usuario según el nombre de usuario
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID +
                " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USER_NAME + " = ?", new String[]{nombreUsuario});

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userId;
    }
}
