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
import android.widget.Spinner;
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

public class RemedioActivity extends AppCompatActivity {
    public static final String REMEDIO_ID = "buscaremedio.com.br.buscaremedio.remedioid";
    public static final String REMEDIO_MED = "buscaremedio.com.br.buscaremedio.medicamento";
    public static final String REMEDIO_APRES = "buscaremedio.com.br.buscaremedio.apresentacao";
    public static final String REMEDIO_APLIC = "buscaremedio.com.br.buscaremedio.aplicacao";

    DatabaseReference databaseRemedio;

    public ListView listViewRemedio;
    public List<Remedio> remedioList;

    private FirebaseAuth firebaseAuth;

    EditText medicamentos, apresentacaos, aplicacaos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio);

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

                for (DataSnapshot ocorrenciaSnapshot : dataSnapshot.getChildren()){
                    Remedio remedio = ocorrenciaSnapshot.getValue(Remedio.class);
                    remedio.setRemedioId(ocorrenciaSnapshot.getKey());

                    remedioList.add(remedio);
                }

                RemedioList adapter = new RemedioList(RemedioActivity.this, remedioList);
                listViewRemedio.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        //attaching listener to listview
        /*listViewRemedio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Remedio remedio = remedioList.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), RemedioActivity.class);

                //putting artist name and id to intent
                intent.putExtra(REMEDIO_ID, remedio.getRemedioId());
                intent.putExtra(REMEDIO_MED, remedio.getMedicamento());
                intent.putExtra(REMEDIO_APRES, remedio.getApresentacao());
                intent.putExtra(REMEDIO_APLIC, remedio.getAplicacao());

                //starting the activity with intent
                startActivity(intent);
            }
        });*/

        listViewRemedio.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Remedio remedio = remedioList.get(i);
                System.out.println(remedio.getRemedioId());
                showUpdateDeleteDialog(remedio.getRemedioId(), remedio.getMedicamento(), remedio.getApresentacao(), remedio.getAplicacao());
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
            public boolean onQueryTextChange(String user) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void novaOcorrencia(View v){
        Intent intent = new Intent(this,InserirRemedio.class);
        startActivity(intent);
    }

    private void showUpdateDeleteDialog(final String remedioId, final String medicamento, String apresentacao, String aplicacao ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText medicamentos = (EditText) dialogView.findViewById(R.id.medtext);
        final EditText apresentacaos = (EditText) dialogView.findViewById(R.id.aprestext);
        final EditText aplicacaos = (EditText) dialogView.findViewById(R.id.aplitext);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(medicamento);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String med = medicamentos.getText().toString().trim();
                String apres = apresentacaos.getText().toString().trim();
                String aplic = aplicacaos.getText().toString().trim();
                if (!TextUtils.isEmpty(med)) {
                    updateRemedio(remedioId, med, apres, aplic);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRemedio(remedioId);
                b.dismiss();
            }
        });
    }

    private boolean updateRemedio(String id, String med, String apres, String aplic) {

        //getting the specified remedio reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Remedio").child(id);

        //updating artist
        Remedio remedio = new Remedio(id, med, apres, aplic);
        dR.setValue(remedio);
        Toast.makeText(getApplicationContext(), "Remedio Atualizado", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteRemedio(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Remedio").child(id);

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Remedio excluido", Toast.LENGTH_LONG).show();


        return true;
    }

}
