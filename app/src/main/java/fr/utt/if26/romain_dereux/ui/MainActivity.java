package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityMainBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.ui.adapter.CursusListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private CursusViewModel cursusViewModel;
    public static final int NEW_CURSUS_ACTIVITY_REQUEST_CODE = 1;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Use View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        RecyclerView recyclerView = binding.recyclerview;
        final CursusListAdapter adapter = new CursusListAdapter(new CursusListAdapter.CursusDiff());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cursusViewModel = new ViewModelProvider(this).get(CursusViewModel.class);

        cursusViewModel.getAllCursus().observe(this, cursus -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(cursus);
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCursusActivity.class);
                startActivityForResult(intent, NEW_CURSUS_ACTIVITY_REQUEST_CODE);
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CURSUS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Cursus cursus = new Cursus(data.getStringExtra(NewCursusActivity.EXTRA_REPLY));
            cursusViewModel.insert(cursus);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}