package com.example.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import OpenHelper.SQLite_OH;

public class MainActivity extends AppCompatActivity {

    TextView tvRegistrese;
    Button btnIngresar;

    SQLite_OH helper = new SQLite_OH(this, "BD1", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegistrese = (TextView)findViewById(R.id.tvRegistrese);
        tvRegistrese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent (getApplicationContext(), registro.class);
                startActivity(i);
            }
        });

        btnIngresar = (Button)findViewById(R.id.btnLogIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText correo = (EditText)findViewById(R.id.txtLogCorreo);
                EditText contraseña = (EditText)findViewById(R.id.txtLogPassword);

                try {
                    Cursor cursor = helper.ConsultarUsuPass(correo.getText().toString(), contraseña.getText().toString());
                    if (cursor.getCount() > 0){
                        Intent i = new Intent (getApplicationContext(), Principal.class);
                        i.putExtra("Usuario", correo.getText());
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show();

                    }
                    correo.setText("");
                    contraseña.setText("");
                    correo.findFocus();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
