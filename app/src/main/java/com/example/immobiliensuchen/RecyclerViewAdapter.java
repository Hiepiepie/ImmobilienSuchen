package com.example.immobiliensuchen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mTitel;
    private ArrayList<String> mPreis;
    private ArrayList<String> mImage;
    private ArrayList<String> mEmail;
    private ArrayList<String> mBeschreibung;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mTitel, ArrayList<String> mPreis
                              ,ArrayList<String> mEmail, ArrayList<String> mBeschreibung,
                               ArrayList<String> mImage, Context mContext) {
        this.mTitel = mTitel;
        this.mPreis = mPreis;
        this.mImage = mImage;
        this.mBeschreibung =mBeschreibung;
        this.mEmail = mEmail;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
        .asBitmap()
        .load(mImage.get(position))
        .into(holder.image);
        holder.Titel.setText(mTitel.get(position));
        holder.Preis.setText(mPreis.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + mTitel.get(position));
                Toast.makeText(mContext,mTitel.get(position),Toast.LENGTH_SHORT).show();

               Intent myIntent = new Intent(mContext, AngebotActivity.class);

                myIntent.putExtra("Email",mEmail.get(position));
                myIntent.putExtra("Beschreibung",mBeschreibung.get(position));
                myIntent.putExtra("Preis",mPreis.get(position));
                myIntent.putExtra("Titel",mTitel.get(position));
                
                mContext.startActivity(myIntent);
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView Titel;
        TextView Preis;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.images);
            Titel = itemView.findViewById(R.id.angebotTitel);
            Preis = itemView.findViewById(R.id.angebotpreis);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
