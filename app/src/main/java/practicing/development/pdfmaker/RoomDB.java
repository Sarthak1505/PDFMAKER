package practicing.development.pdfmaker;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Pdfs.class, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    public abstract Dao Dao();
    private static RoomDB INSTANCE;

    static RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "pdf_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
      new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };
    private  static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
     private Dao dao;

        public PopulateDbAsyncTask(RoomDB db) {
            dao = db.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//          dao.insert(new Pdfs("TITLE 1"));
//          dao.insert(new Pdfs("TITLE 2"));
          return null;
        }
    }



}
