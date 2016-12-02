package com.example.win81user.findhouse.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81user.findhouse.ClickListener;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.squareup.picasso.Picasso;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    public int fixID;
    private ItemModel itemModel;
    private static ClickListener clicklistener = null;


    public FeedAdapter(ItemModel item) {
        itemModel = (ItemModel) item;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public Property getItem(int position) {
        return itemModel.getProperty()[position];
    }

    public void setClickListener(ClickListener listener) {
        this.clicklistener = listener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.fixID=position;
        holder.txtName1.setText(getItem(position).getDescription()+"\n"+getItem(position).getPrice()+"\t"+"Baht");
//        holder.txtName.setText(getItem(position).getPrice());

        Picasso.with(holder.image.getContext()).load(getItem(position).getImage()).into(holder.image);
//        holder.txtStatus.setText(getItem(position).getStatus());
//        Picasso.with(holder.image.getContext()).load(getItem(position).getProfilePic()).into(holder.proPic);
//        holder.url.setText(getItem(position).getUrl());
//        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
//                Long.parseLong(getItem(position).getTimeStamp()),
//                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
//        holder.timeStamp.setText(timeAgo);

    }

    @Override
    public int getItemCount() {
        return itemModel.getProperty().length;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        public TextView txtName,txtName1;
        public ImageView image;
        public TextView txtStatus;
        public ImageView proPic;
        public TextView url;
        public TextView timeStamp;

        public ViewHolder(View v) {
            super(v);
//            txtName = (TextView)v.findViewById(R.id.titleTextView1);
            txtName1 = (TextView)v.findViewById(R.id.titleTextView);
            image = (ImageView)v.findViewById(R.id.coverImageView);
//            txtStatus = (TextView)v.findViewById(R.id.txtStatusMsg);
//            proPic = (ImageView)v.findViewById(R.id.profilePic);
//            url = (TextView)v.findViewById(R.id.txtUrl);
//            timeStamp = (TextView)v.findViewById(R.id.timestamp);

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
