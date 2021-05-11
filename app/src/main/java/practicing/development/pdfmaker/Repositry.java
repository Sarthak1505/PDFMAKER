package practicing.development.pdfmaker;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repositry {
    private static Dao dao;
    private LiveData<List<Pdfs>> mAllPdfs;
    Repositry(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.Dao();
        mAllPdfs = dao.getData();
    }

    LiveData<List<Pdfs>> getData() {
        return mAllPdfs;
    }

    public void insert (Pdfs pdfs) {
        new insertAsyncTask(dao).execute(pdfs);
    }

 public  void delete(Pdfs pdfs){
        new deleteAsyncTask(dao).execute(pdfs);
 }

    private static class  deleteAsyncTask extends  AsyncTask<Pdfs,Void,Void>{
        private Dao mAsyncTaskDao;
        deleteAsyncTask(Dao dao){
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Pdfs... pdfs) {
            mAsyncTaskDao.delete(pdfs[0]);
            return null;
        }
    }

    private static  class insertAsyncTask extends AsyncTask<Pdfs, Void, Void> {

        private Dao mAsyncTaskDao;


        insertAsyncTask(Dao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Pdfs... pdfs) {
            mAsyncTaskDao.insert(pdfs[0]);

            return null;
        }
    }





    }


