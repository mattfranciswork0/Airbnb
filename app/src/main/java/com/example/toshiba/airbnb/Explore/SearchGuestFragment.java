package com.example.toshiba.airbnb.Explore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 07/11/2017.
 */

public class SearchGuestFragment extends Fragment {
    public static String SAVED_SEARCH_ADULT ;
    public static String SAVED_SEARCH_INFANTS ;
    public static String SAVED_SEARCH_CHILDREN ;
    public static boolean SAVED_SEARCH_PETS_ALLOWED = false;

    public static String SEARCH_INFANTS_PATH = " ";
    public static String SEARCH_CHILDREN_PATH = " ";
    public static String SEARCH_PETS_ALLOWED_PATH = " ";
    //get listings that has a total_guest > 1
    public static String SEARCH_TOTAL_GUEST_PATH = "1";
    boolean radioCanUncheck;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_guest, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get rid of section tab from activity
        getActivity().findViewById(R.id.sectionTab).setVisibility(View.GONE);

        final EditText etAdult = (EditText) view.findViewById(R.id.etAdults);
        final EditText etInfants = (EditText) view.findViewById(R.id.etInfants);
        final EditText etChildren = (EditText) view.findViewById(R.id.etChildren);
        final RadioButton rbPets = (RadioButton) view.findViewById(R.id.rbPets);
        radioCanUncheck = false;

        rbPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPets.isChecked() && !radioCanUncheck) {
                    radioCanUncheck = true;
                    SEARCH_PETS_ALLOWED_PATH = "1";
                    SAVED_SEARCH_PETS_ALLOWED = true;

                } else {
                    radioCanUncheck = false;
                    SEARCH_PETS_ALLOWED_PATH = "0";
                    rbPets.setChecked(false);

                }
            }
        });

        if(SEARCH_INFANTS_PATH == null){
            SEARCH_INFANTS_PATH = " ";
        };
        if(SEARCH_CHILDREN_PATH == null){
            SEARCH_CHILDREN_PATH = " ";
        };

        if(SAVED_SEARCH_ADULT != null){
            etAdult.setText(SAVED_SEARCH_ADULT);
        }
        if(SAVED_SEARCH_INFANTS != null){
            etInfants.setText(SAVED_SEARCH_INFANTS);
        }
        if(SAVED_SEARCH_CHILDREN !=  null){
            etChildren.setText(SAVED_SEARCH_CHILDREN);
        }

        if(SAVED_SEARCH_PETS_ALLOWED){
            rbPets.setChecked(true);
            radioCanUncheck = true;
        }

        etAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etAdult.getText().length() == 0){
                    etAdult.setText("0");
                }
            }
        });

        etInfants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etInfants.getText().length() == 0){
                    etInfants.setText("0");
                }
            }
        });

        etChildren.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etChildren.getText().length() == 0){
                    etChildren.setText("0");
                }
            }
        });

        view.findViewById(R.id.bSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Fetching your results...");
                dialog.show();


                SAVED_SEARCH_ADULT = etAdult.getText().toString();
                SAVED_SEARCH_INFANTS = etInfants.getText().toString();
                SAVED_SEARCH_CHILDREN = etChildren.getText().toString();
                Integer integerEtAdultsVal = Integer.parseInt(SAVED_SEARCH_ADULT);
                Integer integerEtInfantsVal = Integer.parseInt(SAVED_SEARCH_INFANTS);
                Integer integerEtChildrenVal = Integer.parseInt(SAVED_SEARCH_CHILDREN);
                if (Integer.parseInt(SAVED_SEARCH_INFANTS) > 0) {
                    SEARCH_INFANTS_PATH = "1"; //1 is true in mysql
                } else {
                    SEARCH_INFANTS_PATH = "0"; //0 is false in mysql
                }

                if (Integer.parseInt(SAVED_SEARCH_CHILDREN) > 0) {
                    SEARCH_CHILDREN_PATH = "1";
                } else {
                    SEARCH_CHILDREN_PATH = "0";
                }



                SEARCH_TOTAL_GUEST_PATH = String.valueOf(integerEtAdultsVal
                        + integerEtInfantsVal +
                        integerEtChildrenVal);
                Log.d("wtfMatt", SEARCH_PETS_ALLOWED_PATH);
                DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getTotalListings(
                        LocationFragment.SEARCH_COUNTRY_PATH,
                        LocationFragment.SEARCH_STREET_PATH,
                        LocationFragment.SEARCH_CITY_PATH,
                        LocationFragment.SEARCH_STATE_PATH,
                        SearchGuestFragment.SEARCH_TOTAL_GUEST_PATH,
                        SearchGuestFragment.SEARCH_INFANTS_PATH,
                        SearchGuestFragment.SEARCH_CHILDREN_PATH,
                        String.valueOf(SearchGuestFragment.SEARCH_PETS_ALLOWED_PATH))
                        .enqueue(new Callback<POJOTotalListings>() {
                            @Override
                            public void onResponse(Call<POJOTotalListings> call, Response<POJOTotalListings> response) {
                                Bundle bundle = new Bundle();
                                bundle.putInt(HomeFragment.SEARCH_BAR_SIZE, Integer.parseInt(response.body().getTotalListings()));
                                HomeFragment homeFragment = new HomeFragment();
                                homeFragment.setArguments(bundle);
                                dialog.dismiss();
                                getFragmentManager().beginTransaction().replace(R.id.sectionFragmentReplace, homeFragment).commit();

                            }

                            @Override
                            public void onFailure(Call<POJOTotalListings> call, Throwable t) {
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
//                                Toast.makeText(getActivity(), "Failed to fetch data, check your internet connection and try again", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
    }
}
