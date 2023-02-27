package com.example.kelime_zar_oyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imageView1,imageView2;
    Button button1,button2;
    TextView  textView1,textView2,textView3;
    ProgressBar progressBar1;
    ListView listView1;
    int[] zar={R.drawable.zar0,R.drawable.zar1,R.drawable.zar2,R.drawable.zar3,R.drawable.zar4,R.drawable.zar5,R.drawable.zar6};
    ArrayAdapter<String> adapter;
    ArrayList<String> eklenenler=new ArrayList<String>();
    int sure=20;
    int hak=5;
    Timer zamanlayici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        button1.setEnabled(false);
        imageView1.setImageResource(zar[0]);
        imageView2.setImageResource(zar[0]);
        progressBar1.setMax(sure);
    }
    public void init(){
    imageView1=findViewById(R.id.imageView1);
    imageView2=findViewById(R.id.imageView2);
    button1=findViewById(R.id.button1);
    button2=findViewById(R.id.button2);
    textView1=findViewById(R.id.textView1);
    textView2=findViewById(R.id.textView2);
    textView3=findViewById(R.id.textView3);
    progressBar1=findViewById(R.id.progressBar1);
    listView1=findViewById(R.id.listView1);
    }


    public void sonlandır(){
        zamanlayici.cancel();
        button1.setEnabled(false);
        button2.setEnabled(true);
    }

    public void zar_at(View view) {

        textView2.setText("Hakkınız: "+hak);
        Random rastgele=new Random();
        int s1,s2,toplam;
        s1=rastgele.nextInt(6)+1;
        s2=rastgele.nextInt(6)+1;
        toplam=s1+s2;
        imageView1.setImageResource(zar[s1]);
        imageView2.setImageResource(zar[s2]);
        textView1.setText("Toplam: "+toplam);

        if(toplam==7||toplam==11){
            Toast.makeText(this, "Kazandınız! ", Toast.LENGTH_SHORT).show();
            sonlandır();
        }
        else if(toplam==2||toplam==3||toplam==12){
            Toast.makeText(this, "Kaybettiniz! ", Toast.LENGTH_SHORT).show();
            sonlandır();
        }
        else {
            eklenenler.add("Birinci Zar: "+s1+" İkinci Zar: "+s2+" Toplam: "+toplam);
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, eklenenler);
            listView1.setAdapter(adapter);
        }
        hak--;
        if (hak==0){
            sonlandır();
            Toast.makeText(this, "Hakkınız Doldu! ", Toast.LENGTH_SHORT).show();
        }

    }


    public void yenioyun(View view) {
        zamanlayici=new Timer();
        TimerTask Gorev=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sure--;
                        if(sure==-1){
                            sonlandır();
                            Toast.makeText(MainActivity.this, "Süreniz Doldu!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressBar1.setProgress(sure);
                            textView3.setText("Süre: "+sure);
                        }
                    }
                });
            }
        };
        zamanlayici.schedule(Gorev,0,1000);
        button1.setEnabled(true);
        button2.setEnabled(false);
        sure=20;
        hak=5;
        textView2.setText("Hakkınız: "+hak);
        textView3.setText("Süre: "+sure);
        eklenenler.clear();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, eklenenler);
        listView1.setAdapter(adapter);
    }
}