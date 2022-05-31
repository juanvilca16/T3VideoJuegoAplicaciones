package com.example.app_sf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    private Button RecuperaBoton;
    private EditText emailEditText;

    @SuppressLint("WrongViewCast") //correcto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        RecuperaBoton = findViewById(R.id.RecuperarBoton);
        emailEditText = findViewById(R.id.emailEditText);
        RecuperaBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailEditText.getText().toString().equals("admin")){

                    startActivity(new Intent(ForgotPassword.this, ValidarCorreo.class));
                    Toast.makeText(ForgotPassword.this, "Correo correcto", Toast.LENGTH_SHORT).show();
                }else{
                    emailEditText.getText().clear();


                    Toast.makeText(ForgotPassword.this, "Correo no existente", Toast.LENGTH_SHORT).show();
                }


                                              }
                                          }
        );
    }


    @Override //correcto para que regrese con el boton atras a la interfaz anterior
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent((ForgotPassword.this), MainActivity.class);
        startActivity(intent);
        finish();
    }




 }


