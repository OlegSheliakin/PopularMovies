package home.oleg.popularmovies.presentation;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import home.oleg.popularmovies.R;

/**
 * Created by Oleg on 16.04.2017.
 */

public abstract class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setupToolbar();
    }

    protected abstract @LayoutRes Integer getLayoutId();

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
    }
}
