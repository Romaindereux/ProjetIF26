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
import fr.utt.if26.romain_dereux.model.UE;
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
        if(cursus.isSt09()){
            sum += 30;
        }
        if(cursus.isSt10()){
            sum += 30;
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
            case R.id.action_populate_db:
                populateDB();
                return true;
            case R.id.action_empty_db:
                emptyDB();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void emptyDB(){
        cursusViewModel.deleteAll();
        ueViewModel.deleteAll();
    }
    /**
     * Populate the db with data
     */
    public void populateDB(){
        // CS ISI
        ueViewModel.insert(new UE("GL02","ISI","CS",6));
        ueViewModel.insert(new UE("NF20","ISI","CS",6));
        ueViewModel.insert(new UE("IF02","ISI","CS",6));
        ueViewModel.insert(new UE("LO12","ISI","CS",6));
        ueViewModel.insert(new UE("IF09","ISI","CS",6));
        ueViewModel.insert(new UE("IF10","ISI","CS",6));
        ueViewModel.insert(new UE("NF16","ISI","CS",6));
        // TM ISI
        ueViewModel.insert(new UE("LO02","ISI","TM",6));
        ueViewModel.insert(new UE("NF21","ISI","TM",6));
        ueViewModel.insert(new UE("IF03","ISI","TM",6));
        ueViewModel.insert(new UE("LO07","ISI","TM",6));
        ueViewModel.insert(new UE("IF26","ISI","TM",6));
        ueViewModel.insert(new UE("IF28","ISI","TM",6));
        ueViewModel.insert(new UE("C01","ISI","TM",3));
        ueViewModel.insert(new UE("C02","ISI","TM",3));
        //EC
        ueViewModel.insert(new UE("LC00","","EC",4));
        ueViewModel.insert(new UE("LC01","","EC",4));
        ueViewModel.insert(new UE("LC02","","EC",4));
        ueViewModel.insert(new UE("LE20","","EC",4));
        ueViewModel.insert(new UE("UX60","","EC",4));
        ueViewModel.insert(new UE("UX61","","EC",4));
        //ME
        ueViewModel.insert(new UE("GE37","","ME",4));
        ueViewModel.insert(new UE("GE41","","ME",6));
        ueViewModel.insert(new UE("PM1","","ME",4));
        //HT
        ueViewModel.insert(new UE("PO03","","HT",4));
        ueViewModel.insert(new UE("UX80","","HT",8));

        ArrayList<String> listCs1 = new ArrayList<String>();
        ArrayList<String> listTm1 = new ArrayList<String>();
        ArrayList<String> listEc1 = new ArrayList<String>();
        ArrayList<String> listMe1 = new ArrayList<String>();
        ArrayList<String> listHt1 = new ArrayList<String>();
        listCs1.add("GL02");
        listCs1.add("NF20");
        listCs1.add("IF02");
        listCs1.add("LO12");
        listCs1.add("IF09");
        listCs1.add("IF10");
        listCs1.add("NF16");
        listTm1.add("LO02");
        listTm1.add("NF21");
        listTm1.add("IF03");
        listTm1.add("LO07");
        listTm1.add("IF26");
        listTm1.add("IF28");
        listTm1.add("C01");
        listTm1.add("C02");
        listEc1.add("LC00");
        listEc1.add("LC01");
        listEc1.add("LC02");
        listEc1.add("LE20");
        listEc1.add("UX60");
        listEc1.add("UX61");
        listMe1.add("GE37");
        listMe1.add("GE41");
        listMe1.add("PM1");
        listHt1.add("PO03");
        listHt1.add("UX80");

        Cursus cursusRomain = new Cursus("Romain", "ISI",listCs1,listTm1,listEc1, listMe1,listHt1,true, false, true );
        cursusRomain.setValid(false);
        Cursus cursus2 = new Cursus("Cursus2", "ISI",listCs1,listTm1,listEc1, listMe1,listHt1,true, true, true );
        cursus2.setValid(true);
        cursusViewModel.insert(cursusRomain);
        cursusViewModel.insert(cursus2);
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