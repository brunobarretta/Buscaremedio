package buscaremedio.com.br.buscaremedio;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemedioActivityUser extends AppCompatActivity {

    DatabaseReference databaseRemedio;

    public ListView listViewRemedio;
    public List<Remedio> remedioList;

    private FirebaseAuth firebaseAuth;

    EditText medicamentos, apresentacaos, aplicacaos;

    String remedioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio_user);

        // for data persistence
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //databaseRemedio=FirebaseDatabase.getInstance().getReference("Remedio");
        //remedioId = databaseRemedio.push().getKey();

        medicamentos = (EditText) findViewById(R.id.medtext);
        apresentacaos = (EditText) findViewById(R.id.aprestext);
        aplicacaos = (EditText) findViewById(R.id.aplitext);

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseRemedio = FirebaseDatabase.getInstance().getReference("Remedio");

        listViewRemedio = (ListView) findViewById(R.id.listViewRemedio);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRemedio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                remedioList = new ArrayList<Remedio>();

                remedioList.clear();

                for (DataSnapshot ocorrenciaSnapshot : dataSnapshot.getChildren()) {
                    Remedio remedio = ocorrenciaSnapshot.getValue(Remedio.class);
                    remedio.setRemedioId(ocorrenciaSnapshot.getKey());

                    remedioList.add(remedio);
                }

                RemedioList adapter = new RemedioList(RemedioActivityUser.this, remedioList);
                listViewRemedio.setAdapter(adapter);

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
            public boolean onQueryTextChange(String user) {
                //findPerson(user);
                return true;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    public void novaOcorrencia(View v) {
        Intent intent = new Intent(this, InserirRemedio.class);
        startActivity(intent);
    }



    private void findPerson(String name){
        Query deleteQuery = databaseRemedio.orderByChild("medicamento").equalTo(name);
        deleteQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while((iterator.hasNext())){
                    Log.d("Item found: ",iterator.next().getValue().toString()+"---");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Item not found: ","this item is not in the list");
            }
        });
    }

}