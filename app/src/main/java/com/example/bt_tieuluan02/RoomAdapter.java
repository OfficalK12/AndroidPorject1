package com.example.bt_tieuluan02;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import model.Room;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.txtType.setText(room.getType());
        holder.txtPrice.setText(room.getPrice() + " VNĐ");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetail.class);
            intent.putExtra("roomType", room.getType());
            intent.putExtra("roomPrice", room.getPrice());
            intent.putExtra("roomCapacity", room.getCapacity());
            intent.putExtra("roomId", room.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtType, txtPrice;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType = itemView.findViewById(R.id.txtRoomType);
            txtPrice = itemView.findViewById(R.id.txtRoomPrice);
        }
    }

}
