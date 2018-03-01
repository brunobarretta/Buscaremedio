package buscaremedio.com.br.buscaremedio;

import android.widget.EditText;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Barretta on 09/09/2017.
 */

@IgnoreExtraProperties
public class Farmacia {

    private double longitude, latitude;
    private String uid;
    private String snippet;
    private String endereco;
    private String telefone;

    public Farmacia() {
        //this constructor is required
    }

    public Farmacia(String uid, String snippet, String endereco, String telefone) {
        this.uid = uid;
        this.snippet = snippet;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void setUid(String remedioId) {
        this.uid = remedioId;
    }

    public String getUid() {
        return uid;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}

