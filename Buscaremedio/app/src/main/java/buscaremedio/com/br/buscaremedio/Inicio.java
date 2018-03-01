package buscaremedio.com.br.buscaremedio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //inserindo o email do usuario no menu
        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textemail);
        txtProfileName.setText(user.getEmail());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(getApplicationContext(), RemedioActivity.class));
            return true;
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            return true;

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(), FarmaciaActivity.class));
            return true;

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplicationContext(), Idioma.class));
            return true;

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(getApplicationContext(), Sobre.class));
            return true;

        } else if (id == R.id.nav_send) {
            //startActivity(new Intent(getApplicationContext(), Logout.class));
            LogOut();
            return true;
        }else if (id == R.id.avalie) {
            Web();
            return true;
        }else if (id == R.id.farmacia) {
            startActivity(new Intent(getApplicationContext(), FarmaciaActivityUser.class));
            return true;

        }else if (id == R.id.remedio) {
            startActivity(new Intent(getApplicationContext(), RemedioActivityUser.class));
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void LogOut() {

        //logging out the user
        firebaseAuth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(this, Login.class));


    }

    public void Web() {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/VQ0043REm34Bcjqi2"));
        startActivity(browserIntent);
    }

    public void abrirfarmacia(View v){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void abrirremedio(View v){
        Intent intent = new Intent(this,RemedioActivityUser.class);
        startActivity(intent);
    }
}
