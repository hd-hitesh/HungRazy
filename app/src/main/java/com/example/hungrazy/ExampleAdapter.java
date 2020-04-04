package com.example.hungrazy;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private List<Datas> mExampleList;
    // private OnItemClickListener mListener;
    Context mContext;

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }

//    public void setOnItemClickListener (OnItemClickListener listener){
//        mListener = listener;
//    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public RatingBar rrrr;

        public ExampleViewHolder(View itemView) {//, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textview1);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView5);
            mTextView4 = itemView.findViewById(R.id.textView19);
            rrrr = itemView.findViewById(R.id.ratingBar_rrrrrr);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener!=null){
//                        int position = getAdapterPosition();
//                        if(position!=RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });

        }

    }


    public ExampleAdapter(Context Context, List<Datas> exampleList) {
        mExampleList = exampleList;
        mContext = Context;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v); // , mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final Datas currentItem = mExampleList.get(position);

        //String q1= Float.toString(currentItem.getAvgReview());
        DecimalFormat df = new DecimalFormat("#.##");


        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getLocality());
        holder.mTextView3.setText(currentItem.getCity());
        holder.rrrr.setRating(currentItem.getAvgReview());
        holder.mTextView4.setText(df.format(currentItem.getAvgReview()));
        holder.rrrr.setIsIndicator(true);
        Toast.makeText(mContext,"Loading..Have Patience..",Toast.LENGTH_SHORT).show();
        Picasso.with(mContext)
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_android_photo)
                .fit()
                .centerCrop()
                .into(holder.mImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Show.class);
                intent.putExtra("name",currentItem.getName());
                intent.putExtra("type",currentItem.getType());
                intent.putExtra("locality",currentItem.getLocality());
                intent.putExtra("city",currentItem.getCity());
                intent.putExtra("food",currentItem.getFood());
                intent.putExtra("service",currentItem.getService());
                intent.putExtra("ambiance",currentItem.getAmbiance());
                intent.putExtra("price",currentItem.getPrice());
                intent.putExtra("avgRating",currentItem.getAvgReview());
                intent.putExtra("os",currentItem.getOtherSuggestion());
                intent.putExtra("imgUrl",currentItem.getImageUrl());

                v.getContext().startActivity(intent);
                Toast.makeText(mContext, currentItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
