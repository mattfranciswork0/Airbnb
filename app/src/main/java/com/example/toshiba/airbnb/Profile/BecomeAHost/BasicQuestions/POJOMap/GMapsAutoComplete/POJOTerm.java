package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Owner on 2017-07-08.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class POJOTerm {
        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("value")
        @Expose
        private String value;

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

}
