package practicing.development.pdfmaker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class savedImage extends AppCompatActivity {
    public PdfViewModel ViewModel ;

    ArrayList<String> pathList = (ArrayList<String>) DataHolder.getInstance().path;
    Image image = null;
    Document doc;
    Uri uri;

    PdfDocument pdfDoc = null;
    String mfile = new SimpleDateFormat("asadsdscd", Locale.getDefault()).format(System.currentTimeMillis());
    private  RoomDB db;
    int cnt =0;
    String dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/";
    Bitmap b = null;
    Bitmap bitmap  =null;
    ImageView displayImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_image);

        displayImage = (ImageView)findViewById(R.id.image);
        Bundle bundle = getIntent().getExtras();
// getting bitmap and displaying it in image view
        if(getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, Objects.requireNonNull(getIntent()
                            .getByteArrayExtra("byteArray")).length);

            displayImage.setImageBitmap(b);
            pathList.add(uploadBitmap(this, b));


        }
  }
// getting image path from bitmap
    public String uploadBitmap(Context mContext, Bitmap bitmap) {

        String imagePath = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                imagePath = uri.toString();

                break;
            } while (cursor.moveToNext());
            cursor.close();
        }
    return imagePath;

    }
// method to discard image
    public void backImage(View view) {
   pathList.remove(pathList.size()-1);
   Intent backImage = new Intent(savedImage.this,Camera.class);
    startActivity(backImage);
    }
// create all images to pdf
    public void createPdf(View view) {
       AlertBox();
    }
// add page
    public void addPages(View view) throws MalformedURLException {
        Intent backImage = new Intent(savedImage.this,Camera.class);
        startActivity(backImage);
    }
    public void AlertBox(){
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog, null, false);


         EditText input = (EditText) viewInflated.findViewById(R.id.edt_comment);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        alert.setView(viewInflated);


// Set up the buttons
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

             String YourEditTextValue = input.getText().toString();


                dest += YourEditTextValue+".pdf";
            try {
                image = new Image(ImageDataFactory.create(pathList.get(0)));
                pdfDoc = new PdfDocument(new PdfWriter(dest));
                doc  = new Document(pdfDoc,new PageSize(image.getImageWidth(),image.getImageHeight()));
                for (int i = 0; i <pathList.size(); i++) {
                    image = new Image(ImageDataFactory.create(pathList.get(i)));
                    pdfDoc.addNewPage(new PageSize(image.getImageWidth(), image.getImageHeight()));
                    doc.setBottomMargin(200);
                    image.setFixedPosition(i+1, 0, 0);

                    doc.add(image);
                }
                doc.close();


                Intent intent = new Intent(savedImage.this,showActivity.class);
                intent.putExtra("path", dest);
                startActivity(intent);
            }catch (MalformedURLException | FileNotFoundException e) {
                e.printStackTrace();
            }
            }
        });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

}
