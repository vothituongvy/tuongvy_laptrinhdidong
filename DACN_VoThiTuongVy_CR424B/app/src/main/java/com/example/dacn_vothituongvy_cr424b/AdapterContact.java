package com.example.dacn_vothituongvy_cr424b;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {

    private final Context ctx;
    private final ArrayList<ModelContact> list;
    private OnItemClickListener onItemClickListener;

    public AdapterContact(Context ctx, ArrayList<ModelContact> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.row_contact_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ModelContact m = list.get(pos);

        h.name.setText(m.getName());

        String img = m.getImage();
        if (img == null || img.isEmpty()) {
            h.avatar.setImageResource(R.drawable.avatar);
        } else if (img.startsWith("/")) {
            h.avatar.setImageURI(Uri.fromFile(new File(img)));
        } else {
            h.avatar.setImageURI(Uri.parse(img));
        }

        // Click vào item để xem chi tiết
        h.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(m);
            }
        });

        // Gọi điện khi click icon gọi
        h.iconCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + m.getPhone()));
            ctx.startActivity(intent);
        });

        // Nhắn tin khi click icon tin nhắn
        h.iconMessage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + m.getPhone())); // chỉ nhắn SMS
            ctx.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar, iconCall, iconMessage;
        TextView name;

        public ViewHolder(@NonNull View v) {
            super(v);
            avatar = v.findViewById(R.id.contact_image);
            name = v.findViewById(R.id.contact_name);
            iconCall = v.findViewById(R.id.contact_call);
            iconMessage = v.findViewById(R.id.contact_message);
        }
    }


    // Interface để callback sự kiện click ra ngoài activity
    public interface OnItemClickListener {
        void onItemClick(ModelContact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
