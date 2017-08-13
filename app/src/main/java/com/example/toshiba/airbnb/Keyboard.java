package com.example.toshiba.airbnb;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;

/**
 * Created by TOSHIBA on 12/08/2017.
 */

public class Keyboard {
        public static void hideKeyboard(@NonNull Activity activity) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

}
