package com.example.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;
import OpenHelper.SQLite_OH;

public class registro extends AppCompatActivity {

    Button btnGrabarUsu;
    EditText txtNomUsu, txtApeUsu, txtCorUsu, txtPasUsu, txtPasUsu2;

    SQLite_OH helper = new SQLite_OH(this, "BD1", null,1);

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[+}{)(?¿&%$#!-]).{8,15}$");

            /*
            Pattern.compile("^" +
                    "(?=.*\\d)" +               //Al menos 1 digito
                    "(?=.*[a-z])" +             //Al menos 1 letra minuscula
                    "(?=.*[A-Z])" +             //Al menos 1 letra mayuscula
                    "(?=.*[+-}{)(?¿&%$#!])" +   //Al menos 1 caracter especial
                    "(?=\\s+$)" +               //Sin especios en blanco
                    ".{8,15}" +                 //Al menos 8 Caracteres
                    "$");*/

    private static final Pattern NAME_PATTERN =
            Pattern.compile("([a-zA-ZÁÉÍÓÚñáéíóú]{2,25}[\\s]*)+");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnGrabarUsu=(Button)findViewById(R.id.btnRegOk);
        txtNomUsu=(EditText)findViewById(R.id.txtRegNombre);
        txtApeUsu=(EditText)findViewById(R.id.txtRegApellido);
        txtCorUsu=(EditText)findViewById(R.id.txtRegEmail);
        txtPasUsu=(EditText)findViewById(R.id.txtRegContraseña);
        txtPasUsu2=(EditText)findViewById(R.id.txtRegContraseña2);

        btnGrabarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarNombre() | !validarApellido() | !validarCorreo() | !validarContraseña() | !validarConfirmacionContraseña()) {
                    return;
                }

                helper.abrirDB();
                helper.insertarRegDB(String.valueOf(txtNomUsu.getText()),
                        String.valueOf(txtApeUsu.getText()),
                        String.valueOf(txtCorUsu.getText()),
                        String.valueOf(txtPasUsu.getText()));
                helper.cerrarDB();

                Toast.makeText(getApplicationContext(), "Registro Almacenado con exito!", Toast.LENGTH_LONG).show();

                Intent i = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(i);
            }
        });

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    private boolean validarNombre(){
        String nameInput = txtNomUsu.getText().toString().trim();

        if (nameInput.isEmpty()){
            txtNomUsu.setError("Introduce un Nombre!");
            return false;
        } else if (!NAME_PATTERN.matcher(nameInput).matches()){
            txtNomUsu.setError("Debe contener al menos dos caracteres, no se permiten números o símbolos especiales!");
            return false;
        }
        else {
            txtNomUsu.setError(null);
            return true;
        }
    }


    private boolean validarApellido(){
        String lastNameInput = txtApeUsu.getText().toString().trim();

        if (lastNameInput.isEmpty()){
            txtApeUsu.setError("Introduce un Apellido!");
            return false;
        } else if (!NAME_PATTERN.matcher(lastNameInput).matches()){
            txtApeUsu.setError("Debe contener al menos dos caracteres, no se permiten números o símbolos especiales!");
            return false;
        }
        else {
            txtApeUsu.setError(null);
            return true;
        }
    }

    private boolean validarCorreo(){
        String emailInput = txtCorUsu.getText().toString().trim();
        Boolean existeEmail = false;

         if (emailInput.isEmpty()){
                txtCorUsu.setError("Introduce un correo!");
                return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                txtCorUsu.setError("Por favor introduce un correo valido!");
                return false;
        }
        else {
             try {
                 Cursor cursor = helper.ConsultarEmail(emailInput);
                 if (cursor.getCount() > 0){
                     txtCorUsu.setError("El correo ya esta dado de alta");
                     return false;
                 } else {
                     txtCorUsu.setError(null);
                     return true;
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         txtCorUsu.setError(null);
         return true;
        }
    }

    private boolean validarContraseña(){
        String passwordInput = txtPasUsu.getText().toString().trim();

        if (passwordInput.isEmpty()){
            txtPasUsu.setError("Introduce una contraseña!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            txtPasUsu.setError("La contraseña debe contener entre 8 a 15 caracteres, con al menos una mayúscula, " +
                    "una minúscula, un número y un símbolo especial: + - } { ) ( ? ¿ & % $ # !");
            return false;
        }
        else {
            txtPasUsu.setError(null);
            return true;
        }
    }

    private boolean validarConfirmacionContraseña(){
        String passwordInput = txtPasUsu.getText().toString().trim();
        String passwordConfirmInput = txtPasUsu2.getText().toString().trim();

        if (passwordConfirmInput.isEmpty()){
            txtPasUsu2.setError("Confirma la contraseña!");
            return false;
        } else if (!passwordConfirmInput.equals(passwordInput)) {
            txtPasUsu2.setError("La Contraseña no coincide");
            return false;
        }
        else {
            txtPasUsu2.setError(null);
            return true;
        }
    }


}
