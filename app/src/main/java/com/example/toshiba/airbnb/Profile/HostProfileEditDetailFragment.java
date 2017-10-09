package com.example.toshiba.airbnb.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Keyboard;
import com.example.toshiba.airbnb.Profile.DTO.AboutMeDTO;
import com.example.toshiba.airbnb.Profile.DTO.EmailDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.LanguagesDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.LocationDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.WorkDetailEditDTO;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.RegisterEmailFragment;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

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
    public static String aboutMeText;
    public static String locationText;
    public static String workText;
    public static String languagesText;
    public static String emailText;
    public static String phoneNumText;
    public static String PHONE_NUM_EDIT;


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
                    aboutMeText = response.body().getAboutMe();
                    locationText = response.body().getLocation();
                    workText = response.body().getWork();
                    languagesText = response.body().getLanguages();
                    emailText = response.body().getEmail();
                    phoneNumText = response.body().getPhoneNum();
                    if (getArguments().getBoolean(HostProfileEditFragment.ABOUT_ME_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.aboutMe));
                        etEdit.setHint(getResources().getString(R.string.aboutMe));

                        if (aboutMeText != null)
                            etEdit.setText(aboutMeText);

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String email = etEdit.getText().toString();
                                uploadDialog();
                                retrofit.insertAboutMe(USER_ID, new AboutMeDTO(email)).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Keyboard.hideKeyboard(getActivity());
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Failed to upload user data, try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        });

                    }
                    if (getArguments().getBoolean(HostProfileEditFragment.EMAIL_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.email));
                        etEdit.setHint(getResources().getString(R.string.email));
                        if (emailText != null)
                            etEdit.setText(emailText);

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String email = etEdit.getText().toString();
                                if (RegisterEmailFragment.isValidEmail(email)) {
                                    uploadDialog();
                                    retrofit.insertEmailDetailEdit(USER_ID, new EmailDetailEditDTO(email)).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            SessionManager sessionManager = new SessionManager(getActivity());
                                            if (sessionManager.isLoggedIn()) {
                                                SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sessionSP.edit();
                                                editor.putString(sessionManager.EMAIL, etEdit.getText().toString());
                                                editor.apply();
                                            }
                                            Keyboard.hideKeyboard(getActivity());
                                            dialog.dismiss();
                                            getFragmentManager().popBackStack();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "Failed to upload user data, try again", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                    dialog.setCancelable(true);
                                    dialog.setTitle("Unable to save");
                                    dialog.setMessage("Please enter a valid email");
                                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        });
                    } else if (getArguments().getBoolean(HostProfileEditFragment.LOCATION_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.location));
                        etEdit.setHint(getResources().getString(R.string.location));
                        if (locationText != null)
                            etEdit.setText(locationText);

                        tvSave.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLocationDetailEdit(USER_ID, new LocationDetailEditDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Keyboard.hideKeyboard(getActivity());
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


                    } else if (getArguments().getBoolean(HostProfileEditFragment.WORK_EDIT)) {
                        tvEdit.setText("Work");
                        etEdit.setHint("Work");
                        if (workText != null)
                            etEdit.setText(workText);
                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertWorkDetailEdit(USER_ID, new WorkDetailEditDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Keyboard.hideKeyboard(getActivity());
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Failed to retrieve user data, try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    } else if (getArguments().getBoolean(HostProfileEditFragment.LANGUAGES_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.languages));
                        etEdit.setHint(getResources().getString(R.string.languages));
                        if (languagesText != null)
                            etEdit.setText(languagesText);

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLanguagesDetailEdit(USER_ID, new LanguagesDetailEditDTO(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Keyboard.hideKeyboard(getActivity());
                                        dialog.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        dialog.dismiss();
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

