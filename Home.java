package it.unibs.ing.fp.fitnessunibs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Home extends AppCompatActivity {

    //private ImageView immagineProfilo;
    private String nomeUtente = "", lingua = "";
    private int flessioniCompiute;
    private int addominaliCompiuti;
    private int squatCompiuti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leggiLingua();
        lingua(lingua);
        setContentView(R.layout.activity_home);

        if(getIntent().getStringExtra("nomeUtente") != null)
            nomeUtente = getIntent().getStringExtra("nomeUtente");
        else
            nomeUtente = leggiPrimoAccesso();
        flessioniCompiute = 1000;
        addominaliCompiuti = 1000;
        squatCompiuti = 1000;


        /*
        * immagineProfilo = (ImageView) findViewById(R.id.immagineProfilo);
        * immagineProfilo.setImageResource(R.drawable.apertura);  Sistemare situazione qualità immagine e sostituire con immagine del profilo
        */
        TextView testoBenvenuto = (TextView) findViewById(R.id.testoBenvenuto);
        testoBenvenuto.append(nomeUtente);

        TextView testoFlessioni = (TextView) findViewById(R.id.statFlessioni);
        testoFlessioni.append(String.valueOf(flessioniCompiute));

        TextView testoAddominali = (TextView) findViewById(R.id.statAddominali);
        testoAddominali.append(String.valueOf(addominaliCompiuti));

        TextView testoSquat = (TextView) findViewById(R.id.statSquat);
        testoSquat.append(String.valueOf(squatCompiuti));

        Button bottoneStorico = (Button) findViewById(R.id.bottoneStorico);
        bottoneStorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Storico.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.impostazioni:
                Intent aImpo = new Intent(getApplicationContext(), Impostazioni.class);
                String pkg = getPackageName();
                aImpo.putExtra(pkg + ".nome_utente", nomeUtente);
                startActivity(aImpo);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    /**
     * Metodo che gestisce il bottone dell'attività e porta alla scelta degli esercizi
     * @param view il bottone bottoneAttività
     */
    public void onClickAllenati(View view) {
        Intent i = new Intent(this, SceltaAttivita.class);
        startActivity(i);
    }


    /**
     * Metodo che salva il nomeUtente sul file
     * @return true se ha salvato, false altrimenti
     */
    private boolean salvaNomeUtente(){
        String filePath = getApplicationContext().getFilesDir().getPath() + "/" + getString(R.string.filePrimoAccesso);
        File f = new File(filePath);
        boolean creato = false;
        do{
            if(f.exists()){
                try {
                    FileWriter scrivi = new FileWriter(f);
                    scrivi.write(nomeUtente);
                    scrivi.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    creato = f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }while(creato);
        return false;
    }


    @Override
    protected void onRestart() {
        Thread t = new Thread(secondoThread);
        t.start();
        super.onRestart();
    }

    private Runnable secondoThread = new Runnable(){
        @Override
        public void run() {
            updateUI();
        }
    };

    private void updateUI(){
        Message msg = new Message();
        Bundle b = new Bundle();
        msg.setData(b);
        nomeUtente = getIntent().getExtras().getString("nomeUtente");
        b.putString("nomeUtente", nomeUtente);
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            String key = b.getString("nomeUtente");
            TextView testoDaAggiornare = (TextView) findViewById(R.id.testoBenvenuto);
            String benvenuto = getString(R.string.stringaBenvenuto);
            testoDaAggiornare.setText(benvenuto + key);
        }
    };

    /**
     * Cambia la lingua dell'app
     * @param lng lingua che si desidera avere nell'app
     */
    public void lingua(String lng){
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void leggiLingua(){
        SharedPreferences linguaSalvata = getSharedPreferences("linguaSalvata", Context.MODE_PRIVATE);
        String daIntent = getIntent().getStringExtra("lingua");
        if(daIntent == null){
            if (!linguaSalvata.getString("lingua", "").equals(""))
                lingua = linguaSalvata.getString("lingua", "");
        }
        else if(daIntent.equals("a")) {
            if (!linguaSalvata.getString("lingua", "").equals(""))
                lingua = linguaSalvata.getString("lingua", "");
        }
        else {
            if(!daIntent.equals(""))
                lingua = getIntent().getStringExtra("lingua");
            SharedPreferences.Editor modificaLingua = linguaSalvata.edit();
            modificaLingua.putString("lingua", lingua);
            modificaLingua.commit();
            modificaLingua.apply();
        }
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