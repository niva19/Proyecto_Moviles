package com.example.pablo.proyecto_moviles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<Product> productList;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //the recyclerview
    RecyclerView recyclerView;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Empresas");

    /*Referencias que voy a quemar*/
  //  DatabaseReference mDesc = myRef.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        if(mAuth == null){
            MenuItem i= findViewById(R.id.nav_gallery);
            i.setVisible(false);
        }






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }//Termina el ON CREATE

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                //initializing the productlist
                productList = new ArrayList<>();

                for (DataSnapshot alert: dataSnapshot.getChildren()) {  /*Aqui agrego lo de la Base de Datos*/
                    productList.add(
                            new Product(
                                    alert.getKey(),
                                    String.valueOf(alert.child("nombre").getValue()),
                                    String.valueOf(alert.child("servicio").getValue()),
                                    String.valueOf(alert.child("descripcion").getValue()),
                                    Double.parseDouble(alert.child("latitud").getValue().toString()),
                                    Double.parseDouble(alert.child("longitud").getValue().toString()),
                                    String.valueOf(alert.child("correo").getValue()),
                                    String.valueOf(alert.child("image").getValue())));
                }
                //adding some items to our list

                //creating recyclerview adapter
                ProductAdapter adapter = new ProductAdapter(Inicio.this, productList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            Intent intento = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intento);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intento = new Intent(getApplicationContext(), RegistrarEmpresa.class);
            startActivity(intento);

        } else if (id == R.id.nav_manage) {
            mAuth.signOut();
            Intent intento = new Intent(getApplicationContext(), Inicio.class);
            startActivity(intento);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
