package buscaremedio.com.br.buscaremedio;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InserirRemedio extends AppCompatActivity {

    EditText medicamento, apresentacao, aplicacao;
    Button save;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_remedio);

        medicamento = (EditText) findViewById(R.id.medtext);
        apresentacao = (EditText) findViewById(R.id.aprestext);
        aplicacao = (EditText) findViewById(R.id.aplitext);
        save = (Button) findViewById(R.id.buttonSave);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Remedio").push().setValue(firebaseDatabase, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //Problem with saving the data
                        if (databaseError != null) {
                            Toast.makeText(InserirRemedio.this, "Erro ao inserir dados!", Toast.LENGTH_LONG).show();
                        } else {
                            //Data uploaded successfully on the server
                            databaseReference.child("medicamento").setValue(medicamento.getText().toString());
                            databaseReference.child("apresentacao").setValue(apresentacao.getText().toString());
                            databaseReference.child("aplicacao").setValue(aplicacao.getText().toString());

                            Toast.makeText(InserirRemedio.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }
}