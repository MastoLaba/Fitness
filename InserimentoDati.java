package it.unibs.ing.fp.fitnessunibs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InserimentoDati extends AppCompatActivity {
    private String nomeUtente = "", lingua = "a";
    private static final String MY_PREF = "My pref";
    private EditText nome, eta, altezza, peso, cognome;
    private RadioButton maschio, femmina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_dati);

        if(getIntent().getStringExtra("lingua") != null)
            lingua = getIntent().getStringExtra("lingua");

        nome = (EditText) findViewById(R.id.editNome);
        eta = (EditText) findViewById(R.id.editEta);
        altezza = (EditText) findViewById(R.id.editAltezza);
        peso = (EditText) findViewById(R.id.editPeso);
        cognome = (EditText) findViewById(R.id.editCognome);
        maschio = (RadioButton) findViewById(R.id.radioM);
        femmina = (RadioButton) findViewById(R.id.radioF);

        SharedPreferences salvataggio = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        nome.setText(salvataggio.getString("Nome", ""));
        eta.setText(salvataggio.getString("Età", ""));
        peso.setText(salvataggio.getString("Peso", ""));
        altezza.setText(salvataggio.getString("Altezza", ""));
        cognome.setText(salvataggio.getString("Cognome", ""));
        maschio.setChecked(salvataggio.getBoolean("Maschio", false));
        femmina.setChecked(salvataggio.getBoolean("Femmina", false));

    }

    /**
     * Metodo richiamato per salvare i dati inseriti
     * @param view bottoneSalva
     */
    public void onDateSave(View view) {
        SharedPreferences salvataggio = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = salvataggio.edit();
        editor.putString("Nome", nome.getText().toString());
        editor.putString("Età", eta.getText().toString());
        editor.putString("Cognome", cognome.getText().toString());
        editor.putString("Altezza", altezza.getText().toString());
        editor.putString("Peso", peso.getText().toString());
        editor.putBoolean("Femmina", femmina.isChecked());
        editor.putBoolean("Maschio", maschio.isChecked());
        editor.apply();
        editor.commit();
        nomeUtente = salvataggio.getString("Nome", "");
        salvaPrimoAccesso();
        Toast.makeText(getApplicationContext(), "Salvataggio riuscito " + nome.getText(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Home.class);
        i.putExtra("nomeUtente", nomeUtente);
        i.putExtra("lingua", lingua);
        startActivity(i);
    }

    /**
     * Metodo richiamato per ripristinare
     * @param view bottoneRipristino
     */
    public void onDateRipristin(View view) {
        nome.setText((""));
        eta.setText((""));
        cognome.setText((""));
        peso.setText((""));
        altezza.setText((""));
        maschio.setChecked(false);
        femmina.setChecked(false);

        SharedPreferences salvataggio = getSharedPreferences(MY_PREF, 0);
        SharedPreferences.Editor editor = salvataggio.edit();
        editor.putString("Nome", nome.getText().toString());
        editor.putString("Età", eta.getText().toString());
        editor.putString("Cognome", cognome.getText().toString());
        editor.putString("Altezza", altezza.getText().toString());
        editor.putString("Peso", peso.getText().toString());
        editor.putBoolean("Femmina", femmina.isChecked());
        editor.putBoolean("Maschio", maschio.isChecked());
        editor.apply();
        Toast.makeText(this, "Dati cancellati ", Toast.LENGTH_SHORT).show();
    }

    public void salvaPrimoAccesso() {
        String filePath = getApplicationContext().getFilesDir().getPath() + "/" + getString(R.string.filePrimoAccesso);
        File file = new File(filePath);
        boolean create = false;
        do {
            if (file.exists()) {
                try {
                    BufferedWriter scrivi = new BufferedWriter(new FileWriter(file));
                    scrivi.write(nomeUtente);
                    scrivi.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                } }else
            {
                try {
                    create = file.createNewFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } while (create);
        return;
    }

    private String leggiPrimoAccesso(){
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
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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