package it.unibs.ing.fp.fitnessunibs;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

public class SceltaLingua extends AppCompatActivity {
    private String lingua = "";
    private boolean impostazioni = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scelta_lingua);

        ImageButton italiano = (ImageButton) findViewById(R.id.bottoneItaliano);
        ImageButton inglese = (ImageButton) findViewById(R.id.bottoneInglese);
        ImageButton spagnolo = (ImageButton) findViewById(R.id.bottoneSpagnolo);

        impostazioni = getIntent().getBooleanExtra("impostazioni", false);
        italiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lingua("it");
                lingua = "it";
                if(impostazioni) {
                    Intent j = new Intent(getApplicationContext(), Home.class);
                    j.putExtra("lingua", lingua);
                    startActivity(j);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), InserimentoDati.class);
                    i.putExtra("lingua", lingua);
                    startActivity(i);
                }
            }
        });

        inglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lingua("en");
                lingua = "en";
                Intent i = new Intent(getApplicationContext(), InserimentoDati.class);
                i.putExtra("lingua", lingua);
                startActivity(i);

            }
        });
        spagnolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lingua("es");
                lingua = "es";
                Intent i = new Intent(getApplicationContext(), InserimentoDati.class);
                i.putExtra("lingua", lingua);
                startActivity(i);
            }


        });
    }

    public void lingua(String lng){
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
    /*INUTILE
    private class BackGround extends AsyncTask <Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Intent i = new Intent(getApplicationContext(), PaginaIniziale.class);
            i.putExtra("lingua", lingua);
            startActivity(i);
            return null;
        }
    }
    */

}
