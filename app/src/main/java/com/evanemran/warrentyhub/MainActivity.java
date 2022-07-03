package com.evanemran.warrentyhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.evanemran.warrentyhub.adapters.DrawerAdapter;
import com.evanemran.warrentyhub.dialog.PostDialog;
import com.evanemran.warrentyhub.listeners.ClickListener;
import com.evanemran.warrentyhub.listeners.PostListener;
import com.evanemran.warrentyhub.models.ImageUri;
import com.evanemran.warrentyhub.models.NavMenu;
import com.evanemran.warrentyhub.models.PostData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerview_home;
    DrawerLayout drawer;
    TextView version_name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FloatingActionButton fab_add;
    PostData newPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview_home = findViewById(R.id.recyclerview_home);
        version_name = findViewById(R.id.version_name);
        fab_add = findViewById(R.id.fab_add);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("posts");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDialog postDialog = new PostDialog(postDataClickListener);
                postDialog.show(getSupportFragmentManager(), "post");
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.open_nav_drawer, R.string.close_nav_drawer
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavMenu();
    }

    private void setupNavMenu() {
        List<NavMenu> navMenus = new ArrayList<>();
        navMenus.add(NavMenu.HOME);
        navMenus.add(NavMenu.PRODUCTS);
        navMenus.add(NavMenu.CATEGORIES);
        navMenus.add(NavMenu.SHOPS);
        navMenus.add(NavMenu.SETTINGS);
        navMenus.add(NavMenu.LOGOUT);
        RecyclerView recycler_nav = findViewById(R.id.recycler_nav);
        recycler_nav.setHasFixedSize(true);
        recycler_nav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, navMenus, navMenuClickListener);
        recycler_nav.setAdapter(drawerAdapter);

        try {
            PackageInfo pInfo =
                    getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version_name.setText("Version: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final ClickListener<NavMenu> navMenuClickListener = new ClickListener<NavMenu>() {
        @Override
        public void onClicked(NavMenu item) {
            Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final PostListener postDataClickListener = new PostListener() {
        @Override
        public void onPostClicked(PostData data, List<ImageUri> imageUri) {

            uploadImage(data, imageUri);

        }
    };

    private void uploadImage(PostData data, List<ImageUri> imageUriList) {
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        for (ImageUri uri: imageUriList){
            if (uri.getImageCode()==101){
                StorageReference ref
                        = storageReference
                        .child(
                                "images/"
                                        + System.currentTimeMillis()+"."+getFileExtension(uri.getUri()));
                ref.putFile(compressImage(uri.getUri()))
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot) {

                                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                data.setProDuctImage(uri.toString());

                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this,
                                                        "Product Image Uploaded!!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        // Image uploaded successfully
                                        // Dismiss dialog
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // Error, Image not uploaded
                        Toast.makeText(MainActivity.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int) progress + "%");
                                    }
                                });
            }
            else if(uri.getImageCode()==102){
                StorageReference ref
                        = storageReference
                        .child(
                                "images/"
                                        + System.currentTimeMillis()+"."+getFileExtension(uri.getUri()));

                ref.putFile(compressImage(uri.getUri()))
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot) {

                                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                data.setInVoiceImage(uri.toString());

                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this,
                                                        "Image Uploaded!!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        // Image uploaded successfully
                                        // Dismiss dialog
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // Error, Image not uploaded
                        Toast.makeText(MainActivity.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int) progress + "%");
                                    }
                                });
            }
        }

        data.setPostedBy(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+" ");
        databaseReference = firebaseDatabase.getReference("posts");
        String key = databaseReference.push().getKey();
        data.setPostId(key);
        databaseReference.child(key).setValue(data);
        Toast.makeText(MainActivity.this, "Posted!", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private Uri compressImage(Uri uri){
        Uri compressedUri = null;
        try {
            int quality = 70;
            int width = 720;
//            int height = Integer.valueOf(txtHeight.getText().toString());
            File compressed = new Compressor(MainActivity.this)
                    .setMaxWidth(width)
                    .setQuality(quality)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .compressToFile(new File(uri.getPath()));

            compressedUri = Uri.fromFile(compressed);

        } catch (IOException e) {
            e.printStackTrace();
            compressedUri = uri;
        }
        return compressedUri;
    }
}