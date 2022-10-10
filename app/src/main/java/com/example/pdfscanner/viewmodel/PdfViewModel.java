package com.example.pdfscanner.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pdfscanner.model.Pdf;
import com.example.pdfscanner.repository.PdfRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PdfViewModel extends AndroidViewModel {


   private PdfRepository repository;

   private LiveData<List<Pdf>> pdfLiveData;

    @Inject
    public PdfViewModel(@NonNull Application application, PdfRepository repository) {
        super(application);
        this.repository = repository;
    }


    public void save(Pdf pdf) {
        repository.save(pdf);
    }

    public void delete(Pdf pdf) {

        repository.delete(pdf);


    }

    public LiveData<List<Pdf>> fetchAllPdfs() {
      //  Log.d("view model" , repository.fetchAllPdfs().getValue().toString());
           pdfLiveData = repository.fetchAllPdfs();

           return pdfLiveData;
    }




}
