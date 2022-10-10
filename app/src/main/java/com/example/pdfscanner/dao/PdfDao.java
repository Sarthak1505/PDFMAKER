package com.example.pdfscanner.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pdfscanner.model.Pdf;

import java.util.List;

@Dao
public interface PdfDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void save(Pdf pdf);

   @Delete
   void delete(Pdf pdf);

   @Query("SELECT * FROM PDF_TABLE ORDER BY id")
   LiveData<List<Pdf>> fetchAllPdfs();

}
