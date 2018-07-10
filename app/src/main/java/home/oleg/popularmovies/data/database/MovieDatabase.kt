package home.oleg.popularmovies.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import home.oleg.popularmovies.data.database.model.MovieDbModel

/**
 * Created by olegshelyakin on 12.01.18.
 */

@Database(entities = [(MovieDbModel::class)], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        val name = "movie_db"
    }

    abstract fun getMovieDao(): MovieDao

}