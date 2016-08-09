package jp.samples.github.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Repository {

    public long id;
    public String name;
    public String description;
    public int forks;
    public int watchers;
    @SerializedName("stargazers_count")
    public int stars;
    public String language;
    public String homepage;
    public User owner;
    public boolean fork;

    public Repository() {
    }

    public boolean hasHomepage() {
        return !TextUtils.isEmpty(homepage);
    }

    public boolean hasLanguage() {
        return !TextUtils.isEmpty(language);
    }

    public boolean isFork() {
        return fork;
    }


}
