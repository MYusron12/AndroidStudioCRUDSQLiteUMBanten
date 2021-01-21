package com.umbanten.dbasemahasiswa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "sekolahku", null, 2);
    }

    //onCreate untuk membuat kolom pada Tabel yang baru
    //no_hp menggunakan TEXT karena sifatnya hanya menyimpan suatu text, karena di SQLLite hanya mengenal INTEGER, TEXT
    //untuk aritmatika baru menggunakan NUMBER
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE student (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nama_depan TEXT," +
                "nama_belakang TEXT," +
                "no_hp TEXT," +
                "gender TEXT," +
                "jenjang TEXT," +
                "hobi TEXT," +
                "alamat TEXT," +
                "tanggal TEXT)";

        db.execSQL(query);

    }

    //onUpgrade untuk memmbuat kolom apabila ada update/penambahan
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryTambahTanggal="ALTER TABLE student ADD COLUMN tanggal TEXT";

        if (newVersion>oldVersion){
            try {
                db.execSQL(queryTambahTanggal);
            }catch (SQLiteException error){

            }
        }

    }
}