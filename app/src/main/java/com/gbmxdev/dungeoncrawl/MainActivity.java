package com.gbmxdev.dungeoncrawl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) throws IOException {
        int c1;
        // Do something in response to button
        System.out.println("Pressed button");
        c1 = System.in.read();
        System.out.println(c1);
        game dungeon = new game();
        game.main( "");


    }
}
