package home.oleg.popularmovies.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Oleg on 16.04.2017.
 */

abstract class BasicActivity : DaggerAppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar?.let(this::setSupportActionBar)
    }
}
