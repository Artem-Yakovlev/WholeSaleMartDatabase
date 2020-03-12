package com.example.wholesalemartdatabase.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wholesalemartdatabase.R;
import com.example.wholesalemartdatabase.data.Customer;
import com.example.wholesalemartdatabase.data.CustomerStatus;
import com.example.wholesalemartdatabase.domain.DataBase;
import com.example.wholesalemartdatabase.ui.mainrecyclerview.MainRecyclerViewAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.math.BigInteger;
import java.util.ArrayList;

public class ViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;
    private FloatingActionButton floatingActionButton;
    private NavController navHostController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view, container, false);
        recyclerView = root.findViewById(R.id.main_view_recycler_view);
        floatingActionButton = root.findViewById(R.id.floating_action_button);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHostController = NavHostFragment.findNavController(this);

        ArrayList<Customer> customers = new ArrayList<>(DataBase.getInstance().getCustomerArrayMap().values());
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getContext(), customers);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                false));

        floatingActionButton.attachToRecyclerView(recyclerView);
        floatingActionButton.setOnClickListener(v -> {
            navHostController.navigate(R.id.createItemFragment);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mainRecyclerViewAdapter != null) {
            mainRecyclerViewAdapter.refreshData(new ArrayList<>(DataBase.getInstance().getCustomerArrayMap().values()));
        }
    }
}
