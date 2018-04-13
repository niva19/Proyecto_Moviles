package com.example.pablo.proyecto_moviles;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RegistrarEmpresa extends AppCompatActivity {
    StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog progressDialog;
    private Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_empresa);
        getSupportActionBar().setTitle("Registrar Empresa");

        ImageView img = (ImageView) findViewById(R.id.imagen);

        //Programamos el evento onclick

        img.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                btnBrowse_click(arg0);
            }

        });

        // alambramos el Button
        Button MiButton = (Button) findViewById(R.id.button2);
        MiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // escriba lo que desea hacer
                DialogSiNO_01();
            }
        });

        // alambramos el Button
        Button MiButton2 = (Button) findViewById(R.id.button3);
        MiButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // escriba lo que desea hacer
                Limpiar();
            }
        });


    }//fin del oncreate

    public void btnBrowse_click(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1234 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();
            try{
                ImageView imagen = (ImageView) findViewById(R.id.imagen);
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imagen.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mt = MimeTypeMap.getSingleton();
        return mt.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    public void btnUpload_click(){
        if(imgUri != null){
            StorageReference ref = mStorage.child("image/" + System.currentTimeMillis() + "."+getImageExt(imgUri));
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Mensaje("La imagen se subio");
                    registrarEmpresa(taskSnapshot.getDownloadUrl().toString());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
        }
    }

    public void registrarEmpresa(String url){

        EditText E_Nombre = (EditText) findViewById(R.id.editText);
        EditText E_Servicio = (EditText) findViewById(R.id.editText2);
        EditText E_Desc = (EditText) findViewById(R.id.editText3);
        EditText E_Lat = (EditText) findViewById(R.id.editText4);
        EditText E_Lon = (EditText) findViewById(R.id.editText5);
        EditText E_Correo = (EditText) findViewById(R.id.editText7);

        String uid = user.getUid();
        String nombre = E_Nombre.getText().toString();
        String servicio = E_Servicio.getText().toString();
        String desc = E_Desc.getText().toString();
        double latitud =Double.parseDouble(E_Lat.getText().toString()) ;
        double longitud = Double.parseDouble(E_Lon.getText().toString());
        String correo = E_Correo.getText().toString();
        String key = mDatabase.child("Empresas").push().getKey();


        //progressDialog.setMessage("Ingresando empresa...");
        //progressDialog.show();
        try {


            Product empresa = new Product(key, nombre, servicio, desc, latitud, longitud,correo, url);
            mDatabase.child("Empresas").child(key).setValue(empresa);
            Limpiar();
            //progressDialog.dismiss();

        }catch (Exception e){
           // Log.i("Error","No se ingreso empresa");
            Log.i("error",String.valueOf(e));
           // progressDialog.dismiss();
        }



    }



    public void DialogSiNO_01(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Estas seguro de hacer esto?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Mensaje("positivo");
                        btnUpload_click();
                    } });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Mensaje("negativo");
                        Limpiar();
                    } });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    };

    public void Limpiar(){
        EditText E_Nombre = (EditText) findViewById(R.id.editText);
        EditText E_Servicio = (EditText) findViewById(R.id.editText2);
        EditText E_Desc = (EditText) findViewById(R.id.editText3);
        EditText E_Lat = (EditText) findViewById(R.id.editText4);
        EditText E_Lon = (EditText) findViewById(R.id.editText5);
        EditText E_Correo = (EditText) findViewById(R.id.editText7);

        E_Nombre.setText("");
        E_Servicio.setText("");
        E_Desc.setText("");
        E_Lat.setText("");
        E_Lon.setText("");
        E_Correo.setText("");
    }


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}//fin de la actividad
