package com.example.pdfscanner.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.pdfscanner.adapter.PdfListAdapter;
import com.example.pdfscanner.databinding.ActivityHomeScreenBinding;
import com.example.pdfscanner.model.Pdf;
import com.example.pdfscanner.viewmodel.PdfViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeScreen extends AppCompatActivity implements PdfListAdapter.onItemClickListener {
    private static final String TAG = "HomeScreen";
    private ActivityHomeScreenBinding binding;
    private final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
    private final int PERMISSION_REQUEST_CODE = 0;
    private PdfViewModel pdfViewModel;
    private PdfListAdapter pdfListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        handleClicks();
        populateRecyclerView();
    }

    private void init() {
        pdfViewModel = new ViewModelProvider(this).get(PdfViewModel.class);
        pdfListAdapter = new PdfListAdapter(this, this);
    }

    private void handleClicks() {
        binding.createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked");
                if (ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    moveToActivity();
                } else {
                    requestForPermissions();

                }
            }
        });
    }

    private void moveToActivity() {
        Intent intent = new Intent(HomeScreen.this, CreatePdf.class);
        startActivity(intent);
    }

    private void requestForPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(HomeScreen.this, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(HomeScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(HomeScreen.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            permissionsDialog("Grant the permissions to use the app", permissions, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(HomeScreen.this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void permissionsDialog(String permissionMessage, String[] permission, int code) {

        new AlertDialog.Builder(HomeScreen.this)
                .setTitle("Permissions Needed")
                .setMessage(permissionMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(HomeScreen.this, permission, code);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .create().show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                moveToActivity();
            } else {
                Toast.makeText(this, "Permissions Denied!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void populateRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.pdfList.setLayoutManager(gridLayoutManager);
        binding.pdfList.setAdapter(pdfListAdapter);

        pdfViewModel.fetchAllPdfs().observe(this, new Observer<List<Pdf>>() {
            @Override
            public void onChanged(List<Pdf> pdfs) {
                if (pdfs != null) {
                    pdfListAdapter.setPdfList(pdfs);
                }
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        String path = pdfListAdapter.providePdf(position).getPdfFilePath();
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("pathOfPdf", path);
        startActivity(intent);
    }

    @Override
    public void onItemDeleted(int position) {
        Pdf deletePdf = pdfListAdapter.providePdf(position);
        Log.d(TAG,"item deleted");
        pdfViewModel.delete(deletePdf);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}