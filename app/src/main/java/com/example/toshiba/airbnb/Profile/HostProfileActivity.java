package com.example.toshiba.airbnb.Profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 14/08/2017.
 */

public class HostProfileActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("Want to save your changes?");
        dialog.setMessage("You'll lose your changes if you continue without saving them.");
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                HostProfileActivity.super.onBackPressed();
            }
        });
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.hostProfileLayout);
        if(fragment instanceof HostProfileEditDetailFragment){
            TextView tvEdit = (TextView) fragment.getView().findViewById(R.id.tvEdit);
            EditText etEdit = (EditText) fragment.getView().findViewById(R.id.etEdit);
            Log.d("testMe", "whoa");

            if(tvEdit.getText().toString().equals(getResources().getString(R.string.location))){
                if (!etEdit.getText().toString().equals("") &&
                        !etEdit.getText().toString().equals(HostProfileEditDetailFragment.locationText))
                    dialog.show();
                else
                    super.onBackPressed();
            }
            else if(tvEdit.getText().toString().equals(getResources().getString(R.string.work))) {
                if (!etEdit.getText().toString().equals("") &&
                        !etEdit.getText().toString().equals(HostProfileEditDetailFragment.workText))
                    dialog.show();
                else
                    super.onBackPressed();
            }
            else if(tvEdit.getText().toString().equals(getResources().getString(R.string.languages))) {
                if (!etEdit.getText().toString().equals("") &&
                        !etEdit.getText().toString().equals(HostProfileEditDetailFragment.languagesText))
                    dialog.show();
                else
                    super.onBackPressed();
            }

        } else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_profile);
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.hostProfileLayout, new HostProfileViewFragment()).commit();



    }
}
