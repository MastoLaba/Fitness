package it.unibs.ing.fp.fitnessunibs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalizzaFlessioni extends AppCompatActivity {


    private static SeekBar seek_bar , seek_bar1;
    private static TextView text_view , text_view1;
    int a=0;
    int b=0;



    private static final String MY_PREF = "My pref";
    private EditText serie,ripetizioni;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizza_flessioni);

        /*serie = (EditText) findViewById(R.id.serie);
        ripetizioni = (EditText) findViewById(R.id.ripetizioni);  */
        ok = (Button) findViewById(R.id.ok);


        see();

    }


    public void see(){

        seek_bar= (SeekBar) findViewById(R.id.seekBar);
        seek_bar1= (SeekBar) findViewById(R.id.seekBar1);
        text_view= (TextView) findViewById(R.id.textView);
        text_view1= (TextView) findViewById(R.id.textView1);
        text_view.setText("valore "+ seek_bar.getProgress()+"/"+seek_bar.getMax());

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valore;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valore = progress;
                text_view.setText("valore "+ progress +"/"+seek_bar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                a = seek_bar.getProgress();
                text_view.setText("valore "+ valore +"/"+seek_bar.getMax());
            }
        });

        seek_bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valore;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valore = progress;
                text_view1.setText("valore "+ progress +"/"+seek_bar1.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                b = seek_bar1.getProgress();
                text_view1.setText("valore "+ valore +"/"+seek_bar1.getMax());
            }
        });
    }

    public void salva(View view){
        Intent i =new Intent(getApplicationContext(),Flessioni.class);
        i.putExtra("a",--a);
        i.putExtra("b",--b); //un numero in meno rispetto quelle che voglio fare
        startActivity(i);
        finish();
    }
}
