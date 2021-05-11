package practicing.development.pdfmaker;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao

public interface Dao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pdfs... pdfs);
@Delete
    void delete(Pdfs... pdfs);
@Query("SELECT * FROM PDF_TABLE ORDER BY mid ASC")
    LiveData<List<Pdfs>> getData();
}
