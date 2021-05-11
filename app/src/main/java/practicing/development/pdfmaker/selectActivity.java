package practicing.development.pdfmaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class selectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);




    }
// method for image scan
    public void create(View view) {
        Intent camera = new Intent(this, Camera.class);
        startActivity(camera);

    }
    public void ImageCreate(View view) {
        Intent camera = new Intent(this,Camera.class);
        startActivity(camera);
    }

    // method for viewing pdfs
    public void viewPdf(View view) {
    Intent viewPdf = new Intent(this,showActivity.class);
  viewPdf.putExtra("path","view");
    startActivity(viewPdf);
    }


    public void Pdfview(View view) {
        Intent viewPdf = new Intent(this,showActivity.class);
        viewPdf.putExtra("view","view");
        startActivity(viewPdf);
    }

}
