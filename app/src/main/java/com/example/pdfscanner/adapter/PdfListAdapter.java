package com.example.pdfscanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdfscanner.BuildConfig;
import com.example.pdfscanner.R;
import com.example.pdfscanner.model.Pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.pdfListViewHolder> {
    private static final String TAG = "PdfListAdapter";
    private List<Pdf> pdfList;
    private final Context context;
    private final onItemClickListener onItemClickListener;

    public PdfListAdapter(Context context, onItemClickListener onItemClickListener) {
        this.context = context;
        pdfList = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public pdfListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list_item, parent, false);
        return new pdfListViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull pdfListViewHolder holder, int position) {
        if (!pdfList.isEmpty()) {
            Pdf pdf = pdfList.get(position);
            Log.d("adapter", pdf.getPdfName());
            String pdfName = pdf.getPdfName() + ".pdf";
            holder.pdfNameView.setText(pdfName);

            holder.moreMenuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.openPopUpMenu(view);
                }
            });


        } else {
            Toast.makeText(context, "No pdf file present !!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public void setPdfList(List<Pdf> pdfs) {
        pdfList = pdfs;
        notifyDataSetChanged();
    }

    public class pdfListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "pdfListViewHolder";
        private final TextView pdfNameView;
        private final ImageView moreMenuView;
        private final onItemClickListener onItemClickListener;

        public pdfListViewHolder(@NonNull View itemView, onItemClickListener onItemClickListener) {
            super(itemView);
            pdfNameView = itemView.findViewById(R.id.pdf_name);
            moreMenuView = itemView.findViewById(R.id.more_menu);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(getAdapterPosition());
        }


        private void openPopUpMenu(View view) {
            PopupMenu pdfListPopUpMenu = new PopupMenu(view.getContext(), view);
            pdfListPopUpMenu.inflate(R.menu.pdf_list_popup_menu);
            pdfListPopUpMenu.setOnMenuItemClickListener(this);
            pdfListPopUpMenu.show();

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_menu_item:
                    deletePdf(getAdapterPosition());
                    return true;
                case R.id.share_menu_item:
                    sharePdf(getAdapterPosition());
                    Log.d(TAG, "share menu item clicked!");
                    return true;
                default:
                    Log.d(TAG, "invalid menu item clicked!");
                    return false;
            }
        }
    }

    public interface onItemClickListener {
        void onItemClicked(int position);

        void onItemDeleted(int position);
    }

    private void sharePdf(int position) {
        String filePath = providePdf(position).getPdfFilePath();
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            Log.d(TAG, "file exist");
            Uri pdfUri = FileProvider
                    .getUriForFile(context,
                            "com.example.pdfscanner.fileprovider",
                            pdfFile);

            Intent sharePdfIntent = new Intent(Intent.ACTION_SEND);
            sharePdfIntent.setType("application/pdf");
            sharePdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharePdfIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            context.startActivity(Intent.createChooser(sharePdfIntent, "Share this pdf file!!"));
        } else {
            Log.e(TAG, "File not exist");
        }
    }

    private void deletePdf(int position) {
//        pdfList.remove(position);
        onItemClickListener.onItemDeleted(position);
        notifyDataSetChanged();
    }

    public Pdf providePdf(int position) {
        return pdfList.get(position);
    }


}
