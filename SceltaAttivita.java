package it.unibs.ing.fp.fitnessunibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SceltaAttivita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scelta_attivita);
    }


    /**
     * Metodo che permette di iniziare l'attività corrispondente
     * @param view La view che è stata premuta
     */
    public void onClickAddominali(View view) {
        Intent addominali = new Intent(getApplicationContext(), Home.class);
        startActivity(addominali);
        finish();
    }

    /**
     * Metodo che permette di iniziare l'attività corrispondente
     * @param view La view che è stata premuta
     */
    public void onClickFlessioni(View view) {
        Toast.makeText(getApplicationContext(), "Aprire activity", Toast.LENGTH_SHORT).show();
        Intent flessioni = new Intent(getApplicationContext(), SerieFlessioni.class);
        startActivity(flessioni);
        finish();
    }

    /**
     * Metodo che permette di iniziare l'attività corrispondente
     * @param view La view che è stata premuta
     */
    public void onClickSquat(View view) {
        Intent squat = new Intent(getApplicationContext(), Home.class);
        startActivity(squat);
        finish();
    }
}
