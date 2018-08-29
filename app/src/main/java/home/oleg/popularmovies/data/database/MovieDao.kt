package home.oleg.popularmovies.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import home.oleg.popularmovies.data.database.model.MovieDbModel
import home.oleg.popularmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    abstract fun get(id: Long): Single<MovieDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(movie: MovieDbModel)

    @Update
    abstract fun update(movie: MovieDbModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(movies: List<MovieDbModel>)

    @Query("DELETE FROM movies WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("SELECT * FROM movies WHERE type = :type")
    abstract fun getAllByType(type: String): Flowable<List<MovieDbModel>>

    @Query("SELECT * FROM movies")
    abstract fun getAll(): Flowable<List<MovieDbModel>>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    abstract fun getFavourites(): Flowable<List<MovieDbModel>>

}