package edu.ucsb.cs.cs184.jaredbjensen.jjensendrawingmultitouch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> colors = new ArrayList<Integer>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("JJensenMultitouch");

        Random random = new Random();
        for (int i=0; i<4; i++) {
            int newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            while (colors.contains(newColor)) {
                newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            }
            colors.set(i, newColor);
        }

        ((MySurfaceView) findViewById(R.id.surfaceView)).setColors(colors);
    }

    public void onButtonTouched(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_clear:
                ((MySurfaceView) findViewById(R.id.surfaceView)).clearCanvas();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}