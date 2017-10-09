package com.example.toshiba.airbnb.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.Util.RealPathUtil;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.PhoneNumFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TOSHIBA on 14/08/2017.
 */

public class HostProfileEditFragment extends Fragment {
    SessionManager sessionManager;
    private static final int SELECT_PICTURE = 1;
    DatabaseInterface retrofit;
    int userId;
    ProgressDialog dialog;
    ImageView ivProfilePic;
    Cloudinary cloudinary;
    Call<POJOUserData> getUserDataCall;
    public static final String ABOUT_ME_EDIT = "ABOUT_ME_EDIT";
    public static final String EMAIL_EDIT = "EMAIL_EDIT";
    public static final String PHONE_NUM_EDIT = "PHONE_NUM_EDIT";
    public static final String LOCATION_EDIT = "LOCATION_EDIT";
    public static final String WORK_EDIT = "WORK_EDIT";
    public static final String LANGUAGES_EDIT = "LANGUAGES_EDIT";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        userId = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                .getInt(SessionManager.USER_ID, 0);
        KeyboardUtil.hideKeyboard(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_edit, container, false);
        dialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
        cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable

        //Check if profile pic already exists in file system server (cloudinary)
       getUserDataCall = retrofit.getUserData(userId);

        getUserDataCall.enqueue(new Callback<POJOUserData>() {
            @Override
            public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                if(response.body().getProfileImagePath() != null) {
                    Glide.with(getActivity()).
                            load(cloudinary.url().generate(response.body().getProfileImagePath().
                                    toString())).into(ivProfilePic);
                    TextView tvAboutMeInput = (TextView) view.findViewById(R.id.tvAboutMeInput);
                    tvAboutMeInput.setText(response.body().getAboutMe());

                } else{
                    Log.d("HostProfileEdit", "default profile pic");
                    Glide.with(getActivity()).load(getResources().getString(R.string.defaultProfilePicture));}
            }

            @Override
            public void onFailure(Call<POJOUserData> call, Throwable t) {

            }
        });

        if (sessionManager.isLoggedIn()) {
            SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(sessionSP.getString(sessionManager.FIRST_NAME, "") + " " + sessionSP.getString(sessionManager.LAST_NAME, ""));
            TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvEmail.setText(sessionSP.getString(sessionManager.EMAIL, ""));
            TextView tvPhoneNum = (TextView) view.findViewById(R.id.tvPhoneNum);
            tvPhoneNum.setText(sessionSP.getString(sessionManager.PHONE_NUM, ""));


            TextView tvEditAboutMe = (TextView) view.findViewById(R.id.tvEditAboutMe);
            tvEditAboutMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HostProfileEditDetailFragment hostProfileEditDetailFragment = new HostProfileEditDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ABOUT_ME_EDIT, true);
                    hostProfileEditDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, hostProfileEditDetailFragment).addToBackStack(null).commit();
                }
            });

            RelativeLayout layoutEmail = (RelativeLayout) view.findViewById(R.id.layoutEmail);
            layoutEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HostProfileEditDetailFragment hostProfileEditDetailFragment = new HostProfileEditDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(EMAIL_EDIT, true);
                    hostProfileEditDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, hostProfileEditDetailFragment).addToBackStack(null).commit();
                }
            });

            RelativeLayout layoutPhoneNum = (RelativeLayout) view.findViewById(R.id.layoutPhoneNum);
            layoutPhoneNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneNumFragment phoneNumFragment = new PhoneNumFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(PHONE_NUM_EDIT, true);
                    phoneNumFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().
                            replace(R.id.hostProfileLayout, phoneNumFragment).addToBackStack(getActivity().getResources().getString(R.string.hostProfileEditFragment)).commit();
                }
            });
        }
        ImageView ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        Glide.with(getActivity()).load(getResources().getString(R.string.GalleryIcon)).into(ivGallery);
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //MIME DATA TYPE
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);


            }
        });


        view.findViewById(R.id.layoutLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HostProfileEditDetailFragment hostProfileEditDetailFragment = new HostProfileEditDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(LOCATION_EDIT, true);
                hostProfileEditDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, hostProfileEditDetailFragment).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.layoutWork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HostProfileEditDetailFragment hostProfileEditDetailFragment = new HostProfileEditDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(WORK_EDIT, true);
                hostProfileEditDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, hostProfileEditDetailFragment).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.layoutLanguages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HostProfileEditDetailFragment hostProfileEditDetailFragment = new HostProfileEditDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(LANGUAGES_EDIT, true);
                hostProfileEditDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, hostProfileEditDetailFragment).addToBackStack(null).commit();
            }
        });






    }

    //when pic is clicked in gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                final Uri imageUri = data.getData();
                //Cloudinary needs real path to upload
                //Using imageUri.getPath() would not get the real path
                //so get Real path from selected gallery by:
                final String realPath;

                    // SDK >= 11 && SDK < 19
                if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), imageUri);

                    // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), imageUri);



                dialog.setMessage("Updating your profile...");
                dialog.setCancelable(false);
                final String realPathAsName = realPath.substring(0,realPath.indexOf(".")).replaceFirst("/","");
                dialog.show();
                //upload it
                retrofit.insertProfileImagePath(userId, realPathAsName).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("HostProfileEdit", "insert success");
                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected Void doInBackground(Void... params) {
                                try {
                                    Map cloudParam = ObjectUtils.asMap("public_id", realPathAsName);
                                    cloudinary.uploader().upload(new File(realPath), cloudParam);
                                } catch (IOException e) {
                                    Log.d("crap", e.toString());
                                    e.printStackTrace();
                                }

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                getUserDataCall.enqueue(new Callback<POJOUserData>() {
                                    @Override
                                    public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                                        Log.d("HostProfileEdit", response.body().getProfileImagePath().toString());
                                        if(response.body().getProfileImagePath() != null) {
                                            Glide.with(getActivity()).
                                                    load(cloudinary.url().generate(response.body().getProfileImagePath().
                                                            toString())).into(ivProfilePic);
                                        }

                                        Log.d("HostProfileEdit", "success retrieve image");
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<POJOUserData> call, Throwable t) {
                                        Log.d("HostProfileEdit", t.toString());
                                        Toast.makeText(getActivity(), "Failed to update profile, try again", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }.execute();


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("HostProfileEdit", "insert fail" + t.toString());
                    }
                });


            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getUserDataCall.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getUserDataCall.cancel();
    }
}
