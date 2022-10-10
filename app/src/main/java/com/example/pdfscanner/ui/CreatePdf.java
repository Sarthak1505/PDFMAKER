package com.example.pdfscanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pdfscanner.R;
import com.example.pdfscanner.databinding.ActivityCreatePdfBinding;
import com.example.pdfscanner.model.Pdf;
import com.example.pdfscanner.viewmodel.PdfViewModel;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.websitebeaver.documentscanner.DocumentScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreatePdf extends AppCompatActivity {


    private static final String TAG = "CreatePdf";
    private String pdfName;
    private final float A4_PAGE_HEIGHT = 595;
    private final float A4_PAGE_WIDTH = 842;


    public PdfViewModel pdfViewModel;

    DocumentScanner documentScanner = new DocumentScanner(
            this,
            (croppedImageResults) -> {
                Log.d(TAG, "images path :- " + croppedImageResults);
                createNameDialog(croppedImageResults);
                return null;
            },
            (errorMessage) -> {
                Log.v(TAG, errorMessage);
                return null;
            },
            () -> {
                Log.v(TAG, "Cancelled");
                return null;
            },
            null,
            null,
            null
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        pdfViewModel = new ViewModelProvider(this).get(PdfViewModel.class);
        documentScanner.startScan();
    }

    private void createNameDialog(ArrayList<String> croppedImageResults) {
        final EditText nameText = new EditText(this);
        nameText.setTextColor(this.getResources().getColor(R.color.white));
        AlertDialog nameDialog = new AlertDialog.Builder(this, R.style.PdfNameDialogTheme)
                .setTitle("Enter Name")
                .setMessage("Enter the name of your pdf")
                .setView(nameText)
                .setPositiveButton("Create", null)
                .setNegativeButton("Discard", null).create();

        nameDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                handleAlertBtnClicks(nameDialog, nameText, croppedImageResults);
            }
        });

        nameDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(CreatePdf.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        nameDialog.show();
        nameDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.getResources().getColor(R.color.white));
        nameDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.getResources().getColor(R.color.white));

    }

    private void handleAlertBtnClicks(AlertDialog nameDialog, EditText nameText, ArrayList<String> croppedImageResults) {
        Button positiveBtn = ((AlertDialog) nameDialog).getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEntered = nameText.getText().toString();
                if (nameEntered.length() == 0) {
                    Toast toast = Toast.makeText(CreatePdf.this, "No Pdf Name is entered !!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    pdfName = nameEntered;
                    createPdf(croppedImageResults);
                    nameDialog.dismiss();
                }
            }
        });
        Button negativeBtn = ((AlertDialog) nameDialog).getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // snackbar showing alert also implement cahce
                Intent intent = new Intent(CreatePdf.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    private void createPdf(ArrayList<String> croppedImageResults) {
        try {
            String sourceDirectory = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/";
            Log.d(TAG, "path:- " + sourceDirectory);
            String filePath = sourceDirectory + pdfName + ".pdf";
            Log.d(TAG, filePath);


            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filePath));
            Document document = new Document(pdfDocument, new PageSize(A4_PAGE_WIDTH, A4_PAGE_HEIGHT));

            for (int imageIndex = 0; imageIndex < croppedImageResults.size(); imageIndex++) {
                String imagePath = croppedImageResults.get(imageIndex);
                Image image = new Image(ImageDataFactory.create(imagePath));
                pdfDocument.addNewPage(new PageSize(image.getImageWidth(), image.getImageHeight()));
                document.setBottomMargin(200);
                image.setFixedPosition(imageIndex + 1, 0, 0);
                document.add(image);

                File file = new File(imagePath);
                if (file.exists()) {
                    file.delete();
                }
            }
            document.close();
            pdfViewModel.save(new Pdf(filePath, pdfName));

            Intent intent = new Intent(CreatePdf.this, PreviewActivity.class);
            intent.putExtra("pathOfPdf", filePath);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}