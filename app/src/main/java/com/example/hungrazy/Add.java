package com.example.hungrazy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Add extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView et_name;
    private TextView et_location;
    private TextView et_city;
    private RatingBar service;
    private RatingBar food;
    private RatingBar ambiance;
    private RatingBar price;
    private TextView os;
    private TextView tv_chooseImage;
    private Button btn_addPlace , ch;
    private ProgressBar progressBar;
    private ImageView img;


    public Uri imguri;
    private StorageTask mUploadTask;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private Datas datas;
    private String s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        radioGroup = findViewById(R.id.rg);
        et_name = (EditText)findViewById(R.id.et_name);
        img = findViewById(R.id.imageView5);
        et_location = (EditText)findViewById(R.id.et_location);
        et_city = (EditText)findViewById(R.id.et_city);
        service = findViewById(R.id.ratingBar1);
        food = findViewById(R.id.ratingBar2);
        ambiance = findViewById(R.id.ratingBar3);
        price = findViewById(R.id.ratingBarPrice);
        os = (EditText)findViewById(R.id.et_os);
        tv_chooseImage = findViewById(R.id.tv_chooseImage);
        btn_addPlace = findViewById(R.id.btn_addPlace);
        progressBar  = findViewById(R.id.progressBar);
        ch = findViewById(R.id.btn_uploadImage);

        datas = new Datas();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });

        btn_addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask!=null && mUploadTask.isInProgress())
                {
                    Toast.makeText(Add.this,"in progress",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        checkPlace();
                        //fileUploader();
                    }
            }
        });

    }

    private void checkPlace() {

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name , city , locality , type , childName;
                name = et_name.getText().toString();
                locality = et_location.getText().toString();
                city = et_city.getText().toString();
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                type = radioButton.getText().toString();
                childName = name.toUpperCase()+" "+locality.toUpperCase()+" "+city.toUpperCase();


                if(!(dataSnapshot.child( "PLACES" ).child(type).child(childName).exists()))
                {
                    fileUploader();
                }
                else
                {
                    Toast.makeText( Add.this, name +type+" in "+ locality +" "+city + " already exist.", Toast.LENGTH_SHORT ).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imguri = data.getData();
            tv_chooseImage.setText(System.currentTimeMillis()+"."+getExtension(imguri));
            img.setImageURI(imguri);
        }
    }

    private void Filechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    /* ********************************************************************** */

    private void fileUploader(){

        if(imguri !=null){

            final String imageid = System.currentTimeMillis()+"."+getExtension(imguri);
            final StorageReference fileReferecnce = mStorageRef.child(imageid);

            mUploadTask =  fileReferecnce.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },3000);

                            Toast.makeText(Add.this,"Image Uploaded",Toast.LENGTH_SHORT).show();

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    s1 = uri.toString();
                                    addPlaceToDatabase(s1,imageid);

                                }
                            });
                        } //end of onSuccess
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        {
                            Toast.makeText(Add.this, exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Toast.makeText(Add.this,"IN progress",Toast.LENGTH_SHORT).show();
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });

        }
        else
        {
            Toast.makeText(Add.this,"Choose image pls",Toast.LENGTH_SHORT).show();
        }

    }//end of fileUploader

    private void addPlaceToDatabase(String s1, String imageid) {

        String name , city , locality , sugg , type , childName;
        float r1 , r2 , r3 ,r4, avgRvw;
        int rbid, numOfRvw;
        double l1 , l2;

        rbid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(rbid);
        type = radioButton.getText().toString();

        name = et_name.getText().toString();
        locality = et_location.getText().toString();
        city = et_city.getText().toString();

        r1 = service.getRating();
        r2 = food.getRating();
        r3 = ambiance.getRating();
        r4 = price.getRating();
        l1 = 0.0;
        l2 = 0.0;
        numOfRvw = 1;
        avgRvw = (r1+r2+r3)/3;

        sugg = os.getText().toString();


        datas.setName(name);
        datas.setLocality(locality);
        datas.setCity(city);
        datas.setService(r1);
        datas.setFood(r2);
        datas.setAmbiance(r3);
        datas.setPrice(r4);
        datas.setImageid(imageid);
        datas.setImageUrl(s1);
        datas.setLattitude(l1);
        datas.setLongitude(l2);
        datas.setNumOfReview(numOfRvw);
        datas.setAvgReview(avgRvw);
        datas.setOtherSuggestion(sugg);
        datas.setType(type);

        childName = name.toUpperCase()+" "+locality.toUpperCase()+" "+city.toUpperCase();

        mDatabaseRef.child("PLACES").child(type).child(childName).setValue(datas);

       // Toast.makeText(Add.this,"Data Uploaded",Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(Add.this);
        builder.setTitle("Successful");
        builder.setMessage("Place successfully ADDED. Press Ok to ReDirect to Homepage.");

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Add.this,HomePage.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        //Toast.makeText(Add.this,s1,Toast.LENGTH_SHORT).show();

    }




    /* ********************************************************************** */

}//end of class
