package com.example.pdfscanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pdfscanner.databinding.ActivityPreviewBinding;

import java.io.File;


public class PreviewActivity extends AppCompatActivity {

    private ActivityPreviewBinding binding;
    private static final String TAG = "PreviewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra("pathOfPdf");
        File pdfFile = new File(filePath);
        binding.pdfView.fromFile(pdfFile)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)

                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(false) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .spacing(10)
                .load();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}