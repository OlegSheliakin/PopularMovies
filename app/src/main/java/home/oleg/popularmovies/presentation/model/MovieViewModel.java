package home.oleg.popularmovies.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Oleg on 15.04.2017.
 */

public class MovieViewModel implements Parcelable {

        private Integer id;
        private String originalTitle;
        private String imagePath;
        private String plotSynopsis;
        private Double userRating;
        private String releaseAt;

        public String getOriginalTitle() {
                return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
                this.originalTitle = originalTitle;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getImagePath() {
                return "/w185" + imagePath;
        }

        public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
        }

        public String getPlotSynopsis() {
                return plotSynopsis;
        }

        public void setPlotSynopsis(String plotSynopsis) {
                this.plotSynopsis = plotSynopsis;
        }

        public Float getUserRating() {
                return userRating.floatValue();
        }

        public void setUserRating(Double userRating) {
                this.userRating = userRating;
        }

        public String getReleaseAt() {
                return releaseAt;
        }

        public void setReleaseAt(String releaseAt) {
                this.releaseAt = releaseAt;
        }


        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.id);
                dest.writeString(this.originalTitle);
                dest.writeString(this.imagePath);
                dest.writeString(this.plotSynopsis);
                dest.writeValue(this.userRating);
                dest.writeString(this.releaseAt);
        }

        public MovieViewModel() {
        }

        protected MovieViewModel(Parcel in) {
                this.id = (Integer) in.readValue(Integer.class.getClassLoader());
                this.originalTitle = in.readString();
                this.imagePath = in.readString();
                this.plotSynopsis = in.readString();
                this.userRating = (Double) in.readValue(Double.class.getClassLoader());
                this.releaseAt = in.readString();
        }

        public static final Parcelable.Creator<MovieViewModel> CREATOR = new Parcelable.Creator<MovieViewModel>() {
                @Override
                public MovieViewModel createFromParcel(Parcel source) {
                        return new MovieViewModel(source);
                }

                @Override
                public MovieViewModel[] newArray(int size) {
                        return new MovieViewModel[size];
                }
        };

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                MovieViewModel that = (MovieViewModel) o;

                if (id != null ? !id.equals(that.id) : that.id != null) return false;
                if (originalTitle != null ? !originalTitle.equals(that.originalTitle) : that.originalTitle != null)
                        return false;
                if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null)
                        return false;
                if (plotSynopsis != null ? !plotSynopsis.equals(that.plotSynopsis) : that.plotSynopsis != null)
                        return false;
                if (userRating != null ? !userRating.equals(that.userRating) : that.userRating != null)
                        return false;
                return releaseAt != null ? releaseAt.equals(that.releaseAt) : that.releaseAt == null;

        }

        @Override
        public int hashCode() {
                int result = id != null ? id.hashCode() : 0;
                result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
                result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
                result = 31 * result + (plotSynopsis != null ? plotSynopsis.hashCode() : 0);
                result = 31 * result + (userRating != null ? userRating.hashCode() : 0);
                result = 31 * result + (releaseAt != null ? releaseAt.hashCode() : 0);
                return result;
        }

        @Override
        public String toString() {
                return "MovieViewModel{" +
                        "id=" + id +
                        ", originalTitle='" + originalTitle + '\'' +
                        ", imagePath='" + imagePath + '\'' +
                        ", plotSynopsis='" + plotSynopsis + '\'' +
                        ", userRating=" + userRating +
                        ", releaseAt='" + releaseAt + '\'' +
                        '}';
        }
}
