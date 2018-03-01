package buscaremedio.com.br.buscaremedio;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FarmaciaActivityUser extends AppCompatActivity {

    DatabaseReference databaseFarmacia;

    public ListView listViewFarmacia;
    public List<Farmacia> farmaciaList;

    private FirebaseAuth firebaseAuth;

    EditText titulo, endereco, telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_user);

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

                for (DataSnapshot ocorrenciaSnapshot : dataSnapshot.getChildren()) {
                    Farmacia farmacia = ocorrenciaSnapshot.getValue(Farmacia.class);
                    farmacia.setUid(ocorrenciaSnapshot.getKey());

                    farmaciaList.add(farmacia);
                }

                FarmaciaList adapter = new FarmaciaList(FarmaciaActivityUser.this, farmaciaList);
                listViewFarmacia.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void novaOcorrencia(View v) {
        Intent intent = new Intent(this, Inserirfarmacia.class);
        startActivity(intent);
    }

}