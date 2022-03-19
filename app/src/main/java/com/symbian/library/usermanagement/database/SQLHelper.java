package com.symbian.library.usermanagement.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "symbian";
    private static final int DB_VERSION = 2;
    private static  SQLHelper INSTANCE;

    public static SQLHelper getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }
        return INSTANCE;
    }

    public SQLHelper(@Nullable Context context) {super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE tbl_usuario" +
            "(cod_usuario INTEGER PRIMARY KEY," +
            "nome TEXT," +
            "sobrenome TEXT," +
            "login TEXT," +
            "senha TEXT);"
        );
        sqLiteDatabase.execSQL(
            "CREATE TABLE tbl_endereco(" +
            "cod_endereco INTEGER PRIMARY KEY," +
            "cod_usuario INTEGER," +
            "cep TEXT," +
            "numero TEXT," +
            "complemeto TEXT," +
            "FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public int addUser(String name, String lastname, String login, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {
            sqLiteDatabase.beginTransaction();

            ContentValues userValues = new ContentValues();
            userValues.put("nome", name);
            userValues.put("sobrenome", lastname);
            userValues.put("login", login);
            userValues.put("senha", password);

            int cod_usuario = (int) sqLiteDatabase.insertOrThrow("tbl_usuario", null, userValues);
            sqLiteDatabase.setTransactionSuccessful();
            return cod_usuario;
        } catch (Exception error) {
            Log.d("SQL_ERROR", error.getMessage());
            return 0;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public int addAddress(int cod_usuario, String cep, String numero, String complemento){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try{
            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("cod_usuario", cod_usuario );
            values.put("cep", cep);
            values.put("complemeto", complemento);
            values.put("numero", numero);

            sqLiteDatabase.insertOrThrow("tbl_endereco", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return 1;
        } catch (Exception error) {
            Log.d("SQL_ERROR", error.getMessage());
            return 0;
        } finally {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public int startLogin(String login, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM tbl_usuario WHERE login = ? AND senha = ?",
            new String[]{login, password}
        );

        try{
            if (cursor.moveToFirst()) {
                int cod_usuario = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                return  cod_usuario;
            }
        }catch (Exception error){
            Log.d("SQL_ERROR", error.getMessage());
            return 0;
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return 0;
    }
}
