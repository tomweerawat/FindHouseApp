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
 * Created by Win81 User on 9/3/2560.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {
    public ArrayList<Property> properties;
    private ItemModel itemModel;
    private static ClickListener clicklistener = null;
    CustomFilter filter;
    Context c;

    public NearbyAdapter(Context cx,ArrayList<Property> properties) {
        this.c= cx;
        this.properties = properties;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nearby_item_layout,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.txtdetail.setText(properties.get(i).getDescription());
        viewHolder.texttitle.setText(properties.get(i).getPropertyname());
        Picasso.with(viewHolder.image.getContext()).load(properties.get(i).getImage()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView txtdetail,texttitle;
        public ViewHolder(View v) {
            super(v);
            txtdetail = (TextView)v.findViewById(R.id.textdetail);
            texttitle = (TextView)v.findViewById(R.id.texttitle);
            image = (ImageView)v.findViewById(R.id.thumbnail);
        }


    }
}
