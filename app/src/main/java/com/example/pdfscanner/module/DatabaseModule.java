package com.example.pdfscanner.module;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pdfscanner.dao.PdfDao;
import com.example.pdfscanner.data.PdfDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    PdfDatabase providePdfDatabase(@ApplicationContext Context applicationContext){
        return Room.databaseBuilder(applicationContext,PdfDatabase.class,"PDF_DATABASE").build();
    }

}
