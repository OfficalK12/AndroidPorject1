package com.example.bt_tieuluan02;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import model.Hotel;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private Context context;
    private List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList){
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.txtHotelName.setText(hotel.getName());
        holder.txtHotelAddress.setText(hotel.getAddress());
        holder.txtHotelCity.setText(hotel.getCity());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetail.class);
            intent.putExtra("name", hotel.getName());
            intent.putExtra("address", hotel.getAddress());
            intent.putExtra("city", hotel.getCity());
            intent.putExtra("hotelId", hotel.getId());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView txtHotelName, txtHotelAddress, txtHotelCity;
        ImageView imgHotel;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtHotelAddress = itemView.findViewById(R.id.txtHotelAddress);
            txtHotelCity = itemView.findViewById(R.id.txtHotelCity);
            imgHotel = itemView.findViewById(R.id.imgHotel);
        }
    }
}
