package practicing.development.pdfmaker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PdfViewModel extends AndroidViewModel {
    public Repositry mRepository;

    public LiveData<List<Pdfs>> mAllPdf;

    public PdfViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repositry(application);
        mAllPdf = mRepository.getData();
    }
    LiveData<List<Pdfs>> getData() { return mAllPdf; }

    public void insert(Pdfs pdfs) { mRepository.insert(pdfs);
    }

    public void  delete(Pdfs pdfs){
        mRepository.delete(pdfs);
    }
}
