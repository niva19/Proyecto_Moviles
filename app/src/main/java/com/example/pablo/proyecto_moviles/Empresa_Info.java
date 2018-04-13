package com.example.pablo.proyecto_moviles;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class Empresa_Info extends AppCompatActivity {



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Empresas");
    String correo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa__info);

        // alambramos el Button
        Button contactar = (Button) findViewById(R.id.contactar);

        //Programamos el evento onclick

        contactar.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                String[] to = { correo };
                i.putExtra(Intent.EXTRA_EMAIL, to);
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i, "Email"));


            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                TextView nombre = (TextView) findViewById(R.id.nombre);
                TextView servicio = (TextView) findViewById(R.id.servicio);
                TextView descripcion = (TextView) findViewById(R.id.descripcion);
                for (DataSnapshot alert: dataSnapshot.getChildren()) {  /*Aqui agrego lo de la Base de Datos*/
                    VariablesGlobales vg = VariablesGlobales.getInstance();
                    if(alert.getKey() == vg.getId()){
                    nombre.setText(String.valueOf(alert.child("nombre").getValue()));
                    servicio.setText(String.valueOf(alert.child("servicio").getValue()));
                    descripcion.setText(String.valueOf(alert.child("descripcion").getValue()));
                    getSupportActionBar().setTitle(String.valueOf(alert.child("nombre").getValue()));
                    correo = String.valueOf(alert.child("correo").getValue());
                    loadImage(String.valueOf(alert.child("image").getValue()));
                    }


                }
                //adding some items to our list

                //creating recyclerview adapter

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    private void loadImage(String url){
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView4);
        Picasso.with(this).load(url).placeholder(R.drawable.default1)
        .error(R.drawable.default1)
        .into(imageView2, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }
}
