package com.example.toshiba.airbnb.Profile.UserProfile;

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
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOAboutMe;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOEmailDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOLanguagesDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOLocationDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOWorkDetailEdit;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.Registration.RegisterEmailFragment;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 05/10/2017.
 */

public class UserProfileEditDetailFragment extends Fragment {
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
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_detail_edit, container, false);
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
                    if (getArguments().getBoolean(UserProfileEditFragment.ABOUT_ME_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.aboutMe));
                        etEdit.setHint(getResources().getString(R.string.aboutMe));

                        if (aboutMeText != null)
                            etEdit.setText(aboutMeText);

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String email = etEdit.getText().toString();
                                uploadDialog();
                                retrofit.insertAboutMe(USER_ID, new DTOAboutMe(email)).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        KeyboardUtil.hideKeyboard(getActivity());
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
                    if (getArguments().getBoolean(UserProfileEditFragment.EMAIL_EDIT)) {
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
                                    retrofit.insertEmailDetailEdit(USER_ID, new DTOEmailDetailEdit(email)).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            SessionManager sessionManager = new SessionManager(getActivity());
                                            if (sessionManager.isLoggedIn()) {
                                                SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sessionSP.edit();
                                                editor.putString(sessionManager.EMAIL, etEdit.getText().toString());
                                                editor.apply();
                                            }
                                            KeyboardUtil.hideKeyboard(getActivity());
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
                    } else if (getArguments().getBoolean(UserProfileEditFragment.LOCATION_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.location));
                        etEdit.setHint(getResources().getString(R.string.location));
                        if (locationText != null)
                            etEdit.setText(locationText);

                        tvSave.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLocationDetailEdit(USER_ID, new DTOLocationDetailEdit(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        KeyboardUtil.hideKeyboard(getActivity());
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


                    } else if (getArguments().getBoolean(UserProfileEditFragment.WORK_EDIT)) {
                        tvEdit.setText("Work");
                        etEdit.setHint("Work");
                        if (workText != null)
                            etEdit.setText(workText);
                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertWorkDetailEdit(USER_ID, new DTOWorkDetailEdit(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        KeyboardUtil.hideKeyboard(getActivity());
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
                    } else if (getArguments().getBoolean(UserProfileEditFragment.LANGUAGES_EDIT)) {
                        tvEdit.setText(getResources().getString(R.string.languages));
                        etEdit.setHint(getResources().getString(R.string.languages));
                        if (languagesText != null)
                            etEdit.setText(languagesText);

                        tvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadDialog();
                                retrofit.insertLanguagesDetailEdit(USER_ID, new DTOLanguagesDetailEdit(etEdit.getText().toString())).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        KeyboardUtil.hideKeyboard(getActivity());
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

