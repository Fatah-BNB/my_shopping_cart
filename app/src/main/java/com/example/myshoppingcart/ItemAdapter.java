package com.example.myshoppingcart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private List<Item> items = new ArrayList<>();
    private onItemClickListener listener;
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.itemName.setText(currentItem.getName());
//        holder.itemPrice.setText(String.valueOf(currentItem.getPrice())+" DA");
        holder.itemQnt.setText(String.valueOf(currentItem.getQuantity()));
        holder.itemTotalPrice.setText(String.valueOf(currentItem.getTotalPrice())+" DA");
        holder.itemCard.setBackgroundColor(currentItem.getColor());
        holder.itemQnt.setOnClickListener(view -> {
            if (currentItem.getQuantity()>1) {
                currentItem.setQuantity(currentItem.getQuantity() - 1);
                MainActivity.itemViewModel.updateItem(currentItem);
            } else {
                MainActivity.itemViewModel.deleteItem(currentItem);
            }
            holder.itemQnt.setText(String.valueOf(currentItem.getQuantity()));

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public Item getItemAtPosition(int position){
        return items.get(position);
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        private TextView itemName, itemQnt, itemPrice, itemTotalPrice;
        private ImageView cart, dots;
        private RelativeLayout itemCard;
        public ItemHolder(View itemView){
            super(itemView);
            cart = itemView.findViewById(R.id.cart_img);
            dots = itemView.findViewById(R.id.dots);
            dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(MainActivity.root ,"Coming soon", Snackbar.LENGTH_SHORT)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .show();
                }
            });
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.itemViewModel.deleteItem(items.get(getAdapterPosition()));
                }
            });
            itemName = itemView.findViewById(R.id.item_name);
            itemQnt = itemView.findViewById(R.id.item_qnt);
//            itemPrice = itemView.findViewById(R.id.item_price);
            itemTotalPrice = itemView.findViewById(R.id.total_price);
            itemCard = itemView.findViewById(R.id.item_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onItemClick(items.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
    public interface onItemClickListener{
        void onItemClick(Item item);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
