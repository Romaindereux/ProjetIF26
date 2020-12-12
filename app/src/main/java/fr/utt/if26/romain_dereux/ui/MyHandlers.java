package fr.utt.if26.romain_dereux.ui;

import android.view.View;
import android.widget.Toast;

import fr.utt.if26.romain_dereux.R;

/**
 * This class is made to handle the click in the Databinding context
 */
public class MyHandlers {

    public void onClickCursusItem(View view){
        //TODO: do something on click on the cursus
        Toast.makeText(view.getContext(), R.string.cursus_clicked, Toast.LENGTH_LONG).show();
    }
}
