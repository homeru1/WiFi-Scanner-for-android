package com.example.wifiscanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment {

    private static String mMessageToDisplay;

    public static CustomDialogFragment newInstance(
            String message) {

        CustomDialogFragment infoDialog = new CustomDialogFragment();
        mMessageToDisplay = message;
        return infoDialog;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("wi-fi characteristics:").setMessage(mMessageToDisplay).create();
    }
}