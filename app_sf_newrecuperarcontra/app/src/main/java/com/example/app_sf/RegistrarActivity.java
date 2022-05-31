package com.example.app_sf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_sf.entidad.Usuario;

public class RegistrarActivity extends AppCompatActivity {

    EditText txtnombre, txtapellido, txtcelular, txtcorreo, txtcontraseña;
    //Spinner spcorreo;
    Button btnregistrar;
    //String correo[] = {"@hotmail.com","@gmail.com","@outlook.es"};
    boolean registro = true;
    String nom,ape,cel,corr,contr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        asignarReferencias();


    }

    /*private void TipoCorreo(){
        ArrayAdapter adaptador = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,correo);
        spcorreo.setAdapter(adaptador);
    }*/

    private void asignarReferencias() {
        //spcorreo = findViewById(R.id.spcorreo);
        txtnombre = findViewById(R.id.txtnombre);
        txtapellido = findViewById(R.id.txtapellido);
        txtcelular = findViewById(R.id.txtcelular);
        txtcorreo = findViewById(R.id.txtcorreo);
        txtcontraseña = findViewById(R.id.txtcontraseña);
        btnregistrar = findViewById(R.id.btnregistrar);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valida()==true){
                    registrar();
                }

            }
        });
        //TipoCorreo();



    }

    private void registrar(){

        String nombre, apellido, celular, correo, contraseña,correocompleto;

        nombre = txtnombre.getText().toString();
        apellido = txtapellido.getText().toString();
        celular = txtcelular.getText().toString();
        correo = txtcorreo.getText().toString();
        contraseña = txtcontraseña.getText().toString();
        //sp = spcorreo.getSelectedItem().toString();
        //correocompleto = correo.concat(sp);


        String mensaje;

        if(registro=true){
            Usuario usuario = new Usuario(nombre,apellido,celular,correo, contraseña);
            mensaje = "Registro exitoso";
        }
        else{
            mensaje = "Error";
        }

        AlertDialog.Builder ventana = new AlertDialog.Builder(RegistrarActivity.this);
        ventana.setTitle("Mensaje informativo");
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ventana.create().show();

    }

    private boolean valida(){
        nom = txtnombre.getText().toString();
        ape =txtapellido.getText().toString();
        cel =txtcelular.getText().toString();
        corr =txtcorreo.getText().toString();
        contr =txtcontraseña.getText().toString();

        boolean rpta = true;

        if(nom.equals("")){
            txtnombre.setError("nombre obligatoria");
            rpta = false;
        }
        if(ape.equals("")){
            txtapellido.setError("apellido obligatorio");
            rpta = false;
        }
        if(cel.equals("")){
            txtcelular.setError("celular Obligatorio");
            rpta = false;
        }
        if(corr.equals("")){
            txtcorreo.setError("correo obligatorio");
            rpta = false;
        }
        if(contr.equals("")){
            txtcontraseña.setError("contraseña obligatoria");
            rpta = false;
        }

        return rpta;
    }
}
