package home.oleg.popularmovies.presentation.model

import android.os.Parcel
import android.os.Parcelable
import home.oleg.popularmovies.domain.entities.Movie

/**
 * Created by Oleg on 15.04.2017.
 */

data class MovieViewModel(
        val id: Long,
        val originalTitle: String? = null,
        val imagePath: String? = null,
        val plotSynopsis: String? = null,
        val userRating: Double? = null,
        val releaseAt: String? = null,
        var isFavourite: Boolean = false
) : Parcelable {

    val w185ImagePath: String
        get() = "/w185" + imagePath!!

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(originalTitle)
        parcel.writeString(imagePath)
        parcel.writeString(plotSynopsis)
        parcel.writeValue(userRating)
        parcel.writeString(releaseAt)
        parcel.writeByte(if (isFavourite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieViewModel> {
        override fun createFromParcel(parcel: Parcel): MovieViewModel {
            return MovieViewModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieViewModel?> {
            return arrayOfNulls(size)
        }
    }

    object Mapper : (Movie) -> MovieViewModel {
        override fun invoke(it: Movie): MovieViewModel {
            return MovieViewModel(
                    id = it.id,
                    originalTitle = it.originalTitle,
                    imagePath = it.posterPath,
                    isFavourite = it.isFavorite,
                    plotSynopsis = it.overview,
                    releaseAt = it.releaseDate,
                    userRating = it.voteAverage)
        }
    }
}
