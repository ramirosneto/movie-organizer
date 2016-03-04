package com.movieorganizer.movieorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import custom.CustomActivity;
import rest.Api;

public class MainActivity extends CustomActivity {
    private EditText ETtitulo, ETtitulocad;
    private String nomeTitulo, nomeTituloCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETtitulo = (EditText) findViewById(R.id.ETtitulo);
        ETtitulocad = (EditText) findViewById(R.id.ETtitulocad);

        ETtitulo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    buscaTitutlo();
                    return true;
                }
                return false;
            }
        });

        ETtitulocad.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    cadastraTitulo();
                    return true;
                }
                return false;
            }
        });
    }

    private void buscaTitutlo() {
        nomeTitulo = ETtitulo.getText().toString();
        String params = "?s=" + nomeTitulo;

        Api api = new Api(MainActivity.this);
        api.buscaFilmes(params, MainActivity.this.getSupportFragmentManager());
    }

    private void cadastraTitulo() {
        nomeTituloCad = ETtitulocad.getText().toString();

        if(nomeTituloCad.isEmpty()) {
            Toast.makeText(this, "Informe o nome do filme!", Toast.LENGTH_SHORT).show();
        }else {
            nomeTituloCad = nomeTituloCad.replace(" ", "+");
            String params = "?t=" + nomeTituloCad;

            Api api = new Api(this);
            api.cadastraFilme(params);

            ETtitulocad.setText("");
        }
    }

    public void verFilmesCadastrados(View view) {
        Intent itFilmesCadastrados = new Intent(this, MoviesOffline.class);
        startActivity(itFilmesCadastrados);
    }
}
