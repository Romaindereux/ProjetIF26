package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityNewCursusBinding;
import fr.utt.if26.romain_dereux.model.Cursus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewCursusActivity extends AppCompatActivity implements IAddCursus{

    public static final String EXTRA_REPLY = "com.example.android.cursuslistsql.REPLY";
    private ActivityNewCursusBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_cursus);

        Cursus cursus = new Cursus("");
        binding.setCursus(cursus);
        //TODO: See if it's possible to delete the interface
        binding.setIAddCursus((IAddCursus) this);

    }
    @Override
    public void inflateNewCursus(){
        Intent replyIntent = new Intent();
        String identifier = binding.getCursus().getIdentifier();
        replyIntent.putExtra(EXTRA_REPLY, identifier);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}