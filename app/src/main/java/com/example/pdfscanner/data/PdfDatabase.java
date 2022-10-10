package com.example.pdfscanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pdfscanner.dao.PdfDao;
import com.example.pdfscanner.model.Pdf;

import java.lang.reflect.Array;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pdf.class}, version = 1,exportSchema = false)
public abstract class PdfDatabase extends RoomDatabase {

   public abstract PdfDao pdfDao();
   private static final int NUMBER_OF_THREADS = 2;
   public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

}
