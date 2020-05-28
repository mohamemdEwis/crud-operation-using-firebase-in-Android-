package com.example.intern_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    List<User> listUsers;

    public UserAdapter(Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new UserViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.tv_User.setText(listUsers.get(position).getuser());
        holder.tv_pass.setText(listUsers.get(position).getpassword());
        holder.tv_phone.setText(listUsers.get(position).getphone());
        holder.tv_Email.setText(listUsers.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tv_User, tv_pass, tv_phone, tv_Email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_User = itemView.findViewById(R.id.tv_User);
            tv_pass = itemView.findViewById(R.id.tv_pass);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_Email = itemView.findViewById(R.id.tv_Email);
        }
    }
}