package com.example.joe.goout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by joe on 8/6/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    SimpleDateFormat YMD_FORMAT;
    private static final Date today = new Date();
    ArrayList<FeedItem>feedItems;
    Context context;


    public MyAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.feedItems = feedItems;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.cardView);
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());

        //To-Do
        if(TextUtils.isEmpty(current.getParkNames())){
            holder.ParkNames.setText(current.getLocation());
        }else{
            holder.ParkNames.setText(current.getParkNames());
        }

        try {
            YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = YMD_FORMAT.format(today);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            Date tomorrow = calendar.getTime();
            String ff  = YMD_FORMAT.format(tomorrow);
            Date date1 = YMD_FORMAT.parse(current.getStartDate());
            Date date2 = YMD_FORMAT.parse(currentDateandTime);
            String gg  = YMD_FORMAT.format(date1);

            if (gg.equals(ff)){
                holder.StartDate.setText("TOMMORROW");
            }else if(date1.equals(date2)) {
                holder.StartDate.setText("TODAY");
            }else if(date1.before(date2)){
                holder.StartDate.setText("EVENT PASS");
            }else {
                holder.StartDate.setText(current.getStartDate());
            }

        }catch (ParseException ex){
            ex.printStackTrace();
        }


        //To-Do
        if(TextUtils.isEmpty(current.getImage())){
            Picasso.with(context)
                    .load(R.mipmap.placeholder)
                    .into(holder.Thumbnail);
        }else{
            Picasso.with(context)
                    .load(current.getImage())
                    .placeholder(R.mipmap.park1)
                    .fit()
                    .into(holder.Thumbnail);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EventDetails.class);
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Title", current.getTitle());
                intent.putExtra("ParkName", current.getParkNames());
                intent.putExtra("Link", current.getLink());
                intent.putExtra("Coordinates", current.getCoordinates());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,ParkNames, StartDate;
        ImageView Thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.titleText);
            cardView =(CardView) itemView.findViewById(R.id.cardView);
            ParkNames =(TextView) itemView.findViewById(R.id.park_names);
            StartDate =(TextView)itemView.findViewById(R.id.start_date);
            Thumbnail = (ImageView) itemView.findViewById(R.id.thum_img);
        }
    }

}
