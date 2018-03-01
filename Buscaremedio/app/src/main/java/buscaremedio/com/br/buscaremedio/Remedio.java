package buscaremedio.com.br.buscaremedio;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Barretta on 07/10/2017.
 */

@IgnoreExtraProperties
public class Remedio {
    private String remedioId;
    private String medicamento;
    private String apresentacao;
    private String aplicacao;

    public Remedio(){
        //this constructor is required
    }

    public Remedio(String remedioId, String medicamento, String apresentacao, String aplicacao) {
        this.remedioId = remedioId;
        this.medicamento = medicamento;
        this.apresentacao = apresentacao;
        this.aplicacao = aplicacao;
    }
    public void setRemedioId(String remedioId){this.remedioId = remedioId;}

    public String getRemedioId() {
        return remedioId;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public String getAplicacao() {
        return aplicacao;
    }
}
