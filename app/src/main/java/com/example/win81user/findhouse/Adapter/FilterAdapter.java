package com.example.win81user.findhouse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81user.findhouse.CustomFilter;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Win81 User on 17/5/2560.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>  {
    public ArrayList<Property> properties;
    private ItemModel itemModel;
    private static ClickListener clicklistener = null;
    CustomFilter filter;
    Context c;

    public FilterAdapter(Context cx,ArrayList<Property> properties) {
        this.c= cx;
        this.properties = properties;
    }


    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item,viewGroup, false);
        return new FilterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtName1.setText(properties.get(i).getPropertyname());
        viewHolder.txtName.setText(properties.get(i).getPrice());
        Picasso.with(viewHolder.image.getContext()).load(properties.get(i).getImage()).into(viewHolder.image);
       /* viewHolder.mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "กดคลิกได้", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName,txtName1;
        private ImageView image,shareImageView;
        private TextView txtStatus;
        private TextView url;
        private TextView timeStamp;

        public ViewHolder(View v) {
            super(v);
            txtName1 = (TextView)v.findViewById(R.id.titleTextView);
            txtName  = (TextView)v.findViewById(R.id.hotel);
            image = (ImageView)v.findViewById(R.id.coverImageView);
//            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }

        }

    }
}
