package com.example.textquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onAcceptClick(View view){
        EditText e = (EditText) findViewById(R.id.input_log);
        String text = e.getText().toString();
        sendResult(text);
    }

    void sendResult(String text){
        Intent i = new Intent();
        i.putExtra("login",text);
        if (text.equals("")) {
            setResult(0, i); // пустой ввод
        }else{
            setResult(1, i); // не пустой ввод
        }
        EditText e = (EditText) findViewById(R.id.input_log);
        e.setText("");
        finish();
    }
    public void finishActivity(View view){
        Intent i = new Intent();
        setResult(0, i);
        EditText e = (EditText) findViewById(R.id.input_log);
        e.setText("");
        finish();
    }
}