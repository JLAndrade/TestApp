package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite_OH extends SQLiteOpenHelper{

/*
    public SQLite_OH(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
*/

    public SQLite_OH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table usuarios(_ID integer primary key autoincrement, " +
                "Nombre  text, Apellido text, Correo text, Password text);";
        db.execSQL(query);

        String query2 = "create table ubicaciones(_ID integer primary key autoincrement, " +
                "Usuario text, Locacion text, Latitud integer, Longitud integer);";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Method Open DB
    public void abrirDB(){
        this.getWritableDatabase();
    }

    //Method Close DB
    public void cerrarDB(){
        this.close();
    }

    //Method Insert Reg DB
    public void insertarRegDB(String nom, String ape, String cor, String pas) {
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nom);
        valores.put("Apellido", ape);
        valores.put("Correo", cor);
        valores.put("Password", pas);
        this.getWritableDatabase().insert("usuarios",null,valores);
    }

    //Method Insert Loc DB
    public void insertarUbiDB(String usu, String loc, Integer lat, Integer lon) {
        ContentValues valores = new ContentValues();
        valores.put("Usuario", usu);
        valores.put("Ubicacion", loc);
        valores.put("Latitud", lat);
        valores.put("Longitud", lon);
        this.getWritableDatabase().insert("ubicaciones",null,valores);
    }


    //Method Verify Email exist!
    public Cursor ConsultarEmail(String cor) throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query("usuarios", new String[]{"_ID", "Correo"},
                "Correo like '"+cor+"'", null, null ,null,null);
        return  mcursor;

    }

    //Method login
    public Cursor ConsultarUsuPass(String cor, String pas) throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query("usuarios", new String[]{"_ID", "Correo", "Password"},
                "Correo like '"+cor+"' " + "and Password like '"+pas+"'", null, null ,null,null);
        return  mcursor;

    }

    //Method Locations
    public Cursor ConsultarUbicaciones(String usu) throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query("ubicaciones", new String[]{"_ID", "Usuario", "Ubicacion", "Latitud", "Longitud"},
                "Usuario like '"+usu+"'", null, null ,null,null);
        return  mcursor;

    }

    public Cursor ConsultarAllUsuarios() throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query("usuarios", new String[]{"_ID", "Nombre", "Apellido", "Correo", "Password"}, null, null, null ,null,null);
        return  mcursor;

    }
}
