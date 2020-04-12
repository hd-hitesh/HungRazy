package com.example.hungrazy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hungrazy.Datas;
import com.example.hungrazy.ExampleAdapter;
import com.example.hungrazy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPage extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Datas> mExampleList;
    private String type;
    int cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

         mExampleList = new ArrayList<>();

        createExampleList();
        //buildRecyclerView();

//        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                 mExampleList.get(position);
//
//                Intent intent = new Intent(ListPage.this, Show.class);
//                startActivity(intent);
//            }
//        });
    }

    private void createExampleList()
    {
        final String a[] = new String[5], b[]=new String[5];
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("PLACES").child(type);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.exists()))
                {
                    Toast.makeText(ListPage.this,"Data Not found. Database Error.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(ListPage.this,"Data found.",Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Datas exampleItem = snapshot.getValue(Datas.class);
                        mExampleList.add(exampleItem);

                    }
                    mAdapter = new ExampleAdapter(ListPage.this,mExampleList);
                    mRecyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListPage.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

       // for(int i=0;i<10;i++) {
            //mExampleList.add(new ExampleItem(R.drawable.ic_android_photo, " 888", "Line 2"));
       // }

    }


    private void buildRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(ListPage.this,mExampleList);

        mRecyclerView.setAdapter(mAdapter);
    }

}
