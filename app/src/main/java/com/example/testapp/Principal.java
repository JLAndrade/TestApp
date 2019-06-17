package com.example.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import OpenHelper.SQLite_OH;

public class Principal extends AppCompatActivity {

    SQLite_OH helper = new SQLite_OH(this, "BD1", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        String usuario = getIntent().getStringExtra("usuario");


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

        try {
            Cursor cursor = helper.ConsultarAllUsuarios();
            if (cursor.getCount() > 0) {
                if (cursor != null) {
                    cursor.moveToFirst();
                }
                StringBuilder builder = new StringBuilder();
                do {

                    Integer id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String lastname = cursor.getString(2);
                    String email = cursor.getString(3);
                    String pass = cursor.getString(4);

                    builder.append("ID: " + id + " Nombre: " + name + " Apellido: " + lastname + " Correo: " + email + " Contraseña: " + pass + "");

                    TextView txView = (TextView) findViewById(R.id.txtData);
                    txView.setText(builder.toString());
                    //-------------------------------------------
/*


                        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                        TableRow tbrow0 = new TableRow(this);
                        TextView tv0 = new TextView(this);

                        tv0.setText(id);
                        tv0.setTextColor(Color.RED);
                        tbrow0.addView(tv0);
                        TextView tv1 = new TextView(this);
                        tv1.setText(name);
                        tv1.setTextColor(Color.RED);
                        tbrow0.addView(tv1);
                        TextView tv2 = new TextView(this);
                        tv2.setText(lastname);
                        tv2.setTextColor(Color.RED);
                        tbrow0.addView(tv2);
                        TextView tv3 = new TextView(this);
                        tv3.setText(email);
                        tv3.setTextColor(Color.RED);
                        tbrow0.addView(tv3);
                        TextView tv4 = new TextView(this);
                        tv4.setText(pass);
                        tv4.setTextColor(Color.RED);
                        tbrow0.addView(tv4);

                        stk.addView(tbrow0);
                        //-------------------

                        for (int i = 0; i < 25; i++) {
                            TableRow tbrow = new TableRow(this);
                            TextView t1v = new TextView(this);
                            t1v.setText("" + i);
                            t1v.setGravity(Gravity.CENTER);
                            tbrow.addView(t1v);
                            TextView t2v = new TextView(this);
                            t2v.setText("Product " + i);
                            t2v.setGravity(Gravity.CENTER);
                            tbrow.addView(t2v);
                            TextView t3v = new TextView(this);
                            t3v.setText("Rs." + i);
                            t3v.setGravity(Gravity.CENTER);
                            tbrow.addView(t3v);
                            TextView t4v = new TextView(this);
                            t4v.setText("" + i * 15 / 32 * 10);
                            t4v.setGravity(Gravity.CENTER);
                            tbrow.addView(t4v);
                            stk.addView(tbrow);
                        }*/


                } while (cursor.moveToNext());

            } else {
                Intent i = new Intent(getApplicationContext(), AddUbicaciones.class);
                startActivity(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
