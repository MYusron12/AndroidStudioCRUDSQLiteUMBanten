package com.umbanten.dbasemahasiswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class StudentItemAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> studentList;
    LayoutInflater layoutInflater;

    public StudentItemAdapter(Context context, List<Student> students){
        super(context,R.layout.student_item,students);
        this.context=context;
        studentList=students;
        layoutInflater=LayoutInflater.from(context);

    }

    @Nullable
    //Untuk mendapatkan data siswa berdasarkan posisi dari list
    @Override
    public Student getItem(int position) {
        return studentList.get(position);
    }

    //Untuk mendapatkan posisi data siswa dari list
    @Override
    public int getCount() {
        return studentList.size();
    }

    @NonNull
    //Untuk menghubungkan student_item.xml dengan ListView
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=convertView;

        //cek apakah view kosong
        if (view==null) {
            view=layoutInflater.inflate(R.layout.student_item,null);

        }

        TextView tvNama=view.findViewById(R.id.tv_nama);
        TextView tvJenjang=view.findViewById(R.id.tv_jenjang);
        TextView tvGender=view.findViewById(R.id.tv_gender);
        TextView tvNoHp=view.findViewById(R.id.tv_nohp);

        Student student=getItem(position);

        tvNama.setText(student.getNamaDepan()+" "+student.getNamaBelakang());
        tvJenjang.setText(student.getJenjang());
        tvGender.setText(student.getGender());
        tvNoHp.setText(student.getNoHp());

        return view;
    }

}