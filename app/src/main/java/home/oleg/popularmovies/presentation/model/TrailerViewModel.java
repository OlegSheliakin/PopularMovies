package home.oleg.popularmovies.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by olegshelyakin on 07.08.17.
 */

public class TrailerViewModel implements Parcelable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.key);
    }

    public TrailerViewModel() {
    }

    protected TrailerViewModel(Parcel in) {
        this.name = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<TrailerViewModel> CREATOR = new Parcelable.Creator<TrailerViewModel>() {
        @Override
        public TrailerViewModel createFromParcel(Parcel source) {
            return new TrailerViewModel(source);
        }

        @Override
        public TrailerViewModel[] newArray(int size) {
            return new TrailerViewModel[size];
        }
    };
}
