package edu.ucsb.cs.cs184.jaredbjensen.jjensendrawingmultitouch;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> colors = new ArrayList<>();
    Random random = new Random();
    MySurfaceView surfaceView;
    Button[] buttons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("JJensenMultitouch");

        surfaceView  = (MySurfaceView) findViewById(R.id.surfaceView);

        for (int i=0; i<4; i++) {
            int newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            while (colors.contains(newColor) || newColor == Color.BLACK) {
                newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            }
            colors.add(i, newColor);
        }

        buttons[0] = (Button) findViewById(R.id.finger1);
        buttons[1] = (Button) findViewById(R.id.finger2);
        buttons[2] = (Button) findViewById(R.id.finger3);
        buttons[3] = (Button) findViewById(R.id.finger4);

        buttons[0].setBackgroundColor(colors.get(0));
        buttons[1].setBackgroundColor(colors.get(1));
        buttons[2].setBackgroundColor(colors.get(2));
        buttons[3].setBackgroundColor(colors.get(3));

        surfaceView.setColors(colors);
    }

    public void onButtonTouched(View v) {
        String button = v.getTag().toString();
        final int buttonIndex = Integer.valueOf(button);

        int newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        while (colors.contains(newColor) || newColor == Color.BLACK) {
            newColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
        colors.set(buttonIndex, newColor);
        buttons[buttonIndex].setBackgroundColor(newColor);

        final int color = newColor;
        runOnUiThread(new Runnable() {
            public void run() {
                surfaceView.setColor(buttonIndex, color);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_clear:
                surfaceView.clearCanvas();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}