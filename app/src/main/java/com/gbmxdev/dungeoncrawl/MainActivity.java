package com.gbmxdev.dungeoncrawl;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Called when the user taps the Send button
     */
    public void sendCMD(View view) throws IOException {
        int c1;
        // Do something in response to button
        System.out.println("Pressed button");
        c1 = System.in.read();
        System.out.println(c1);
        game dungeon = new game();
        game.main();
    }
    public void sendMessage(View view) throws IOException {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}