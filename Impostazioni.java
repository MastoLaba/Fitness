package it.unibs.ing.fp.fitnessunibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Impostazioni extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

    }

    /**
     * Metodo che gestisce la pressione del tasto modifica del nome utente
     * @param view il bottone bottoneCambiaNome
     */
    public void onClickCambiaNomeUtente(View view) {
        Intent i = new Intent(getApplicationContext(), InserimentoDati.class);
        i.putExtra("impostazioni", true);
        startActivity(i);
    }

    public void onClickCambiaLingua(View view) {
        Intent i = new Intent(getApplicationContext(), SceltaLingua.class);
        i.putExtra("impostazioni", true);
        startActivity(i);

    }

    public void onClickRiconoscimenti(View view) {

        Intent i = new Intent(getApplicationContext(), Riconoscimenti.class);
        startActivity(i);

    }
}
