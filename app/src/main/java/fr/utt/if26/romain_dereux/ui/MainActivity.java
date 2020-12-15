package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityMainBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.ui.adapter.CursusListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.channels.Channels;

public class MainActivity extends AppCompatActivity {

    private CursusViewModel cursusViewModel;
    public static final int NEW_CURSUS_ACTIVITY_REQUEST_CODE = 1;
    private ActivityMainBinding binding;
    private static final String KEY_TEXT_CREATE = "key_text_create";


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
            adapter.submitList(cursus);
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCursusActivity.class);
                startActivityForResult(intent, NEW_CURSUS_ACTIVITY_REQUEST_CODE);
                createNotification(intent);

            }
        });

        createNotificationChannel();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CURSUS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Cursus cursus = new Cursus(data.getStringExtra(NewCursusActivity.EXTRA_REPLY_ID), data.getStringExtra(NewCursusActivity.EXTRA_REPLY_BRANCHE));
            cursusViewModel.insert(cursus);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void createNotification(Intent intent){
        createNotificationChannel();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.cursus)
                .setContentTitle("Création cursus")
                .setContentText("Création d'un nouveau cursus")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Création d'un nouveau cursus UTT"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        mNotificationManager.notify(0, builder.build());

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CHANNEL_NAME";
            String description = "Channel intial pour les notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}