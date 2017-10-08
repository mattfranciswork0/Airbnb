package com.example.toshiba.airbnb.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.DTO.LanguagesDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.LocationDetailDTO;
import com.example.toshiba.airbnb.Profile.DTO.WorkDetailEditDTO;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TOSHIBA on 05/10/2017.
 */

public class HostProfileEditDetailFragment extends Fragment {
    ProgressDialog dialog;
    public static String locationText;
    public static String workText;
    public static String languagesText;



    public void uploadDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Uploading profile");
        dialog.setCancelable(false);
        dialog.show();
    }

    DatabaseInterface retrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_detail_edit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView tvEdit = (TextView) view.findViewById(R.id.tvEdit);
        final EditText etEdit = (EditText) view.findViewById(R.id.etEdit);
        final TextView tvSave = (TextView) view.findViewById(R.id.tvSave);
        if (!getArguments().isEmpty()) {
            final int USER_ID = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                    .getInt(SessionManager.USER_ID, 0);


            uploadDialog();
            //change dialog message
            dialog.setMessage("Getting info....");

            retrofit.getUserData(USER_ID).enqueue(new Callback<POJOUserData>() {
                @Override
                public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                    locationText = response.body().getLocation();
                    workText = response.body().getWork();
                    languagesText = response.body().getLanguages();

                    if (getArguments().getBoolean(HostProfileEditFragment.LOCATION_EDIT)) {
                        tvEdit.setText("Location");
                        etEdit.setHint("Location");
                        if(locationText != null)
                            etEdit.setText(locationText);

                        tvSave.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLocationDetailEdit(USER_ID, new LocationDetailDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Failed to upload user data, try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });


                    }

                    if (getArguments().getBoolean(HostProfileEditFragment.WORK_EDIT)) {
                        tvEdit.setText("Work");
                        etEdit.setHint("Work");
                        if(workText != null)
                            etEdit.setText(workText);
                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertWorkDetailEdit(USER_ID, new WorkDetailEditDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();;
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Failed to retrieve user data, try again", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }


                    if (getArguments().getBoolean(HostProfileEditFragment.LANGUAGES_EDIT)) {
                        tvEdit.setText("Languages");
                        etEdit.setHint("Languages");
                        if(languagesText != null)
                            etEdit.setText(languagesText );

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLanguagesDetailEdit(USER_ID, new LanguagesDetailEditDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Failed to upload user data, try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                    
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<POJOUserData> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed to retrieve user data, try again", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    getFragmentManager().popBackStack();
                }
            });

           
        }
    }

}

