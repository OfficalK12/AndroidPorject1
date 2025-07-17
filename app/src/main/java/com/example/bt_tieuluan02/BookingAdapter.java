package com.example.bt_tieuluan02;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.Booking;
import sqlite.HotelDBHelper;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private Context context;
    private List<Booking> bookingList;
    private HotelDBHelper dbHelper;
    private SQLiteDatabase db;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        this.dbHelper = new HotelDBHelper(context);
        this.db = dbHelper.getReadableDatabase(); // Mở DB 1 lần
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        // Lấy tên khách sạn theo roomId
        String hotelName = getHotelNameFromRoomId(booking.getRoomId());
        holder.txtHotelName.setText(hotelName);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ConfirmedBookingDetailActivity.class);
            // Gửi toàn bộ dữ liệu cần thiết qua intent
            intent.putExtra("bookingId", booking.getId());
            intent.putExtra("roomId", booking.getRoomId());
            intent.putExtra("userId", booking.getUserId());
            intent.putExtra("hotelName", hotelName);
            intent.putExtra("checkIn", booking.getCheckIn());
            intent.putExtra("checkOut", booking.getCheckOut());
            intent.putExtra("paymentMethod", booking.getPaymentMethod());
            intent.putExtra("totalPrice", booking.getPriceAtBooking());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // Đóng DB khi không dùng adapter nữa (bạn có thể gọi trong fragment/activity khi destroy)
    public void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    // ViewHolder
    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView txtHotelName;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
        }
    }

    // Hàm lấy hotelName theo roomId
    private String getHotelNameFromRoomId(int roomId) {
        String hotelName = "Unknown Hotel";

        // 1. Lấy hotel_id từ rooms table theo roomId
        String queryRoom = "SELECT hotel_id FROM rooms WHERE _id = ?";
        try (Cursor cursorRoom = db.rawQuery(queryRoom, new String[]{String.valueOf(roomId)})) {
            if (cursorRoom.moveToFirst()) {
                int hotelId = cursorRoom.getInt(0);

                // 2. Lấy tên khách sạn theo hotelId
                String queryHotel = "SELECT name FROM hotels WHERE _id = ?";
                try (Cursor cursorHotel = db.rawQuery(queryHotel, new String[]{String.valueOf(hotelId)})) {
                    if (cursorHotel.moveToFirst()) {
                        hotelName = cursorHotel.getString(0);
                    }
                }
            }
        }

        return hotelName;
    }

    
}
