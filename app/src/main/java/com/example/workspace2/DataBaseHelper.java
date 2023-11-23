package com.example.workspace2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "workspace.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Tabla de tareas
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_START_DATE = "task_start_date";
    private static final String COLUMN_TASK_END_DATE = "task_end_date";
    private static final String COLUMN_USER_ID_FK = "user_id_fk"; // Nueva columna para la clave externa

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);

        // Crear la tabla de tareas con clave externa
        String createTaskTableQuery = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_START_DATE + " TEXT, " +
                COLUMN_TASK_END_DATE + " TEXT, " +
                COLUMN_USER_ID_FK + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createTaskTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de la base de datos si es necesario
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaInicioStr = dateFormat.format(tarea.getFechaInicio());
        String fechaFinStr = dateFormat.format(tarea.getFechaFin());

        values.put(COLUMN_TASK_START_DATE, fechaInicioStr);
        values.put(COLUMN_TASK_END_DATE, fechaFinStr);

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
