package fr.utt.if26.romain_dereux.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.MyApp;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityMainBinding;
import fr.utt.if26.romain_dereux.databinding.RecyclerviewCursusItemBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.ui.adapter.CursusListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.channels.Channels;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListItemClickListener{

    private CursusViewModel cursusViewModel;
    private UEViewModel ueViewModel;
    public static final int NEW_CURSUS_ACTIVITY_REQUEST_CODE = 1;
    private ActivityMainBinding binding;
    private static final String KEY_TEXT_CREATE = "key_text_create";

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        /*  Toolbar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.list_cursus);

        /* Creation of the recyclerView */
        RecyclerView rvListCursus = binding.recyclerview;
        final CursusListAdapter adapter = new CursusListAdapter(new CursusListAdapter.CursusDiff(), this);
        rvListCursus.setAdapter(adapter);
        rvListCursus.setLayoutManager(new LinearLayoutManager(this));
        cursusViewModel = new ViewModelProvider(this).get(CursusViewModel.class);
        cursusViewModel.getAllCursus().observe(this, cursus -> {
            adapter.submitList(cursus);
        });

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);


        /* Fab button Listener */
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
            Cursus cursus = (Cursus) data.getSerializableExtra(NewCursusActivity.EXTRA_REPLY_CURSUS);
            cursus.setBranche(data.getStringExtra(NewCursusActivity.EXTRA_REPLY_BRANCHE));
            cursus.setValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180);
            cursusViewModel.insert(cursus);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public int getSumCreditCursus(Cursus cursus){
        int sum=0;
        for (String sigle : cursus.getListCs()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListTm()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListEc()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListMe()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListHt()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        return sum;
    }


    @Override
    public void onListItemClick(int position) {

        Intent intent = new Intent(this, ViewCursusActivity.class);
        intent.putExtra("cursus", cursusViewModel.getCursus(position));
        startActivity(intent);

    }


    /**
     * Create the options menu
     * @param menu Menu: menu with the option to view list ue
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    /**
     * Start new activity when user click on the option
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_view_ue:
                Intent intent = new Intent(this, ViewListUEActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * OPTIONNAL
     * The following create a notification channel and create a new notification
     */
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

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
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