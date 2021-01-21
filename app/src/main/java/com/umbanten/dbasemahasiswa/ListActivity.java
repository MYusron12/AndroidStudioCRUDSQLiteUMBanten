package com.umbanten.dbasemahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    StudentItemAdapter studentItemAdapter;
    StudentDataSource studentDataSource;
    List<Student> studentList;
    ListView lvStudent;
    //SearchView svStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lvStudent=findViewById(R.id.lv_student);
        svStudent=findViewById(R.id.sv_student);
        lvStudent.setOnItemClickListener(this);

        //untuk mengaktifkan context menu
        registerForContextMenu(lvStudent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Mendapatkan info context menu
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int posisiItem=adapterContextMenuInfo.position;

        long studentId=studentList.get(posisiItem).getId();

        //untuk menentukan pilihan menu ubah dan hapus
        int menuId=item.getItemId();

        switch (menuId){
            case R.id.menu_ubah:

                //Kiirm data idSiswa ke DetailActivity
                Intent intent=new Intent(this, FormInputActivity.class);
                intent.putExtra("idSiswa",studentId);
                startActivity(intent);

                break;
            case R.id.menu_hapus:

                boolean statusHapus=studentDataSource.deleteStudent(studentId);
                if (statusHapus){

                    //Kosongkan list
                    studentList.clear();

                    List<Student> students=studentDataSource.getAllStudent();

                    //Tambahkan ke list
                    for (int i = 0;i < students.size();i++){
                        Student student=students.get(i);
                        studentList.add(student);
                    }

                    //Refresh adapter
                    studentItemAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    //Untuk menghubungkan main_menu di xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //untuk Aksi ketika menu di klik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId=item.getItemId();

        switch (menuId){
            case R.id.menu_input:
                //Pindah ke activity FormInputActivity
                startActivity(new Intent( this,FormInputActivity.class));
                break;

            case R.id.menu_logout:
                //Pindah ke activity FormInputActivity
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Ambil data dari database
        studentDataSource=new StudentDataSource(getApplicationContext());
        studentDataSource.bukaDatabase();
        studentList=studentDataSource.getAllStudent();
        //studentDataSource.tutupDatabase();

        System.out.println("Panjang : "+studentList);
        //Setting Adapter
        studentItemAdapter=new StudentItemAdapter(this,studentList);
        //Masukkan adapter ke Listview
        lvStudent.setAdapter(studentItemAdapter);

        svStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //Fungsi ini aktif ketika mengganti satu huruf pada pencarian
            @Override
            public boolean onQueryTextChange(String kataKunci) {
                //Kosongkan list
                studentList.clear();

                List<Student> students=studentDataSource.getAllStudentSearch(kataKunci);

                //Tambahkan ke list
                for (int i=0;i<students.size();i++){
                    Student student=students.get(i);
                    studentList.add(student);
                }

                //Refresh adapter
                studentItemAdapter.notifyDataSetChanged();
                return false;

            }
        });


    }

    //Digunakan untuk Aksi ketika item di Klik
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        long idSiswa=studentList.get(position).getId();

        //Kiirm data idSiswa ke DetailActivity
        Intent intent=new Intent(this, DetailActivity.class);
        intent.putExtra("idSiswa",idSiswa);
        startActivity(intent);

    }
}
