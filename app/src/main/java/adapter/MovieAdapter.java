package adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.movieorganizer.movieorganizer.MovieDetail;
import com.movieorganizer.movieorganizer.R;

import java.util.ArrayList;

import custom.CustomFragment;
import it.sephiroth.android.library.picasso.Picasso;
import model.MovieData;

@SuppressLint("ValidFragment")
public class MovieAdapter extends CustomFragment {
    private ArrayList<MovieData> recipeList;
    private ArrayList localArrayList = new ArrayList();
    private ListView localListView;

    public MovieAdapter(ArrayList arraylist) {
        this.localArrayList = arraylist;
    }

    private void loadRecipeList() {
        this.recipeList = new ArrayList(localArrayList);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.list_movies, null);
        loadRecipeList();
        localListView = (ListView) localView.findViewById(R.id.LVtitulos);
        localListView.setAdapter(new RecipeAdapter());

        localListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData localData = (MovieData) localListView.getAdapter().getItem((int) id);

                Intent itDetail = new Intent(getActivity(), MovieDetail.class);
                itDetail.putExtra("imdbID", localData.getImdbID());
                MovieAdapter.this.startActivity(itDetail);
            }
        });

        return localView;
    }

    private class RecipeAdapter extends BaseAdapter {
        private RecipeAdapter() {
            //
        }

        public int getCount() {
            return MovieAdapter.this.recipeList.size();
        }

        public MovieData getItem(int paramInt) {
            return MovieAdapter.this.recipeList.get(paramInt);
        }

        public long getItemId(int paramInt) {
            return paramInt;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            if (paramView == null) {
                paramView = LayoutInflater.from(MovieAdapter.this.getActivity()).inflate(R.layout.movie, null);
            }

            MovieData localData = getItem(paramInt);

            ((TextView) paramView.findViewById(R.id.TVnome)).setText(localData.getTitle());
            ((TextView) paramView.findViewById(R.id.TVano)).setText(localData.getYear());
            ((TextView) paramView.findViewById(R.id.TVtipo)).setText(localData.getType());

            ImageView IVposter = ((ImageView) paramView.findViewById(R.id.IVposter));
            Picasso.with(getActivity())
                    .load(localData.getPoster())
                    .error(R.drawable.noposter)
                    .placeholder(R.drawable.noposter)
                    .fit()
                    .into(IVposter);

            return paramView;
        }
    }
}