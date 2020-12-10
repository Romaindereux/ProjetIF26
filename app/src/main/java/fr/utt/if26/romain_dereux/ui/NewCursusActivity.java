package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import fr.utt.if26.romain_dereux.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewCursusActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.cursuslistsql.REPLY";

    private EditText mEditCursusIdentifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cursus);
        mEditCursusIdentifier = findViewById(R.id.et_cursus_identifier);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditCursusIdentifier.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = mEditCursusIdentifier.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}