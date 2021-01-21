package com.umbanten.dbasemahasiswa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FormInputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    /* step 1: deklarasi properti */
    EditText etNamaDepan,etNamaBelakang,etNoHp,etAlamat,etTanggal;
    RadioGroup rbGender;
    RadioButton rbPria,rbWanita;
    Spinner spJenjang;
    CheckBox cbMembaca,cbMenulis,cbMenggambar;
    Button btnSimpan;

    StudentDataSource studentDataSource;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input);

        //Aktifkan Panah Kembali
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final long studentId=getIntent().getLongExtra("idSiswa",0);

        /* step 2: hubungkan id di xml ke java */
        etNamaDepan=findViewById(R.id.et_firstname);
        etNamaBelakang=findViewById(R.id.et_lastname);
        etNoHp=findViewById(R.id.et_hp);
        etTanggal=findViewById(R.id.et_tanggal);
        etAlamat=findViewById(R.id.et_alamat);
        rbGender=findViewById(R.id.rg_gender);
        rbPria=findViewById(R.id.rb_pria);
        rbWanita=findViewById(R.id.rb_wanita);
        spJenjang=findViewById(R.id.sp_jenjang);
        cbMembaca=findViewById(R.id.cb_membaca);
        cbMenulis=findViewById(R.id.cb_menulis);
        cbMenggambar=findViewById(R.id.cb_menggambar);
        btnSimpan=findViewById(R.id.btn_simpan);


        //Cek id untuk menentukan edit data dan tambah data
        if (studentId > 0){

            StudentDataSource studentDataSource=new StudentDataSource(this);
            studentDataSource.bukaDatabase();
            Student student=studentDataSource.getStudent(studentId);
            studentDataSource.tutupDatabase();

            String namaDepan=student.getNamaDepan();
            String namaBelakang=student.getNamaBelakang();
            String noHp=student.getNoHp();
            String tanggal=student.getTanggal();
            String gender=student.getGender();
            String jenjang=student.getJenjang();
            String hobi=student.getHobi();
            String alamat=student.getAlamat();

            etNamaDepan.setText(namaDepan);
            etNamaBelakang.setText(namaBelakang);
            etNoHp.setText(noHp);
            etTanggal.setText(tanggal);
            etAlamat.setText(alamat);

            //Radio Button
            if (gender.equals("Pria")) {
                rbPria.setChecked(true);
            }else {
                rbWanita.setChecked(true);
            }

            //Spinner
            switch (jenjang){
                case "SD":
                    spJenjang.setSelection(0);
                    break;
                case "SMP":
                    spJenjang.setSelection(1);
                    break;
                case "SMA":
                    spJenjang.setSelection(2);
                    break;
                case "D-III":
                    spJenjang.setSelection(3);
                    break;
                case "S-1 Sarjana":
                    spJenjang.setSelection(4);
                    break;
                case "S-2 Pasca Sarjana":
                    spJenjang.setSelection(5);
                    break;
                case "S-3 Doktor":
                    spJenjang.setSelection(6);
                    break;
            }

            //Check box
            //Membaca, Menulis
            String pisahKalimat[] =hobi.split(",");
            //Membaca [0]
            //Menulis [1]

            //Setting pilihan nilai ke checkbox
            for (int i = 0; i < pisahKalimat.length; i++){
                switch (pisahKalimat[i]){
                    case "Membaca":
                        cbMembaca.setChecked(true);
                        break;
                    case "Menulis":
                        cbMenulis.setChecked(true);
                        break;
                    case "Menggambar":
                        cbMenggambar.setChecked(true);
                        break;
                }
            }
        }


        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilTanggal();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* step 3: simpan input user ke dalam variabel */
                //untuk type EditText (penggunaan nama variabel kata pertama kecil, kata kedua Besar)
                String namaDepan=etNamaDepan.getText().toString();
                String namaBelakang=etNamaBelakang.getText().toString();
                String noHp=etNoHp.getText().toString();
                String alamat=etAlamat.getText().toString();
                String tanggal=etTanggal.getText().toString();

                //untuk Radio Button
                int radioButtonId = rbGender.getCheckedRadioButtonId();
                String gender = "";

                switch (radioButtonId) {
                    //jika pilh Pria
                    case R.id.rb_pria:
                        gender = "Pria";
                        break;
                    //jika pilih Wanita
                    case R.id.rb_wanita:
                        gender = "Wanita";
                        break;
                }

                //untuk Spinner
                String jenjang=spJenjang.getSelectedItem().toString();

                //untuk Checkbox
                List<String> listHobi=new ArrayList<>();

                //Jika Menggambar di pilih
                if (cbMenggambar.isChecked()){
                    listHobi.add("Menggambar");
                }

                //Jika Menulis di pilih
                if (cbMenulis.isChecked()){
                    listHobi.add("Menulis");
                }

                //Jika Membaca di pilih
                if (cbMembaca.isChecked()){
                    listHobi.add("Membaca");
                }

                String gabungHobi= TextUtils.join(",",listHobi);

                //Cek data Kosong
                if (namaDepan.isEmpty()) {
                    etNamaDepan.setError("Nama Depan Belum di isi");
                    return;
                }

                if (namaBelakang.isEmpty()) {
                    etNamaBelakang.setError("Nama Belakang Belum di isi");
                    return;
                }

                if (noHp.isEmpty()) {
                    etNoHp.setError("No HP Belum di isi");
                    return;
                }

                //jika menulis kosong
                if (cbMenulis.isChecked()){
                    listHobi.add("Menulis");
                }

                //jika membaca kosong
                if (cbMembaca.isChecked()){
                    listHobi.add("Membaca");
                }

                //jika menggambar kosong
                if (cbMenggambar.isChecked()){
                    listHobi.add("Menggambar");
                }

                if (alamat.isEmpty()) {
                    etAlamat.setError("Alamat Belum di isi");
                    return;
                }

                //Simpan sementara ke POJO
                student=new Student();
                student.setNamaDepan(namaDepan);
                student.setNamaBelakang(namaBelakang);
                student.setNoHp(noHp);
                student.setGender(gender);
                student.setJenjang(jenjang);
                student.setHobi(gabungHobi);
                student.setAlamat(alamat);
                student.setTanggal(tanggal);

                boolean statusSimpan;

                studentDataSource=new StudentDataSource(getApplicationContext());
                studentDataSource.bukaDatabase();

                if (studentId>0){
                    student.setId(studentId);

                    statusSimpan = studentDataSource.editStudent(student);

                }else {

                    statusSimpan = studentDataSource.addStudent(student);
                }

                //statusSimpan=studentDataSource.addStudent(student);

                //Jika berhasil simpan ke Database
                if (statusSimpan) {
                    //buat menghapus Activity
                    finish();

                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Tidak Berhasil",Toast.LENGTH_SHORT).show();
                }

                //Toast untuk Popup (modals)
                //\n untuk ganti baris (br)

                /*
                Toast.makeText(getApplicationContext(), "Nama Depan : "+namaDepan+
                        "\nNama Belakang : "+namaBelakang+
                        "\nNo Hp : "+noHp+
                        "\nGender : "+gender+
                        "\nJenjang : "+jenjang+
                        "\nHobi : "+gabungHobi+
                        "\nAlamat : "+alamat, Toast.LENGTH_LONG).show();
                        */
            }
        });

    }

    //untuk menangkap pilihan tanggal yang terpilih
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        etTanggal.setText(dayOfMonth+"/"+month+"/"+year);
    }

    private void tampilTanggal(){
        //Untuk mengambil data tanggal sekarang
        Calendar calendar=Calendar.getInstance();

        new DatePickerDialog(this,FormInputActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


}