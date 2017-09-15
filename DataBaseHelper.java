package it.unibs.ing.fp.fitnessunibs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Classe per l'utilizzo di un database
 * Created by brescia on 08/09/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EserciziData.db";
    private static final String TABLE_NAME = "esercizi_data_table";

    private static final String COL_1 = "ID";
    private static final String COL_2 = "DATA";
    private static final String COL_3 = "RIPETIZIONI";
    private static final String COL_4 = "TIPOLOGIA";
    private static final String COL_5 = "DURATA";


    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATA TEXT,RIPETIZIONI TEXT,TIPOLOGIA TEXT, DURATA INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserisce una nuova attività all'interno del database
     * @param data data in cui si è svolta l'attività
     * @param ripetizioni numero di volte che è stata fatta l'attività
     * @param tipologia tipo di atività svolta
     * @param durata per quanto tempo è stata fatta l'attività
     * @return true se il dato è stato aggiunto correttamente, altrimenti false
     */
    public boolean insertData(String data, String ripetizioni, String tipologia, String durata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, data);
        contentValues.put(COL_3, ripetizioni);
        contentValues.put(COL_4, tipologia);
        contentValues.put(COL_5, durata);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Restituisce tutte le informazioni all'interno del database
     * @return un cursore che si sposta lungo le righe del database e attraverso il quale si possono ottenere informazioni
     */
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }

    /**
     * Aggiorna i dati presenti nel database
     * @param id id che si vuole modificare
     * @param data data che deve essere modificata
     * @param ripetizioni ripetizoni che devono essere modificate
     * @param tipologia tipologia che deve essere moidificata
     * @param durata durata che deve essere modificata
     * @return restituice true perchè l'aggionarmento è andato a buon fine
     */
    public boolean updateData(String id, String data, String ripetizioni, String tipologia, String durata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, data);
        contentValues.put(COL_3, ripetizioni);
        contentValues.put(COL_4, tipologia);
        contentValues.put(COL_5, durata);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    /**
     * Metodo per eliminare un dato dal database
     * @param id valore univoco per ogni elemento presente nel database
     * @return
     */
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        return i;
    }
}
