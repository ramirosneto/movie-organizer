package com.movieorganizer.movieorganizer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import custom.CustomActivity;
import rest.Api;

public class MovieDetail extends CustomActivity {
    String imdbID;
    Button BTadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Detalhe Título");

        if(getIntent().hasExtra("imdbID")) {
            imdbID = getIntent().getStringExtra("imdbID");

            String params = "?i=" + imdbID;

            Api api = new Api(this);
            api.buscaDetalhe(params);
        }else {
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        switch (paramMenuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(paramMenuItem);
        }
    }
}
