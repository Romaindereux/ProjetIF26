package fr.utt.if26.romain_dereux.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.FragmentDialogAddUeBinding;

/**
 * This fragment is used by the ViewCursusActivity when the user click on the + of the action bar
 * in order to add a new ue to the cursus
 */
public class DialogAddUE extends DialogFragment {

    public static final String TAG = "DialogAddUE";
    private FragmentDialogAddUeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogAddUeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.setDialogAddUe(this);
        return view;
    }
}