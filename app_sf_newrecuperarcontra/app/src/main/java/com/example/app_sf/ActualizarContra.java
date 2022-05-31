package com.example.app_sf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarContra extends AppCompatActivity {

  Button ActualizarBoton;
  EditText contrasenaActualizada, repetirContra;
  String password[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contra);

        ActualizarBoton= findViewById(R.id.ActualizarBoton);
        contrasenaActualizada= findViewById(R.id.contrasenaActual);
        repetirContra=findViewById(R.id.repetirContra);

        ActualizarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if ((contrasenaActualizada.getText().toString().equals("actual")) && (repetirContra.getText().toString().equals("actual")))
                {
                    startActivity(new Intent(ActualizarContra.this, MainActivity.class));
                    Toast.makeText(ActualizarContra.this, "Contraseña Actualizada", Toast.LENGTH_SHORT);
                }
                else
                {
                 //   if ((contrasenaActualizada.getText().toString().equals("actual")) != (repetirContra.getText().toString().equals("actual"))) {
                        contrasenaActualizada.getText().clear();
                        repetirContra.getText().clear();
                        Toast.makeText(ActualizarContra.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
               //     }
                }
            }
        });

        }




    @Override //correcto para que regrese con el boton atras a la interfaz anterior
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent((ActualizarContra.this), MainActivity.class);
        startActivity(intent);
        finish();
    }


}