package com.example.victoraso.myapplication.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.victoraso.myapplication.R;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView horseRider, horseName;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View itemView) {
        super(itemView);

        this.horseRider = itemView.findViewById(R.id.horseRider);
        this.horseName = itemView.findViewById(R.id.horseName);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }

}
