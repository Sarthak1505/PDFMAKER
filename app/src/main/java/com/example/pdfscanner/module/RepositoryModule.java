package com.example.pdfscanner.module;

import com.example.pdfscanner.data.PdfDatabase;
import com.example.pdfscanner.repository.PdfRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
public class RepositoryModule {

    @Provides
    @ViewModelScoped
    PdfRepository providePdfRepository(PdfDatabase pdfDatabase) {
        return new PdfRepository(pdfDatabase);
    }
}
