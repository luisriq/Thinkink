package com.grupo2tbd.thinkink;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.grupo2tbd.thinkink.Rest.UploadImage;
import com.grupo2tbd.thinkink.Views.InformacionPerfilFragment;
import com.grupo2tbd.thinkink.Views.Picture_list;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cris_ on 21/12/2015.
 */
public class PerfilTatuador extends AppCompatActivity {

    private static final int IMAGE_PICKER_SELECT = 100;
    private ProgressDialog progressDialog;
    public static final String Preferencias = "ThinkInk";
    private ViewPager pager;

    public PerfilTatuador() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(), config);
        setContentView(R.layout.perfil_tatuador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = (ViewPager) findViewById(R.id.viewPagerPerfil);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout tabs = (TabLayout) findViewById(R.id.tabsViewpager);
        tabs.setupWithViewPager(pager);
        FloatingActionButton subir = (FloatingActionButton) findViewById(R.id.subirFoto);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(e, IMAGE_PICKER_SELECT);
            }
        });

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public ArrayList<Fragment> fragments = new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(InformacionPerfilFragment.newInstance());
            fragments.add(Picture_list.newInstance(getIntent().getIntExtra("id", -1)));
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos){
            return fragments.get(pos);
    }

        public CharSequence getPageTitle(int position){
            switch(position) {

                case 0: return "Información";
                case 1: return "Galería 1";
                case 2: return "Galería 2";
                case 3: return "Mapa";
                default: return "Información";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Subiendo Imagen....");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            String path = getPathFromCameraData(data, this);
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {
                /*SendFoto send = new SendFoto(getContext(), path);
                send.execute();*/

                UploadImage service =
                        ServiceGenerator.createService(UploadImage.class);

                File file = new File(path);

                RequestBody requestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                int idUsuario = getIntent().getIntExtra("id", -1);
                Log.e("IDDD", ""+idUsuario);

                Call<Galeria.Foto> call = service.upload(requestBody, idUsuario);
                call.enqueue(new Callback<Galeria.Foto>() {
                    @Override
                    public void onResponse(Response<Galeria.Foto> response, Retrofit retrofit) {
                        progressDialog.dismiss();
                        //Actualizar lista
                        ((Picture_list)((MyPagerAdapter)pager.getAdapter()).getItem(1)).agregarFoto(response.body());

                        Toast.makeText(PerfilTatuador.this, "Imagen Subida correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Upload", t.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(PerfilTatuador.this, "Error al subir imagen", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_salir:
                SharedPreferences.Editor editor = this.getSharedPreferences(PerfilTatuador.Preferencias, Context.MODE_PRIVATE).edit();
                editor.remove("idUsuario");
                editor.commit();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }



}
