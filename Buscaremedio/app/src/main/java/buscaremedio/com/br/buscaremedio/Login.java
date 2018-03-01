package buscaremedio.com.br.buscaremedio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button logar;
    private EditText loginconfirma;
    private EditText senhaconfirma;
    private TextView textViewSignin;
    private TextView esqueceusenha;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),Inicio.class));

        }

        loginconfirma = (EditText) findViewById(R.id.loginconfirma);
        senhaconfirma = (EditText) findViewById(R.id.senhaconfirma);
        logar = (Button) findViewById(R.id.logar);
        textViewSignin = (TextView) findViewById(R.id.texViewSignin);
        esqueceusenha = (TextView) findViewById(R.id.esqueceusenha);

        progressDialog = new ProgressDialog(this);

        //listener para os botões
        logar.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        esqueceusenha.setOnClickListener(this);
    }

    private void userLogin(){
        String email = loginconfirma.getText().toString().trim();
        String password = senhaconfirma.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Porfavor insira seu e-mail", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Porfavor insira sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Validando, por favor aguarde...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Inicio.class));
                        }else{
                            Toast.makeText(Login.this, "Erro na validação, confirme seus dados", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == logar){
            userLogin();
        }

        if(view == textViewSignin){
            startActivity(new Intent(this, Cadastro.class));
        }

        if(view == esqueceusenha ){
            startActivity(new Intent(this, Resetsenha.class));
        }
    }
}