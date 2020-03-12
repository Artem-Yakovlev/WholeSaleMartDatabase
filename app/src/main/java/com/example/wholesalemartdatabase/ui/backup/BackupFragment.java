package com.example.wholesalemartdatabase.ui.backup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wholesalemartdatabase.R;
import com.example.wholesalemartdatabase.domain.DataBase;


public class BackupFragment extends Fragment {

    Button cleanData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_backup, container, false);
        cleanData = root.findViewById(R.id.settings_delete_database);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cleanData.setOnClickListener(v -> {
            DataBase.getInstance().cleanData();
        });
    }
}
