package com.elencticsoft.collegeseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockDialogFragment;

public class SavedDialogBox extends SherlockDialogFragment {
    public static SavedDialogBox newInstance(int collegeId) {
        SavedDialogBox box = new SavedDialogBox();
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
        builder.setMessage(R.string.saved_dialog_message)
               .setPositiveButton(R.string.view_details, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       MainActivity activity = (MainActivity) getSherlockActivity();
                       activity.updateDetailedView(collegeId);
                   }
               })
               .setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       DatabaseHandler.getInstance(getSherlockActivity()).deleteCollege(collegeId);
                       ((MainActivity)getSherlockActivity()).updateSavedList();
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