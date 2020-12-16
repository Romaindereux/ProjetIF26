package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityNewCursusBinding;
import fr.utt.if26.romain_dereux.model.Cursus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCursusActivity extends AppCompatActivity implements IAddCursus{

    public static final String TAG = "NewCursusActivity";

    public static final String EXTRA_REPLY_ID = "identifier";
    public static final String EXTRA_REPLY_BRANCHE = "branche";
    private ActivityNewCursusBinding binding;

    public String sigleDB;
    public Integer creditDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_cursus);

        Cursus cursus = new Cursus("", "");
        binding.setCursus(cursus);
        //TODO: See if it's possible to delete the interface
        binding.setIAddCursus((IAddCursus) this);
        binding.setNewCursusActivity(this);


    }
    @Override
    public void inflateNewCursus(){
        Intent replyIntent = new Intent();
        String identifier = binding.getCursus().getIdentifier();
        int brancheID = binding.getCursus().getBrancheID();
        String branche = getResources().getStringArray(R.array.branches_array)[brancheID];
        replyIntent.putExtra(EXTRA_REPLY_ID, identifier);
        replyIntent.putExtra(EXTRA_REPLY_BRANCHE, branche);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void inflateNewUE(String category){
        Toast.makeText(this, "New cs", Toast.LENGTH_LONG).show();
        DialogNewUE dialogNewUE = new DialogNewUE();
        dialogNewUE.show(getSupportFragmentManager(), "new cs");


    }
}