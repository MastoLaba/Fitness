package it.unibs.ing.fp.fitnessunibs;

import android.content.Context;
import android.database.Cursor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by brescia on 08/09/2017.
 */


public class AiutoStorico {
    private DataBaseHelper db;
    private Date inizio = null;
    private LocalDateTime inizioAttivita = null;
    private int numeroRipetizioni;

    public AiutoStorico(Context context){
        db = new DataBaseHelper(context);
    }


    public void aggiungiDato(String data, String nomeEsercizio, String ripetizione, String durata){
        db.insertData(data, nomeEsercizio, ripetizione, durata);
    }

    public Cursor getAllData(){
        return db.getAllData();
    }

    public boolean eliminaDato(String id){
        int risultato = db.deleteData(id);
        if(risultato == -1)
            return false;
        else
            return true;
    }

    public Cursor getElemento(String id){
        Cursor c = db.getAllData();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if(c.getString(0).equals(id))
                return c;
        }
        return null;
    }

}
