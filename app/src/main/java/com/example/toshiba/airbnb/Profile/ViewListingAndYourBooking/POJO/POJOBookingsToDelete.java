package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 05/11/2017.
 */

public class POJOBookingsToDelete {

    @SerializedName("user_id")
    @Expose
    private List<UserId> userId = null;
    @SerializedName("listing_id")
    @Expose
    private List<ListingId> listingId = null;
    @SerializedName("check_in")
    @Expose
    private List<CheckIn> checkIn = null;
    @SerializedName("check_out")
    @Expose
    private List<CheckOut> checkOut = null;

    public List<UserId> getUserId() {
        return userId;
    }

    public void setUserId(List<UserId> userId) {
        this.userId = userId;
    }

    public List<ListingId> getListingId() {
        return listingId;
    }

    public void setListingId(List<ListingId> listingId) {
        this.listingId = listingId;
    }

    public List<CheckIn> getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(List<CheckIn> checkIn) {
        this.checkIn = checkIn;
    }

    public List<CheckOut> getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(List<CheckOut> checkOut) {
        this.checkOut = checkOut;
    }

    public class UserId {
        @SerializedName("user_id")
        @Expose
        private Integer userId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

    public class ListingId {

        @SerializedName("listing_id")
        @Expose
        private Integer listingId;

        public Integer getListingId() {
            return listingId;
        }

        public void setListingId(Integer listingId) {
            this.listingId = listingId;
        }
    }

    public class CheckIn {
        @SerializedName("check_in")
        @Expose
        private String checkIn;

        public String getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(String checkIn) {
            this.checkIn = checkIn;
        }
    }

    public class CheckOut {
        @SerializedName("check_out")
        @Expose
        private String checkOut;

        public String getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(String checkOut) {
            this.checkOut = checkOut;

        }
    }
}
