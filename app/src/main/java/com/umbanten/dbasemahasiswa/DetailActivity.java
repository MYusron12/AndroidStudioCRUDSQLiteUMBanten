package com.umbanten.dbasemahasiswa;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView tvNama,tvNoHP,tvTanggal,tvGender,tvHobi,tvJenjang,tvAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Aktifkan Panah Kembali
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNama=findViewById(R.id.tv_nama_lengkap);
        tvNoHP=findViewById(R.id.tv_nohp);
        tvTanggal=findViewById(R.id.tv_tanggal);
        tvGender=findViewById(R.id.tv_gender);
        tvJenjang=findViewById(R.id.tv_jenjang);
        tvGender=findViewById(R.id.tv_gender);
        tvHobi=findViewById(R.id.tv_hobi);
        tvAlamat=findViewById(R.id.tv_alamat);

        //Tangkap id
        long idSiswa=getIntent().getLongExtra("idSiswa",0);

        StudentDataSource studentDataSource=new StudentDataSource(this);
        studentDataSource.bukaDatabase();
        Student student=studentDataSource.getStudent(idSiswa);
        studentDataSource.tutupDatabase();

        String namaLengkap=student.getNamaDepan()+" "+student.getNamaBelakang();
        String noHp=student.getNoHp();
        String tanggal=student.getTanggal();
        String gender=student.getGender();
        String jenjang=student.getJenjang();
        String hobi=student.getHobi();
        String alamat=student.getAlamat();

        tvNama.setText(namaLengkap);
        tvNoHP.setText(noHp);
        tvTanggal.setText(tanggal);
        tvGender.setText(gender);
        tvJenjang.setText(jenjang);
        tvHobi.setText(hobi);
        tvAlamat.setText(alamat);

    }

    //untuk Aksi ketika menu di klik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId=item.getItemId();

        switch (menuId){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

