package practicing.development.pdfmaker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(MainActivity.this,selectActivity.class );
        Thread thread = new Thread() {
            public void run() {
                try {

                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                }
            }
        };
        thread.start();

    }
}
