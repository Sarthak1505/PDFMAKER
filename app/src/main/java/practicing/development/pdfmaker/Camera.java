package practicing.development.pdfmaker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Camera extends AppCompatActivity {
ImageView imageView;
    Uri imageUri;
   static Bitmap bitmap = null;
    final int REQUEST_CODE = 1000;
    final int IMAGE_CAPTURE_CODE = 1001;
    public String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        requestPermissions(permission, REQUEST_CODE);
    }
// checking permission to open camera
    public void Capture(View view) {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                &&(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            openCamera();
        } else {
            Toast.makeText(this, "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show();
            requestPermissions(permission, REQUEST_CODE);
        }
    }
    // checking permission to open galllery
    public void openfromGallery(View view) {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                &&(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))
        {
        openGallery();
        }
     else {
            Toast.makeText(this, "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show();
            requestPermissions(permission, REQUEST_CODE);
        }
    }
// open camera
int REQUEST_CODE_INTENT = 99;
    private void openCamera() {
//        ScannerConstants.selectedImageBitmap=btimap
//        startActivityForResult(Intent(MainActivity@this,  ImageCropActivity::class.java),
//        SyncStateContract.Constants._COUNT);

        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE_INTENT);

    }
    // select from gallery
    public void openGallery(){

        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent,REQUEST_CODE_INTENT);
    }

// converting captured image into bitmap and sending it to savedimage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);

            try {
         bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
         getContentResolver().delete(uri, null, null);
                bitmap = RotateBitmap(bitmap,90);
         Intent intent = new Intent(Camera.this, savedImage.class);
         ByteArrayOutputStream bs = new ByteArrayOutputStream();

         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bs);

         getImageUri(Camera.this);
         intent.putExtra("byteArray", bs.toByteArray());
         startActivity(intent);

     }catch (IOException e){
         e.printStackTrace();
     }

        }


    }

    public void getImageUri(Context inContext) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  Bitmap inImage = bitmap;
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Intent sendPath = new Intent(Camera.this,savedImage.class);
        sendPath.putExtra("imagePath",path);
        startActivity(sendPath);
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }



}
