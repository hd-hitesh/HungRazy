package com.example.hungrazy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hungrazy.R;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

     Button btn_cafe ;
     Button btn_lounge;
     Button btn_restro;
     Button btn_street;
     Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn_cafe = findViewById(R.id.btn_cafe);
        btn_lounge = findViewById(R.id.btn_lounge);
        btn_restro = findViewById(R.id.btn_restrnt);
        btn_street = findViewById(R.id.btn_streetfood);
        btn_add = findViewById(R.id.btn_add);


        btn_cafe.setOnClickListener(this);
        btn_lounge.setOnClickListener(this);
        btn_restro.setOnClickListener(this);
        btn_street.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_cafe: {

                Toast.makeText(this, "Cafe Button Pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListPage.class);
                intent.putExtra("type","Cafe");
                startActivity(intent);
                break;
            }

            case R.id.btn_lounge: {

                Toast.makeText(this, "Lounge button pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListPage.class);
                intent.putExtra("type","Lounge");

                startActivity(intent);
                break;
            }

            case R.id.btn_restrnt: {

                Toast.makeText(this, "Restaurant button pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListPage.class);
                intent.putExtra("type","Restaurant");

                startActivity(intent);
                break;
            }

            case R.id.btn_streetfood: {

                Toast.makeText(this, "Street food button pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListPage.class);
                intent.putExtra("type","Street Food");
                startActivity(intent);
                break;
            }

            case R.id.btn_add: {

                Toast.makeText(this, "Add button pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Add.class);
                startActivity(intent);
                break;
            }
        }//end of switch

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}
