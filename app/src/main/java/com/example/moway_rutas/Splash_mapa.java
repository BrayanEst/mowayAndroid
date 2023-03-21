package com.example.moway_rutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.moway_rutas.Recorrido.ModeloRecorrido;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_mapa extends AppCompatActivity {
    ImageView imageView;
    ModeloRecorrido itemDetailRecorrido;

    public static String baseUrlUrlrecorrido = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_mapa);

        //statuspush();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrlUrlrecorrido));

                startActivity(intent);
                finish();
            }
        };
        Timer time = new Timer();
        time.schedule(task,2000);
    }
    private void statuspush() {
        itemDetailRecorrido = (ModeloRecorrido) getIntent().getSerializableExtra("listp");
        String nq = itemDetailRecorrido.getUrl_recorrido();
        baseUrlUrlrecorrido = "";
        baseUrlUrlrecorrido = nq;
        //Toast.makeText(this, ""+baseUrlUrlrecorrido, Toast.LENGTH_SHORT).show();
    }

}