package rest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.movieorganizer.movieorganizer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.MovieAdapter;
import it.sephiroth.android.library.picasso.Picasso;
import model.MovieData;
import sqlite.BD;

public class Api {
    private Context context;
    private static final String prefixURL = "http://www.omdbapi.com";
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    public Api(Context context) {
        this.context = context;
    }

    public void cadastraFilme(String params) {
        requestQueue = Volley.newRequestQueue(context);

        final String url = prefixURL + params;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MovieData movie = new MovieData(response.getString("imdbID"), response.getString("Title"), response.getString("Year"), response.getString("Type"), response.getString("Poster"));

                            BD bd = new BD(context);
                            bd.inserirFilme(movie);

                            Toast.makeText(context, "Filme "+response.getString("Title")+" cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(context, "Nenhum título encontrado!", Toast.LENGTH_LONG).show();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Nenhum título encontrado!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(request);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando títulos....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void buscaFilmes(String params, final FragmentManager fragment) {
        requestQueue = Volley.newRequestQueue(context);

        final String url = prefixURL + params;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList localArrayList = new ArrayList();
                            JSONArray array = response.getJSONArray("Search");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject c = array.getJSONObject(i);

                                localArrayList.add(new MovieData(c.getString("imdbID"), c.getString("Title"), c.getString("Year"), c.getString("Type"), c.getString("Poster")));
                            }

                            Object localObject = new MovieAdapter(localArrayList);
                            FragmentTransaction fragmentTransaction = fragment.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, (Fragment) localObject, null);
                            fragmentTransaction.commit();
                        } catch (JSONException e) {
                            Toast.makeText(context, "Nenhum título encontrado!", Toast.LENGTH_LONG).show();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Nenhum título encontrado!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(request);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando títulos....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void buscaDetalhe(String params) {
        requestQueue = Volley.newRequestQueue(context);

        final String url = prefixURL + params;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final TextView TVtitulo = (TextView) ((Activity) (context)).findViewById(R.id.TVtitulo);
                            final TextView TVrating = (TextView) ((Activity) (context)).findViewById(R.id.TVrating);
                            final TextView TVduracao = (TextView) ((Activity) (context)).findViewById(R.id.TVduracao);
                            final TextView TVdescricao = (TextView) ((Activity) (context)).findViewById(R.id.TVdescricao);
                            final TextView TVatores = (TextView) ((Activity) (context)).findViewById(R.id.TVatores);
                            final ImageView IVposter = (ImageView) ((Activity) (context)).findViewById(R.id.IVposter);

                            TVtitulo.setText(response.getString("Title")+" ("+response.getString("Year")+")");
                            TVrating.setText(response.getString("imdbRating")+" / 10");
                            TVduracao.setText(response.getString("Runtime"));
                            TVdescricao.setText(response.getString("Plot"));
                            TVatores.setText("Actors: "+response.getString("Actors"));
                            Picasso.with(context)
                                    .load(response.getString("Poster"))
                                    .error(R.drawable.noposter)
                                    .placeholder(R.drawable.noposter)
                                    .fit()
                                    .into(IVposter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(request);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando títulos....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}