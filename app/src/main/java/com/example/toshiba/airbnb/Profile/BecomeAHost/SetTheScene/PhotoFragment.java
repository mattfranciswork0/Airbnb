package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;



        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.toshiba.airbnb.R;

        import java.io.FileNotFoundException;
        import java.io.InputStream;


        import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */

public class PhotoFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    public static final String BIT_MAP_IMAGE = "BIT_MAP_IMAGE";
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();
        view.findViewById(R.id.bAddPhoto).setOnClickListener(new View.OnClickListener() {
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

        view.findViewById(R.id.bDoItLater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();

                //Read IMAGE URI FORM SD CARD with InputStream
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    GalleryFragment galleryFragment = new GalleryFragment();
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    bundle.putParcelable(BIT_MAP_IMAGE, image);
                    galleryFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.progressFragment,galleryFragment).commit();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }

    }

}

