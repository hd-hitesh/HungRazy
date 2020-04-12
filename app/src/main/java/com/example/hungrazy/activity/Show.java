package com.example.hungrazy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hungrazy.R;
import com.squareup.picasso.Picasso;

public class Show extends AppCompatActivity {

    private Button btn_addReview_s;
    private TextView placename_s;
    private TextView fb1;
    private TextView address, ac;
    private ImageView p_image_s;
    private RatingBar rf_s , rs_s , ra_s ,rp_s, aavg_s;
    private String type , iu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
 //       Toast.makeText(this,intent.getStringExtra("type"),Toast.LENGTH_SHORT).show();
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference().child("PLACES")
//                                                         .child(intent.getStringExtra("type"))
//                                                         .child(intent.getStringExtra("dbt"));

        btn_addReview_s = findViewById(R.id.btn_addReview);
        placename_s = findViewById(R.id.placeName_s);
        address = findViewById(R.id.tv_location_s);
        fb1 = findViewById(R.id.tv_fb1_s);
        rf_s = findViewById(R.id.ratingBar_fs);
        ra_s = findViewById(R.id.ratingBar_as);
        rs_s = findViewById(R.id.ratingBar_ss);
        rp_s = findViewById(R.id.ratingBar_ps);
        aavg_s = findViewById(R.id.ratingBar_AVG);
        p_image_s = findViewById(R.id.imageView6);
        ac = findViewById(R.id.textView13);

        type = intent.getStringExtra("type");
        iu = intent.getStringExtra("imgUrl");
        placename_s.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("locality"));
        ac.setText(intent.getStringExtra("city"));
        fb1.setText(intent.getStringExtra("os"));
        rf_s.setRating(intent.getFloatExtra("food",0));
        rs_s.setRating(intent.getFloatExtra("service",0));
        ra_s.setRating(intent.getFloatExtra("ambiance",0));
        rp_s.setRating(intent.getFloatExtra("price",0));
        aavg_s.setRating(intent.getFloatExtra("avgRating",0));
        Picasso.with(this)
                .load(intent.getStringExtra("imgUrl"))
                .into(p_image_s);

        rf_s.setIsIndicator(true);
        ra_s.setIsIndicator(true);
        rs_s.setIsIndicator(true);
        rp_s.setIsIndicator(true);
        aavg_s.setIsIndicator(true);


        btn_addReview_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(Show.this,Add_revieww.class);
               String childName = placename_s.getText().toString() +" "+ address.getText().toString()+" "+ ac.getText().toString();
               String ad = address.getText().toString()+" "+ac.getText().toString();
               intent.putExtra("typeOfPlace",type);
               intent.putExtra("title",placename_s.getText());
               intent.putExtra("address",ad);
               intent.putExtra("childPlaceName",childName.toUpperCase());
               intent.putExtra("imgUrl",iu);
              startActivity(intent);
            }
        });
    }

}
