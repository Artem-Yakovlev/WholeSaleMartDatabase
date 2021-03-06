package com.example.wholesalemartdatabase.ui.mainrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wholesalemartdatabase.R;
import com.example.wholesalemartdatabase.data.Customer;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context context;
    private ArrayList<Customer> customers;
    private OnItemClicked onItemClickedListener;

    public MainRecyclerViewAdapter(Context context, ArrayList<Customer> customers, OnItemClicked onItemClickedListener) {
        this.context = context;
        this.customers = customers;
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.customer_card_layout, parent, false), onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bindData(customers.get(position));
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void refreshData(ArrayList<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }
}
