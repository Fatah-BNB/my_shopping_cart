package com.example.myshoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private List<String> lists = new ArrayList<>();
    private onListClickListener listener;
    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        String currentList = lists.get(position);
        holder.listName.setText(currentList);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setLists(List<String> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    public String getListPosition(int position){
        return lists.get(position);
    }

    class ListHolder extends RecyclerView.ViewHolder{
        private TextView listName;
        public ListHolder(View itemView){
            super(itemView);
            listName = itemView.findViewById(R.id.list_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onListClick(lists.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
        public interface onListClickListener{
        void onListClick(String list);
    }
    public void setOnListClickListener(onListClickListener listener){
        this.listener = listener;
    }
}
