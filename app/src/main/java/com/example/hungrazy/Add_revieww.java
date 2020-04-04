package com.example.hungrazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Add_revieww extends AppCompatActivity {


    private TextView et_PlaceName_r;
    private TextView et_address_r;
    private RatingBar service_r;
    private RatingBar food_r;
    private RatingBar ambiance_r;
    private RatingBar price_r;
    private TextView os_r;
    private Button btn_Review_r;
    public String type;
    public String childName;
    private ImageView i;

    float r1 , r2 , r3 ,r4, avgRvwDB , avg , r1DB ,r2DB , r3DB , r4DB;
    String sugg , suggDB ;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_revieww);

        et_PlaceName_r =findViewById(R.id.textView12);
        i = findViewById(R.id.imageView7);
        service_r = findViewById(R.id.ratingBar1_r);
        food_r = findViewById(R.id.ratingBar2_r);
        ambiance_r = findViewById(R.id.ratingBar3_r);
        price_r = findViewById(R.id.ratingBarppp);
        os_r = (EditText)findViewById(R.id.et_os_r);
        btn_Review_r = findViewById(R.id.btn_addReview);
        et_address_r = findViewById(R.id.tv_adress_r);

        Intent intent = getIntent();
        type = intent.getStringExtra("typeOfPlace");
        childName = intent.getStringExtra("childPlaceName");

        et_PlaceName_r.setText(intent.getStringExtra("title"));
        et_address_r.setText(intent.getStringExtra("address"));
        Picasso.with(this)
                .load(intent.getStringExtra("imgUrl"))
                .into(i);

        btn_Review_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(Add_revieww.this,type,Toast.LENGTH_SHORT).show();
                //Toast.makeText(Add_revieww.this,childName,Toast.LENGTH_SHORT).show();

                updateReview();
            }
        });

    }


    private void updateReview() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("PLACES").child(type);

//         float r1 , r2 , r3 , avgRvw , r1DB ,r2DB , r3DB;
//        String sugg , suggDB;

        r1 = service_r.getRating();
        r2 = food_r.getRating();
        r3 = ambiance_r.getRating();
        r4 = price_r.getRating();
        sugg = os_r.getText().toString();

        //myRef.child("PLACES").child("Cafe").child("DOMINOS PIZZA INDRAPURI BHOPAL").child("food");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(childName).exists()))
                {
                    Toast.makeText(Add_revieww.this,childName + " Not found. Database Error.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                     r1DB = Float.parseFloat(dataSnapshot.child(childName).child("service").getValue().toString());
                     r2DB = Float.parseFloat(dataSnapshot.child(childName).child("food").getValue().toString());
                     r3DB = Float.parseFloat(dataSnapshot.child(childName).child("ambiance").getValue().toString());
                     r4DB = Float.parseFloat(dataSnapshot.child(childName).child("price").getValue().toString());

                    avgRvwDB = Float.parseFloat(dataSnapshot.child(childName).child("avgReview").getValue().toString());
                     n = Integer.parseInt(dataSnapshot.child(childName).child("numOfReview").getValue().toString());
                     if( dataSnapshot.child(childName).child("otherSuggestion").exists())
                    {
                       suggDB =  dataSnapshot.child(childName).child("otherSuggestion").getValue().toString();
                    }

                    Toast.makeText(Add_revieww.this,"Data Fetched",Toast.LENGTH_SHORT).show();

                    updateEveryThing();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }//end of updateReview

    private void updateEveryThing() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("PLACES").child(type).child(childName);

        r1 = (r1DB*n + r1)/(n+1);
        r2 = (r2DB*n + r2)/(n+1);
        r3 = (r3DB*n + r3)/(n+1);
        r4 = (r4DB*n + r4)/(n+1);

        avg = ((avgRvwDB*n)+(r1+r2+r3+r4)/4)/(n+1);
        if (sugg!=null){
            sugg = sugg +"\n"+suggDB;
        }else{
            sugg = suggDB;
        }

        n=n+1;

        myRef.child("service").setValue(r1);
        myRef.child("food").setValue(r2);
        myRef.child("ambiance").setValue(r3);
        myRef.child("price").setValue(r4);
        myRef.child("avgReview").setValue(avg);
        myRef.child("numOfReview").setValue(n);
        myRef.child("otherSuggestion").setValue(sugg);

        //Toast.makeText(Add_revieww.this,"Updated",Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(Add_revieww.this);
        builder.setTitle("Successful");
        builder.setMessage("Review successfully ADDED. Press button to go back..");

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Add_revieww.this,HomePage.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
