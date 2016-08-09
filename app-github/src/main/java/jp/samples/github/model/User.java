package jp.samples.github.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class User {

    public long id;
    public String name;
    public String url;
    public String email;
    public String login;
    public String location;
    @SerializedName("avatar_url")
    public String avatarUrl;

    public User() {
    }

    public boolean hasEmail() {
        return !TextUtils.isEmpty(email);
    }

    public boolean hasLocation() {
        return !TextUtils.isEmpty(location);
    }

}
