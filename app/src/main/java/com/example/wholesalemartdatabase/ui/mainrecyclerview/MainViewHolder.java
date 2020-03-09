package com.example.wholesalemartdatabase.ui.mainrecyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wholesalemartdatabase.R;
import com.example.wholesalemartdatabase.data.Customer;

class MainViewHolder extends RecyclerView.ViewHolder {

    private View view;

    MainViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }

    void bindData(Customer customer) {

        ((TextView) view.findViewById(R.id.customer_card_name)).setText(customer.getName());
        ((TextView) view.findViewById(R.id.customer_card_phone)).setText(customer.getPhone());

        ((TextView) view.findViewById(R.id.customer_card_cash))
                .setText("$ " + customer.getTotalPurchases().toString(10));

        int statusImageId = 0;

        switch (customer.getCustomerStatus()) {
            case GOLD:
                statusImageId = R.drawable.ic_person_pin_gold_24dp;
                break;
            case SILVER:
                statusImageId = R.drawable.ic_person_pin_silver_24dp;
                break;
            case BRONZE:
                statusImageId = R.drawable.ic_person_pin_bronze_24dp;
                break;
        }
        Glide.with(view.getContext())
                .load(statusImageId)
                .into((ImageView) view.findViewById(R.id.customer_card_status));


    }

}
