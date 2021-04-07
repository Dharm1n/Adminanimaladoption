package com.example.tempanimaladoption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker codepicker;
    EditText phoneno;
    Button send;;

    private void setclicklistner(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(MainActivity.this, manageotp.class);
                itent.putExtra("mobile", codepicker.getFullNumberWithPlus().replace(" ", ""));
                startActivity(itent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneno = (EditText) findViewById(R.id.phoneno);
        codepicker = (CountryCodePicker) findViewById(R.id.codepicker);
        codepicker.registerCarrierNumberEditText(phoneno);

        send = (Button) findViewById(R.id.sendotp);
        setclicklistner();
    }
}