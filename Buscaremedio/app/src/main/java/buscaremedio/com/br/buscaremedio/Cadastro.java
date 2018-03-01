package buscaremedio.com.br.buscaremedio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity implements View.OnClickListener{

    //definindo os view objects
    private Button login;
    private EditText loginconfirma;
    private EditText senhaconfirma;
    private TextView textviewSignup;

    private ProgressDialog progressDialog;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //Isso significa que o usuario ja esta logado
            //entao fecha essa activity
            finish();
            startActivity(new Intent(getApplicationContext(),Inicio.class));

        }

        progressDialog = new ProgressDialog(this);

        login = (Button)findViewById(R.id.login);

        loginconfirma = (EditText) findViewById(R.id.loginconfirma);
        senhaconfirma = (EditText) findViewById(R.id.senhaconfirma);

        textviewSignup = (TextView) findViewById(R.id.texViewSignup);

        login.setOnClickListener(this);
        textviewSignup.setOnClickListener(this);

    }

    private void registerUser(){
        //pegando email e password do edit texts
        String email = loginconfirma.getText().toString().trim();
        String password = senhaconfirma.getText().toString().trim();

        //cconferindo se os campos estão vazios
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Porfavor insira seu e-mail", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Porfavor insira sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        //caso o email e senha estiverem vazios
        //Cria um progressDialog com a mensagem

        progressDialog.setMessage("Registrando Usúario...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), Inicio.class));
                        }else{
                            Toast.makeText(Cadastro.this, "Registro não concluído, tente novamente", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            registerUser();
        }

        if (view == textviewSignup) {
            startActivity(new Intent(this, Login.class));
        }
    }
}

