package it.unibs.ing.fp.fitnessunibs;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Storico extends AppCompatActivity {

    private AiutoStorico as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        as = new AiutoStorico(getApplicationContext());
        inizializzaListView();
    }

    private void inizializzaListView(){
        Cursor c = as.getAllData();
        ArrayList<HashMap<String, Object>> data=new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            HashMap<String, Object> esercizioMap = new HashMap<>();
            esercizioMap.put("data", c.getString(1));
            esercizioMap.put("tipologia", c.getString(2));
            esercizioMap.put("ripetizioni", c.getString(3));
            esercizioMap.put("durata", c.getString(4));
            data.add(esercizioMap);
        }
        String[] from = {"data", "tipologia", "ripetizioni", "durata"};
        int[] to = {R.id.dataStoricoListView, R.id.nomeEsercizioStoricoListView, R.id.ripetizioniStoricoListView, R.id.durataStoricoListView};

        SimpleAdapter adapter2 = new SimpleAdapter(getApplicationContext(), data, R.layout.listview_storico, from, to);
        ListView elencoStorico = (ListView) findViewById(R.id.listaStoricoFlessioni);
        elencoStorico.setAdapter(adapter2);
        elencoStorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getElemento(String.valueOf(i+1));
            }
        });
    }

    //Si potrebbe eliminare ma nel caso di trasformazini di dati puo essere utile
    public void aggiungiDato(String data, String nomeEsercizio, String ripetizioni, String durata){
        as.aggiungiDato(data, nomeEsercizio, ripetizioni, durata);
    }

    /**
     * Restituisce una stringa che descrive una riga
     * @param id id della riga che sara descritta nella stringa
     * @return stringa descrittiva della riga scelta
     */
    public String getElemento(String id){
        Cursor c = as.getElemento(id);
        StringBuffer elemento = new StringBuffer();
        elemento.append("ID = "+c.getString(0)+"\n");
        elemento.append("DATA = "+c.getString(1)+"\n");
        elemento.append("RIPETIZIONI = "+c.getString(2)+"\n");
        elemento.append("TIPOLOGIA = "+c.getString(3)+"\n");
        elemento.append("DURATA = "+c.getString(4)+"\n");
        showMessage("Elemento "+id, elemento.toString());
        return new String(elemento);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



    public void viewAll(){
        Cursor res = as.getAllData();
        //se vuoto
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("DATA : "+ res.getString(1) + "\n");
            buffer.append("RIPETIZIONI : "+ res.getString(2) + "\n");
            buffer.append("TIPOLOGIA : "+ res.getString(3) + "\n");
            buffer.append("DURATA : "+ res.getString(4) + "\n\n\n");
        }
        showMessage("Totale", buffer.toString());
    }




}
