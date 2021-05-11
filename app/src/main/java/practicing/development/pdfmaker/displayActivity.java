package practicing.development.pdfmaker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class displayActivity extends AppCompatActivity {
    public static final String SAMPLE_FILE = "android_tutorial.pdf"; //your file path
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        path = (String) intent.getExtras().get("path");

        pdfView= (PDFView)findViewById(R.id.pdfView);
        display(path);
    }

    private void display(String FileName) {



        File file = new File( FileName);
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",file);
        pdfView.fromUri(uri)
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen

                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen


                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(false) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
               .spacing(10)
                .load();
        Toast.makeText(this,FileName,Toast.LENGTH_SHORT).show();


    }
    }



