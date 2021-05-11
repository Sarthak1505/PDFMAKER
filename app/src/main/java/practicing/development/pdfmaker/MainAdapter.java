package practicing.development.pdfmaker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.PdfHolder> {
    private List<Pdfs> pdfsList = new ArrayList<>();
    RoomDB roomDB;
    Context context;
    PdfViewModel pdfViewModel;
    onItemClickedListner listner;
 String view;
    public MainAdapter(Context context,String view) {
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public PdfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_manager, parent, false);

        return new PdfHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PdfHolder holder, int position) {
        if (view.equals("view")) {
            if (pdfsList != null) {
                Pdfs pdfs = pdfsList.get(position);
                holder.setData(pdfs.getFilePath().substring(29));
                holder.shareView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File file = new File(pdfs.getFilePath());

                        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("application/pdf");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        context.startActivity(Intent.createChooser(intent, "Share it"));


                    }
                });
            } else {
                Toast.makeText(context, "NO PDFS CREATED IN THIS APP", Toast.LENGTH_SHORT).show();
            }
        } else {
            Pdfs current = pdfsList.get(position);
            Log.d("Adapter", "message:- " + current);
            holder.textView.setText(current.getFilePath().substring(29));
            holder.shareView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(current.getFilePath());
                    Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(intent, "Share it"));


                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return pdfsList.size();
    }
    public void setPdf(List<Pdfs> pdf){
        pdfsList = pdf;
        notifyDataSetChanged();
    }

    public void update(List<Pdfs> pdfsList) {
        this.pdfsList = pdfsList;

        notifyDataSetChanged();
    }

    public String pathOfFile(Pdfs pdfs) {
        return pdfs.getFilePath();
    }

    public Pdfs position(int position) {
        return pdfsList.get(position);
    }

    class PdfHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView deleteView;
        private ImageView shareView;
        public  void setData(String path){
            textView.setText(path);
        }
        public PdfHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listTextView);
            deleteView = itemView.findViewById(R.id.delete_view);
            shareView = itemView.findViewById(R.id.share_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClicked(pdfsList.get(position));
                    }
                }
            });


        }
    }

    public interface onItemClickedListner {
        void onItemClicked(Pdfs pdfs);
    }

    public void setOnItemClickedListner(onItemClickedListner listner) {
        this.listner = listner;
    }


}
