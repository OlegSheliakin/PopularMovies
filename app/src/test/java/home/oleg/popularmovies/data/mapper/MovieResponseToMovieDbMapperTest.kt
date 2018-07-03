package home.oleg.popularmovies.data.mapper

import home.oleg.popularmovies.data.entities.MovieResponse
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by Oleg Sheliakin on 03.07.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MovieResponseToMovieDbMapperTest {

    private val mapper = MovieResponseToMovieDbMapper()

    @Test
    operator fun invoke() {
        val expected = MovieResponse.Result(id = 1)
        val actual = mapper.invoke(expected)

        assertThat(actual.id, CoreMatchers.equalTo(expected.id))
    }

}