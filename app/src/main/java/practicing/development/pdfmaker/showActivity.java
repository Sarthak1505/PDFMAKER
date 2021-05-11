package practicing.development.pdfmaker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class showActivity extends AppCompatActivity {
 LinearLayoutManager linearLayoutManager;
    public PdfViewModel viewModel ;

 ImageView deleteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
       Intent intent = getIntent();
        Log.d("TAG", "onCreate: "+intent.toString());
       if(intent.getExtras().getString("path").equals("view")) {
      RecyclerView recyclerView = findViewById(R.id.recycler_view);
      linearLayoutManager = new LinearLayoutManager(showActivity.this);
      recyclerView.setLayoutManager(linearLayoutManager);
      recyclerView.setHasFixedSize(true);
   MainAdapter viewAdapter = new MainAdapter(this,"view");
   recyclerView.setAdapter(viewAdapter);
      viewModel = new ViewModelProvider(this).get(PdfViewModel.class);
      viewModel.getData().observe(this, new Observer<List<Pdfs>>() {
          @Override
          public void onChanged(List<Pdfs> pdfsList) {
             viewAdapter.setPdf(pdfsList);
          }
      });
           new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
               @Override
               public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                   return false;
               }

               @Override
               public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                   viewModel.delete(viewAdapter.position(viewHolder.getAdapterPosition()));
               }
           }).attachToRecyclerView(recyclerView);
           viewAdapter.setOnItemClickedListner(new MainAdapter.onItemClickedListner() {
               @Override
               public void onItemClicked(Pdfs pdfs) {
                  ArrayList<String> path = new ArrayList<>();
                  path.add(viewAdapter.pathOfFile(pdfs));
                   Intent intent1 = new Intent(showActivity.this, displayActivity.class);
                   intent1.putExtra("path", path.get(0));
                   startActivity(intent1);
               }
           });
       }

else {
      final String[] path = {intent.getExtras().getString("path")};
      Log.d("pdfTag", "message:- " + path.length + " " + path[0]);

      RecyclerView recyclerView = findViewById(R.id.recycler_view);
      linearLayoutManager = new LinearLayoutManager(showActivity.this);
      recyclerView.setLayoutManager(linearLayoutManager);

      recyclerView.setHasFixedSize(true);
      MainAdapter adapter = new MainAdapter(showActivity.this,intent.getExtras().getString("path"));
      recyclerView.setAdapter(adapter);
      viewModel = new ViewModelProvider(this).get(PdfViewModel.class);

      viewModel.getData().observe(this, new Observer<List<Pdfs>>() {
          @Override
          public void onChanged(List<Pdfs> pdfs) {
              // updation in recycler view


              adapter.update(pdfs);

          }
      });
      Pdfs pdfs1 = new Pdfs(path[0]);
      viewModel.insert(pdfs1);

      new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
              return false;
          }

          @Override
          public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

              viewModel.delete(adapter.position(viewHolder.getAdapterPosition()));
          }
      }).attachToRecyclerView(recyclerView);
      adapter.setOnItemClickedListner(new MainAdapter.onItemClickedListner() {
          @Override
          public void onItemClicked(Pdfs pdfs) {
              path[0] = adapter.pathOfFile(pdfs);
              Intent intent1 = new Intent(showActivity.this, displayActivity.class);
              intent1.putExtra("path", path[0]);
              startActivity(intent1);
          }
      });

  }

    }


}