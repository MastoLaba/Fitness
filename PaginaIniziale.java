package it.unibs.ing.fp.fitnessunibs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class PaginaIniziale extends AppCompatActivity{
    private String nomeUtente = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_iniziale);

        String nome = leggiPrimoAccesso();
        if(nome != null)
            nomeUtente = nome;
        Toast.makeText(getApplicationContext(), String.valueOf(nomeUtente), Toast.LENGTH_SHORT).show();


        int timeout = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(nomeUtente.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), SceltaLingua.class);
                    startActivity(intent);
                    PaginaIniziale.this.finish();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    i.putExtra("nomeUtente", nomeUtente);
                    startActivity(i);
                    PaginaIniziale.this.finish();
                }
            }
        },timeout);
    }

    public String leggiPrimoAccesso(){
        String filePath = getApplicationContext().getFilesDir().getPath() + "/" + getString(R.string.filePrimoAccesso);
        File f = new File(filePath);
        String lettura = "";
        boolean creato = false;
        do {
            if (f.exists()) {
                try {
                    BufferedReader leggi = new BufferedReader(new FileReader(f));
                    lettura = leggi.readLine();
                    leggi.close();
                    creato = false;
                    if (lettura != null)
                        return lettura;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    creato = f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }while(creato);
        return lettura;
    }
}
