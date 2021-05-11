package practicing.development.pdfmaker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PDF_TABLE")
public class Pdfs {
     @PrimaryKey(autoGenerate = true)
    private int mid;
     @ColumnInfo(name = "path")
    private String FilePath;
     @ColumnInfo(name = "name")
    private String Name;

    public Pdfs(String FilePath) {
        this.FilePath = FilePath;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setmFilePath(String mFilePath) {
        this.FilePath = mFilePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
