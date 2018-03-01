package buscaremedio.com.br.buscaremedio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class Idioma extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma);

        progressDialog = new ProgressDialog(this);
    }

    public void voltar(View v){
        setResult(Activity.RESULT_OK, null);
        finish();

    }

    public void idiomaEN(View v){

        progressDialog.setMessage("Atualizando, por favor aguarde...");
        progressDialog.show();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();

        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();

        progressDialog.dismiss();
        Toast.makeText(Idioma.this, "Idioma alterado para Inglês", Toast.LENGTH_SHORT).show();

        Intent refresh = new Intent(this, Inicio.class);
        startActivity(refresh);
        finish();


    }

    public void idiomaPT(View v){

        progressDialog.setMessage("Atualizando, por favor aguarde...");
        progressDialog.show();


        Locale locale = new Locale("pt");
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();

        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();

        progressDialog.dismiss();
        Toast.makeText(Idioma.this, "Idioma alterado para Português", Toast.LENGTH_SHORT).show();

        Intent refresh = new Intent(this, Inicio.class);
        startActivity(refresh);
        finish();



    }
}
