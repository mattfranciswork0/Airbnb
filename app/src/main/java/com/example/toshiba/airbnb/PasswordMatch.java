package com.example.toshiba.airbnb;

/**
 * Created by Owner on 2017-06-28.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordMatch {

    @SerializedName("passwordMatch")
    @Expose
    private Boolean passwordMatch;

    public Boolean getPasswordMatch() {
        return passwordMatch;
    }

    public void setPasswordMatch(Boolean passwordMatch) {
        this.passwordMatch = passwordMatch;
    }

}