package com.example.app_sf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ValidarCorreo extends AppCompatActivity {

     Button ValidarBoton;
     EditText editcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_correo);

        ValidarBoton= findViewById(R.id.Validarcodigo);
        editcode= findViewById(R.id.editcode);

        ValidarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editcode.getText().toString().equals("Codigo"))
                {
                    startActivity(new Intent(ValidarCorreo.this,ActualizarContra.class));
                    Toast.makeText(ValidarCorreo.this, "Codigo Valido", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    editcode.getText().clear();
                    Toast.makeText(ValidarCorreo.this,"Codigo Invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override //correcto para que regrese con el boton atras a la interfaz anterior
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent((ValidarCorreo.this), ForgotPassword.class);
        startActivity(intent);
        finish();
    }

}