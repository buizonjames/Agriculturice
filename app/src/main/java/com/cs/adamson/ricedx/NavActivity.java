package com.cs.adamson.ricedx;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Bitmap image;
    String base64;
    DrawerLayout mdrawer;
    FrameLayout frameLayout;
    NavigationView navigationView;
    String imagePath, selectedImagePath;
    File file;
    byte[] bytes;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_nav);
        setTitle("Menu");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mdrawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);

        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/RiceDX";
        File targetDirector = new File(targetPath);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public void onBackPressed(){
        if (mdrawer.isDrawerOpen(GravityCompat.START))
            mdrawer.closeDrawer(GravityCompat.START);

        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if(item.isChecked()){
            mdrawer.closeDrawer(GravityCompat.START);
            return false;
        }

        if((id == R.id.nav_history) || (id == R.id.nav_about) || (id == R.id.nav_exit) || (id == R.id.nav_help)){

            if(id == R.id.nav_help){
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                finish();
            }

            if(id == R.id.nav_history){
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                finish();
            }

            if(id == R.id.nav_about){
                //startActivity(new Intent(getApplicationContext(), AboutActivity.class)); //TEST
                finish();
            }

            if(id == R.id.nav_exit){
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        else
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();

        mdrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.camera){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Analyze Image from...");
            alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    captureImage();
                }
            });

            alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fromGallery();
                }
            });

            AlertDialog alertDialogBox = alertDialog.create();
            alertDialogBox.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
    }

    private void captureImage(){
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.png");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            if(intent.getData() == null)
                Log.v("TAG", "Intent is null");
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 ){
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.png");
            try{
                cropCapturedImage(Uri.fromFile(file));
            }catch(ActivityNotFoundException aNFE){
                String errormsg = "ERROR";
                Toast toast = Toast.makeText(this, errormsg, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Log.v("TAG", "Intent Data IS NOT NULL");
            Uri filepath = data.getData();
            //imagePath = getRealPath(filepath);
            //selectedImagePath = imagePath;
            cropCapturedImage(filepath);
        }

        if(requestCode == 3){
            Bundle extras = data.getExtras();
            image = extras.getParcelable("data");
            Log.v("TAG", "File Output SUCCESS");
            file = new File(Environment.getExternalStorageDirectory() + "/RiceDX");
            if(!file.isDirectory())
                file.mkdir();

            file = new File(Environment.getExternalStorageDirectory() + "/RiceDX", "Palay" + "_" + System.currentTimeMillis()+ ".png");

            FileOutputStream fos = null;

            try{
                fos = new FileOutputStream(file);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

            if(image.compress(Bitmap.CompressFormat.PNG, 100, fos)){
                Toast saved = Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT);
                saved.show();
            }
            else {
                Toast unsaved = Toast.makeText(getApplicationContext(), "Image not saved", Toast.LENGTH_SHORT);
                unsaved.show();
            }

            base64 = encodeImage(file);
            Log.v("Response", base64);
            result();
        }
    }

    public String encodeImage(File filepath){
        try{
            InputStream is = new FileInputStream(filepath);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try{
                while((bytesRead = is.read(buffer)) != -1){
                    outputStream.write(buffer,0,bytesRead);
                }
            }catch (IOException ex){
                Log.d("Error IOE", ex.getMessage());
                ex.printStackTrace();
            }

            bytes = outputStream.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        String encImage = Base64.encodeToString(bytes, Base64.NO_WRAP | Base64.URL_SAFE);
        return encImage;
    }

    public void cropCapturedImage(Uri picUri){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");

        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);

        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("return-data", true);
        Log.v("TAG", "Crop SUCCESS");

        if(cropIntent.getExtras() == null)
            Log.v("TAG", "Crop Intent is NULL");
        else
            Log.v("TAG", "Crop Intent contains EXTRAS");

        startActivityForResult(cropIntent, 3);
    }

    private void result(){
        try{
            Intent intent = new Intent(NavActivity.this, ResultActivity.class); //TEST
            Bundle extras = new Bundle();
            extras.putParcelable("photo", image);
            intent.putExtras(extras);
            intent.putExtra("B64", base64);
            intent.putExtra("filepath", file);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
