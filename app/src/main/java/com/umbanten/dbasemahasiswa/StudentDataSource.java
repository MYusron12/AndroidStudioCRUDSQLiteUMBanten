package com.umbanten.dbasemahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class StudentDataSource {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public StudentDataSource(Context context) {
        databaseHelper=new DatabaseHelper(context);
    }

    public void bukaDatabase() {
        sqLiteDatabase=databaseHelper.getWritableDatabase();
    }

    public void tutupDatabase() {
        sqLiteDatabase=databaseHelper.getReadableDatabase();
    }

    public boolean addStudent(Student student){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nama_depan",student.getNamaDepan());
        contentValues.put("nama_belakang",student.getNamaBelakang());
        contentValues.put("no_hp",student.getNoHp());
        contentValues.put("gender",student.getGender());
        contentValues.put("jenjang",student.getJenjang());
        contentValues.put("hobi",student.getHobi());
        contentValues.put("alamat",student.getAlamat());
        contentValues.put("tanggal",student.getTanggal());

        long cekStatus=sqLiteDatabase.insert("student",null,contentValues);
        return cekStatus > 0;
    }

    public boolean editStudent(Student student){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nama_depan",student.getNamaDepan());
        contentValues.put("nama_belakang",student.getNamaBelakang());
        contentValues.put("no_hp",student.getNoHp());
        contentValues.put("gender",student.getGender());
        contentValues.put("jenjang",student.getJenjang());
        contentValues.put("hobi",student.getHobi());
        contentValues.put("alamat",student.getAlamat());
        contentValues.put("tanggal",student.getTanggal());

        long cekStatus=sqLiteDatabase.update("student", contentValues,
                "id="+student.getId(), null);
        return cekStatus > 0;
    }

    private Student ubahPojo(Cursor cursor){

        Student student=new Student();

        student.setId(cursor.getLong(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNoHp(cursor.getString(3));
        student.setGender(cursor.getString(4));
        student.setJenjang(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));
        student.setTanggal(cursor.getString(8));

        return student;
    }

    public List<Student> getAllStudent() {

        String query="SELECT * FROM student";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        //Arahkan cursor ke posisi awal
        cursor.moveToFirst();

        List<Student> studentList=new ArrayList<>();

        //Ambil data cursor, lalu masukkan POJO
        while (!cursor.isAfterLast()) {
            Student student = ubahPojo(cursor);
            studentList.add(student);
            //Pindahkan posisi cursor ke data selanjutnya
            cursor.moveToNext();
        }

        return studentList;
    }

    public Student getStudent(long idSiswa) {

        String query="SELECT * FROM student WHERE id="+idSiswa;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        //Arahkan cursor ke posisi awal
        cursor.moveToFirst();
        Student student = ubahPojo(cursor);

        return student;
    }

    public List<Student> getAllStudentSearch(String kataKunci) {

        String query="SELECT * FROM student WHERE nama_depan LIKE ? OR nama_belakang LIKE ?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{"%"+kataKunci+"%","%"+kataKunci+"%"});
        //Arahkan cursor ke posisi awal
        cursor.moveToFirst();

        List<Student> studentList=new ArrayList<>();

        //Ambil data cursor, lalu masukkan POJO
        while (!cursor.isAfterLast()) {
            Student student = ubahPojo(cursor);
            studentList.add(student);
            //Pindahkan posisi cursor ke data selanjutnya
            cursor.moveToNext();
        }

        return studentList;
    }

    public boolean deleteStudent(long id){

        long cekstatus=sqLiteDatabase.delete("student","id="+id,null);
        return cekstatus > 0;
    }

}