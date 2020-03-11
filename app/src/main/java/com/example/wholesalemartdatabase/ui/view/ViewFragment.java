package com.example.wholesalemartdatabase.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wholesalemartdatabase.R;
import com.example.wholesalemartdatabase.data.Customer;
import com.example.wholesalemartdatabase.data.CustomerStatus;
import com.example.wholesalemartdatabase.ui.mainrecyclerview.MainRecyclerViewAdapter;

import java.math.BigInteger;
import java.util.ArrayList;

public class ViewFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view, container, false);
        recyclerView = root.findViewById(R.id.main_view_recycler_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Customer> customers = new ArrayList<>();

        customers.add(new Customer( "Artem Bagrenec", "+79056644712", new BigInteger("6000", 10), CustomerStatus.GOLD));
        customers.add(new Customer( "Artem Shrek", "+79056644710", new BigInteger("2000", 10), CustomerStatus.SILVER));
        customers.add(new Customer( "Artem Rudoi", "+79056644711", new BigInteger("400", 10), CustomerStatus.BRONZE));


        recyclerView.setAdapter(new MainRecyclerViewAdapter(getContext(), customers));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                false));
    }
}
