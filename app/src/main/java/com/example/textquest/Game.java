package com.example.textquest;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b= (Button) findViewById(R.id.button_mood);
        b.setBackground(getDrawable(R.drawable.mood));

        b= (Button) findViewById(R.id.button_money);
        b.setBackground(getDrawable(R.drawable.money));

        b= (Button) findViewById(R.id.button_relationships);
        b.setBackground(getDrawable(R.drawable.relationships));

        b= (Button) findViewById(R.id.buttonRightEvent);
        b.setBackground(getDrawable(R.drawable.arrow_right));

        b= (Button) findViewById(R.id.buttonLeftEvent);
        b.setBackground(getDrawable(R.drawable.arrow_left));
    }
    public void onClickArrow(View view){
        Toast.makeText(this,"YES",Toast.LENGTH_SHORT).show();
    }
}