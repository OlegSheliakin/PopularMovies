package home.oleg.popularmovies.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import home.oleg.popularmovies.data.database.model.MovieDbModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    abstract fun get(id: Long): Single<MovieDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: MovieDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movies: List<MovieDbModel>)

    @Query("DELETE FROM movies WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("SELECT * FROM movies WHERE type = :type")
    abstract fun getAll(type: String): Flowable<List<MovieDbModel>>

    @Query("DELETE FROM movies")
    abstract fun clearAll()

}