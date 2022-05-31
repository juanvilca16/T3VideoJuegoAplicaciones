package com.example.app_sf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth; // base de datos

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    boolean passwordVisible;
    TextView recuperacontrasena; // Recuperarcontraseña
    private FirebaseAuth mAuth; //Base de datos firebase
    Button button, button2;

    //Inicio - Salir
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }//Fin - Salir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        recuperacontrasena = findViewById(R.id.recuperacontra); //recuperar contraseña
        recuperacontrasena.setMovementMethod(LinkMovementMethod.getInstance()); //metodo de recuperar contraseña

        mAuth = FirebaseAuth.getInstance(); //Base de datos firebase

        //Inicio - login
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("admin") && password.getText().toString().equals("123")){
                    //Toast.makeText(MainActivity.this, "Credenciales correctas", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(MainActivity.this, Principal.class);
                    startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                    Toast.makeText(MainActivity.this, "Credenciales correctas", Toast.LENGTH_SHORT).show();
                }else{
                    email.getText().clear();
                    password.getText().clear();

                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }); //Fin - Login

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(intent);
                finish();

            }
        });


        //Inicio - Ocultar pass
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        }); //Fin - oculatar pass

        //Inicio - Recuperar Contraseña
        recuperacontrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        }); //Fin - Ocultar contraseña



    }

    }