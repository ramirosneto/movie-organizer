package com.movieorganizer.movieorganizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import custom.CustomActivity;
import model.MovieData;
import sqlite.BD;

public class MoviesOffline extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_offline);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Filmes cadastrados");

        BD bd = new BD(this);

        List<MovieData> list = bd.listarFilmes();
        ArrayList localArrayList = new ArrayList();

        for(MovieData md : list) {
            localArrayList.add(new MovieData(md.getImdbID(), md.getTitle(), md.getYear(), md.getType(), md.getPoster()));
        }

        Object localObject = new MovieAdapter(localArrayList);
        FragmentManager fragment = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, (Fragment) localObject, null);
        fragmentTransaction.commit();
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
