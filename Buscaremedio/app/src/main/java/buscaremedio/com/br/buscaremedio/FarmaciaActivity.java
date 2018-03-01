package buscaremedio.com.br.buscaremedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barretta on 09/11/2017.
 */

public class FarmaciaActivity extends AppCompatActivity {

    DatabaseReference databaseFarmacia;

    public ListView listViewFarmacia;
    public List<Farmacia> farmaciaList;

    private FirebaseAuth firebaseAuth;

    EditText titulo, endereco, telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia);

        titulo = (EditText) findViewById(R.id.tittext);
        endereco = (EditText) findViewById(R.id.endtext);
        telefone = (EditText) findViewById(R.id.teltext);

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseFarmacia = FirebaseDatabase.getInstance().getReference("Farmacias");

        listViewFarmacia = (ListView) findViewById(R.id.listViewFarmacia);



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFarmacia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                farmaciaList = new ArrayList<Farmacia>();

                farmaciaList.clear();

                for (DataSnapshot ocorrenciaSnapshot : dataSnapshot.getChildren()){
                    Farmacia farmacia = ocorrenciaSnapshot.getValue(Farmacia.class);
                    farmacia.setUid(ocorrenciaSnapshot.getKey());

                    farmaciaList.add(farmacia);
                }

                FarmaciaList adapter = new FarmaciaList(FarmaciaActivity.this, farmaciaList);
                listViewFarmacia.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        listViewFarmacia.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Farmacia farmacia = farmaciaList.get(i);
                System.out.println(farmacia.getUid());
                showUpdateDeleteDialog(farmacia.getUid(), farmacia.getSnippet(), farmacia.getEndereco(), farmacia.getTelefone());
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void novaOcorrencia(View v){
        Intent intent = new Intent(this,Inserirfarmacia.class);
        startActivity(intent);
    }

    private void showUpdateDeleteDialog(final String farmaciaId, final String titulo, String endereco, String telefone ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_farmacia, null);
        dialogBuilder.setView(dialogView);

        final EditText titulos = (EditText) dialogView.findViewById(R.id.tittext);
        final EditText enderecos = (EditText) dialogView.findViewById(R.id.endtext);
        final EditText telefones = (EditText) dialogView.findViewById(R.id.teltext);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(titulo);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit = titulos.getText().toString().trim();
                String end = enderecos.getText().toString().trim();
                String tel = telefones.getText().toString().trim();
                if (!TextUtils.isEmpty(tit)) {
                    updateRemedio(farmaciaId, tit, end, tel);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRemedio(farmaciaId);
                b.dismiss();
            }
        });
    }

    private boolean updateRemedio(String id, String tit, String end, String tel) {

        //getting the specified remedio reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Farmacias").child(id);

        //updating artist
        Farmacia farmacia = new Farmacia(id, tit, end, tel);
        dR.setValue(farmacia);
        Toast.makeText(getApplicationContext(), "Farmácia Atualizada", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteRemedio(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Farmacias").child(id);

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Farmácia Excluida", Toast.LENGTH_LONG).show();


        return true;
    }

}
