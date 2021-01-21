package com.umbanten.dbasemahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    Button btnLogin;

    //password sementara
    String username="admin";
    String password="admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername=findViewById(R.id.et_username);
        etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername=etUsername.getText().toString();
                String inputPassword=etPassword.getText().toString();

                //Cek Login
                //Jika keduanya salah
                if (!inputUsername.equals(username)&&!inputPassword.equals(password)){
                    Toast.makeText(getApplicationContext(),"Username dan Password Salah!!",Toast.LENGTH_SHORT).show();
                }

                //Jika salah satu salah
                else if (!inputUsername.equals(username)||!inputPassword.equals(password)){
                    Toast.makeText(getApplicationContext(),"Username dan Password Salah!!",Toast.LENGTH_SHORT).show();
                }

                //Jika Keduanya Benar
                else{
                    etUsername.setText("");
                    etPassword.setText("");
                    startActivity(new Intent(getApplicationContext(),ListActivity.class));
                }
            }
        });
    }
}