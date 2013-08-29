package com.elencticsoft.collegeseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class CollegeDialogBox extends SherlockDialogFragment {
    public static CollegeDialogBox newInstance(int collegeId) {
        CollegeDialogBox box = new CollegeDialogBox();
        Bundle args = new Bundle();
        args.putInt("college", collegeId);
        box.setArguments(args);
        return box;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int collegeId = getArguments().getInt("college");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        builder.setMessage(R.string.dialog_message)
               .setPositiveButton(R.string.view_details, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       MainActivity activity = (MainActivity) getSherlockActivity();
                       activity.updateDetailedView(collegeId);
                   }
               })
               .setNeutralButton(R.string.save, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       try {
                           DatabaseHandler.getInstance(getSherlockActivity()).saveCollege(collegeId);
                       } catch (SQLException e) {
                           Toast.makeText(getSherlockActivity(), "College already exists!",
                                   Toast.LENGTH_LONG).show();
                       }
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}