package it.unibs.ing.fp.fitnessunibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SerieFlessioni extends AppCompatActivity {

    Button bottone1, bottone2, bottone3, bottone4, bottonePersonalizza;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_flessioni);

        bottone1 = (Button) findViewById(R.id.bottone1);
        bottone2 = (Button) findViewById(R.id.bottone2);
        bottone3 = (Button) findViewById(R.id.bottone3);
        bottone4 = (Button) findViewById(R.id.bottone4);
        bottonePersonalizza = (Button) findViewById(R.id.bottonePersonalizza);

        bottone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Flessioni.class);
                i.putExtra("a",2);
                i.putExtra("b",14); //un numero in meno rispetto quelle che voglio fare
                startActivity(i);
                finish();

            }
        });


        bottone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Flessioni.class);
                i.putExtra("a",3);
                i.putExtra("b",14); //un numero in meno rispetto quelle che voglio fare
                startActivity(i);
                finish();
            }
        });

        bottone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Flessioni.class);
                i.putExtra("a",2);
                i.putExtra("b",19); //un numero in meno rispetto quelle che voglio fare
                startActivity(i);
                finish();
            }
        });

        bottone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Flessioni.class);
                i.putExtra("a",1);
                i.putExtra("b",3); //un numero in meno rispetto quelle che voglio fare
                startActivity(i);
                finish();
            }
        });


        bottonePersonalizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),PersonalizzaFlessioni.class);
                startActivity(i);
                finish();
            }
        });

    }
}
