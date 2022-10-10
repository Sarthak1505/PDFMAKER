package com.example.pdfscanner.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pdfscanner.dao.PdfDao;
import com.example.pdfscanner.data.PdfDatabase;
import com.example.pdfscanner.model.Pdf;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ViewModelScoped;


public class PdfRepository {


    private PdfDatabase pdfDatabase;

    @Inject
    public PdfRepository(PdfDatabase pdfDatabase){
        this.pdfDatabase = pdfDatabase;
    }


    public void save(Pdf pdf) {

           PdfDatabase.databaseWriterExecutor.execute(() -> {
               Log.d("repo","Pdf saved successfully") ;
               pdfDatabase.pdfDao().save(pdf);
           });


    }


    public void delete(Pdf pdf) {
     PdfDatabase.databaseWriterExecutor.execute(() ->{
         pdfDatabase.pdfDao().delete(pdf);
     });
    }


    public LiveData<List<Pdf>> fetchAllPdfs() {
        return pdfDatabase.pdfDao().fetchAllPdfs();
    }



}
