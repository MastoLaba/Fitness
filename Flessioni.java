package it.unibs.ing.fp.fitnessunibs;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static it.unibs.ing.fp.fitnessunibs.R.id.testoCount;

public class Flessioni extends AppCompatActivity {
    private static final String TEMPO = "%2.0f:%2.0f";
    private TextView conta, time;
    Chronometer crono, cronoSupporto;
    private CountDownTimer countDownTimer;
    Button bottonePartenza, bottonePausa,bottoneTocchi,bottoneTornaHome, bottoneAbbandona;
    ArrayList<String> listaTempi = new ArrayList<>();
    int count =0;
    private int flessioniTotali = 0;
    Boolean activity = false;
    String tempoCorrente="";
    long tempoPassato=0;
    private long inizio=0;
    long durata=0;
    boolean a = true;
    boolean b=false;
    int ripe=0;
    ImageButton home;

    //Aggiungi treemap per ripetizoni e tempo
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flessioni);

        conta = (TextView) findViewById(R.id.testoNumero);
        bottonePartenza = (Button) findViewById(R.id.bottonePartenza);
        bottonePausa = (Button) findViewById(R.id.bottonePausa);
        bottoneTocchi = (Button) findViewById(R.id.bottoneTocchi);
        bottoneTornaHome = (Button) findViewById(R.id.bottoneTorna);
        bottoneAbbandona = (Button) findViewById(R.id.bottoneAbbandona);
        time = (TextView) findViewById(testoCount);
        crono = (Chronometer) findViewById(R.id.cronometro);
        home = (ImageButton) findViewById(R.id.imageButton2);
        home.setVisibility(View.INVISIBLE);
        home.setEnabled(false);
        cronoSupporto = (Chronometer) findViewById(R.id.chronometer2);

        bottoneTocchi.setEnabled(false);
        bottonePausa.setEnabled(false);
        bottoneTornaHome.setVisibility(View.INVISIBLE);
        crono.setVisibility(View.INVISIBLE);

        final int ripetizioni = getIntent().getExtras().getInt("a");
        final int numeroFlessioni = getIntent().getExtras().getInt("b");

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(!activity){
                    long minuti=((SystemClock.elapsedRealtime()-crono.getBase())/1000)/60;
                    long secondi=((SystemClock.elapsedRealtime()-crono.getBase())/1000)%60;
                    double minutiInteri = (double) minuti;
                    double secondiInteri = (double) secondi;
                    tempoCorrente = String.format(TEMPO, minutiInteri, secondiInteri);
                    cronoSupporto.setText(tempoCorrente);
                    crono.setText(tempoCorrente);
                    tempoPassato=SystemClock.elapsedRealtime();
                }else {
                    long minuti=((tempoPassato-crono.getBase())/1000)/60;
                    long secondi=((tempoPassato-crono.getBase())/1000)%60;

                    tempoCorrente=minuti+":"+secondi;
                    crono.setText(tempoCorrente);
                    cronoSupporto.setText(tempoCorrente);
                    tempoPassato=tempoPassato + 1000;
                }


            }
        });


        bottoneTocchi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {




                time.setVisibility(View.INVISIBLE);

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        inizio=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        durata = (System.currentTimeMillis()-inizio)/1000;

                        conta.setVisibility(View.VISIBLE);

                        if(durata <=2){
                            Toast.makeText(getApplicationContext(), "Flessione non valida", Toast.LENGTH_SHORT).show();
                        }else{

                            if(count==numeroFlessioni){
                                count++;
                                if(ripe==ripetizioni) {
                                    conta.setText("Hai finito!");
                                    crono.stop();
                                    cronoSupporto.stop();
                                    home.setVisibility(View.VISIBLE);
                                    home.setEnabled(true);
                                    listaTempi.add(crono.getText().toString());
                                    bottonePartenza.setVisibility(View.INVISIBLE);
                                    bottoneAbbandona.setVisibility(View.INVISIBLE);
                                    bottonePausa.setVisibility(View.INVISIBLE);
                                    bottoneTornaHome.setVisibility(View.VISIBLE);
                                    flessioniTotali += count;
                                    String tempo = crono.getText().toString();
                                    AiutoStorico as = new AiutoStorico(getApplicationContext());
                                    GregorianCalendar giornata = new GregorianCalendar();
                                    String oggi = new String(giornata.get(Calendar.DAY_OF_MONTH)+"/"+(giornata.get(Calendar.MONTH)+1)+"/"+giornata.get(Calendar.YEAR));
                                    as.aggiungiDato(oggi, "Flessioni", String.valueOf(flessioniTotali), tempo);
                                }
                                else {
                                    conta.setVisibility(View.VISIBLE);
                                    conta.setText("Riposati");
                                    crono.stop();
                                    cronoSupporto.stop();
                                    cronoSupporto.setText(tempoCorrente);
                                    listaTempi.add(crono.getText().toString());
                                    ripe++;
                                    bottonePartenza.setEnabled(false);
                                    bottonePausa.setVisibility(View.VISIBLE);
                                    bottoneTornaHome.setVisibility(View.INVISIBLE);
                                    bottoneTocchi.setEnabled(false);
                                    bottonePausa.setEnabled(false);
                                    crono.setVisibility(View.INVISIBLE);
                                    flessioniTotali += count;
                                    count=0;
                                    time.setVisibility(View.VISIBLE);
                                    // riparte il tutto però con la pausa di 1 minuto, che si ptrebbe settare da impostazioni
                                    restart();
                                }

                            }else{
                                count++;
                                conta.setText(""+count);
                            }
                        }
                }
                return true;
            }
        });

    }





    /*public void contaFlessioni(View view) {

        time.setVisibility(View.INVISIBLE);


        if(count==14){

            conta.setText("Hai finito!");
            crono.stop();
            bottonePartenza.setVisibility(View.INVISIBLE);
            bottonePausa.setVisibility(View.INVISIBLE);
            bottoneTornaHome.setVisibility(View.VISIBLE);

        } else{
            count++;
            conta.setText(""+count);
        }


    }*/


    // TASTO PARTENZA
    public void partenza(View view) {
        time.setVisibility(View.VISIBLE);
        bottonePartenza.setEnabled(false);
        bottonePausa.setVisibility(View.VISIBLE);
        bottoneTornaHome.setVisibility(View.INVISIBLE);
        bottoneTocchi.setEnabled(false);


        if(a){
            crono.setVisibility(View.INVISIBLE);
        }else{
            crono.setVisibility(View.VISIBLE);
        }

        start();
        bottonePartenza.setEnabled(false);


    }

    // TASTO PAUSA
    public void pausa(View view) {
        bottonePausa.setEnabled(false);
        bottoneTocchi.setEnabled(false);
        bottonePartenza.setText("Riprendi");
        bottonePartenza.setEnabled(true);
        crono.stop();
        cronoSupporto.stop();
        cronoSupporto.setText(tempoCorrente);
        crono.setText(tempoCorrente);
        activity= true;


        // serve per la visibilità del cronometro dopo aver premuto pausa
        a=false;

    }

    // TASTO TORNA ALLA HOME
    public void tornaSceltaAttivita (View view) {
        Intent i = new Intent(getApplicationContext(), SceltaAttivita.class);
        startActivity(i);
        finish();
    }

    // METODO DEL CONTO ALLA ROVESCIA E PARTE IL CRONOMETRO
    private void start() {
        time.setText("3");
        time.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(4*1000, 1000){
            public void onTick(long millisUntilFinisched){
                time.setText(""+millisUntilFinisched/1000);
            }
            public void onFinish(){
                time.setText("Via!");
                bottonePausa.setEnabled(true);
                bottoneTocchi.setEnabled(true);
                crono.setVisibility(View.VISIBLE);
                //  QUA PARTE IL CRONOMETRO
                if(!activity){
                    crono.setBase(SystemClock.elapsedRealtime());
                    cronoSupporto.setBase(SystemClock.elapsedRealtime());
                    crono.start();
                    cronoSupporto.start();
                }else{
                    crono.start();
                }
                crono.setVisibility(View.VISIBLE);

            }
        };
        countDownTimer.start();
    }

    private void restart() {
        activity = false;
        time.setText("2");
        time.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(6*1000, 1000){
            public void onTick(long millisUntilFinisched){
                time.setText(""+millisUntilFinisched/1000);
            }
            public void onFinish(){
                time.setText("Via!");
                conta.setText("");
                bottonePausa.setEnabled(true);
                bottoneTocchi.setEnabled(true);
                crono.setVisibility(View.VISIBLE);
                //  QUA PARTE IL CRONOMETRO
                if(!activity){
                    crono.setBase(SystemClock.elapsedRealtime());
                    crono.start();
                }else{
                    crono.start();
                }
                if(!activity){
                    cronoSupporto.start();
                }else{
                    cronoSupporto.setBase(SystemClock.elapsedRealtime());
                    cronoSupporto.start();
                }
                crono.setVisibility(View.VISIBLE);
            }
        };
        countDownTimer.start();
    }

    public void abbandona(View view){
        final Dialog dialogo = new Dialog(this);
        dialogo.setTitle("Sicuro di voler uscire ?");
        dialogo.setCancelable(false);
        dialogo.setContentView(R.layout.dialogo_abbandona);
        Button bottoneConferma = (Button) dialogo.findViewById(R.id.tastoConfermaDialogo);
        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
        Button bottoneAnnulla = (Button) dialogo.findViewById(R.id.tastoAnnullaDialogo);
        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        dialogo.show();

    }

    public void tornaHome(View view){
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
    }


}
