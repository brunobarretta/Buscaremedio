package buscaremedio.com.br.buscaremedio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inserirfarmacia extends AppCompatActivity {

    EditText longitude, latitude, snippet, endereco, telefone;
    Button save;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserirfarmacia);

        longitude = (EditText) findViewById(R.id.longtext);
        latitude = (EditText) findViewById(R.id.lattext);
        snippet = (EditText) findViewById(R.id.snippettext);
        endereco = (EditText) findViewById(R.id.endtext);
        telefone = (EditText) findViewById(R.id.teltext);
        save = (Button) findViewById(R.id.buttonSave);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Farmacias").push().setValue(firebaseDatabase, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //Problem with saving the data
                        if (databaseError != null) {
                            Toast.makeText(Inserirfarmacia.this, "Erro ao inserir dados!", Toast.LENGTH_LONG).show();
                        } else {
                            //Data uploaded successfully on the server

                            double lng = Double.parseDouble(longitude.getText().toString());
                            double lat = Double.parseDouble(latitude.getText().toString());

                            databaseReference.child("longitude").setValue(lng);
                            databaseReference.child("latitude").setValue(lat);
                            databaseReference.child("snippet").setValue(snippet.getText().toString());
                            databaseReference.child("endereco").setValue(endereco.getText().toString());
                            databaseReference.child("telefone").setValue(telefone.getText().toString());
                            Toast.makeText(Inserirfarmacia.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }
}

