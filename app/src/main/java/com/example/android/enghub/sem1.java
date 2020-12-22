package com.example.android.enghub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class sem1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem1);

        TextView em1 = findViewById(R.id.textView5);
        TextView phy1 = findViewById(R.id.textView6);
        TextView chem1 = findViewById(R.id.textView7);
        TextView mech1 = findViewById(R.id.textView8);
        TextView bee = findViewById(R.id.textView9);

        em1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),em1.class));
                finish();
            }
        });

        phy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),phy1.class));
                finish();
            }
        });

        chem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),chem1.class));
                finish();
            }
        });

        mech1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),mechanics.class));
                finish();
            }
        });

        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bee.class));
                finish();
            }
        });

    }
}