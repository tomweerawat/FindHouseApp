package com.example.win81user.findhouse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    public void setClickListener(ClickListener listener) {
        this.clicklistener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.txtdetail.setText(properties.get(i).getDescription());
        viewHolder.texttitle.setText(properties.get(i).getPropertyname());
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
        private ImageView image;
        private TextView txtdetail,texttitle;
        private Toolbar mtoolbar;
        private ArrayList<Property> data;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            txtdetail = (TextView)v.findViewById(R.id.textdetail);
            texttitle = (TextView)v.findViewById(R.id.texttitle);
            image = (ImageView)v.findViewById(R.id.thumbnail);
         /*   mtoolbar = (Toolbar)v.findViewById(R.id.toolbardd1);
            mtoolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);*/
        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }


        }
    }
}
