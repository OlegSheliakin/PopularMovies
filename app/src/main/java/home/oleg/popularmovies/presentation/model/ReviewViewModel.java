package home.oleg.popularmovies.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by olegshelyakin on 07.08.17.
 */

public class ReviewViewModel implements Parcelable {

    private String author;
    private String overview;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.overview);
    }

    public ReviewViewModel() {
    }

    protected ReviewViewModel(Parcel in) {
        this.author = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<ReviewViewModel> CREATOR = new Parcelable.Creator<ReviewViewModel>() {
        @Override
        public ReviewViewModel createFromParcel(Parcel source) {
            return new ReviewViewModel(source);
        }

        @Override
        public ReviewViewModel[] newArray(int size) {
            return new ReviewViewModel[size];
        }
    };
}
