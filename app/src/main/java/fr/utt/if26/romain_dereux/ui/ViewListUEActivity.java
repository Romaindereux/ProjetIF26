package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityViewListUeBinding;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.adapter.UEListAdapter;
import fr.utt.if26.romain_dereux.ui.adapter.UeAdapter;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewListUEActivity extends AppCompatActivity {

    ActivityViewListUeBinding binding;
    LiveData<List<UE>> listUE;
    UEViewModel ueViewModel;

    public static final String TAG = "ViewListUEActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_list_ue);
        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);

        listUE = ueViewModel.getAllUE();
        RecyclerView recyclerView = binding.rvListue;
        final UeAdapter adapter = new UeAdapter(new UeAdapter.UeDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ueViewModel.getAllUE().observe(this, ue ->{
            adapter.submitList(ue);
        });


    }
}