package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.Homes.HomeDescActivity;
import com.example.toshiba.airbnb.Explore.POJO.POJOListingData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOListingImages;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RealPathUtil;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */


public class GalleryFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(50);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button bNext = (Button) view.findViewById(R.id.bNext);
        Button bPreview = (Button) view.findViewById(R.id.bPreview);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
                (int) getResources().getDimension(R.dimen.item_decoration_margin)));

        //Add image must be below setAdapter() becuase views in GalleryAdpater must be inflated first for addImage() to work.
        if (getArguments() != null) {
            if (getArguments().containsKey(PhotoFragment.IMAGE_URI)) {
                final Uri imageUri = Uri.parse(getArguments().getString(PhotoFragment.IMAGE_URI));
                galleryAdapter.addImageFromPhone(imageUri);
                getArguments().remove(PhotoFragment.IMAGE_URI);
            }

            //load from database
            if (getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                bNext.setVisibility(View.GONE);
                bPreview.setVisibility(View.GONE);
                dialog.setMessage("Loading...");
                dialog.show();
                final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        POJOListingData body = response.body();
                        galleryAdapter = new GalleryAdapter(GalleryFragment.this, true);
                        for (int i = 0; i < body.getImageData().size(); i++) {
//                            Log.d("imcool", body.getImageData().get(i).getCaption().toString());
                            galleryAdapter.addImageFromDatabase(body.getImageData().get(i).getImagePath());
                            galleryAdapter.addCaptionFromDatabase(body.getImageData().get(i).getCaption());


                        }

                        recyclerView.setAdapter(galleryAdapter);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
            }
        } else {
            galleryAdapter = new GalleryAdapter(GalleryFragment.this, false);
            recyclerView.setAdapter(galleryAdapter);
        }

        view.findViewById(R.id.ivAddPhoto).setOnClickListener(new View.OnClickListener() {
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

        bPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeDescActivity.class);
                startActivity(intent);
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new DescribePlaceFragment()).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                //Cloudinary needs real path to upload
                //Using imageUri.getPath() would not get the real path
                //so get Real path from selected gallery by:
                if (getArguments() != null) {
                    if (getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                        final String realPath;
                        final Cloudinary cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable));
                        if (Build.VERSION.SDK_INT < 19)
                            realPath = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), imageUri);

                            // SDK > 19 (Android 4.4)
                        else
                            realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), imageUri);

                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Uploading your picture...");
                        dialog.setCancelable(false);
                        final String realPathAsName = realPath.substring(0, realPath.indexOf(".")).replaceFirst("/", "");
                        dialog.show();
                        final int listingId = getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID);
                        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        retrofit.updateListingImages(listingId,
                                new DTOListingImages(realPathAsName, null)).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                new AsyncTask<Void, Void, Void>() {

                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        try {
                                            Map cloudParam = ObjectUtils.asMap("public_id", realPathAsName);
                                            Log.d("loveu", realPathAsName);
                                            cloudinary.uploader().upload(new File(realPath), cloudParam);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        dialog.dismiss();
                                        galleryAdapter.addImageFromDatabase(realPathAsName);
                                        galleryAdapter.notifyItemInserted(galleryAdapter.getItemCount() + 1);
                                    }


                                }.execute();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("mattError", t.toString());
                            }
                        });
                    }

                }else {
                    galleryAdapter.addImageFromPhone(imageUri);
                    galleryAdapter.notifyItemInserted(galleryAdapter.getItemCount() + 1);
                }
            }
        }

    }
}