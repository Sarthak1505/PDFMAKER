package practicing.development.pdfmaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ListenActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private TextView textView;
    String path = "";
    String dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + " .pdf";
    private static final String PRIMARY = "primary";
    private static final String LOCAL_STORAGE = "/storage/self/primary/";
    private static final String EXT_STORAGE = "/storage/7764-A034/";
    private static final String COLON = ":";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView6);
        textView.setMovementMethod(new ScrollingMovementMethod());
        Cursor cursor = null;
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.UK);
            }
        });
    }

    public void opennPDF(View view) {

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, 1000);
     setVisible(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == 1000) && (resultCode == RESULT_OK)) {
            path = data.getDataString();
            if (path.contains(".pdf")) {
                path = path.substring(16);
                Log.i("path",path);
            }
            try {
                readPdfFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

        public void readPdfFile (String path) throws IOException {
            Log.i("methodcalled","method called ");
             String fullPath;
            //convert from uri to full path
//        if (uri.getPath().contains(PRIMARY)) {
//            fullPath = LOCAL_STORAGE + uri.getPath().split(COLON)[1];
//        } else {
//            fullPath = EXT_STORAGE + uri.getPath().split(COLON)[1];
//        }
//        Log.v("URI", uri.getPath() + " " + fullPath);
            String stringParser;
                PdfReader pdfReader = new PdfReader(path);
                PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));

                int pages = pdfDoc.getNumberOfPages();
                for (int i = 1; i <= pages; i++) {
                    PdfPage page = pdfDoc.getPage(i);
                    stringParser = PdfTextExtractor.getTextFromPage(page).trim();
                    pdfReader.close();
                    textView.setText(stringParser);

             }
            textToSpeech.speak("HI THERE", TextToSpeech.QUEUE_FLUSH, null, null);
    }



}
